package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.db.dao.BaseDataPermissionDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseDataPermissionMapper;
import cn.cncc.caos.uaa.db.daoex.BaseDataPermissionMapperEx;
import cn.cncc.caos.uaa.db.pojo.BaseDataPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@Service
public class BaseDataPermissionService {

  @Autowired
  private BaseDataPermissionMapper baseDataPermissionMapper;

  @Autowired
  private BaseDataPermissionMapperEx baseDataPermissionMapperEx;

  public Map<String, Map<String, List<String>>> getDataPermissionByDepIdOrRoleId(Integer depId, List<Integer> roleIds) throws IOException {
    List<BaseDataPermission> dataPermissions;
    if (CollectionUtils.isEmpty(roleIds)) {
      dataPermissions = baseDataPermissionMapper.selectByExample()
          .where(BaseDataPermissionDynamicSqlSupport.depId, isEqualTo(depId))
          .and(BaseDataPermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
          .build().execute();
    } else {
      dataPermissions = baseDataPermissionMapperEx.getDataPermissionByDepIdOrRoleId(depId, roleIds);
    }
    Map<String, Map<String, List<String>>> result = new HashMap<>();
    if (!CollectionUtils.isEmpty(dataPermissions)) {
      //遍历basedatapermission，如果标志为all就直接返回Map<String, Map<String, List<String>>>
      //否则组装
      for (BaseDataPermission dataPermission : dataPermissions) {
        if ("ALL".equalsIgnoreCase(dataPermission.getType())) {
          return result;
        }
      }
      for (BaseDataPermission dataPermission : dataPermissions) {
        String dataScopeStr = dataPermission.getDataScope();
        Map<String, Map<String, List<String>>> dataPermissionMap = JacksonUtil.jsonToObj(dataScopeStr, Map.class, String.class, Map.class);
        for (String table : dataPermissionMap.keySet()) {
          Map<String, List<String>> columnAndScopeMapDb = dataPermissionMap.get(table);
          Map<String, List<String>> columnAndScopeMap = result.get(table);
          if (columnAndScopeMap == null) {
            result.put(table, columnAndScopeMapDb);
            continue;
          }
          for (String column : columnAndScopeMapDb.keySet()) {
            List<String> scopesDb = columnAndScopeMapDb.get(column);
            List<String> scopes = columnAndScopeMap.get(column);
            if (CollectionUtils.isEmpty(scopes)) {
              columnAndScopeMap.put(column, scopesDb);
              continue;
            }
            scopesDb.addAll(scopes);
            scopesDb = new ArrayList<>(new HashSet<>(scopesDb));
            columnAndScopeMap.put(column, scopesDb);
          }
          result.put(table, columnAndScopeMap);
        }
      }
      return result;
    }
    return null;
  }
}
