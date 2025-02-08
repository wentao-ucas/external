package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.pojo.AllServerConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AllServerConfigMapperEx {

  @Select("select * from all_server_config where status=1 and (location='all' or location=#{locationType}) and (server_env='all' or server_env=#{envType})")
  List<AllServerConfig> getByServerEnv(@Param("envType") String envType, @Param("locationType") String locationType);

  @Select("select * from all_server_config where module=#{module} and status=1 and (location='all' or location=#{locationType}) and (server_env='all' or server_env=#{envType})")
  List<AllServerConfig> getByServerEnvAndModule(@Param("envType") String type, @Param("module") String module, @Param("locationType") String locationType);
}
