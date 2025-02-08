package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BaseDepMapperEx {

  @Select("insert into base_dep ")
  @Options(useGeneratedKeys=true, keyProperty="baseDep.id",keyColumn ="id")
  void insertAndGetPrimaryKey(@Param("baseDep")BaseDep baseDep);

  @Insert({
      "<script>",
      "insert into base_dep (dep_code, update_time, dep_name, dep_desc, parent_id,level, is_valid) values ",
      "<foreach collection='depList' item='item' index='index' separator=','>",
      "(#{item.depCode}, #{item.updateTime}, #{item.depName}, #{item.depDesc}, #{item.parentId}, #{item.level}, #{item.isValid})",
      "</foreach>",
      "on duplicate key update update_time = values(update_time), dep_name = values(dep_name), dep_desc = values(dep_desc), parent_id = values(parent_id), level = values(level), is_valid = values(is_valid)",
      "</script>"
  })
  void batchUpdate(@Param("depList") List<BaseDep> depList);

  @Select({"select * from base_dep"})
  List<BaseDep> selectAll();

  @Select({
          "<script>",
          "select distinct id from base_dep where is_valid=1 and dep_name in ",
          "<foreach item='item' index='index' collection='depNames' open='(' separator=',' close=')'>",
          "#{item}",
          "</foreach>",
          "</script>"
  })
  List<BaseDep> getDepIdByNames(@Param("depNames")List<String> depNames);
}
