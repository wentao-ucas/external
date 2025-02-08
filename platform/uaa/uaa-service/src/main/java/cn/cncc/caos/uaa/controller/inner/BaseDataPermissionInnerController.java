package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.DataPermissionService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
// @Tag(name = "获取数据权限")
// @RestController
@MojitoSchema(schemaId = "inner_api/baseDataPermissionInnerController")
public class BaseDataPermissionInnerController {

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Value("${data.permission.key}")
  private String dataPermissionKey;

  @Autowired
  private DataPermissionService dataPermissionService;

  // @RequestMapping(value = "/inner_api/data/permission", method = RequestMethod.POST)
  @Operation(summary = "添加部门数据权限")
  public JwResponseData<Object> addDataPermission(@RequestBody Map jsonObject) {
    return JwResponseData.success("新增成功");
  }

  // @RequestMapping(value = "/inner_api/data/permission/delete", method = RequestMethod.GET)
  @Operation(summary = "清除关于数据权限redis缓存")
  public JwResponseData<Object> deleteRedisData() {
    Set<String> keys = stringRedisTemplate.keys("dataPermission" + "*");
    if (!CollectionUtils.isEmpty(keys)) {
      stringRedisTemplate.delete(keys);
    }
    return JwResponseData.success("清除redis缓存成功");
  }

  // @RequestMapping(value = "/inner_api/data/permission/by/user", method = RequestMethod.POST)
  @Operation(summary = "根据用户获取数据权限")
  public JwResponseData<Map<String, Map<String, String>>> getDataPermissionByUser(@RequestBody BaseUser baseUser) {
    try {
      Map<String, Map<String,String>> dataPermissionByDepIdOrRoleId = dataPermissionService.getDataPermission(baseUser);
      return JwResponseData.success("获取数据权限成功", dataPermissionByDepIdOrRoleId);
    } catch (IOException e) {
      log.info("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}
