package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
//import cn.cncc.caos.uaa.feign.AuthCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataPermissionService {

//  @Autowired
//  private AuthCenterFeignClient authCenterFeignClient;

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Value("${data.permission.key}")
  private String dataPermissionKey;

  @Autowired
  private BaseDataPermissionService baseDataPermissionService;

  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;

  public Map<String, Map<String, String>> getDataPermission(BaseUser user) throws IOException {
    if (user == null) {
      return null;
    }
    //获取用户的roleId
    List<BaseRole> baseRoles = baseRoleMapperEx.getRoleByUserId(user.getId());
    List<Integer> roleIds = null;
    if (!CollectionUtils.isEmpty(baseRoles)) {
      roleIds = baseRoles.stream().map(BaseRole::getId).collect(Collectors.toList());
    }
    String key = dataPermissionKey + user.getDepId() + roleIds;
    String dataPermissionJson = stringRedisTemplate.opsForValue().get(key);
    Map<String, Map<String, String>> dataPermissions;
    if (!StringUtils.isEmpty(dataPermissionJson)) {
      dataPermissions = JacksonUtil.jsonToObj(dataPermissionJson, Map.class, String.class, Map.class);
      return dataPermissions;
    }
    //根据userDepId获取
    Map<String, Map<String, List<String>>> data = baseDataPermissionService.getDataPermissionByDepIdOrRoleId(user.getDepId(), roleIds);
    if (data == null) {
      return null;
    }
    dataPermissions = new HashMap<>();
    for (String table : data.keySet()) {
      Map<String, List<String>> columnPermissions = data.get(table);
      Map<String, String> columnPermissionsMap = new HashMap<>();
      for (String column : columnPermissions.keySet()) {
        List<String> names = columnPermissions.get(column);
        StringBuilder nameStr = new StringBuilder();
        if ("countersign_role_name_list".equalsIgnoreCase(column) || "business_system_id_list".equalsIgnoreCase(column)) {
          names.forEach(name -> nameStr.append("'%").append(name).append("%'").append(","));
        } else {
          names.forEach(name -> nameStr.append("'").append(name).append("'").append(","));
        }
        String depNames = nameStr.toString();
        if (depNames.endsWith(","))
          depNames = depNames.substring(0, depNames.lastIndexOf(","));
        columnPermissionsMap.put(column, depNames);
      }
      dataPermissions.put(table, columnPermissionsMap);
    }
    stringRedisTemplate.opsForValue().set(key, JacksonUtil.objToJson(dataPermissions));
    return dataPermissions;
  }
}
