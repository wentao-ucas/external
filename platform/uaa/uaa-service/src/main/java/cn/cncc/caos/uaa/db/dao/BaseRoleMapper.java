package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
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

@Mapper
public interface BaseRoleMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseRole> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseRoleResult")
    BaseRole selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseRoleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_id", property="sysId", jdbcType=JdbcType.INTEGER),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseRole> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseRole);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRole);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRole)
                .map(id).toProperty("id")
                .map(roleName).toProperty("roleName")
                .map(roleDesc).toProperty("roleDesc")
                .map(sysId).toProperty("sysId")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRole)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(roleName).toPropertyWhenPresent("roleName", record::getRoleName)
                .map(roleDesc).toPropertyWhenPresent("roleDesc", record::getRoleDesc)
                .map(sysId).toPropertyWhenPresent("sysId", record::getSysId)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRole>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, roleName, roleDesc, sysId, isValid, updateTime)
                .from(baseRole);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRole>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, roleName, roleDesc, sysId, isValid, updateTime)
                .from(baseRole);
    }


    default BaseRole selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, roleName, roleDesc, sysId, isValid, updateTime)
                .from(baseRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRole)
                .set(id).equalTo(record::getId)
                .set(roleName).equalTo(record::getRoleName)
                .set(roleDesc).equalTo(record::getRoleDesc)
                .set(sysId).equalTo(record::getSysId)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRole)
                .set(id).equalToWhenPresent(record::getId)
                .set(roleName).equalToWhenPresent(record::getRoleName)
                .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(sysId).equalToWhenPresent(record::getSysId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }


    default int updateByPrimaryKey(BaseRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRole)
                .set(roleName).equalTo(record::getRoleName)
                .set(roleDesc).equalTo(record::getRoleDesc)
                .set(sysId).equalTo(record::getSysId)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRole)
                .set(roleName).equalToWhenPresent(record::getRoleName)
                .set(roleDesc).equalToWhenPresent(record::getRoleDesc)
                .set(sysId).equalToWhenPresent(record::getSysId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}