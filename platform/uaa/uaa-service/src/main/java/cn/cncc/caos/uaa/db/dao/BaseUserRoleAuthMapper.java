package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.BaseUserRoleAuth;
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

import static cn.cncc.caos.uaa.db.dao.BaseUserRoleAuthDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseUserRoleAuthMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseUserRoleAuth> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseUserRoleAuthResult")
    BaseUserRoleAuth selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseUserRoleAuthResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    List<BaseUserRoleAuth> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseUserRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserRoleAuth)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseUserRoleAuth record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserRoleAuth)
                .map(id).toProperty("id")
                .map(roleId).toProperty("roleId")
                .map(userId).toProperty("userId")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .map(status).toProperty("status")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseUserRoleAuth record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserRoleAuth)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserRoleAuth>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, roleId, userId, createTime, updateTime, status)
                .from(baseUserRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserRoleAuth>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, roleId, userId, createTime, updateTime, status)
                .from(baseUserRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseUserRoleAuth selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, roleId, userId, createTime, updateTime, status)
                .from(baseUserRoleAuth)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseUserRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRoleAuth)
                .set(id).equalTo(record::getId)
                .set(roleId).equalTo(record::getRoleId)
                .set(userId).equalTo(record::getUserId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(status).equalTo(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseUserRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRoleAuth)
                .set(id).equalToWhenPresent(record::getId)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(status).equalToWhenPresent(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseUserRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRoleAuth)
                .set(roleId).equalTo(record::getRoleId)
                .set(userId).equalTo(record::getUserId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(status).equalTo(record::getStatus)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseUserRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRoleAuth)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}