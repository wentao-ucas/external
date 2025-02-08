package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.HistoryOper;
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

import static cn.cncc.caos.uaa.db.dao.HistoryOperDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface HistoryOperMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<HistoryOper> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("HistoryOperResult")
    HistoryOper selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="HistoryOperResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="oper_type", property="operType", jdbcType=JdbcType.VARCHAR),
        @Result(column="oper_time", property="operTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="dep_id", property="depId", jdbcType=JdbcType.INTEGER),
        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="oper_data", property="operData", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<HistoryOper> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(historyOper);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, historyOper);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, historyOper)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(HistoryOper record) {
        return insert(SqlBuilder.insert(record)
                .into(historyOper)
                .map(id).toProperty("id")
                .map(userName).toProperty("userName")
                .map(operType).toProperty("operType")
                .map(operTime).toProperty("operTime")
                .map(userId).toProperty("userId")
                .map(depId).toProperty("depId")
                .map(realName).toProperty("realName")
                .map(operData).toProperty("operData")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(HistoryOper record) {
        return insert(SqlBuilder.insert(record)
                .into(historyOper)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(userName).toPropertyWhenPresent("userName", record::getUserName)
                .map(operType).toPropertyWhenPresent("operType", record::getOperType)
                .map(operTime).toPropertyWhenPresent("operTime", record::getOperTime)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(depId).toPropertyWhenPresent("depId", record::getDepId)
                .map(realName).toPropertyWhenPresent("realName", record::getRealName)
                .map(operData).toPropertyWhenPresent("operData", record::getOperData)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<HistoryOper>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, userName, operType, operTime, userId, depId, realName, operData)
                .from(historyOper);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<HistoryOper>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, userName, operType, operTime, userId, depId, realName, operData)
                .from(historyOper);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default HistoryOper selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, userName, operType, operTime, userId, depId, realName, operData)
                .from(historyOper)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(HistoryOper record) {
        return UpdateDSL.updateWithMapper(this::update, historyOper)
                .set(id).equalTo(record::getId)
                .set(userName).equalTo(record::getUserName)
                .set(operType).equalTo(record::getOperType)
                .set(operTime).equalTo(record::getOperTime)
                .set(userId).equalTo(record::getUserId)
                .set(depId).equalTo(record::getDepId)
                .set(realName).equalTo(record::getRealName)
                .set(operData).equalTo(record::getOperData);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(HistoryOper record) {
        return UpdateDSL.updateWithMapper(this::update, historyOper)
                .set(id).equalToWhenPresent(record::getId)
                .set(userName).equalToWhenPresent(record::getUserName)
                .set(operType).equalToWhenPresent(record::getOperType)
                .set(operTime).equalToWhenPresent(record::getOperTime)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(depId).equalToWhenPresent(record::getDepId)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(operData).equalToWhenPresent(record::getOperData);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(HistoryOper record) {
        return UpdateDSL.updateWithMapper(this::update, historyOper)
                .set(userName).equalTo(record::getUserName)
                .set(operType).equalTo(record::getOperType)
                .set(operTime).equalTo(record::getOperTime)
                .set(userId).equalTo(record::getUserId)
                .set(depId).equalTo(record::getDepId)
                .set(realName).equalTo(record::getRealName)
                .set(operData).equalTo(record::getOperData)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(HistoryOper record) {
        return UpdateDSL.updateWithMapper(this::update, historyOper)
                .set(userName).equalToWhenPresent(record::getUserName)
                .set(operType).equalToWhenPresent(record::getOperType)
                .set(operTime).equalToWhenPresent(record::getOperTime)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(depId).equalToWhenPresent(record::getDepId)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(operData).equalToWhenPresent(record::getOperData)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}