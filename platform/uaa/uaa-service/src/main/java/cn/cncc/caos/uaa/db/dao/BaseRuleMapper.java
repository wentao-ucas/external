package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.BaseRule;
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

import static cn.cncc.caos.uaa.db.dao.BaseRuleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseRuleMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseRule> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseRuleResult")
    BaseRule selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseRuleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="formula", property="formula", jdbcType=JdbcType.VARCHAR),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="seq", property="seq", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseRule> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseRule);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRule);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseRule)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseRule record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRule)
                .map(id).toProperty("id")
                .map(name).toProperty("name")
                .map(title).toProperty("title")
                .map(formula).toProperty("formula")
                .map(parentId).toProperty("parentId")
                .map(seq).toProperty("seq")
                .map(status).toProperty("status")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseRule record) {
        return insert(SqlBuilder.insert(record)
                .into(baseRule)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(name).toPropertyWhenPresent("name", record::getName)
                .map(title).toPropertyWhenPresent("title", record::getTitle)
                .map(formula).toPropertyWhenPresent("formula", record::getFormula)
                .map(parentId).toPropertyWhenPresent("parentId", record::getParentId)
                .map(seq).toPropertyWhenPresent("seq", record::getSeq)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRule>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, name, title, formula, parentId, seq, status, createTime, updateTime)
                .from(baseRule);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseRule>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, name, title, formula, parentId, seq, status, createTime, updateTime)
                .from(baseRule);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseRule selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, name, title, formula, parentId, seq, status, createTime, updateTime)
                .from(baseRule)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseRule record) {
        return UpdateDSL.updateWithMapper(this::update, baseRule)
                .set(id).equalTo(record::getId)
                .set(name).equalTo(record::getName)
                .set(title).equalTo(record::getTitle)
                .set(formula).equalTo(record::getFormula)
                .set(parentId).equalTo(record::getParentId)
                .set(seq).equalTo(record::getSeq)
                .set(status).equalTo(record::getStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseRule record) {
        return UpdateDSL.updateWithMapper(this::update, baseRule)
                .set(id).equalToWhenPresent(record::getId)
                .set(name).equalToWhenPresent(record::getName)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(formula).equalToWhenPresent(record::getFormula)
                .set(parentId).equalToWhenPresent(record::getParentId)
                .set(seq).equalToWhenPresent(record::getSeq)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseRule record) {
        return UpdateDSL.updateWithMapper(this::update, baseRule)
                .set(name).equalTo(record::getName)
                .set(title).equalTo(record::getTitle)
                .set(formula).equalTo(record::getFormula)
                .set(parentId).equalTo(record::getParentId)
                .set(seq).equalTo(record::getSeq)
                .set(status).equalTo(record::getStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseRule record) {
        return UpdateDSL.updateWithMapper(this::update, baseRule)
                .set(name).equalToWhenPresent(record::getName)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(formula).equalToWhenPresent(record::getFormula)
                .set(parentId).equalToWhenPresent(record::getParentId)
                .set(seq).equalToWhenPresent(record::getSeq)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}