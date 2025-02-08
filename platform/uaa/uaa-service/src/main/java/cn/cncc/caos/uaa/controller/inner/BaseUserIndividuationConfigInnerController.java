package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.db.pojo.BaseUserIndividuationConfig;
import cn.cncc.caos.uaa.service.BaseUserIndividuationConfigService;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 用户个性化配置请求处理入口.
 * ---用户是否接收短信(细化到模块儿)
 * ---用户是否接收邮件(细化到模块儿)
 * @date 2022/11/02 14:37.
 */
@Slf4j
// @RestController
@MojitoSchema(schemaId = "inner_api/baseUserIndividuationConfigInnerController")
public class BaseUserIndividuationConfigInnerController {

  @Autowired
  private BaseUserIndividuationConfigService baseUserIndividuationConfigService; // 配置信息

  /**
   * @Description 根据用户唯一id和查询模块儿id查询用户指定配置.
   * @Param userId 用户唯一id.
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
   **/
  // @RequestMapping(value = {"/inner_api/user/individuation/one/id"}, method = RequestMethod.GET)
  public JwResponseData<BaseUserIndividuationConfig> getUserIndividuationConfigOneByUserId(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "moduleId") String moduleId) {
    try {
      log.info("调用方法:getUserIndividuationConfigOne,查询入参:userId:{},moduleId:{}", userId, moduleId);
      BaseUserIndividuationConfig resultDB = baseUserIndividuationConfigService.getUserIndividuationConfigOneByUserId(userId, moduleId);
      log.info("调用方法:getUserIndividuationConfigOne,查询成功!");
      return JwResponseData.success("getUserIndividuationConfigOne获取成功", resultDB);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * @Description 根据用户realName和查询模块儿id查询用户指定配置.
   * @Param realName 用户realName.
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
   **/
  // @RequestMapping(value = {"/inner_api/user/individuation/one/name"}, method = RequestMethod.GET)
  public JwResponseData<BaseUserIndividuationConfig> getUserIndividuationConfigOneByRealName(@RequestParam(value = "realName") String realName, @RequestParam(value = "moduleId") String moduleId) {
    try {
      log.info("调用方法:getUserIndividuationConfigOneByRealName,查询入参:realName:{},moduleId:{}", realName, moduleId);
      BaseUserIndividuationConfig resultDB = baseUserIndividuationConfigService.getUserIndividuationConfigOneByRealName(realName, moduleId);
      log.info("调用方法:getUserIndividuationConfigOneByRealName,查询成功!");
      return JwResponseData.success("getUserIndividuationConfigOneByRealName获取成功", resultDB);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * @Description 根据登录用户realName列表和查询模块儿id查询用户指定配置.
   * @Param realNames 用户realName列表(使用","分割).
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
   **/
  // @RequestMapping(value = {"/inner_api/user/individuation/list/names"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByRealNames(@RequestParam(value = "realNames") String realNames, @RequestParam(value = "moduleId") String moduleId) {
    try {
      log.info("调用方法:getUsersIndividuationConfigListByRealNames,查询入参:realNames:{},moduleId:{}", realNames, moduleId);
      List<BaseUserIndividuationConfig> resultDB = baseUserIndividuationConfigService.getUsersIndividuationConfigListByRealNames(realNames, moduleId);
      log.info("调用方法:getUsersIndividuationConfigListByRealNames,查询成功!");
      return JwResponseData.success("getUsersIndividuationConfigListByRealNames获取成功", resultDB);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * @Description 根据用户id列表和查询模块儿id查询用户指定配置.
   * @Param userIds 用户userIds列表(list的json串).
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
   **/
  // @RequestMapping(value = {"/inner_api/user/individuation/list/ids"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByIds(@RequestParam(value = "userIds") String userIds, @RequestParam(value = "moduleId") String moduleId) {
    try {
      log.info("调用方法:getUsersIndividuationConfigListByIds,查询入参:userIds:{},moduleId:{}", userIds, moduleId);
      List<BaseUserIndividuationConfig> resultDB = baseUserIndividuationConfigService.getUsersIndividuationConfigListByIds(userIds, moduleId);
      log.info("调用方法:getUsersIndividuationConfigListByIds,查询成功!");
      return JwResponseData.success("getUsersIndividuationConfigListByIds获取成功", resultDB);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * @Description 根据部门id和查询模块儿id查询用户指定配置.
   * @Param depId 部门id.
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 用户对应模块儿的配置信息.
   **/
  // @RequestMapping(value = {"/inner_api/user/individuation/list/dep"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseUserIndividuationConfig>> getUsersIndividuationConfigListByDepId(@RequestParam(value = "depId") Integer depId, @RequestParam(value = "moduleId") String moduleId) {
    try {
      log.info("调用方法:getUsersIndividuationConfigListByDepId,查询入参:depId:{},moduleId:{}", depId, moduleId);
      List<BaseUserIndividuationConfig> resultDB = baseUserIndividuationConfigService.getUsersIndividuationConfigListByDepId(depId, moduleId);
      log.info("调用方法:getUsersIndividuationConfigListByDepId,查询成功!");
      return JwResponseData.success("getUsersIndividuationConfigListByDepId获取成功", resultDB);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

}
