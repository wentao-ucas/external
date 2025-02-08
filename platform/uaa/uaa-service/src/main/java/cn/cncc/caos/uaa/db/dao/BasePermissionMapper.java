package cn.cncc.caos.uaa.db.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static cn.cncc.caos.uaa.db.dao.BasePermissionDynamicSqlSupport.*;

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
import cn.cncc.caos.uaa.db.pojo.BasePermission;

@Mapper
public interface BasePermissionMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BasePermission> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BasePermissionResult")
    BasePermission selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BasePermissionResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="path", property="path", jdbcType=JdbcType.VARCHAR),
        @Result(column="parent_name", property="parentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="seq", property="seq", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.VARCHAR),
        @Result(column="notice", property="notice", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BasePermission> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(basePermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, basePermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, basePermission)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BasePermission record) {
        return insert(SqlBuilder.insert(record)
                .into(basePermission)
                .map(id).toProperty("id")
                .map(name).toProperty("name")
                .map(title).toProperty("title")
                .map(path).toProperty("path")
                .map(parentName).toProperty("parentName")
                .map(seq).toProperty("seq")
                .map(status).toProperty("status")
                .map(env).toProperty("env")
                .map(notice).toProperty("notice")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BasePermission record) {
        return insert(SqlBuilder.insert(record)
                .into(basePermission)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(name).toPropertyWhenPresent("name", record::getName)
                .map(title).toPropertyWhenPresent("title", record::getTitle)
                .map(path).toPropertyWhenPresent("path", record::getPath)
                .map(parentName).toPropertyWhenPresent("parentName", record::getParentName)
                .map(seq).toPropertyWhenPresent("seq", record::getSeq)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(env).toPropertyWhenPresent("env", record::getEnv)
                .map(notice).toPropertyWhenPresent("notice", record::getNotice)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BasePermission>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, name, title, path, parentName, seq, status, env, notice, createTime, updateTime)
                .from(basePermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BasePermission>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, name, title, path, parentName, seq, status, env, notice, createTime, updateTime)
                .from(basePermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BasePermission selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, name, title, path, parentName, seq, status, env, notice, createTime, updateTime)
                .from(basePermission)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BasePermission record) {
        return UpdateDSL.updateWithMapper(this::update, basePermission)
                .set(id).equalTo(record::getId)
                .set(name).equalTo(record::getName)
                .set(title).equalTo(record::getTitle)
                .set(path).equalTo(record::getPath)
                .set(parentName).equalTo(record::getParentName)
                .set(seq).equalTo(record::getSeq)
                .set(status).equalTo(record::getStatus)
                .set(env).equalTo(record::getEnv)
                .set(notice).equalTo(record::getNotice)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BasePermission record) {
        return UpdateDSL.updateWithMapper(this::update, basePermission)
                .set(id).equalToWhenPresent(record::getId)
                .set(name).equalToWhenPresent(record::getName)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(path).equalToWhenPresent(record::getPath)
                .set(parentName).equalToWhenPresent(record::getParentName)
                .set(seq).equalToWhenPresent(record::getSeq)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(env).equalToWhenPresent(record::getEnv)
                .set(notice).equalToWhenPresent(record::getNotice)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BasePermission record) {
        return UpdateDSL.updateWithMapper(this::update, basePermission)
                .set(name).equalTo(record::getName)
                .set(title).equalTo(record::getTitle)
                .set(path).equalTo(record::getPath)
                .set(parentName).equalTo(record::getParentName)
                .set(seq).equalTo(record::getSeq)
                .set(status).equalTo(record::getStatus)
                .set(env).equalTo(record::getEnv)
                .set(notice).equalTo(record::getNotice)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BasePermission record) {
        return UpdateDSL.updateWithMapper(this::update, basePermission)
                .set(name).equalToWhenPresent(record::getName)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(path).equalToWhenPresent(record::getPath)
                .set(parentName).equalToWhenPresent(record::getParentName)
                .set(seq).equalToWhenPresent(record::getSeq)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(env).equalToWhenPresent(record::getEnv)
                .set(notice).equalToWhenPresent(record::getNotice)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}