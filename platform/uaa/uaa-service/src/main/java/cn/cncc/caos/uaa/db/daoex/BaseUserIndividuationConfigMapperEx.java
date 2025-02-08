package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.dao.BaseUserIndividuationConfigMapper;
import cn.cncc.caos.uaa.db.pojo.BaseUserIndividuationConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseUserIndividuationConfigMapperEx extends BaseUserIndividuationConfigMapper{

  @Insert({
          "<script>",
          "insert into base_user_individuation_config(user_id, module_id, module_name, system_id, mail_status, sms_status, create_time, update_time) values ",
          "<foreach collection='list' item='item' index='index' separator=','>",
          "(#{item.userId}, #{item.moduleId}, #{item.moduleName}, #{item.systemId}, #{item.mailStatus}, #{item.smsStatus},#{item.createTime},#{item.updateTime})",
          "</foreach>",
          "on duplicate key update mail_status = values(mail_status), sms_status = values(sms_status), update_time = values(update_time)",
          "</script>"
  })
  void insertOrUpdate(List<BaseUserIndividuationConfig> list);

}
