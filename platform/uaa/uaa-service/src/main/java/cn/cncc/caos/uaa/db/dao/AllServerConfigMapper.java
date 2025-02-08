package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.AllServerConfig;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import javax.annotation.Generated;
import java.util.List;

import static cn.cncc.caos.uaa.db.dao.AllServerConfigDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface AllServerConfigMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<AllServerConfig> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("AllServerConfigResult")
    AllServerConfig selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="AllServerConfigResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="module", property="module", jdbcType=JdbcType.VARCHAR),
        @Result(column="function", property="function", jdbcType=JdbcType.VARCHAR),
        @Result(column="config_key", property="configKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="config_value", property="configValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="config_desc", property="configDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="server_env", property="serverEnv", jdbcType=JdbcType.VARCHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR),
        @Result(column="c_time", property="cTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="u_time", property="uTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    List<AllServerConfig> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(allServerConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, allServerConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, allServerConfig)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(AllServerConfig record) {
        return insert(SqlBuilder.insert(record)
                .into(allServerConfig)
                .map(id).toProperty("id")
                .map(module).toProperty("module")
                .map(function).toProperty("function")
                .map(configKey).toProperty("configKey")
                .map(configValue).toProperty("configValue")
                .map(configDesc).toProperty("configDesc")
                .map(serverEnv).toProperty("serverEnv")
                .map(location).toProperty("location")
                .map(cTime).toProperty("cTime")
                .map(uTime).toProperty("uTime")
                .map(status).toProperty("status")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(AllServerConfig record) {
        return insert(SqlBuilder.insert(record)
                .into(allServerConfig)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(module).toPropertyWhenPresent("module", record::getModule)
                .map(function).toPropertyWhenPresent("function", record::getFunction)
                .map(configKey).toPropertyWhenPresent("configKey", record::getConfigKey)
                .map(configValue).toPropertyWhenPresent("configValue", record::getConfigValue)
                .map(configDesc).toPropertyWhenPresent("configDesc", record::getConfigDesc)
                .map(serverEnv).toPropertyWhenPresent("serverEnv", record::getServerEnv)
                .map(location).toPropertyWhenPresent("location", record::getLocation)
                .map(cTime).toPropertyWhenPresent("cTime", record::getcTime)
                .map(uTime).toPropertyWhenPresent("uTime", record::getuTime)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<AllServerConfig>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, module, function, configKey, configValue, configDesc, serverEnv, location, cTime, uTime, status)
                .from(allServerConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<AllServerConfig>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, module, function, configKey, configValue, configDesc, serverEnv, location, cTime, uTime, status)
                .from(allServerConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default AllServerConfig selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, module, function, configKey, configValue, configDesc, serverEnv, location, cTime, uTime, status)
                .from(allServerConfig)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(AllServerConfig record) {
        return UpdateDSL.updateWithMapper(this::update, allServerConfig)
                .set(id).equalTo(record::getId)
                .set(module).equalTo(record::getModule)
                .set(function).equalTo(record::getFunction)
                .set(configKey).equalTo(record::getConfigKey)
                .set(configValue).equalTo(record::getConfigValue)
                .set(configDesc).equalTo(record::getConfigDesc)
                .set(serverEnv).equalTo(record::getServerEnv)
                .set(location).equalTo(record::getLocation)
                .set(cTime).equalTo(record::getcTime)
                .set(uTime).equalTo(record::getuTime)
                .set(status).equalTo(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(AllServerConfig record) {
        return UpdateDSL.updateWithMapper(this::update, allServerConfig)
                .set(id).equalToWhenPresent(record::getId)
                .set(module).equalToWhenPresent(record::getModule)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(configKey).equalToWhenPresent(record::getConfigKey)
                .set(configValue).equalToWhenPresent(record::getConfigValue)
                .set(configDesc).equalToWhenPresent(record::getConfigDesc)
                .set(serverEnv).equalToWhenPresent(record::getServerEnv)
                .set(location).equalToWhenPresent(record::getLocation)
                .set(cTime).equalToWhenPresent(record::getcTime)
                .set(uTime).equalToWhenPresent(record::getuTime)
                .set(status).equalToWhenPresent(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(AllServerConfig record) {
        return UpdateDSL.updateWithMapper(this::update, allServerConfig)
                .set(module).equalTo(record::getModule)
                .set(function).equalTo(record::getFunction)
                .set(configKey).equalTo(record::getConfigKey)
                .set(configValue).equalTo(record::getConfigValue)
                .set(configDesc).equalTo(record::getConfigDesc)
                .set(serverEnv).equalTo(record::getServerEnv)
                .set(location).equalTo(record::getLocation)
                .set(cTime).equalTo(record::getcTime)
                .set(uTime).equalTo(record::getuTime)
                .set(status).equalTo(record::getStatus)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(AllServerConfig record) {
        return UpdateDSL.updateWithMapper(this::update, allServerConfig)
                .set(module).equalToWhenPresent(record::getModule)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(configKey).equalToWhenPresent(record::getConfigKey)
                .set(configValue).equalToWhenPresent(record::getConfigValue)
                .set(configDesc).equalToWhenPresent(record::getConfigDesc)
                .set(serverEnv).equalToWhenPresent(record::getServerEnv)
                .set(location).equalToWhenPresent(record::getLocation)
                .set(cTime).equalToWhenPresent(record::getcTime)
                .set(uTime).equalToWhenPresent(record::getuTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}