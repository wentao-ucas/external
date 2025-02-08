package cn.cncc.caos.external.provider.cloud.db.dao;

import static cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskLogDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.external.provider.cloud.db.pojo.CloudTaskLog;
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
public interface CloudTaskLogMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<CloudTaskLog> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("CloudTaskLogResult")
    CloudTaskLog selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="CloudTaskLogResult", value = {
        @Result(column="TASKINSTID", property="taskinstid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="TASKID", property="taskid", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="RESULT", property="result", jdbcType=JdbcType.VARCHAR),
        @Result(column="PARSERESULT", property="parseresult", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATOR", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATETIME", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UPDTTIME", property="updttime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TASKINSTSTATUS", property="taskinststatus", jdbcType=JdbcType.CHAR),
        @Result(column="ENDTIME", property="endtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DEPTNO", property="deptno", jdbcType=JdbcType.VARCHAR)
    })
    List<CloudTaskLog> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(cloudTaskLog);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, cloudTaskLog);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String taskinstid_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, cloudTaskLog)
                .where(taskinstid, isEqualTo(taskinstid_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(CloudTaskLog record) {
        return insert(SqlBuilder.insert(record)
                .into(cloudTaskLog)
                .map(taskinstid).toProperty("taskinstid")
                .map(taskid).toProperty("taskid")
                .map(status).toProperty("status")
                .map(result).toProperty("result")
                .map(parseresult).toProperty("parseresult")
                .map(operator).toProperty("operator")
                .map(createtime).toProperty("createtime")
                .map(updttime).toProperty("updttime")
                .map(taskinststatus).toProperty("taskinststatus")
                .map(endtime).toProperty("endtime")
                .map(deptno).toProperty("deptno")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(CloudTaskLog record) {
        return insert(SqlBuilder.insert(record)
                .into(cloudTaskLog)
                .map(taskinstid).toPropertyWhenPresent("taskinstid", record::getTaskinstid)
                .map(taskid).toPropertyWhenPresent("taskid", record::getTaskid)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(result).toPropertyWhenPresent("result", record::getResult)
                .map(parseresult).toPropertyWhenPresent("parseresult", record::getParseresult)
                .map(operator).toPropertyWhenPresent("operator", record::getOperator)
                .map(createtime).toPropertyWhenPresent("createtime", record::getCreatetime)
                .map(updttime).toPropertyWhenPresent("updttime", record::getUpdttime)
                .map(taskinststatus).toPropertyWhenPresent("taskinststatus", record::getTaskinststatus)
                .map(endtime).toPropertyWhenPresent("endtime", record::getEndtime)
                .map(deptno).toPropertyWhenPresent("deptno", record::getDeptno)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<CloudTaskLog>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, taskinstid, taskid, status, result, parseresult, operator, createtime, updttime, taskinststatus, endtime, deptno)
                .from(cloudTaskLog);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<CloudTaskLog>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, taskinstid, taskid, status, result, parseresult, operator, createtime, updttime, taskinststatus, endtime, deptno)
                .from(cloudTaskLog);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default CloudTaskLog selectByPrimaryKey(String taskinstid_) {
        return SelectDSL.selectWithMapper(this::selectOne, taskinstid, taskid, status, result, parseresult, operator, createtime, updttime, taskinststatus, endtime, deptno)
                .from(cloudTaskLog)
                .where(taskinstid, isEqualTo(taskinstid_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(CloudTaskLog record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskLog)
                .set(taskinstid).equalTo(record::getTaskinstid)
                .set(taskid).equalTo(record::getTaskid)
                .set(status).equalTo(record::getStatus)
                .set(result).equalTo(record::getResult)
                .set(parseresult).equalTo(record::getParseresult)
                .set(operator).equalTo(record::getOperator)
                .set(createtime).equalTo(record::getCreatetime)
                .set(updttime).equalTo(record::getUpdttime)
                .set(taskinststatus).equalTo(record::getTaskinststatus)
                .set(endtime).equalTo(record::getEndtime)
                .set(deptno).equalTo(record::getDeptno);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(CloudTaskLog record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskLog)
                .set(taskinstid).equalToWhenPresent(record::getTaskinstid)
                .set(taskid).equalToWhenPresent(record::getTaskid)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(result).equalToWhenPresent(record::getResult)
                .set(parseresult).equalToWhenPresent(record::getParseresult)
                .set(operator).equalToWhenPresent(record::getOperator)
                .set(createtime).equalToWhenPresent(record::getCreatetime)
                .set(updttime).equalToWhenPresent(record::getUpdttime)
                .set(taskinststatus).equalToWhenPresent(record::getTaskinststatus)
                .set(endtime).equalToWhenPresent(record::getEndtime)
                .set(deptno).equalToWhenPresent(record::getDeptno);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(CloudTaskLog record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskLog)
                .set(taskid).equalTo(record::getTaskid)
                .set(status).equalTo(record::getStatus)
                .set(result).equalTo(record::getResult)
                .set(parseresult).equalTo(record::getParseresult)
                .set(operator).equalTo(record::getOperator)
                .set(createtime).equalTo(record::getCreatetime)
                .set(updttime).equalTo(record::getUpdttime)
                .set(taskinststatus).equalTo(record::getTaskinststatus)
                .set(endtime).equalTo(record::getEndtime)
                .set(deptno).equalTo(record::getDeptno)
                .where(taskinstid, isEqualTo(record::getTaskinstid))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(CloudTaskLog record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskLog)
                .set(taskid).equalToWhenPresent(record::getTaskid)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(result).equalToWhenPresent(record::getResult)
                .set(parseresult).equalToWhenPresent(record::getParseresult)
                .set(operator).equalToWhenPresent(record::getOperator)
                .set(createtime).equalToWhenPresent(record::getCreatetime)
                .set(updttime).equalToWhenPresent(record::getUpdttime)
                .set(taskinststatus).equalToWhenPresent(record::getTaskinststatus)
                .set(endtime).equalToWhenPresent(record::getEndtime)
                .set(deptno).equalToWhenPresent(record::getDeptno)
                .where(taskinstid, isEqualTo(record::getTaskinstid))
                .build()
                .execute();
    }
}