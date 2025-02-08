package cn.cncc.caos.uaa.controller;


import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleMapper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.BaseUserRelRoleHelper;
import cn.cncc.caos.uaa.service.BaseUserRelRoleService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.isValid;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;


@Slf4j
@RestController
public class BaseUserRelRoleController {

  @Autowired
  private BaseUserRelRoleMapper baseUserRelRoleMapper;

  @Autowired
  private BaseUserRelRoleService baseUserRelRoleService;

  private String operTypeUpdate = "roleRelUserUpdate";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @Resource
  private BaseUserRelRoleHelper baseUserRelRoleHelper;

  @PreAuthorize("hasAuthority('user-group-relation-user-manage')")
  @ResponseBody
  @RequestMapping("/api/admin/role_rel/get_all")
  public JwResponseData<List<BaseUserRelRole>> getAllRel() {
    try {
      List<BaseUserRelRole> list = baseUserRelRoleMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("获得用户角色对应关系成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-relation-user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role_rel/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneRole(@RequestBody Map jsonObject) {
    try {
      BaseUserRelRole burr = new BaseUserRelRole();
      burr.setUserId(Integer.parseInt(jsonObject.get("user_id").toString()));
      burr.setRoleId(Integer.parseInt(jsonObject.get("roleId").toString()));
      burr.setIsValid(1);
      burr.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserRelRoleMapper.insert(burr);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("add roleRelUser end operationUser:"+user.getRealName()+", content:"+ JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("新增用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-relation-user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role_rel/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateOneRel(@RequestBody Map jsonObject) {
    try {
      BaseUserRelRole burr = new BaseUserRelRole();
      burr.setId(Integer.parseInt(jsonObject.get("id").toString()));
      burr.setUserId(Integer.parseInt(jsonObject.get("user_id").toString()));
      burr.setRoleId(Integer.parseInt(jsonObject.get("roleId").toString()));
      burr.setIsValid(1);
      burr.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserRelRoleMapper.updateByPrimaryKeySelective(burr);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update roleRelUser end operationUser:"+user.getRealName()+", content:"+JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("更新用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-relation-user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role_rel/update_by_role_id", method = RequestMethod.POST)
  public JwResponseData<String> updateRelByRoleId(@RequestBody Map jsonObject) {

    try {
      baseUserRelRoleHelper.updateUserRelRole(jsonObject);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update roleRelUser end operationUser:"+user.getRealName()+", content:"+JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("更新用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  @PreAuthorize("hasAuthority('user-group-relation-user-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role_rel/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteOneRel(@RequestBody Map jsonObject) {
    try {
      BaseUserRelRole burr = new BaseUserRelRole();
      burr.setId(Integer.parseInt(jsonObject.get("id").toString()));
      burr.setIsValid(0);
      burr.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserRelRoleMapper.updateByPrimaryKeySelective(burr);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete roleRelUser end operationUser:"+user.getRealName()+", content:"+JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("删除用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

