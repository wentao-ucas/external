package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.BaseUserRoleAuth;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface BaseUserRoleAuthMapperEx {

  @Update("update base_user_role_auth set update_time=#{date},status=0 where role_id=#{roleId}")
  void deleteRoleAuthPermission(@Param("date") Date date, @Param("roleId") String roleId);


  @Select({
      "select * from base_user where is_valid = 1 and id in (select user_id from base_user_role_auth where role_id = #{role_id} and status=1) order by base_user.id"
  })
  @Results(
      id="cluster_base_user",
      value= {
          @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
          @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
          @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
          @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
          @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
          @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
          @Result(column="last_login_time", property="lastLoginTime", jdbcType=JdbcType.TIMESTAMP),
          @Result(column="image_url", property="imageUrl", jdbcType=JdbcType.VARCHAR),
          @Result(column="is_online", property="isOnline", jdbcType=JdbcType.INTEGER),
          @Result(column="is_admin", property="isAdmin", jdbcType=JdbcType.INTEGER),
          @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
          @Result(column="dep_id", property="depId", jdbcType=JdbcType.INTEGER),
          @Result(column="location_name", property="locationName", jdbcType=JdbcType.VARCHAR),
          @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
          @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
      })
  List<BaseUser> selectUsersByRoleAuthId(String roleId);

  @Insert(
          {
                  "<script>",
                  "insert into base_user_role_auth (id, role_id, user_id, create_time, update_time, status) values ",
                  "<foreach collection='list' item='item' index='index' separator=','>",
                  "(#{item.id}, #{item.roleId}, #{item.userId}, #{item.createTime}, #{item.updateTime}, #{item.status})",
                  "</foreach>",
                  "</script>"
          }
  )
  void batchInsert(@Param("list") List<BaseUserRoleAuth> targetBaseUserRoleAuthList);

  @Update("update base_user_role_auth set status=0, update_time=#{currentTime} where user_id=#{targetId}")
  void deleteByUser(@Param("targetId") Integer targetId, @Param("currentTime") Date currentTime);
}
