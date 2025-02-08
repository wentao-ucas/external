package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseUserRelRoleMapperEx {
  @Select({
          "<script>",
          "select br.role_name roleName, bu.real_name realName from base_user_rel_role bur ",
          "LEFT JOIN base_user bu on bur.user_id=bu.id ",
          "LEFT JOIN base_role br on br.id=bur.role_id ",
          "where bur.is_valid=1 and bur.role_id and bu.is_valid=1 in ",
          "<foreach collection='list' item='roleId' open='(' separator=',' close=')'>",
          "#{roleId}",
          "</foreach>",
          "</script>"
  })
  List<Map<String, String>> getUserAndRoleByRoleId(@Param("list") List<Integer> roleIdList);

  @Insert(
          {
                  "<script>",
                  "insert into base_user_rel_role (id, role_id, user_id, update_time, is_valid) values ",
                  "<foreach collection='list' item='item' index='index' separator=','>",
                  "(#{item.id}, #{item.roleId}, #{item.userId}, #{item.updateTime}, #{item.isValid})",
                  "</foreach>",
                  "</script>"
          }
  )
  void batchInsert(@Param("list") List<BaseUserRelRole> targetBaseUserRelRoleList);

  @Delete("delete from base_user_rel_role where user_id = #{targetId}")
  void deleteByUser(@Param("targetId") Integer targetId);

  @Update({"<script>",
          "<foreach collection=\"list\" item=\"item\" separator=\";\">",
          " delete from",
          " base_user_rel_role ",
          " where id = #{item.id}",
          "</foreach>",
          "</script>"})
  void batchDelete(@Param("list") List<BaseUserRelRole> baseUserRelRoleList);
}
