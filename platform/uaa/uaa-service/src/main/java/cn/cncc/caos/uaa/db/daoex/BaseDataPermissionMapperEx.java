package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.BaseDataPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BaseDataPermissionMapperEx {


  @Select({
      "<script>",
      "select * from base_data_permission where status=1 and (dep_id=#{depId} or role_id in ",
      "<foreach collection='roleIds' item='id' open='(' separator=',' close=')'>",
      "#{id}",
      "</foreach>",
      ")",
      "</script>"
  })
  List<BaseDataPermission> getDataPermissionByDepIdOrRoleId(@Param("depId") Integer depId,@Param("roleIds") List<Integer> roleIds);
}
