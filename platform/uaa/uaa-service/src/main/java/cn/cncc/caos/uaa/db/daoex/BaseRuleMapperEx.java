package cn.cncc.caos.uaa.db.daoex;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.Set;

@Mapper
public interface BaseRuleMapperEx {
  @Update({
      "<script>",
      "update base_rule set update_time=#{date},status=0 where id in ",
      "<foreach collection='baseRuleIdsDelete' item='id' index='index' open='(' close=')' separator=','>",
      "#{id}",
      "</foreach>",
      "</script>"
  })
  void batchUpdateBaseRules(@Param("baseRuleIdsDelete") Set<String> baseRuleIdsDelete, @Param("date") Date date);

}
