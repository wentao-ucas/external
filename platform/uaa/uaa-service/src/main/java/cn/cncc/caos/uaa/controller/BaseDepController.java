package cn.cncc.caos.uaa.controller;


import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.DataAuthResponseCode;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseDepMapper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.BaseSyncService;
import cn.cncc.caos.uaa.service.DepService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseDepDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@RestController
public class BaseDepController {
  @Autowired
  private BaseDepMapper baseDepMapper;
  @Autowired
  private DepService depService;
  @Autowired
  private BaseSyncService baseSyncService;
  private String operTypeAdd = "depAdd";
  private String operTypeUpdate = "depUpdate";
  private String operTypeDelete = "depDelete";
  @Autowired
  private OperationHistoryService operationHistoryService;

  @ResponseBody
  @RequestMapping("/api/admin/dep/get_all")
  @PreAuthorize("hasAuthority('department-manage')")
  public JwResponseData<List<BaseDep>> getAllDep() {
    try {
      List<BaseDep> list = baseDepMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("获得所有部门成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping("/api/admin/dep/get_all_by_tree")
  @PreAuthorize("hasAuthority('department-manage')")
  public JwResponseData<Map> getAllDepByTree() {
    try {
      Map result = depService.getDepTreeData();
      return JwResponseData.success("获得部门树成功", result);
    } catch (IOException e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping("/api/admin/dep/get_children_by_id")
  @PreAuthorize("hasAuthority('department-manage')")
  public JwResponseData<List<BaseDep>> getAllChildrenDepById(@RequestParam(value = "parentId", required = true) Integer parentIdInput) {
    if (parentIdInput == null) {
      parentIdInput = -1;
    }
    try {
      List<BaseDep> list = baseDepMapper.selectByExample().where(isValid, isEqualTo(1)).and(parentId, isEqualTo(parentIdInput)).orderBy(id).build().execute();
      return JwResponseData.success("获得子部门列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  @PreAuthorize("hasAuthority('department-manage')")
  @ResponseBody
  @RequestMapping(value = {"/api/admin/dep/add_one", "/inner_api/admin/dep/add_one"}, method = RequestMethod.POST)
  public JwResponseData<String> addOneDepJSONObject(@RequestBody Map jsonObject) {
    try {
      BaseDep bd = new BaseDep();
      bd.setDepName(jsonObject.get("depName").toString());
      bd.setDepDesc(jsonObject.get("depDesc").toString());
//      bd.setLevel(Integer.parseInt(jsonObject.get("level").toString()));
      bd.setParentId(Integer.parseInt(jsonObject.get("parentId").toString()));
      bd.setUpdateTime(TimeUtil.getCurrentTime());
      bd.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      baseDepMapper.insert(bd);
//        depUtil.buildDepTreeData();
      depService.deleteDepTreeData();
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(jsonObject));

      return JwResponseData.success("新增部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('department-manage')")
  @ResponseBody
  @RequestMapping(value = {"/api/admin/dep/update_one"}, method = RequestMethod.POST)
  public JwResponseData<String> updateOneDepJSONObject(@RequestBody Map jsonObject) {
    try {
      BaseDep bd = new BaseDep();
      bd.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bd.setDepName(jsonObject.get("depName").toString());
      bd.setDepDesc(jsonObject.get("depDesc").toString());
//      bd.setLevel(Integer.parseInt(jsonObject.get("level").toString()));
      bd.setParentId(Integer.parseInt(jsonObject.get("parentId").toString()));
      bd.setUpdateTime(TimeUtil.getCurrentTime());
      bd.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      baseDepMapper.updateByPrimaryKeySelective(bd);
//      baseDepDataSyncExecutor.sendMessage(DataDepFuncType.UPDATE_USER_ROLE,jsonObject);
//        depUtil.buildDepTreeData();
      depService.deleteDepTreeData();
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update dep end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(jsonObject));

      return JwResponseData.success("更新部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('department-manage')")
  @ResponseBody
  @RequestMapping(value = {"/api/admin/dep/delete_one"}, method = RequestMethod.POST)
  public JwResponseData<String> deleteOneDepJSONObject(@RequestBody Map jsonObject) {
    try {
      Integer idInput = Integer.parseInt(jsonObject.get("id").toString());

      long count = baseDepMapper.countByExample().where(isValid, isEqualTo(1)).and(parentId, isEqualTo(idInput)).build().execute();
      if (count > 0) {
        return JwResponseData.error(DataAuthResponseCode.DEP_NOT_DELETE_BECAUSE_HAS_CHILDREN, "调用/api/admin/dep/delete_one异常");
      }

      BaseDep bd = new BaseDep();
      bd.setId(idInput);
      bd.setUpdateTime(TimeUtil.getCurrentTime());
      bd.setIsValid(0);
      baseDepMapper.updateByPrimaryKeySelective(bd);

//        depUtil.buildDepTreeData();
      depService.deleteDepTreeData();
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete dep end operationUser:" + user.getRealName() + ",depId:" + idInput);
      operationHistoryService.insertHistoryOper(user, operTypeDelete, JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("删除部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = {"/api/admin/dep/parent/level"}, method = RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('department-manage', 'standard-library','record-library')")
  public JwResponseData<BaseDep> getParentLevel(@RequestParam(value = "depId") Integer depId) {
    BaseDep baseDep = depService.getParentLevel(depId);
    return JwResponseData.success("查询成功", baseDep);
  }

  @ResponseBody
  @RequestMapping(value = "/inner_api/admin/dep/iops/get", method = RequestMethod.GET)
  public JwResponseData<Map<String, Object>> iopsGet() {
    try {
      baseSyncService.scheduleTaskDepSync();
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("获取信息失败"));
    }
    return null;
  }


}


