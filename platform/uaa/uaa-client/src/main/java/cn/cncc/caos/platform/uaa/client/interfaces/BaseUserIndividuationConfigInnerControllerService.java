package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserIndividuationConfig;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BaseUserIndividuationConfigInnerControllerService {
    /**
     * @Description 根据用户唯一id和查询模块儿id查询用户指定配置.
     * @Param userId 用户唯一id.
     * @Param moduleId 模块儿唯一id.
     * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
     **/
    public JwResponseData<BaseUserIndividuationConfig> getUserIndividuationConfigOneByUserId(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "moduleId") String moduleId);

    /**
     * @Description 根据用户realName和查询模块儿id查询用户指定配置.
     * @Param realName 用户realName.
     * @Param moduleId 模块儿唯一id.
     * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
     **/
    public JwResponseData<BaseUserIndividuationConfig> getUserIndividuationConfigOneByRealName(@RequestParam(value = "realName") String realName, @RequestParam(value = "moduleId") String moduleId);

    /**
     * @Description 根据登录用户realName列表和查询模块儿id查询用户指定配置.
     * @Param realNames 用户realName列表(使用","分割).
     * @Param moduleId 模块儿唯一id.
     * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
     **/
    public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByRealNames(@RequestParam(value = "realNames") String realNames, @RequestParam(value = "moduleId") String moduleId);

    /**
     * @Description 根据用户id列表和查询模块儿id查询用户指定配置.
     * @Param userIds 用户userIds列表(list的json串).
     * @Param moduleId 模块儿唯一id.
     * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
     **/
    public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByIds(@RequestParam(value = "userIds") String userIds, @RequestParam(value = "moduleId") String moduleId);

    /**
     * @Description 根据部门id和查询模块儿id查询用户指定配置.
     * @Param depId 部门id.
     * @Param moduleId 模块儿唯一id.
     * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
     **/
    public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByDepId(@RequestParam(value = "depId") Integer depId, @RequestParam(value = "moduleId") String moduleId);


}
