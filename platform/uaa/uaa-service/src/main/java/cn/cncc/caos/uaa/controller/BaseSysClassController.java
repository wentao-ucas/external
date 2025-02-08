package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseSysClassMapper;
import cn.cncc.caos.uaa.db.dao.BaseSysMapper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSys;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSysClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseSysClassDynamicSqlSupport.isValid;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@RestController
public class BaseSysClassController {
  @Autowired
  private BaseSysClassMapper baseSysClassMapper;

  @Autowired
  private BaseSysMapper baseSysMapper;

  private String operTypeAdd = "sysClassAdd";
  private String operTypeUpdate = "sysClassUpdate";
  private String operTypeDelete = "sysClassDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;

//    private class ExSysClass extends BaseSysClass {
//        public String getSysNameAssemble() {
//            return sysNameAssemble;
//        }
//
//        public void setSysNameAssemble(String sysNameAssemble) {
//            this.sysNameAssemble = sysNameAssemble;
//        }
//
//        String sysNameAssemble;
//    }

  @PreAuthorize("hasAuthority('system-class-manage')")
  @ResponseBody
  @RequestMapping("/api/admin/sys_class/get_all")
  public JwResponseData<List> getAllSys() {
    try {
      List<BaseSysClass> listClass = baseSysClassMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      List<BaseSys> listSys = baseSysMapper.selectByExample().where(isValid, isEqualTo(1)).build().execute();

      Map<String, String> map = new HashMap<String, String>();
      for (BaseSys baseSys : listSys) {
        map.put(baseSys.getId().toString(), baseSys.getSysTitle().toString());
      }

      List jsonArray = new ArrayList();

      for (BaseSysClass baseSysClass : listClass) {
        Map o = JacksonUtil.objToObj(baseSysClass,Map.class);
        String sysAssemble = baseSysClass.getSysAssemble();
        sysAssemble = sysAssemble.replace("(", "");
        sysAssemble = sysAssemble.replace(")", "");
        String[] sysArray = sysAssemble.split(",");

        List<String> sysNameAssemble = new ArrayList<>();
        for (String sysTemp : sysArray) {
          sysNameAssemble.add(map.get(sysTemp));
        }
        o.put("sysNameAssemble", StringUtils.join(sysNameAssemble.toArray(), ","));
        jsonArray.add(o);
      }
//        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXs" +jsonArray.toString() );
      return JwResponseData.success("获得系统分类成功", jsonArray);
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-class-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys_class/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addSysClass(@RequestBody Map jsonObject) {
    try {
      BaseSysClass bt = new BaseSysClass();
      bt.setClassName(jsonObject.get("className").toString());
      bt.setClassDesc(jsonObject.get("classDesc").toString());
      bt.setSysAssemble(jsonObject.get("sysAssemble").toString());

      try {
        log.info(JacksonUtil.objToJson(jsonObject));
      } catch (IOException e) {
        log.error("", e);
      }

      bt.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysClassMapper.insert(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user,operTypeAdd, JacksonUtil.objToJson(jsonObject));//JacksonUtil.objToJson());
      return JwResponseData.success("新增系统分类成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-class-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys_class/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateSysClass(@RequestBody Map jsonObject) {
    try {
      BaseSysClass bt = new BaseSysClass();
      bt.setId(Integer.parseInt(jsonObject.get("id").toString()));
      bt.setClassName(jsonObject.get("className").toString());
      bt.setClassDesc(jsonObject.get("classDesc").toString());
      bt.setSysAssemble(jsonObject.get("sysAssemble").toString());

      bt.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysClassMapper.updateByPrimaryKeySelective(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update sysClass end operationUser:"+user.getRealName()+",content:"+ JacksonUtil.objToJson(jsonObject));//JacksonUtil.objToJson());
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(jsonObject));//JacksonUtil.objToJson());
      return JwResponseData.success("更新系统分类成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('system-class-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/sys_class/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteSysClass(@RequestBody Map jsonObject) {
    try {
      BaseSysClass bt = new BaseSysClass();
      bt.setId(Integer.parseInt(jsonObject.get("id").toString()));
      //log.info("@@@" + bt.getId().toString());
      bt.setIsValid(0);
      bt.setUpdateTime(TimeUtil.getCurrentTime());
      baseSysClassMapper.updateByPrimaryKeySelective(bt);
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete sysClass end operationUser:"+user.getRealName()+",sysClass:"+jsonObject);
      operationHistoryService.insertHistoryOper(user,operTypeDelete,JacksonUtil.objToJson(jsonObject));//JacksonUtil.objToJson());

      return JwResponseData.success("删除系统分类成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

