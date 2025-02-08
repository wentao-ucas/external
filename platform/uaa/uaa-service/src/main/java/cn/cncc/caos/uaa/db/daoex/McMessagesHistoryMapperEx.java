package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.dao.McMessagesHistoryMapper;
import cn.cncc.caos.uaa.db.pojo.McMessages;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface McMessagesHistoryMapperEx extends McMessagesHistoryMapper {

  @Insert({
          "<script>",
          "insert into mc_messages_history (id, user_id, user_real_name, system, function, title, content, params, status, read_time, is_valid, create_time, update_time) values ",
          "<foreach collection='mcMessagesList' item='item' index='index' separator=','>",
          "(#{item.id}, #{item.userId}, #{item.userRealName}, #{item.system}, #{item.function}, #{item.title}, #{item.content}, #{item.params}, #{item.status}, #{item.readTime}, #{item.isValid}, #{item.createTime}, #{item.updateTime})",
          "</foreach>",
          "</script>"
  })
  void batchInsert(@Param("mcMessagesList") List<McMessages> mcMessagesList);

}