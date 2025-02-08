package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.BaseDataPermission;
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

import static cn.cncc.caos.uaa.db.dao.BaseDataPermissionDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseDataPermissionMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseDataPermission> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseDataPermissionResult")
    BaseDataPermission selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseDataPermissionResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="dep_id", property="depId", jdbcType=JdbcType.INTEGER),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_scope", property="dataScope", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<BaseDataPermission> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseDataPermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDataPermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDataPermission)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseDataPermission record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDataPermission)
                .map(id).toProperty("id")
                .map(depId).toProperty("depId")
                .map(roleId).toProperty("roleId")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .map(status).toProperty("status")
                .map(type).toProperty("type")
                .map(dataScope).toProperty("dataScope")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseDataPermission record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDataPermission)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(depId).toPropertyWhenPresent("depId", record::getDepId)
                .map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(type).toPropertyWhenPresent("type", record::getType)
                .map(dataScope).toPropertyWhenPresent("dataScope", record::getDataScope)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDataPermission>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, depId, roleId, createTime, updateTime, status, type, dataScope)
                .from(baseDataPermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDataPermission>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, depId, roleId, createTime, updateTime, status, type, dataScope)
                .from(baseDataPermission);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseDataPermission selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, depId, roleId, createTime, updateTime, status, type, dataScope)
                .from(baseDataPermission)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseDataPermission record) {
        return UpdateDSL.updateWithMapper(this::update, baseDataPermission)
                .set(id).equalTo(record::getId)
                .set(depId).equalTo(record::getDepId)
                .set(roleId).equalTo(record::getRoleId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(status).equalTo(record::getStatus)
                .set(type).equalTo(record::getType)
                .set(dataScope).equalTo(record::getDataScope);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseDataPermission record) {
        return UpdateDSL.updateWithMapper(this::update, baseDataPermission)
                .set(id).equalToWhenPresent(record::getId)
                .set(depId).equalToWhenPresent(record::getDepId)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(type).equalToWhenPresent(record::getType)
                .set(dataScope).equalToWhenPresent(record::getDataScope);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseDataPermission record) {
        return UpdateDSL.updateWithMapper(this::update, baseDataPermission)
                .set(depId).equalTo(record::getDepId)
                .set(roleId).equalTo(record::getRoleId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(status).equalTo(record::getStatus)
                .set(type).equalTo(record::getType)
                .set(dataScope).equalTo(record::getDataScope)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseDataPermission record) {
        return UpdateDSL.updateWithMapper(this::update, baseDataPermission)
                .set(depId).equalToWhenPresent(record::getDepId)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(type).equalToWhenPresent(record::getType)
                .set(dataScope).equalToWhenPresent(record::getDataScope)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}