package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.BaseRoleAuthPermission;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface BaseRoleAuthPermissionMapperEx {

  @Update("update base_role_auth_permission set update_time=#{date},status=0 where role_id=#{roleId}")
  void deleteRoleAuthPermission(@Param("date") Date date, @Param("roleId") String roleId);

  @Insert({
      "<script>",
      "insert into base_role_auth_permission values ",
      "<foreach collection='baseRolePermissions' item='item' index='index' separator=','>",
      "(#{item.id}, #{item.roleId}, #{item.permissionId}, #{item.createTime}, #{item.updateTime}, #{item.status})",
      "</foreach>",
      "</script>"
  })
  void batchInsert(@Param("baseRolePermissions") List<BaseRoleAuthPermission> baseRolePermissions);

  @Select("select brap.permission_id from base_role_auth_permission brap where brap.role_id in (select bura.role_id from base_user_role_auth bura where bura.user_id=#{userId} and bura.status=1) and brap.status=1")
  List<String> getPermissionIdsByUserId(@Param("userId")Integer userId);


}
