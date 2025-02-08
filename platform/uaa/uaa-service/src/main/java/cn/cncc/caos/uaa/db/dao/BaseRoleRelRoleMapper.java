package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseRoleRelRoleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.uaa.db.pojo.BaseRoleRelRole;
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
public interface BaseRoleRelRoleMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseRoleRelRole> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseRoleRelRoleResult")
    BaseRoleRelRole selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseRoleRelRoleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id_key", property="roleIdKey", jdbcType=JdbcType.INTEGER),
        @Result(column="role_id_value", property="roleIdValue", jdbcType=JdbcType.INTEGER),
        @Result(column="role_rel_usage", property="roleRelUsage", jdbcType=JdbcType.VARCHAR)
    })
    List<BaseRoleRelRole> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseRoleRelRole);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRoleRelRole);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRoleRelRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseRoleRelRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRoleRelRole)
                .map(id).toProperty("id")
                .map(roleIdKey).toProperty("roleIdKey")
                .map(roleIdValue).toProperty("roleIdValue")
                .map(roleRelUsage).toProperty("roleRelUsage")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseRoleRelRole record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRoleRelRole)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(roleIdKey).toPropertyWhenPresent("roleIdKey", record::getRoleIdKey)
                .map(roleIdValue).toPropertyWhenPresent("roleIdValue", record::getRoleIdValue)
                .map(roleRelUsage).toPropertyWhenPresent("roleRelUsage", record::getRoleRelUsage)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRoleRelRole>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, roleIdKey, roleIdValue, roleRelUsage)
                .from(baseRoleRelRole);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRoleRelRole>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, roleIdKey, roleIdValue, roleRelUsage)
                .from(baseRoleRelRole);
    }


    default BaseRoleRelRole selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, roleIdKey, roleIdValue, roleRelUsage)
                .from(baseRoleRelRole)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseRoleRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleRelRole)
                .set(id).equalTo(record::getId)
                .set(roleIdKey).equalTo(record::getRoleIdKey)
                .set(roleIdValue).equalTo(record::getRoleIdValue)
                .set(roleRelUsage).equalTo(record::getRoleRelUsage);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseRoleRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleRelRole)
                .set(id).equalToWhenPresent(record::getId)
                .set(roleIdKey).equalToWhenPresent(record::getRoleIdKey)
                .set(roleIdValue).equalToWhenPresent(record::getRoleIdValue)
                .set(roleRelUsage).equalToWhenPresent(record::getRoleRelUsage);
    }


    default int updateByPrimaryKey(BaseRoleRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleRelRole)
                .set(roleIdKey).equalTo(record::getRoleIdKey)
                .set(roleIdValue).equalTo(record::getRoleIdValue)
                .set(roleRelUsage).equalTo(record::getRoleRelUsage)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseRoleRelRole record) {
        return UpdateDSL.updateWithMapper(this::update, baseRoleRelRole)
                .set(roleIdKey).equalToWhenPresent(record::getRoleIdKey)
                .set(roleIdValue).equalToWhenPresent(record::getRoleIdValue)
                .set(roleRelUsage).equalToWhenPresent(record::getRoleRelUsage)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}