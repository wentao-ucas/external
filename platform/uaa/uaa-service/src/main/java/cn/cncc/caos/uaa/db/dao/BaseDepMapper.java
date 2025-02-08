package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
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

import static cn.cncc.caos.uaa.db.dao.BaseDepDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseDepMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseDep> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseDepResult")
    BaseDep selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseDepResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="dep_name", property="depName", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_desc", property="depDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="dep_code", property="depCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.INTEGER),
//        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseDep> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseDep);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDep);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDep)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseDep record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDep)
                .map(id).toProperty("id")
                .map(depName).toProperty("depName")
                .map(depDesc).toProperty("depDesc")
                .map(depCode).toProperty("depCode")
                .map(parentId).toProperty("parentId")
//                .map(level).toProperty("level")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseDep record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDep)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(depName).toPropertyWhenPresent("depName", record::getDepName)
                .map(depDesc).toPropertyWhenPresent("depDesc", record::getDepDesc)
                .map(depCode).toPropertyWhenPresent("depCode", record::getDepCode)
                .map(parentId).toPropertyWhenPresent("parentId", record::getParentId)
//                .map(level).toPropertyWhenPresent("level", record::getLevel)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDep>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, depName, depDesc, depCode, parentId, isValid, updateTime,level)
                .from(baseDep);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDep>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, depName, depDesc, depCode, parentId, isValid, updateTime)
                .from(baseDep);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseDep selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, depName, depDesc, depCode, parentId, isValid, updateTime)
                .from(baseDep)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseDep record) {
        return UpdateDSL.updateWithMapper(this::update, baseDep)
                .set(id).equalTo(record::getId)
                .set(depName).equalTo(record::getDepName)
                .set(depDesc).equalTo(record::getDepDesc)
                .set(depCode).equalTo(record::getDepCode)
                .set(parentId).equalTo(record::getParentId)
//                .set(level).equalTo(record::getLevel)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseDep record) {
        return UpdateDSL.updateWithMapper(this::update, baseDep)
                .set(id).equalToWhenPresent(record::getId)
                .set(depName).equalToWhenPresent(record::getDepName)
                .set(depDesc).equalToWhenPresent(record::getDepDesc)
                .set(depCode).equalToWhenPresent(record::getDepCode)
                .set(parentId).equalToWhenPresent(record::getParentId)
//                .set(level).equalToWhenPresent(record::getLevel)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseDep record) {
        return UpdateDSL.updateWithMapper(this::update, baseDep)
                .set(depName).equalTo(record::getDepName)
                .set(depDesc).equalTo(record::getDepDesc)
                .set(depCode).equalTo(record::getDepCode)
                .set(parentId).equalTo(record::getParentId)
//                .set(level).equalTo(record::getLevel)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseDep record) {
        return UpdateDSL.updateWithMapper(this::update, baseDep)
                .set(depName).equalToWhenPresent(record::getDepName)
                .set(depDesc).equalToWhenPresent(record::getDepDesc)
                .set(depCode).equalToWhenPresent(record::getDepCode)
                .set(parentId).equalToWhenPresent(record::getParentId)
//                .set(level).equalToWhenPresent(record::getLevel)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}