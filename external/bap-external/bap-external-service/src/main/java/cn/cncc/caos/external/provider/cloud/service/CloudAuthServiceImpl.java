package cn.cncc.caos.external.provider.cloud.service;

import cn.cncc.caos.common.core.enums.PubParamTypeEnum;
import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.StringUtil;
import cn.cncc.caos.data.client.BaseDataClient;
import cn.cncc.caos.data.client.pubparam.api.IPubParamService;
import cn.cncc.caos.data.client.pubparam.dto.PubParamDto;
import cn.cncc.caos.external.client.cloud.api.ICloudAuthService;
import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import cn.cncc.caos.external.client.cloud.vo.CloudAuthParamVO;
import cn.cncc.caos.external.provider.cloud.model.auth.AuthWrap;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.opm.OpmAuth;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.Domain;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.Identity;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.OpsAuthBody;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.OpsAuth;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.Password;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.Project;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.Scope;
import cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops.User;

import cn.cncc.caos.common.core.enums.CloudPlaneTypeEnum;
import cn.cncc.caos.common.core.exception.BapParseException;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
@Slf4j
@MojitoSchema(schemaId = "inner_api/cloudAuthService")
public class CloudAuthServiceImpl implements ICloudAuthService {
    private static String REDIS_KEY = "CloudAuthKey";
    private Map<String, TokenCacheItem> tokenmap = new HashMap<>();

    @Autowired
    private RestTemplate httpsRestTemplate;
    @Autowired
    private BaseDataClient baseDataClient;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Author dengjq
     * @Description 根据authId获取Token缓存对象，该对象包括token值，过期时间，token生成时间等属性
     * @version: v1.0.0
     * @Date 12:34 2024/11/11
     * @Param [authId]
     * @return cn.cncc.caos.bap.external.client.cloud.dto.TokenCacheItem
     **/
    public TokenCacheItem getToken(String authId){
        //return getTokenCacheItemFromRedis(authId);
        if (tokenmap.containsKey(authId)){
            return tokenmap.get(authId);
        }
        return null;
    }


    private TokenCacheItem getTokenCacheItemFromRedis(String authId){
        try{
            log.info("getTokenCacheItemFromRedis start");
            BoundHashOperations hashKey = redisTemplate.boundHashOps(REDIS_KEY);
            String tokenItemString = (String) hashKey.get(authId);
            if (StringUtil.isEmpty(tokenItemString)) {
                throw new Exception("无对应此平台信息: appSecret-" + authId);
            }
            TokenCacheItem tokenCacheItem = JacksonUtil.jsonToObj(tokenItemString, TokenCacheItem.class);
            return tokenCacheItem;
        }catch (BapParamsException es){
            log.error("", es);
        }catch (Exception e){
            log.error("", e);
        }
        return null;
    }
    private void putTokenCacheItemToRedis(String authId,TokenCacheItem tokenCacheItem ){
        try{
            BoundHashOperations hashKey = redisTemplate.boundHashOps(REDIS_KEY);
            hashKey.put(authId, JacksonUtil.objToJson(tokenCacheItem));
        }catch (JsonProcessingException e) {
            log.error("fail to convert to json,authId={},{}",authId,e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CloudAuthParamVO> getCloudAuthList() {
        List<CloudAuthParamVO> cloudAuthParamVOList = new ArrayList<>();
        IPubParamService pubParamService = baseDataClient.getPubParamService();
        List<PubParamDto> pubParamDtoList = pubParamService.getParamlistByType(PubParamTypeEnum.HWCLOUD_AUTH.getType());
        for (PubParamDto pubParamDto: pubParamDtoList){
            CloudAuthParamVO cloudAuthParamVO = new CloudAuthParamVO();
            cloudAuthParamVO.setAuthId(pubParamDto.getParmid());
            cloudAuthParamVO.setAuthName(pubParamDto.getParmname());
            cloudAuthParamVO.setAuthDesc(pubParamDto.getParmdesc());
            cloudAuthParamVOList.add(cloudAuthParamVO);
        }
        return cloudAuthParamVOList;
    }

    /**
     * @Author dengjq
     * @Description 根据authId获取AuthWrap对象，AuthWrap封装认证参数对象
     * @version: v1.0.0
     * @Date 14:39 2024/11/8
     * @Param [authId]
     * @return cn.cncc.caos.bap.external.provider.cloud.model.auth.AuthWrap
     **/

    private AuthWrap getAuthWrapFromId(String authId) throws Exception {
        PubParamDto pubParamDto = baseDataClient.getPubParamService().getParamDetailById(authId);
        if (pubParamDto == null){
            String errInfo = String.format("cann't get auth record with %s from data-service", authId);
            log.error(errInfo);
            throw new Exception(errInfo);
        }
        Map<String, String> authParamMap = JacksonUtil.jsonToObj(pubParamDto.getParmval(),HashMap.class,String.class,String.class);
        AuthWrap authWrap = new AuthWrap();
        if (authParamMap.containsKey("projectname")){ //运营面有projectname参数
            String userName = authParamMap.get("username");
            String userPassword = authParamMap.get("userpassword");
            String domainName = authParamMap.get("domainname");
            String projectName = authParamMap.get("projectname");
            String url = authParamMap.get("url");

            OpsAuth opsAuth = new OpsAuth();
            OpsAuthBody opsAuthBody = new OpsAuthBody();
            Identity identity = new Identity();
            Password pwd = new Password();
            User user = new User();
            Domain domain = new Domain();

            domain.setName(domainName);
            user.setName(userName);
            user.setPassword(userPassword);
            user.setDomain(domain);
            pwd.setUser(user);
            identity.setPassword(pwd);
            Project project = new Project();
            project.setName(projectName);
            Scope scope = new Scope();
            scope.setProject(project);
            opsAuthBody.setIdentity(identity);
            opsAuthBody.setScope(scope);
            opsAuth.setAuth(opsAuthBody);

            authWrap.setAuth(opsAuth);
            authWrap.setUrl(url);

        }else{  //运维面
            String grantType = authParamMap.get("grantType");
            String userName = authParamMap.get("userName");
            String value = authParamMap.get("value");
            String url = authParamMap.get("url");
            OpmAuth opmAuth = new OpmAuth(grantType, userName, value);
            authWrap.setAuth(opmAuth);
            authWrap.setUrl(url);
        }

        return authWrap;
    }


    /**
     * @Author dengjq
     * @Description 云平台认证授权
     * @version: v1.0.0
     * @Date 13:51 2024/11/8
     * @Param [authWrap, authId]
     * @return void
     **/
    private void auth(AuthWrap authWrap, String authId) throws Exception {
        HttpEntity httpEntity = getHttpEntity(authWrap.getAuth(), null);;
        HttpMethod httpMethod = HttpMethod.POST;
        if(authWrap.getAuth() instanceof OpmAuth){
            httpMethod = HttpMethod.PUT;
        }

        //请求获取token
        long currentTime = System.currentTimeMillis();
        ResponseEntity<Map> authResponse = null;
        try {
            authResponse = httpsRestTemplate.exchange(authWrap.getUrl(), httpMethod, httpEntity, Map.class);
        } catch (RestClientException e) {
            log.error("获取token错!",e);
            throw new Exception(e.getMessage());
        }

        TokenCacheItem tokenCacheItem = new TokenCacheItem();
        tokenCacheItem.setTimestamp(currentTime);
        log.debug("Timestamp={}",currentTime);
        //获取token
        String token = "unknown";
        if (authWrap.getAuth() instanceof OpsAuth){
            token = authResponse.getHeaders().get("X-Subject-Token").get(0);
        }else{
            if (authResponse.hasBody() && authResponse.getBody().containsKey("accessSession")){
                token = (String)authResponse.getBody().get("accessSession");
                int expires = (int)(authResponse.getBody().get("expires"));
                tokenCacheItem.setExpires(expires);
                tokenCacheItem.setRoaRand((String)authResponse.getBody().get("roaRand"));
            }else{
                String errInfo = String.format("Get %s token failed", CloudPlaneTypeEnum.HWCLOUD_OPM.getValue());
                log.error(errInfo);
                throw new BapParseException(errInfo) ;
            }
        }
        tokenCacheItem.setHuaweitoken(token);
        log.debug("Huaweitoken={}",token);
        tokenmap.put(authId,tokenCacheItem);
        //如果启用redis缓存，取消注释
        //putTokenCacheItemToRedis(authId, tokenCacheItem);
    }


    private HttpEntity getHttpEntity(Object params, String token) {
        HttpHeaders HttpHeaders=new HttpHeaders();
        HttpHeaders.add("Content-Type", "application/json");
        HttpHeaders.add("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            HttpHeaders.add("X-Auth-Token", token);
        }
        return new HttpEntity(params,HttpHeaders);
    }

    @Override
    public synchronized boolean isValid(String authId){
        log.debug("isValid begin, authId={}",authId);
        //使用redis缓存
        /*
        TokenCacheItem tokenCacheItem = getTokenCacheItemFromRedis(authId);
        if (tokenCacheItem != null){
            if(tokenCacheItem.getTimestamp() != -1){
                long time = tokenCacheItem.getTimestamp();
                long currentTime = System.currentTimeMillis();
                long expires = 22*60*60*1000;
                if (tokenCacheItem.getExpires() != -1){
                    expires = tokenCacheItem.getExpires() * 1000;
                }
                if(currentTime-time < expires){
                    return true;
                }
            }
        }*/
        if(tokenmap != null && tokenmap.containsKey(authId)){
            TokenCacheItem tokenCacheItem = tokenmap.get(authId);
            if(tokenCacheItem.getTimestamp() != -1){
                long time = tokenCacheItem.getTimestamp();
                long currentTime = System.currentTimeMillis();
                long expires = 22*60*60*1000;
                if (tokenCacheItem.getExpires() != -1){
                    expires = tokenCacheItem.getExpires() * 1000;
                }
                if(currentTime-time < expires){
                    return true;
                }
            }
        }
        try {
            auth(getAuthWrapFromId(authId), authId);
        } catch (Exception e) {
            log.debug("Token认证失败," + e.getLocalizedMessage());
            return false;
        }
        log.debug("Token认证完成,未见异常");
        return true;
    }
}
