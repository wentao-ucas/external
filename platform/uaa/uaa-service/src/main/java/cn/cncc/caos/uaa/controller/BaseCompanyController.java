package cn.cncc.caos.uaa.controller;


import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseCompanyMapper;
import cn.cncc.caos.uaa.db.pojo.BaseCompany;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseCompanyDynamicSqlSupport.companyName;
import static cn.cncc.caos.uaa.db.dao.BaseCompanyDynamicSqlSupport.isValid;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isLike;

@Slf4j
@RestController
public class BaseCompanyController {
  @Autowired
  private BaseCompanyMapper baseCompanyMapper;

  private String operTypeAdd = "companyAdd";
  private String operTypeUpdate = "companyUpdate";
  private String operTypeDelete = "companyDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @ResponseBody
  @PreAuthorize("hasAnyAuthority('implement-get','implement-add')")
  @RequestMapping({"/open_api/company/get_all"})
  public JwResponseData<List<BaseCompany>> getAll() {
    try {
      List<BaseCompany> list = baseCompanyMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("获得所有公司成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  @PreAuthorize("hasAuthority('company-manage')")
  @RequestMapping({"/api/admin/company/get_by_condition"})
  public JwResponseData<Object> getAllByPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                           @RequestParam(value = "searchString", required = false, defaultValue = "") String searchString
  ) {
    try {
      Long count = baseCompanyMapper.countByExample().where(companyName, isLike("%" + searchString + "%")).and(isValid, isEqualTo(1)).build().execute();
      PageHelper.startPage(pageNum, pageSize);
      List<BaseCompany> list = baseCompanyMapper.selectByExample().where(companyName, isLike("%" + searchString + "%")).and(isValid, isEqualTo(1)).build().execute();
      Map result = new HashMap();
      result.put("total", count);
      result.put("list", list);
      return JwResponseData.success("获得所有公司成功", result);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('company-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/company/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneDep(@RequestBody BaseCompany jsonObject) {
    try {
      jsonObject.setUpdateTime(TimeUtil.getCurrentTime());
      baseCompanyMapper.insertSelective(jsonObject);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user,operTypeAdd, JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("新增部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('company-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/company/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateOneDep(@RequestBody BaseCompany jsonObject) {
    try {
      jsonObject.setUpdateTime(TimeUtil.getCurrentTime());
      baseCompanyMapper.updateByPrimaryKeySelective(jsonObject);
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update company end operationUser:"+user.getRealName()+",companyId:"+jsonObject.getId());
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("更新部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('company-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/company/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteOneDep(@RequestBody BaseCompany jsonObject) {
    try {
      jsonObject.setIsValid(0);
      jsonObject.setUpdateTime(TimeUtil.getCurrentTime());
      baseCompanyMapper.updateByPrimaryKeySelective(jsonObject);
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete company end operationUser:"+user.getRealName()+",companyName:"+jsonObject.getCompanyName());
      operationHistoryService.insertHistoryOper(user,operTypeDelete,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("删除部门成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

