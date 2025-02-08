package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.McMessagesHistory;
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

import static cn.cncc.caos.uaa.db.dao.McMessagesHistoryDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface McMessagesHistoryMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<McMessagesHistory> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("McMessagesHistoryResult")
    McMessagesHistory selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="McMessagesHistoryResult", value = {
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
            @Result(column="user_real_name", property="userRealName", jdbcType=JdbcType.VARCHAR),
            @Result(column="system", property="system", jdbcType=JdbcType.TINYINT),
            @Result(column="function", property="function", jdbcType=JdbcType.TINYINT),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_can_skip", property="isCanSkip", jdbcType=JdbcType.TINYINT),
            @Result(column="params", property="params", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
            @Result(column="read_time", property="readTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="is_valid", property="isValid", jdbcType=JdbcType.TINYINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<McMessagesHistory> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(mcMessagesHistory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, mcMessagesHistory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, mcMessagesHistory)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(McMessagesHistory record) {
        return insert(SqlBuilder.insert(record)
                .into(mcMessagesHistory)
                .map(id).toProperty("id")
                .map(userId).toProperty("userId")
                .map(userRealName).toProperty("userRealName")
                .map(system).toProperty("system")
                .map(function).toProperty("function")
                .map(title).toProperty("title")
                .map(content).toProperty("content")
                .map(isCanSkip).toProperty("isCanSkip")
                .map(params).toProperty("params")
                .map(status).toProperty("status")
                .map(readTime).toProperty("readTime")
                .map(isValid).toProperty("isValid")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(McMessagesHistory record) {
        return insert(SqlBuilder.insert(record)
                .into(mcMessagesHistory)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(userRealName).toPropertyWhenPresent("userRealName", record::getUserRealName)
                .map(system).toPropertyWhenPresent("system", record::getSystem)
                .map(function).toPropertyWhenPresent("function", record::getFunction)
                .map(title).toPropertyWhenPresent("title", record::getTitle)
                .map(content).toPropertyWhenPresent("content", record::getContent)
                .map(isCanSkip).toPropertyWhenPresent("isCanSkip", record::getIsCanSkip)
                .map(params).toPropertyWhenPresent("params", record::getParams)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(readTime).toPropertyWhenPresent("readTime", record::getReadTime)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<McMessagesHistory>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, userId, userRealName, system, function, title, content, isCanSkip, params, status, readTime, isValid, createTime, updateTime)
                .from(mcMessagesHistory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<McMessagesHistory>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, userId, userRealName, system, function, title, content, isCanSkip, params, status, readTime, isValid, createTime, updateTime)
                .from(mcMessagesHistory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default McMessagesHistory selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, userId, userRealName, system, function, title, content, isCanSkip, params, status, readTime, isValid, createTime, updateTime)
                .from(mcMessagesHistory)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(McMessagesHistory record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessagesHistory)
                .set(id).equalTo(record::getId)
                .set(userId).equalTo(record::getUserId)
                .set(userRealName).equalTo(record::getUserRealName)
                .set(system).equalTo(record::getSystem)
                .set(function).equalTo(record::getFunction)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(isCanSkip).equalTo(record::getIsCanSkip)
                .set(params).equalTo(record::getParams)
                .set(status).equalTo(record::getStatus)
                .set(readTime).equalTo(record::getReadTime)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(McMessagesHistory record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessagesHistory)
                .set(id).equalToWhenPresent(record::getId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(userRealName).equalToWhenPresent(record::getUserRealName)
                .set(system).equalToWhenPresent(record::getSystem)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(isCanSkip).equalToWhenPresent(record::getIsCanSkip)
                .set(params).equalToWhenPresent(record::getParams)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(readTime).equalToWhenPresent(record::getReadTime)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(McMessagesHistory record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessagesHistory)
                .set(userId).equalTo(record::getUserId)
                .set(userRealName).equalTo(record::getUserRealName)
                .set(system).equalTo(record::getSystem)
                .set(function).equalTo(record::getFunction)
                .set(title).equalTo(record::getTitle)
                .set(content).equalTo(record::getContent)
                .set(isCanSkip).equalTo(record::getIsCanSkip)
                .set(params).equalTo(record::getParams)
                .set(status).equalTo(record::getStatus)
                .set(readTime).equalTo(record::getReadTime)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(McMessagesHistory record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessagesHistory)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(userRealName).equalToWhenPresent(record::getUserRealName)
                .set(system).equalToWhenPresent(record::getSystem)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(content).equalToWhenPresent(record::getContent)
                .set(isCanSkip).equalToWhenPresent(record::getIsCanSkip)
                .set(params).equalToWhenPresent(record::getParams)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(readTime).equalToWhenPresent(record::getReadTime)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}