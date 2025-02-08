package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
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

import static cn.cncc.caos.uaa.db.dao.BaseRoleAuthDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseRoleAuthMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseRoleAuth> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseRoleAuthResult")
    BaseRoleAuth selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseRoleAuthResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_id", property="sysId", jdbcType=JdbcType.INTEGER),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="role_key", property="roleKey", jdbcType=JdbcType.VARCHAR)
    })
    List<BaseRoleAuth> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRoleAuth)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseRoleAuth record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRoleAuth)
                .map(id).toProperty("id")
                .map(roleName).toProperty("roleName")
                .map(roleDesc).toProperty("roleDesc")
                .map(sysId).toProperty("sysId")
                .map(isValid).toProperty("isValid")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .map(roleKey).toProperty("roleKey")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseRoleAuth record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRoleAuth)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(roleName).toPropertyWhenPresent("roleName", record::getRoleName)
                .map(roleDesc).toPropertyWhenPresent("roleDesc", record::getRoleDesc)
                .map(sysId).toPropertyWhenPresent("sysId", record::getSysId)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .map(roleKey).toPropertyWhenPresent("roleKey", record::getRoleKey)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRoleAuth>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, roleName, roleDesc, sysId, isValid, createTime, updateTime, roleKey)
                .from(baseRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRoleAuth>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, roleName, roleDesc, sysId, isValid, createTime, updateTime, roleKey)
                .from(baseRoleAuth);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseRoleAuth selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, roleName, roleDesc, sysId, isValid, createTime, updateTime, roleKey)
                .from(baseRoleAuth)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleAuth)
                .set(id).equalTo(record::getId)
                .set(roleName).equalTo(record::getRoleName)
                .set(roleDesc).equalTo(record::getRoleDesc)
                .set(sysId).equalTo(record::getSysId)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(roleKey).equalTo(record::getRoleKey);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleAuth)
                .set(id).equalToWhenPresent(record::getId)
                .set(roleName).equalToWhenPresent(record::getRoleName)
                .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(sysId).equalToWhenPresent(record::getSysId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(roleKey).equalToWhenPresent(record::getRoleKey);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleAuth)
                .set(roleName).equalTo(record::getRoleName)
                .set(roleDesc).equalTo(record::getRoleDesc)
                .set(sysId).equalTo(record::getSysId)
                .set(isValid).equalTo(record::getIsValid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(roleKey).equalTo(record::getRoleKey)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseRoleAuth record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleAuth)
                .set(roleName).equalToWhenPresent(record::getRoleName)
                .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(sysId).equalToWhenPresent(record::getSysId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(roleKey).equalToWhenPresent(record::getRoleKey)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}