package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
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
public interface BaseUserRelRoleMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseUserRelRole> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseUserRelRoleResult")
    BaseUserRelRole selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseUserRelRoleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseUserRelRole> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseUserRelRole);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserRelRole);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserRelRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseUserRelRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserRelRole)
                .map(id).toProperty("id")
                .map(roleId).toProperty("roleId")
                .map(userId).toProperty("userId")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseUserRelRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserRelRole)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(roleId).toPropertyWhenPresent("roleId", record::getRoleId)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserRelRole>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, roleId, userId, isValid, updateTime)
                .from(baseUserRelRole);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserRelRole>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, roleId, userId, isValid, updateTime)
                .from(baseUserRelRole);
    }


    default BaseUserRelRole selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, roleId, userId, isValid, updateTime)
                .from(baseUserRelRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseUserRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRelRole)
                .set(id).equalTo(record::getId)
                .set(roleId).equalTo(record::getRoleId)
                .set(userId).equalTo(record::getUserId)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseUserRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRelRole)
                .set(id).equalToWhenPresent(record::getId)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }


    default int updateByPrimaryKey(BaseUserRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRelRole)
                .set(roleId).equalTo(record::getRoleId)
                .set(userId).equalTo(record::getUserId)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseUserRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserRelRole)
                .set(roleId).equalToWhenPresent(record::getRoleId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}