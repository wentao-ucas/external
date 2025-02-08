package cn.cncc.caos.external.provider.cloud.service;

import cn.cncc.caos.common.core.response.BasePageRes;
import cn.cncc.caos.common.core.enums.RecordStatus;
import cn.cncc.caos.common.core.utils.IDUtil;
import cn.cncc.caos.common.core.utils.ModelMapperUtil;
import cn.cncc.caos.external.provider.cloud.model.FieldParseResult;
import cn.cncc.caos.external.provider.cloud.service.http.DeferResultService;
import cn.cncc.caos.external.provider.cloud.service.http.HttpClientFutureCallback;
import cn.cncc.caos.external.provider.cloud.service.http.HttpClientService;
import cn.cncc.caos.external.client.cloud.api.ICloudTaskCfgService;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto;
import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskInstStatusEnum;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskStatusEnum;
import cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskCfgDynamicSqlSupport;
import cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskCfgMapper;
import cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskLogMapper;
import cn.cncc.caos.external.provider.cloud.db.pojo.CloudTaskCfg;
import cn.cncc.caos.external.provider.cloud.db.pojo.CloudTaskLog;
import cn.cncc.caos.external.provider.cloud.util.TaskResultParserUtil;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

/**
 * @className: CloudTaskCfgService
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 14:45
 */
@Service
@Slf4j
@MojitoSchema(schemaId = "inner_api/cloudTaskCfgService")
public class CloudTaskCfgServiceImpl implements ICloudTaskCfgService {

    @Autowired
    private CloudTaskCfgMapper cloudTaskCfgMapper;

    @Autowired
    private CloudTaskLogMapper cloudTaskLogMapper;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private DeferResultService deferResultService;

    @Autowired
    private CloudAuthServiceImpl cloudAuthService;

    @Autowired
    private TaskResultParserUtil taskResultParserUtil;
    @Value("${cloud.request.maxCallNum}")
    int maxCallNum;
    @Value("${cloud.request.waitTime}")
    String waitTime;

    @Override
    public List<CloudTaskCfgDto> getCloudTaskCfgListByStatus(CloudTaskStatusEnum statusEnum, String deptNo) {
        List<CloudTaskCfg> cloudTaskCfgList = cloudTaskCfgMapper.selectByExample()
                .where(CloudTaskCfgDynamicSqlSupport.status,isEqualTo(statusEnum.getStatus()))
                .where(CloudTaskCfgDynamicSqlSupport.deptno,isEqualTo(deptNo)).build().execute();
        List<CloudTaskCfgDto> cloudTaskCfgDtoList = ModelMapperUtil.mapList(cloudTaskCfgList, CloudTaskCfgDto.class);
        return cloudTaskCfgDtoList;
    }

    @Override
    public BasePageRes<CloudTaskCfgDto> getAllCloudTaskCfgPage(Integer pageNum, Integer pageSize, String deptNo) {
        // 启动分页
        PageHelper.startPage(pageNum, pageSize);

        // 根据部门号查询云任务配置
        List<CloudTaskCfg> cloudTaskCfgList = cloudTaskCfgMapper.selectByExample()
                .where(CloudTaskCfgDynamicSqlSupport.deptno, isEqualTo(deptNo))
                .build()
                .execute();

        // 使用 PageInfo 来获取分页信息
        PageInfo<CloudTaskCfg> pageInfo = new PageInfo<>(cloudTaskCfgList, pageSize);

        // 将查询结果转换为 DTO 对象列表
        List<CloudTaskCfgDto> cloudTaskCfgDtoList = ModelMapperUtil.mapList(cloudTaskCfgList, CloudTaskCfgDto.class);

        // 返回分页结果
        return new BasePageRes<>(pageInfo.getTotal(), cloudTaskCfgDtoList);
    }


    @Override
    public List<CloudTaskCfgDto> getAllCloudTaskCfg(String deptNo) {
        List<CloudTaskCfg> cloudTaskCfgList = cloudTaskCfgMapper.selectByExample()
                .where(CloudTaskCfgDynamicSqlSupport.deptno,isEqualTo(deptNo)).build().execute();
        List<CloudTaskCfgDto> cloudTaskCfgDtoList = ModelMapperUtil.mapList(cloudTaskCfgList, CloudTaskCfgDto.class);
        return cloudTaskCfgDtoList;
    }

    @Override
    public CloudTaskCfgDto getCloudTaskCfgDetailsById(String taskId) {
        CloudTaskCfg cloudTaskCfg = cloudTaskCfgMapper.selectByPrimaryKey(taskId);
        return ModelMapperUtil.map(cloudTaskCfg, CloudTaskCfgDto.class);
    }

    @Override
    public List<CloudTaskLogDto> synStartCloudTask(List<String> taskLists, String deptNo,String operator) {
        List<CloudTaskLogDto> cloudTaskCfgDtoList = new ArrayList<>();
        for (String taskId: taskLists){
            CloudTaskCfg cloudTaskCfg = cloudTaskCfgMapper.selectByPrimaryKey(taskId);
            if (cloudTaskCfg == null){
                log.warn("cannot find CloudTask with {}", taskId);
                continue;
            }
            try {
                CloudTaskLog cloudTaskLog = executeCloudTask(cloudTaskCfg, deptNo, operator);
                cloudTaskCfgDtoList.add(ModelMapperUtil.map(cloudTaskLog, CloudTaskLogDto.class));
            } catch (Exception e) {
                log.error("Failed to start CloudTaskCfg with {},err={}",taskId,e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return cloudTaskCfgDtoList;
    }
    private CloudTaskLog executeCloudTask(CloudTaskCfg taskCfg, String deptNo,String operator) throws Exception {
        CloudTaskLog cloudTaskLog = new CloudTaskLog();
        cloudTaskLog.setTaskid(taskCfg.getTaskid());
        cloudTaskLog.setDeptno(deptNo);
        cloudTaskLog.setOperator(operator);
        cloudTaskLog.setStatus(RecordStatus.NORMAL.getStatus());
        cloudTaskLog.setTaskinststatus(CloudTaskInstStatusEnum.RUNNING.getStatus());
        cloudTaskLog.setTaskinstid(IDUtil.buildUUID());

        int insertResult = cloudTaskLogMapper.insert(cloudTaskLog);
        if (insertResult != 1){
            log.error("insert data into CloudTaskLog failed");
            throw new Exception("insert data into CloudTaskLog failed");
        }
        String token;
        if (cloudAuthService.isValid(taskCfg.getAuthid())){
            TokenCacheItem tokenCacheItem = cloudAuthService.getToken(taskCfg.getAuthid());
            token = tokenCacheItem.getHuaweitoken();
        }else{
            log.error("Exception occurred while obtaining valid token");
            throw new Exception("Exception occurred while obtaining valid token");
        }
        String stepId = IDUtil.getSimpleID();
        final CompletableFuture<Object> deferredResult = new CompletableFuture<Object>();
        deferResultService.putDeferredResult(stepId,deferredResult);
        String httpResult = doHttpRequest(stepId,token,taskCfg);
        deferResultService.delDeferredResultById(stepId);
        if(httpResult.startsWith("ERROR:")){
            cloudTaskLog.setTaskinststatus(CloudTaskInstStatusEnum.FAIL.getStatus());
        }else{
            cloudTaskLog.setTaskinststatus(CloudTaskInstStatusEnum.SUCC.getStatus());
            List<FieldParseResult> fieldParseResults = taskResultParserUtil.ParseHttpResult(cloudTaskLog.getTaskinstid(), httpResult,taskCfg.getParserule());
            cloudTaskLog.setParseresult(fieldParseResults.toString());
        }
        cloudTaskLog.setResult(httpResult);
        cloudTaskLog.setEndtime(new Date());

        cloudTaskLogMapper.updateByPrimaryKeySelective(cloudTaskLog);

        return cloudTaskLog;
    }
    private String doHttpRequest(String stepId,String token ,CloudTaskCfg taskCfg){
        Map<String, String> requestHeader = new HashMap<String, String>(){
            {
                put("X-Auth-Token", token);
            }
        };
        try {
            if (taskCfg.getReqtype().equals(HttpMethod.PUT.name())){
                httpClientService.doAsycPut(stepId,taskCfg.getUrl(),requestHeader,taskCfg.getParam(),
                        new HttpClientFutureCallback(stepId,taskCfg.getUrl(),requestHeader,taskCfg.getParam(),1,maxCallNum,HttpMethod.PUT.name()));
            } else if (taskCfg.getReqtype().equals(HttpMethod.POST.name())) {
                httpClientService.doAsycPost(stepId,taskCfg.getUrl(),requestHeader,taskCfg.getParam(),
                        new HttpClientFutureCallback(stepId,taskCfg.getUrl(),requestHeader,taskCfg.getParam(),1,maxCallNum,HttpMethod.POST.name()));
            }else if (taskCfg.getReqtype().equals(HttpMethod.GET.name())){
                httpClientService.doAsycGet(stepId,taskCfg.getUrl(),requestHeader,
                        new HttpClientFutureCallback(stepId,taskCfg.getUrl(),requestHeader,null,1,maxCallNum,HttpMethod.GET.name()));
            }else if (taskCfg.getReqtype().equals(HttpMethod.DELETE.name())){
                httpClientService.doAsycDelete(stepId,taskCfg.getUrl(),requestHeader,
                        new HttpClientFutureCallback(stepId,taskCfg.getUrl(),requestHeader,null,1,maxCallNum,HttpMethod.DELETE.name()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求华为云异常:",e);
            return "ERROR:请求华为云"+taskCfg.getUrl()+"异常";
        }

        CompletableFuture deferredResult = deferResultService.getCompletableFutureById(stepId);
        if (null == deferredResult) {
            log.error("从result map中未匹配到msgID: " + stepId);
            return "ERROR:从result map中未匹配到msgID" + stepId;
        }

        try {
            return (String) deferredResult.get(Long.parseLong(waitTime), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("从华为云获取响应失败:",e);
            return "ERROR:InterruptedException";
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("从华为云获取响应失败:",e);
            return "ERROR:ExecutionException";
        } catch (TimeoutException e) {
            e.printStackTrace();
            log.error("从华为云获取响应失败:",e);
            return "ERROR:TimeoutException";
        }
    }

    @Override
    public boolean addCloudTask(CloudTaskCfgDto cloudTaskCfgDto) {
        CloudTaskCfg cloudTaskCfg = ModelMapperUtil.map(cloudTaskCfgDto, CloudTaskCfg.class);
        int result = cloudTaskCfgMapper.insert(cloudTaskCfg);
        return result == 1;
    }
}
