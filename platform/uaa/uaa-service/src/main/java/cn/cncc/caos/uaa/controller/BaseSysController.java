package cn.cncc.caos.uaa.controller;


import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseSysClassMapper;
import cn.cncc.caos.uaa.db.dao.BaseSysMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.helper.RestTemplateHelper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.platform.uaa.client.api.DataAuthResponseCode;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSys;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSysClass;
import cn.cncc.caos.uaa.utils.HttpUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport.sysId;
import static cn.cncc.caos.uaa.db.dao.BaseSysClassDynamicSqlSupport.isValid;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@RestController
@Slf4j
public class BaseSysController {
  @Autowired
  HttpServletRequest httpServletRequest;
  @Autowired
  private BaseSysMapper baseSysMapper;
  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;
  @Autowired
  private BaseSysClassMapper baseSysClassMapper;

  private String operTypeAdd = "sysAdd";
  private String operTypeUpdate = "sysUpdate";
  private String operTypeDelete = "sysDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @Resource
  private ServerConfigHelper serverConfigHelper;

  @Resource
  private RestTemplateHelper restTemplateHelper;

  @RequestMapping(value = "/open_api/get_all_system", method = RequestMethod.GET)
  JwResponseData<Map<String, List<BaseSys>>> getAllSystemByUserNameOauth(@RequestParam(value = "userName", required = true) String userName) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      log.info("JW-Net-Source-Is-Emis" + httpServletRequest.getHeader("JW-Net-Source-Is-Emis"));
      int flagFromEmis = 0;
      if (httpServletRequest.getHeader("JW-Net-Source-Is-Emis") != null && httpServletRequest.getHeader("JW-Net-Source-Is-Emis").toString().toLowerCase().equals("yes"))
        flagFromEmis = 1;
      log.info("authentication.getName()" + authentication.getName());
      log.info("flagFromEmis" + flagFromEmis);
      log.info("authentication.getAuthorities" + authentication.getAuthorities().toString());
      cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser baseUser;
      try {
        baseUser = JacksonUtil.jsonToObj(authentication.getName(), cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser.class);
      }catch (IOException e) {
        log.error("json to obj error", e);
        return JwResponseData.error(JwResponseCode.COMPLETE_USER_ERROR);
      }
      if (StringUtils.isEmpty(baseUser.getUserName()))
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      if (!userName.equals(baseUser.getUserName())) {
//        throw new DataAuthException(DataAuthResponseCode.NOT_AUTHORIZED_GET_INFO_FROM_OTHER_USER);
        return JwResponseData.error(JwResponseCode.COMPLETE_USER_ERROR);
      }

      List<BaseSysClass> listBaseSysClass = baseSysClassMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      List<BaseSys> listBaseSys = baseSysMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();

      Map<String, List<BaseSys>> map = new HashMap<String, List<BaseSys>>();

      for (BaseSysClass tmpSysClass : listBaseSysClass) {
        List<BaseSys> listSysResult = new ArrayList<BaseSys>();
        log.info(tmpSysClass.getClassName());
        if (flagFromEmis == 1 && !tmpSysClass.getClassName().equals("通用工具集")) {
          continue;
        }
        for (BaseSys tmpSys : listBaseSys) {
          log.info(tmpSysClass.getSysAssemble() + "==========" + tmpSys.getSysTitle());
          if (tmpSysClass.getSysAssemble().indexOf("(" + tmpSys.getId().toString() + ")") != -1) {
            //在其中
            if (flagFromEmis == 1 && !tmpSys.getSysName().equals("process_platform")) {
              continue;
            }

            listSysResult.add(tmpSys);
          }
        }
        map.put(tmpSysClass.getClassName(), listSysResult);
      }
      return JwResponseData.success("单点登录中心获取系统列表成功", map);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-manage')")
  @ResponseBody
  @RequestMapping("/api/admin/sys/get_all")
  public JwResponseData<List<BaseSys>> getAllSys() {
    try {
      List<BaseSys> list = baseSysMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("获得系统列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneRoleType(@RequestBody Map jsonObject) {
    try {
      BaseSys bt = new BaseSys();
      bt.setSysName(jsonObject.get("sysName").toString());
      bt.setSysTitle(jsonObject.get("sysTitle").toString());
      bt.setSysDesc(jsonObject.get("sysDesc").toString());
      bt.setDeveloper(jsonObject.get("developer").toString());
      bt.setImageName(jsonObject.get("imageName").toString());
      bt.setInterfaceNum(Integer.parseInt(jsonObject.get("interfaceNum").toString()));
      bt.setSysStatus(jsonObject.get("sysStatus").toString());
      bt.setSysVersion(jsonObject.get("sysVersion").toString());
      bt.setSysUrl(jsonObject.get("sysUrl").toString());
      bt.setIsBuiltIn(Integer.parseInt(jsonObject.get("isBuiltIn").toString()));

      bt.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysMapper.insert(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user,operTypeAdd,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("新增系统成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateOneSys(@RequestBody Map jsonObject) {
    try {
      BaseSys bt = new BaseSys();
      bt.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bt.setSysName(jsonObject.get("sysName").toString());
      bt.setSysTitle(jsonObject.get("sysTitle").toString());
      bt.setSysDesc(jsonObject.get("sysDesc").toString());
      bt.setDeveloper(jsonObject.get("developer").toString());
      bt.setImageName(jsonObject.get("imageName").toString());
      bt.setInterfaceNum(Integer.parseInt(jsonObject.get("interfaceNum").toString()));
      bt.setSysStatus(jsonObject.get("sysStatus").toString());
      bt.setSysVersion(jsonObject.get("sysVersion").toString());
      bt.setSysUrl(jsonObject.get("sysUrl").toString());
      bt.setIsBuiltIn(Integer.parseInt(jsonObject.get("isBuiltIn").toString()));

      bt.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysMapper.updateByPrimaryKeySelective(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update sys end operationUser:"+user.getRealName()+",content:"+JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("更新系统成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteOneRoleType(@RequestBody Map jsonObject) {

    try {
      Integer sysIdInput = Integer.parseInt(jsonObject.get("id").toString());
      long count = baseRoleMapperEx.countByExample().where(isValid, isEqualTo(1)).and(sysId, isEqualTo(sysIdInput)).build().execute();
      if (count > 0) {
        return JwResponseData.error(DataAuthResponseCode.SYS_NOT_DELETE_BECAUSE_HAS_ROLE, "调用/api/admin/sys/delete_one异常");
      }

      BaseSys bt = new BaseSys();
      bt.setId(sysIdInput);
      //log.info("@@@" + bt.getId().toString());
      bt.setIsValid(0);
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysMapper.updateByPrimaryKeySelective(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete sys end operationUser:"+user.getRealName()+",sysId:"+sysIdInput);
      operationHistoryService.insertHistoryOper(user,operTypeDelete,JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("删除系统成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @Operation(summary = "获取cncc应用列表")
  @RequestMapping(value = "/open_api/get_all_system/cncc", method = RequestMethod.GET)
  public JwResponseData<Map<String, Object>> getCNCCSys(
          @RequestParam(value = "userName") String userName,
          @RequestParam(value = "cnccToken") String cnccToken
  ) {
    Map<String, Object> res = new HashMap<>();
    // 直接请求，返回401的话，获取token，在请求一次
    String ip = serverConfigHelper.getValue("cncc.ip");
    String systemUrl = serverConfigHelper.getValue("cncc.system.url");
    Map<String, Object> params = new HashMap<>();
    params.put("userName", userName);
    HttpHeaders httpHeaders = HttpUtil.buildTokenHttpHeaders(cnccToken);

    HttpEntity<Object> formEntity = new HttpEntity<>(httpHeaders);

    String loginUrl = serverConfigHelper.getValue("cncc.login.url");
    Map<String, Object> response = restTemplateHelper.requestCNCCApi(loginUrl, ip, systemUrl, formEntity, params, userName);

    Object data = response.get("data");
    if (data != null)
      res.put("data", data);
    res.put("userDetails", response.get("userDetails"));
    return JwResponseData.success("", res);
  }
}

