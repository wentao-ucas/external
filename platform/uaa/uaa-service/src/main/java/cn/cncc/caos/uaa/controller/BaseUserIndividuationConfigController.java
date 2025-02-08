package cn.cncc.caos.uaa.controller;


import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.model.individuation.AddOrUpdateIndividuationReq;
import cn.cncc.caos.uaa.model.individuation.IndividuationResp;
import cn.cncc.caos.uaa.service.BaseUserIndividuationConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description 用户个性化配置请求处理入口.
 * ---用户是否接收短信(细化到模块儿)
 * ---用户是否接收邮件(细化到模块儿)
 * @date 2022/11/02 14:37.
 */
@Slf4j
@RestController
public class BaseUserIndividuationConfigController {

  @Autowired
  private BaseUserIndividuationConfigService baseUserIndividuationConfigService; // 配置信息

  /**
   * @Description 根据登录用户唯一id查询用户所有个性化配置.
   * @Param userId 用户唯一id.
   * @Return List 用户所有配置信息结果集.
   **/
  @PreAuthorize("hasAuthority('ownspace-index')")
  @RequestMapping(value = {"/open_api/user/individuation/query", "/inner_api/user/individuation/query"}, method = RequestMethod.GET)
  JwResponseData<List> getUserIndividuationConfig(@RequestParam(value = "userId") Integer userId) {
    try {
      log.info("调用方法:getUserIndividuationConfig,查询入参:", userId);
//      List userIndividuationConfigList = baseUserIndividuationConfigService.getUserIndividuationConfig(userId);
      List userIndividuationConfigList = baseUserIndividuationConfigService.getUserIndividuationConfigNew(userId);
      log.info("调用方法:getUserIndividuationConfig,查询成功!");
      return JwResponseData.success("getUserIndividuationConfig获取成功", userIndividuationConfigList);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * @Description 新增或更新单个用户的单个配置信息.
   * @Param configInfo 用户需要添加或更新的配置信息.
   * @Return String 添加结果.
   **/
  @PreAuthorize("hasAuthority('ownspace-index')")
  @RequestMapping(value = {"/open_api/user/individuation/update", "/inner_api/user/individuation/update"}, method = RequestMethod.POST)
  JwResponseData<String> addOrUpdateUserIndividuationConfig(@RequestBody AddOrUpdateIndividuationReq req) {
    try {
      log.info("调用方法:addOrUpdateUserIndividuationConfig,输入入参,req:{}", req);
      Integer userId = req.getUserId();
      IndividuationResp configInfo = req.getConfigInfo();
      baseUserIndividuationConfigService.addOrUpdateUserIndividuationConfig(userId, configInfo);
      log.info("调用方法:addOrUpdateUserIndividuationConfig,添加或更新成功!");
      return JwResponseData.success("添加用户配置信息完成");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  /**
   * @Description 用户初始化参数.
   * @Param req 初始化参数.
   * @Return String 执行结果.
   **/
  @RequestMapping(value = {"/inner_api/user/individuation/init"}, method = RequestMethod.GET)
  JwResponseData<String> initUserIndividuationConfig(@RequestBody Map req) {
    try {
      log.info("调用方法:initUserIndividuationConfig,查询入参:", JacksonUtil.objToJson(req));
      baseUserIndividuationConfigService.initUserIndividuationConfig(req);
      log.info("调用方法:initUserIndividuationConfig,查询成功!");
      return JwResponseData.success("initUserIndividuationConfig数据初始化成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


}
