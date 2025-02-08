package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.dao.McMessagesMapper;
import cn.cncc.caos.uaa.db.pojo.McMessages;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface McMessagesMapperEx extends McMessagesMapper {

  @Update("update mc_messages set `status` = #{status}, read_time = #{updateTime}, update_time = #{updateTime} where `status` = #{oriStatus} and user_id = #{userId} and system = #{system} and create_time < #{updateTime}")
  int batchUpdateMessagesBySystem(@Param("status") byte status, @Param("oriStatus") byte oriStatus, @Param("userId") Integer userId,
                                  @Param("system") byte system, @Param("updateTime") Date updateTime);

  @Select("select system, count(1) as count from mc_messages where user_id = #{userId} and status = #{status} group by system")
  List<Map<String, Integer>> getSystemMessagesCount(@Param("status")byte status, @Param("userId")Integer userId);

  @Insert({
          "<script>",
          "delete from mc_messages ",
          "where id in ",
          "<foreach collection='idList' item='id' open='(' separator=',' close=')'>",
          "#{id}",
          "</foreach>",
          "</script>"
  })
  void batchDelete(@Param("idList") List<String> idList);
}