package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseDictDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.uaa.db.pojo.BaseDict;
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
public interface BaseDictMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseDict> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseDictResult")
    BaseDict selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseDictResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="dict_type", property="dictType", jdbcType=JdbcType.VARCHAR),
        @Result(column="dict_code", property="dictCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="dict_value", property="dictValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="dict_desc", property="dictDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="dict_seq", property="dictSeq", jdbcType=JdbcType.INTEGER),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.INTEGER)
    })
    List<BaseDict> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseDict);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDict);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseDict)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseDict record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDict)
                .map(id).toProperty("id")
                .map(dictType).toProperty("dictType")
                .map(dictCode).toProperty("dictCode")
                .map(dictValue).toProperty("dictValue")
                .map(dictDesc).toProperty("dictDesc")
                .map(dictSeq).toProperty("dictSeq")
                .map(parentId).toProperty("parentId")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseDict record) {
        return insert(SqlBuilder.insert(record)
                .into(baseDict)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(dictType).toPropertyWhenPresent("dictType", record::getDictType)
                .map(dictCode).toPropertyWhenPresent("dictCode", record::getDictCode)
                .map(dictValue).toPropertyWhenPresent("dictValue", record::getDictValue)
                .map(dictDesc).toPropertyWhenPresent("dictDesc", record::getDictDesc)
                .map(dictSeq).toPropertyWhenPresent("dictSeq", record::getDictSeq)
                .map(parentId).toPropertyWhenPresent("parentId", record::getParentId)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDict>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, dictType, dictCode, dictValue, dictDesc, dictSeq, parentId)
                .from(baseDict);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseDict>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, dictType, dictCode, dictValue, dictDesc, dictSeq, parentId)
                .from(baseDict);
    }


    default BaseDict selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, dictType, dictCode, dictValue, dictDesc, dictSeq, parentId)
                .from(baseDict)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseDict record) {
        return UpdateDSL.updateWithMapper(this::update, baseDict)
                .set(id).equalTo(record::getId)
                .set(dictType).equalTo(record::getDictType)
                .set(dictCode).equalTo(record::getDictCode)
                .set(dictValue).equalTo(record::getDictValue)
                .set(dictDesc).equalTo(record::getDictDesc)
                .set(dictSeq).equalTo(record::getDictSeq)
                .set(parentId).equalTo(record::getParentId);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseDict record) {
        return UpdateDSL.updateWithMapper(this::update, baseDict)
                .set(id).equalToWhenPresent(record::getId)
                .set(dictType).equalToWhenPresent(record::getDictType)
                .set(dictCode).equalToWhenPresent(record::getDictCode)
                .set(dictValue).equalToWhenPresent(record::getDictValue)
                .set(dictDesc).equalToWhenPresent(record::getDictDesc)
                .set(dictSeq).equalToWhenPresent(record::getDictSeq)
                .set(parentId).equalToWhenPresent(record::getParentId);
    }


    default int updateByPrimaryKey(BaseDict record) {
        return UpdateDSL.updateWithMapper(this::update, baseDict)
                .set(dictType).equalTo(record::getDictType)
                .set(dictCode).equalTo(record::getDictCode)
                .set(dictValue).equalTo(record::getDictValue)
                .set(dictDesc).equalTo(record::getDictDesc)
                .set(dictSeq).equalTo(record::getDictSeq)
                .set(parentId).equalTo(record::getParentId)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseDict record) {
        return UpdateDSL.updateWithMapper(this::update, baseDict)
                .set(dictType).equalToWhenPresent(record::getDictType)
                .set(dictCode).equalToWhenPresent(record::getDictCode)
                .set(dictValue).equalToWhenPresent(record::getDictValue)
                .set(dictDesc).equalToWhenPresent(record::getDictDesc)
                .set(dictSeq).equalToWhenPresent(record::getDictSeq)
                .set(parentId).equalToWhenPresent(record::getParentId)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}