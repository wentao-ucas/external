package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.BasePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BasePostMapperEx {

  @Select({
          "<script>",
          "select * from base_post where ",
          "1=1 ",
          "<when test='basePost.postCode != null'>",
          "AND post_code like CONCAT('%',#{basePost.postCode},'%') ",
          "</when>",
          "<when test='basePost.postName != null'>",
          "AND post_name like CONCAT('%',#{basePost.postName},'%') ",
          "</when>",
          "<when test='basePost.status != null'>",
          "AND status = #{basePost.status} ",
          "</when>",
          "<when test='basePost.status == null'>",
          "AND status != 0 ",
          "</when>",
          "order by post_sort",
          "</script>"
  })
  List<BasePost> selectBasePostList(@Param("basePost") BasePost basePost);

  @Select({
          "<script>",
          "select count(*) from base_post where ",
          "1=1 ",
          "<when test='basePost.postCode != null'>",
          "AND post_code like CONCAT('%',#{basePost.postCode},'%') ",
          "</when>",
          "<when test='basePost.postName != null'>",
          "AND post_name like CONCAT('%',#{basePost.postName},'%') ",
          "</when>",
          "<when test='basePost.status != null'>",
          "AND status = #{basePost.status} ",
          "</when>",
          "<when test='basePost.status == null'>",
          "AND status != 0 ",
          "</when>",
          "</script>"
  })
  long selectBasePostCount(@Param("basePost") BasePost basePost);

}
