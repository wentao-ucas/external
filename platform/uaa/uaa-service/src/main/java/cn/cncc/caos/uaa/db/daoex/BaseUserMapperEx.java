package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserAndRoleName;
import cn.cncc.caos.uaa.db.dao.BaseUserMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseUserMapperEx extends BaseUserMapper {

    //zhoujie add start from here
    @Select({
            "select * from base_user",
            "where user_name = #{user_name,jdbcType=VARCHAR}"
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
    BaseUser getUserByUserName(String userName);

    @Select({
            "select * from base_user where is_valid = 1"
    })
    @ResultMap("cluster_base_user")
    public List<BaseUser> selectAll();

    @Select({
            "select * from base_user limit #{pageStart},#{pageSize} order by update_time desc"
    })
    @ResultMap("cluster_base_user")
    public List<BaseUser> selectPages();

    @Select({
            "select * from base_user where is_valid = 1 and id in (select user_id from base_user_rel_role where role_id = #{role_id,jdbcType=INTEGER}) order by base_user.id"
    })
    @ResultMap("cluster_base_user")
    public List<BaseUser> selectUsersByRoleId(int roleId);

    @Select({
            "<script>",
            "select * from base_user where is_valid = 1 and id in (select user_id from base_user_rel_role where role_id in ",
                    "<foreach collection='roleIdList' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                    "</foreach>",
                    ")order by base_user.id",
            "</script>"
    })
    @ResultMap("cluster_base_user")
    public List<BaseUser> selectUsersByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    @Select({
            "<script>",
            "select base_user.*,base_role.role_name from base_user left join base_user_rel_role on base_user.id=base_user_rel_role.user_id left join",
            "base_role on base_role.id = base_user_rel_role.role_id where",
            "base_user.is_valid=1 and base_user_rel_role.is_valid=1 and base_role.is_valid=1",
            "and base_role.role_name in ",
            "<foreach collection='roleNameList' item='name' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "</script>"
    })
    @Results(value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="last_login_time", property="lastLoginTime", jdbcType=JdbcType.DATE),
        @Result(column="image_url", property="imageUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_online", property="isOnline", jdbcType=JdbcType.INTEGER),
        @Result(column="is_admin", property="isAdmin", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.DATE),
        @Result(column="dep_id", property="depId", jdbcType=JdbcType.INTEGER),
        @Result(column="location_name", property="locationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.DATE),
        @Result(column="is_public_user", property="isPublicUser", jdbcType=JdbcType.INTEGER),
        @Result(column="company_name", property="companyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR)
    })
    public List<BaseUserAndRoleName> selectUsersByRoleNameList(@Param("roleNameList") List<String> roleNameList);

    @Select({"select * from base_user where id in (select user_id from base_user_rel_role where role_id in",
            "(select role_id_value from base_role_rel_role where role_id_key = #{roleIdKey,jdbcType=INTEGER} and role_rel_usage = #{roleRelUsage,jdbcType=VARCHAR}));"})
    @ResultMap("cluster_base_user")
    List<BaseUser> getUserListByRoleIdKey(@Param("roleIdKey") Integer roleIdKey, @Param("roleRelUsage") String roleRelUsage);


    @Select("select * from base_user where id=#{userId}")
  BaseUser getUserById(@Param("userId") int userId);

    @Insert({
        "insert into base_user (",
        "user_name, real_name, password, phone, email, image_url, is_online, is_admin, create_time, dep_id, location_name, is_valid, update_time, is_public_user, company_name, duty_role)",
        " values (#{bu.userName},#{bu.realName},#{bu.password},#{bu.phone},#{bu.email},#{bu.imageUrl},#{bu.isOnline},#{bu.isAdmin}, #{bu.createTime},#{bu.depId},#{bu.locationName},#{bu.isValid},#{bu.updateTime},#{bu.isPublicUser},#{bu.companyName}, #{bu.dutyRole})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAndGetId(@Param("bu") BaseUser bu);


    @Select("select * from base_user where is_valid = 1 and real_name = #{realName}")
    BaseUser getBaseUserByRealName(@Param("realName") String realName);

  @Insert({
      "<script>",
      "insert into base_user (id, create_time, update_time,  user_name, real_name, email, password, phone, last_login_time, image_url, is_online, is_admin, dep_id, location_name, is_valid, is_public_user, company_name, duty_role) values ",
      "<foreach collection='userList' item='item' index='index' separator=','>",
      "(#{item.id}, #{item.createTime}, #{item.updateTime}, #{item.userName}, #{item.realName}, #{item.email}, #{item.password}, #{item.phone}, #{item.lastLoginTime}, #{item.imageUrl}, #{item.isOnline}, #{item.isAdmin}, #{item.depId}, #{item.locationName}, #{item.isValid}, #{item.isPublicUser}, #{item.companyName}, #{item.dutyRole})",
      "</foreach>",
      "on duplicate key update update_time = values(update_time), user_name = values(user_name), real_name = values(real_name), email = values(email), password = values(password), phone = values(phone), last_login_time = values(last_login_time), image_url = values(image_url), is_online = values(is_online), company_name = values(company_name), duty_role = values(duty_role), is_admin = values(is_admin), dep_id = values(dep_id), location_name = values(location_name), is_valid = values(is_valid), is_public_user = values(is_public_user)",
      "</script>"
  })
  void batchUpdate(@Param("userList") List<BaseUser> baseUserList);

  @Insert({
          "<script>",
          "insert into base_user (id, user_name, update_time, real_name, email, phone, dep_id, location_name, is_valid) values ",
          "<foreach collection='userList' item='item' index='index' separator=','>",
          "(#{item.id}, #{item.userName}, #{item.updateTime}, #{item.realName}, #{item.email}, #{item.phone}, #{item.depId}, #{item.locationName}, #{item.isValid})",
          "</foreach>",
          "on duplicate key update update_time = values(update_time), user_name = values(user_name), real_name = values(real_name), email = values(email), phone = values(phone), dep_id = values(dep_id), location_name = values(location_name), is_valid = values(is_valid)",
          "</script>"
  })
  void batchUpdateIops(@Param("userList") List<BaseUser> baseUserList);

  @Select("select id,user_name userName,real_name realName,email email,dep_id depId,location_name locationName,phone phone,company_name companyName,duty_role dutyRole from base_user where is_valid = 1 and dep_id = #{depId}")
  List<Map<String, Object>> getUserMapByDepId(@Param("depId") Integer depId);

  @Select({
          "<script>",
          "select id, real_name realName ",
          "from base_user ",
          "where is_valid = 1 ",
          "and dep_id in ",
          "<foreach collection='depIdList' item='id' open='(' separator=',' close=')'>",
          "#{id}",
          "</foreach>",
          "</script>"
  })
  List<Map<String, Object>> getUserMapByDepIdList(@Param("depIdList") List<Integer> depIdList);

  @Select({
      "<script>",
      "select id, real_name realName, location_name locationName",
      "from base_user ",
      "where is_valid = 1 ",
      "and dep_id in ",
      "<foreach collection='depIdList' item='id' open='(' separator=',' close=')'>",
      "#{id}",
      "</foreach>",
      "</script>"
  })
  List<Map<String, Object>> getUserMapByDepIdList1(@Param("depIdList") List<Integer> depIdList);

  @Select({
          "<script>",
          "select * from base_user",
          "where id in (select distinct(user_id) from base_user_rel_role where role_id in (select id from base_role where role_name like '%领导%'))",
          "and is_valid = 1",
          "</script>"
  })
  List<BaseUser> getLeaderUserList();

}
