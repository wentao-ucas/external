package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.db.dao.BaseDictDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseDictMapper;
import cn.cncc.caos.uaa.db.pojo.BaseDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@RestController
public class BaseDictController {
  @Autowired
  private BaseDictMapper baseDictMapper;

  @RequestMapping(value = "/open_api/base_dict/get/location_name", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('ownspace-index')")
  public JwResponseData<Object> testException2() {
    try {
      List<BaseDict> list = baseDictMapper.selectByExample().where(BaseDictDynamicSqlSupport.dictCode, isEqualTo("location_name")).build().execute();
      return JwResponseData.success("获得所有地点名称成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

