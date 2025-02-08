package cn.cncc.caos.uaa.db.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static cn.cncc.caos.uaa.db.dao.BaseUserIndividuationConfigDynamicSqlSupport.*;

import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
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
import cn.cncc.caos.uaa.db.pojo.BaseUserIndividuationConfig;

@Mapper
public interface BaseUserIndividuationConfigMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseUserIndividuationConfig> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseUserIndividuationConfigResult")
    BaseUserIndividuationConfig selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseUserIndividuationConfigResult", value = {
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="module_id", property="moduleId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="module_name", property="moduleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.VARCHAR),
        @Result(column="mail_status", property="mailStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="sms_status", property="smsStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseUserIndividuationConfig> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseUserIndividuationConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserIndividuationConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer userId_, String moduleId_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserIndividuationConfig)
                .where(userId, isEqualTo(userId_))
                .and(moduleId, isEqualTo(moduleId_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseUserIndividuationConfig record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserIndividuationConfig)
                .map(userId).toProperty("userId")
                .map(moduleId).toProperty("moduleId")
                .map(moduleName).toProperty("moduleName")
                .map(systemId).toProperty("systemId")
                .map(mailStatus).toProperty("mailStatus")
                .map(smsStatus).toProperty("smsStatus")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseUserIndividuationConfig record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserIndividuationConfig)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(moduleId).toPropertyWhenPresent("moduleId", record::getModuleId)
                .map(moduleName).toPropertyWhenPresent("moduleName", record::getModuleName)
                .map(systemId).toPropertyWhenPresent("systemId", record::getSystemId)
                .map(mailStatus).toPropertyWhenPresent("mailStatus", record::getMailStatus)
                .map(smsStatus).toPropertyWhenPresent("smsStatus", record::getSmsStatus)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserIndividuationConfig>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, userId, moduleId, moduleName, systemId, mailStatus, smsStatus, createTime, updateTime)
                .from(baseUserIndividuationConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserIndividuationConfig>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, userId, moduleId, moduleName, systemId, mailStatus, smsStatus, createTime, updateTime)
                .from(baseUserIndividuationConfig);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseUserIndividuationConfig selectByPrimaryKey(Integer userId_, String moduleId_) {
        return SelectDSL.selectWithMapper(this::selectOne, userId, moduleId, moduleName, systemId, mailStatus, smsStatus, createTime, updateTime)
                .from(baseUserIndividuationConfig)
                .where(userId, isEqualTo(userId_))
                .and(moduleId, isEqualTo(moduleId_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseUserIndividuationConfig record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserIndividuationConfig)
                .set(userId).equalTo(record::getUserId)
                .set(moduleId).equalTo(record::getModuleId)
                .set(moduleName).equalTo(record::getModuleName)
                .set(systemId).equalTo(record::getSystemId)
                .set(mailStatus).equalTo(record::getMailStatus)
                .set(smsStatus).equalTo(record::getSmsStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseUserIndividuationConfig record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserIndividuationConfig)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(moduleId).equalToWhenPresent(record::getModuleId)
                .set(moduleName).equalToWhenPresent(record::getModuleName)
                .set(systemId).equalToWhenPresent(record::getSystemId)
                .set(mailStatus).equalToWhenPresent(record::getMailStatus)
                .set(smsStatus).equalToWhenPresent(record::getSmsStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseUserIndividuationConfig record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserIndividuationConfig)
                .set(moduleName).equalTo(record::getModuleName)
                .set(systemId).equalTo(record::getSystemId)
                .set(mailStatus).equalTo(record::getMailStatus)
                .set(smsStatus).equalTo(record::getSmsStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(userId, isEqualTo(record::getUserId))
                .and(moduleId, isEqualTo(record::getModuleId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseUserIndividuationConfig record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserIndividuationConfig)
                .set(moduleName).equalToWhenPresent(record::getModuleName)
                .set(systemId).equalToWhenPresent(record::getSystemId)
                .set(mailStatus).equalToWhenPresent(record::getMailStatus)
                .set(smsStatus).equalToWhenPresent(record::getSmsStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(userId, isEqualTo(record::getUserId))
                .and(moduleId, isEqualTo(record::getModuleId))
                .build()
                .execute();
    }
}