package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseRoleAuthMapperEx {
  // WHERE bu.is_valid =1 为了防止有人删库，做容错处理
  @Select({"SELECT br.id as id, br.role_name as roleName,br.role_desc as roleDesc, bs.sys_name as sysName, bs.sys_title as sysTitle,bu.id as userId, bu.user_name as userName, bu.real_name as userRealName FROM",
      "base_user_role_auth bur LEFT JOIN base_role_auth br ON bur.role_id = br.id left join base_sys as bs on br.sys_id = bs.id ",
      "left join base_user bu on bur.user_id = bu.id ",
      "WHERE br.is_valid=1 and bur.status=1 and bu.is_valid =1 and br.role_key = #{roleKey,jdbcType=VARCHAR}"})
  List<Map<String,Object>> getRoleAndUsersByRoleKey(@Param("roleKey") String roleKey);

  @Select({"SELECT br.*, bur.user_id as user_id FROM",
      "base_user_role_auth bur LEFT JOIN base_role_auth br ON bur.role_id = br.id",
      "WHERE br.is_valid=1 and bur.status=1 and bur.user_id = #{userId,jdbcType=INTEGER}"})
  @Results(
      id="cluster_base_role",
      value={
          @Result(column="id", property="id", jdbcType= JdbcType.VARCHAR, id=true),
          @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
          @Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR),
          @Result(column="sys_id", property="sysId", jdbcType=JdbcType.INTEGER),
          @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
          @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
          @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
      })
  List<BaseRoleAuth> getRoleByUserId(@Param("userId") int userId);

  @Select({"SELECT br.* from base_role_auth as br",
      "WHERE br.is_valid=1 and br.role_key = #{roleKey,jdbcType=VARCHAR}"})
  @ResultMap("cluster_base_role")
  List<BaseRoleAuth> getRoleByRoleKey(String roleOperationDep);
}
