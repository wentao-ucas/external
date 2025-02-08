package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.dao.BaseRoleMapper;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseRoleMapperEx extends BaseRoleMapper {

    //zhoujie add start from here
    @Select({"SELECT br.*, bur.user_id as user_id FROM",
            "base_user_rel_role bur LEFT JOIN base_role br ON bur.role_id = br.id",
            "WHERE br.is_valid=1 and bur.is_valid=1 and bur.user_id = #{userId,jdbcType=INTEGER}"})
    @Results(
            id="cluster_base_role",
            value={
                    @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
                    @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
                    @Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR),
                    @Result(column="sys_id", property="sysId", jdbcType=JdbcType.INTEGER),
                    @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
                    @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
            })
    List<BaseRole> getRoleByUserId(@Param("userId") Integer userId);

    @Select({"SELECT br.id as id, br.role_name as roleName,br.role_desc as roleDesc,br.role_key as roleKey, bs.sys_name as sysName, bs.sys_title as sysTitle FROM",
            "base_user_rel_role bur LEFT JOIN base_role br ON bur.role_id = br.id left join base_sys as bs on br.sys_id = bs.id",
            "WHERE br.is_valid=1 and bur.is_valid=1 and bur.user_id = #{userId,jdbcType=INTEGER}"})
    List<Map<String,Object>> getSystemAndRoleMapByUserId(@Param("userId") Integer userId);

    @Select({"SELECT br.* from base_role as br left join base_sys as bs on br.sys_id = bs.id",
            "WHERE br.is_valid=1 and bs.sys_name = #{sysName,jdbcType=VARCHAR}"})
    @ResultMap("cluster_base_role")
    List<BaseRole> getRoleBySysName(@Param("sysName") String sysName);

    @Select({"SELECT br.* from base_role as br",
            "WHERE br.is_valid=1 and br.role_key = #{roleKey,jdbcType=VARCHAR}"})
    @ResultMap("cluster_base_role")
    List<BaseRole> getRoleByRoleKey(@Param("roleKey") String roleKey);

    @Select({"<script> " +
            "SELECT br.* from base_role as br",
            "WHERE br.is_valid=1 and br.role_key in" +
            "<foreach collection='roleKeyArray' item='item' index='index' open='(' close=')' separator=','>",
            "  #{item}",
            "</foreach>",
            "</script>"})
    @ResultMap("cluster_base_role")
    List<BaseRole> getRoleByRoleKeyArray(@Param("roleKeyArray") List<String> roleKeyArray);

    // WHERE bu.is_valid =1 为了防止有人删库，做容错处理
    @Select({"SELECT br.id as id, br.role_name as roleName,br.role_desc as roleDesc, bs.sys_name as sysName, bs.sys_title as sysTitle,bu.id as userId, bu.user_name as userName, bu.real_name as userRealName FROM",
            "base_user_rel_role bur LEFT JOIN base_role br ON bur.role_id = br.id left join base_sys as bs on br.sys_id = bs.id ",
            "left join base_user bu on bur.user_id = bu.id ",
            "WHERE br.is_valid=1 and bur.is_valid=1 and bu.is_valid =1 and br.role_key = #{roleKey,jdbcType=VARCHAR}"})
    List<Map<String,Object>> getRoleAndUsersByRoleKey(@Param("roleKey") String roleKey);

    @Select({"select * from base_role where id in (select role_id_value from base_role_rel_role where role_id_key = #{roleIdKey,jdbcType=INTEGER} and and role_rel_usage = #{roleRelUsage,jdbcType=VARCHAR})"})
    @ResultMap("cluster_base_role")
    List<BaseRole> getRoleListByRoleIdKey(@Param("roleIdKey") Integer roleIdKey, @Param("roleRelUsage") String roleRelUsage);

    @Insert("insert into base_role (role_name,role_desc,sys_id,update_time,is_valid)values(#{baseRole.roleName},#{baseRole.roleDesc},#{baseRole.sysId},#{baseRole.updateTime},#{baseRole.isValid})")
    @Options(useGeneratedKeys=true, keyProperty="baseRole.id",keyColumn ="id")
    int insertAndGetId(@Param("baseRole") BaseRole baseRole);

    @Select({"SELECT DISTINCT t1.role_name FROM base_role t1 ",
            "INNER JOIN base_user_rel_role t2 on t1.id=t2.role_id ",
            "INNER JOIN base_user t3 on t3.id=t2.user_id ",
            "WHERE t1.is_valid=1 and t2.is_valid=1 and t3.is_valid=1 and t3.dep_id=#{depId} ",
    })
    @ResultMap("cluster_base_role")
    List<BaseRole> getRoleNameByDepid(@Param("depId") String depId);


    @Select({
            "select br.* from base_role br ",
            "left join base_user_rel_role burr ",
            "on burr.role_id=br.id ",
            "where br.role_key='roleOperationDep' and burr.user_id=#{userId} and br.is_valid=1 and burr.is_valid=1"
    })
    List<BaseRole> getOperationDepRoleByUserId(@Param("userId") Integer userId);
}
