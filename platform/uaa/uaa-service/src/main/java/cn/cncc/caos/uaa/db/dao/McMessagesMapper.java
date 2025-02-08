package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.McMessages;
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

import static cn.cncc.caos.uaa.db.dao.McMessagesDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface McMessagesMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<McMessages> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("McMessagesResult")
    McMessages selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="McMessagesResult", value = {
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
            @Result(column="user_real_name", property="userRealName", jdbcType=JdbcType.VARCHAR),
            @Result(column="system", property="system", jdbcType=JdbcType.TINYINT),
            @Result(column="function", property="function", jdbcType=JdbcType.TINYINT),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="is_can_skip", property="isCanSkip", jdbcType=JdbcType.TINYINT),
            @Result(column="params", property="params", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
            @Result(column="read_time", property="readTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="is_valid", property="isValid", jdbcType=JdbcType.TINYINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<McMessages> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(mcMessages);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, mcMessages);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, mcMessages)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(McMessages record) {
        return insert(SqlBuilder.insert(record)
                .into(mcMessages)
                .map(id).toProperty("id")
                .map(userId).toProperty("userId")
                .map(userRealName).toProperty("userRealName")
                .map(system).toProperty("system")
                .map(function).toProperty("function")
                .map(title).toProperty("title")
                .map(isCanSkip).toProperty("isCanSkip")
                .map(params).toProperty("params")
                .map(status).toProperty("status")
                .map(readTime).toProperty("readTime")
                .map(isValid).toProperty("isValid")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .map(content).toProperty("content")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(McMessages record) {
        return insert(SqlBuilder.insert(record)
                .into(mcMessages)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(userRealName).toPropertyWhenPresent("userRealName", record::getUserRealName)
                .map(system).toPropertyWhenPresent("system", record::getSystem)
                .map(function).toPropertyWhenPresent("function", record::getFunction)
                .map(title).toPropertyWhenPresent("title", record::getTitle)
                .map(isCanSkip).toPropertyWhenPresent("isCanSkip", record::getIsCanSkip)
                .map(params).toPropertyWhenPresent("params", record::getParams)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(readTime).toPropertyWhenPresent("readTime", record::getReadTime)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .map(content).toPropertyWhenPresent("content", record::getContent)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<McMessages>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, userId, userRealName, system, function, title, isCanSkip, params, status, readTime, isValid, createTime, updateTime, content)
                .from(mcMessages);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<McMessages>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, userId, userRealName, system, function, title, isCanSkip, params, status, readTime, isValid, createTime, updateTime, content)
                .from(mcMessages);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default McMessages selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, userId, userRealName, system, function, title, isCanSkip, params, status, readTime, isValid, createTime, updateTime, content)
                .from(mcMessages)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(McMessages record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessages)
                .set(id).equalTo(record::getId)
                .set(userId).equalTo(record::getUserId)
                .set(userRealName).equalTo(record::getUserRealName)
                .set(system).equalTo(record::getSystem)
                .set(function).equalTo(record::getFunction)
                .set(title).equalTo(record::getTitle)
                .set(isCanSkip).equalTo(record::getIsCanSkip)
                .set(params).equalTo(record::getParams)
                .set(status).equalTo(record::getStatus)
                .set(readTime).equalTo(record::getReadTime)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(content).equalTo(record::getContent);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(McMessages record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessages)
                .set(id).equalToWhenPresent(record::getId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(userRealName).equalToWhenPresent(record::getUserRealName)
                .set(system).equalToWhenPresent(record::getSystem)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(isCanSkip).equalToWhenPresent(record::getIsCanSkip)
                .set(params).equalToWhenPresent(record::getParams)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(readTime).equalToWhenPresent(record::getReadTime)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(content).equalToWhenPresent(record::getContent);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(McMessages record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessages)
                .set(userId).equalTo(record::getUserId)
                .set(userRealName).equalTo(record::getUserRealName)
                .set(system).equalTo(record::getSystem)
                .set(function).equalTo(record::getFunction)
                .set(title).equalTo(record::getTitle)
                .set(isCanSkip).equalTo(record::getIsCanSkip)
                .set(params).equalTo(record::getParams)
                .set(status).equalTo(record::getStatus)
                .set(readTime).equalTo(record::getReadTime)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(content).equalTo(record::getContent)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(McMessages record) {
        return UpdateDSL.updateWithMapper(this::update, mcMessages)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(userRealName).equalToWhenPresent(record::getUserRealName)
                .set(system).equalToWhenPresent(record::getSystem)
                .set(function).equalToWhenPresent(record::getFunction)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(isCanSkip).equalToWhenPresent(record::getIsCanSkip)
                .set(params).equalToWhenPresent(record::getParams)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(readTime).equalToWhenPresent(record::getReadTime)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(content).equalToWhenPresent(record::getContent)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}