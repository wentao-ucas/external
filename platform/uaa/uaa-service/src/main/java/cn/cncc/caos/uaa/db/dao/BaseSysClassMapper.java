package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseSysClassDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSysClass;
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
public interface BaseSysClassMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseSysClass> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseSysClassResult")
    BaseSysClass selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseSysClassResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_desc", property="classDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_assemble", property="sysAssemble", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseSysClass> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseSysClass);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSysClass);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSysClass)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseSysClass record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSysClass)
                .map(id).toProperty("id")
                .map(className).toProperty("className")
                .map(classDesc).toProperty("classDesc")
                .map(sysAssemble).toProperty("sysAssemble")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseSysClass record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSysClass)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(className).toPropertyWhenPresent("className", record::getClassName)
                .map(classDesc).toPropertyWhenPresent("classDesc", record::getClassDesc)
                .map(sysAssemble).toPropertyWhenPresent("sysAssemble", record::getSysAssemble)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSysClass>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, className, classDesc, sysAssemble, isValid, updateTime)
                .from(baseSysClass);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSysClass>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, className, classDesc, sysAssemble, isValid, updateTime)
                .from(baseSysClass);
    }


    default BaseSysClass selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, className, classDesc, sysAssemble, isValid, updateTime)
                .from(baseSysClass)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseSysClass record) {
        return UpdateDSL.updateWithMapper(this::update, baseSysClass)
                .set(id).equalTo(record::getId)
                .set(className).equalTo(record::getClassName)
                .set(classDesc).equalTo(record::getClassDesc)
                .set(sysAssemble).equalTo(record::getSysAssemble)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseSysClass record) {
        return UpdateDSL.updateWithMapper(this::update, baseSysClass)
                .set(id).equalToWhenPresent(record::getId)
                .set(className).equalToWhenPresent(record::getClassName)
                .set(classDesc).equalToWhenPresent(record::getClassDesc)
                .set(sysAssemble).equalToWhenPresent(record::getSysAssemble)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }


    default int updateByPrimaryKey(BaseSysClass record) {
        return UpdateDSL.updateWithMapper(this::update, baseSysClass)
                .set(className).equalTo(record::getClassName)
                .set(classDesc).equalTo(record::getClassDesc)
                .set(sysAssemble).equalTo(record::getSysAssemble)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseSysClass record) {
        return UpdateDSL.updateWithMapper(this::update, baseSysClass)
                .set(className).equalToWhenPresent(record::getClassName)
                .set(classDesc).equalToWhenPresent(record::getClassDesc)
                .set(sysAssemble).equalToWhenPresent(record::getSysAssemble)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}