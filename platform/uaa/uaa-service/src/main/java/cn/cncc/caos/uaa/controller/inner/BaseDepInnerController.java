package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.DepService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import com.github.pagehelper.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
// @RestController
@MojitoSchema(schemaId = "inner_api/baseDepInnerController")
public class BaseDepInnerController {
  @Autowired
  private DepService depService;
  private final String IOPS_OPERATOR = "门户推送";

  // @RequestMapping(value = "/inner_api/dep", method = RequestMethod.GET)
  @Operation(summary = "根据部门id获取部门")
  public JwResponseData<BaseDep> getDepById(@RequestParam(value = "depId") Integer depId) {
    BaseDep baseDep = depService.getById(depId);
    return JwResponseData.success("获取部门成功", baseDep);
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/admin/dep/get_dep_by_name", method = RequestMethod.GET)
  @Operation(summary = "根据部门名获取部门")
  public JwResponseData<BaseDep> getDepByName(@RequestParam("name") String name) {
    return depService.getDepByName(name);
  }

  /* 根据部门ID获取子部门信息 */
  @ResponseBody
  // @RequestMapping(value = "/inner_api/admin/dep/subdivision", method = RequestMethod.GET)
  public JwResponseData<List<BaseDep>> getDepSubdivision(@RequestParam("depName") String depName) {
    try {
      List<BaseDep> baseDep = depService.getDepSubdivision(depName);
      return JwResponseData.success("根据部门ID获取子部门信息成功", baseDep);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/admin/dep", method = RequestMethod.POST)
  public JwResponseData<BaseDep> addOneDep(@RequestBody BaseDep baseDep) {
    try {
      baseDep = depService.apiAddOneDep(baseDep, IOPS_OPERATOR);
      return JwResponseData.success("新增部门成功", baseDep);
    } catch (BapParamsException e) {
      return JwResponseData.error(JwResponseCode.INSERT_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.INSERT_ERROR.fillArgs("系统服务异常"));
    }
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/admin/dep", method = RequestMethod.DELETE)
  public JwResponseData<String> deleteOneDep(@RequestParam(value = "depCode") String depCode) {
    try {
      depService.apiDeleteOneDep(depCode, IOPS_OPERATOR);
      return JwResponseData.success("删除部门成功");
    } catch (BapParamsException e) {
      return JwResponseData.error(JwResponseCode.DELETE_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.DELETE_ERROR.fillArgs("系统服务异常"));
    }
  }

  @ResponseBody
  // @RequestMapping(value = {"/inner_api/admin/dep"}, method = RequestMethod.PUT)
  public JwResponseData<BaseDep> updateOneDep(@RequestBody BaseDep baseDep) {
    try {
      depService.apiUpdateOneDep(baseDep, IOPS_OPERATOR);
      return JwResponseData.success("更新部门成功", baseDep);
    } catch (BapParamsException e) {
      return JwResponseData.error(JwResponseCode.UPDATE_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.UPDATE_ERROR.fillArgs("系统服务异常"));
    }
  }


  @ResponseBody
  // @RequestMapping(value = "/inner_api/admin/dep/all", method = RequestMethod.GET)
  public JwResponseData<List<BaseDep>> getDepAll() {
    List<BaseDep> depList = depService.getDepAll();
    return JwResponseData.success("获取所有部门成功", depList);
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/dep/code", method = RequestMethod.GET)
  public JwResponseData<BaseDep> getDepByCode(@RequestParam("depCode") String depCode) {
    BaseDep baseDep = depService.getDepByCode(depCode);
    return JwResponseData.success("获取成功", baseDep);
  }


  @ResponseBody
  // @RequestMapping(value = "/inner_api/dep/code", method = RequestMethod.POST)
  public JwResponseData<Map<Integer, String>> getDepCodeByIds(@RequestBody List<Integer> idList) {
    try {
      Map<Integer, String> res = new HashMap<>();

      List<BaseDep> list = depService.getByIdList(idList);
      if (CollectionUtils.isEmpty(list))
        return JwResponseData.success("", res);
      for (BaseDep dep : list)
        res.put(dep.getId(), dep.getDepCode());
      return JwResponseData.success("", res);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("获取信息失败"));
    }
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/dep/judge/is/pay", method = RequestMethod.GET)
  public JwResponseData<Boolean> judgeIsPayDep(@RequestParam("depId") Integer depId) {
    String parentDepNames = depService.getParentDepNames(depId);
    if (StringUtil.isNotEmpty(parentDepNames) && parentDepNames.contains("总行系统事业部"))
      return JwResponseData.success("获取成功", false);
    return JwResponseData.success("获取成功", true);
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/dep/dep/id", method = RequestMethod.POST)
  public JwResponseData<List<String>> getDepIdByNames(@RequestBody List<String> depNames) {
    return JwResponseData.success("获取成功", depService.getDepIdByNames( depNames));
  }

}


