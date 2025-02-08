package cn.cncc.caos.uaa.db.daoex;

import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface BasePermissionMapperEx {

  @Update({
      "<script>",
      "update base_permission set update_time=#{date},status=0 where id in ",
      "<foreach collection='basePermissionIdsDelete' item='id' index='index' open='(' close=')' separator=','>",
      "#{id}",
      "</foreach>",
      "</script>"
  })
  void batchUpdateBasePermissions(@Param("basePermissionIdsDelete") Set<String> basePermissionsDelete, @Param("date") Date date);

  @Select("select bp.* from base_permission bp where bp.id in (select brap.permission_id from base_role_auth_permission brap where brap.role_id in (select bura.role_id from base_user_role_auth bura where bura.user_id=#{userId} and status=1) and status=1) and status=1")
  List<cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission> getPermissionsByUserId(@Param("userId") Integer userId);
}
