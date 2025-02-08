package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.model.rule.BaseRuleReq;
import cn.cncc.caos.uaa.model.rule.BaseRuleRes;
import cn.cncc.caos.uaa.model.rule.BaseRuleUpdateReq;
import cn.cncc.caos.uaa.service.BaseRuleService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "规则相关接口")
@Slf4j
public class BaseRuleController {

  @Autowired
  private BaseRuleService baseRuleService;

  private String operTypeAdd = "ruleAdd";
  private String operTypeUpdate = "ruleUpdate";
  private String operTypeDelete = "ruleDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;


  @PreAuthorize("hasAuthority('rule-manage')")
  @Operation(summary = "新增规则接口")
  @RequestMapping(value = "/open_api/rule", method = RequestMethod.POST)
  public JwResponseData<Object> addRule(@Validated @RequestBody BaseRuleReq baseRuleReq) {
    if (baseRuleReq == null) {
      return JwResponseData.error(JwResponseCode.BIND_ERROR);
    }
    try {
      baseRuleService.addRule(baseRuleReq);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(baseRuleReq));
      return JwResponseData.success("新增成功");
    } catch (DataAuthException dae) {
      log.error(dae.getCm().getMsg(), dae);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(dae.getCm().getMsg()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('rule-manage')")
  @Operation(summary = "修改规则接口")
  @RequestMapping(value = "/open_api/rule", method = RequestMethod.PUT)
  public JwResponseData<Object> updateRule(@Validated @RequestBody BaseRuleUpdateReq baseRuleUpdateReq) {
    try {
      baseRuleService.updateRule(baseRuleUpdateReq);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update rule end operationUser:" + user.getRealName() + ",ruleId:" + baseRuleUpdateReq.getId());
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(baseRuleUpdateReq));
      return JwResponseData.success("修改成功");
    } catch (DataAuthException dae) {
      log.error(dae.getCm().getMsg(), dae);
      return JwResponseData.error(JwResponseCode.DB_DATA_NOT_EXIST);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('rule-manage')")
  @Operation(summary = "删除规则接口")
  @RequestMapping(value = "/open_api/rule", method = RequestMethod.DELETE)
  public JwResponseData<Object> deleteRule(@RequestParam(value = "id") String id) {
    try {
      //判断有没有子规则，有全删除
      baseRuleService.deleteRule(id);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete rule end operationUser:" + user.getRealName() + ",ruleId:" + id);
      operationHistoryService.insertHistoryOper(user, operTypeDelete, "delete Rule:"+id);
      return JwResponseData.success("删除成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('rule-manage')")
  @Operation(summary = "获取全部规则列表")
  @RequestMapping(value = "/open_api/rule/list", method = RequestMethod.GET)
  public JwResponseData<List<BaseRuleRes>> getFullList(@RequestParam(value = "searchColumn", required = false) String searchColumn) {
    try {
      List<BaseRuleRes> allRule = baseRuleService.getAllRule(searchColumn);
      return JwResponseData.success("获取规则成功", allRule);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  @Operation(summary = "删除权限的缓存接口")
  @RequestMapping(value = "/inner_api/rule/cache", method = RequestMethod.GET)
  public JwResponseData<Object> deleteRuleCache() {
    baseRuleService.deleteRuleCache();
    return JwResponseData.success("删除缓存成功");
  }

}
