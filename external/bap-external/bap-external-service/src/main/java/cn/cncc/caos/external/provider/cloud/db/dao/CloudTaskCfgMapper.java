package cn.cncc.caos.external.provider.cloud.db.dao;

import static cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskCfgDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.external.provider.cloud.db.pojo.CloudTaskCfg;
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
public interface CloudTaskCfgMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<CloudTaskCfg> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("CloudTaskCfgResult")
    CloudTaskCfg selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="CloudTaskCfgResult", value = {
        @Result(column="TASKID", property="taskid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="TASKDESC", property="taskdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="URL", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="REQTYPE", property="reqtype", jdbcType=JdbcType.VARCHAR),
        @Result(column="PLANETYPE", property="planetype", jdbcType=JdbcType.CHAR),
        @Result(column="PARAM", property="param", jdbcType=JdbcType.VARCHAR),
        @Result(column="AUTHID", property="authid", jdbcType=JdbcType.VARCHAR),
        @Result(column="CRONCFG", property="croncfg", jdbcType=JdbcType.VARCHAR),
        @Result(column="PARSERULE", property="parserule", jdbcType=JdbcType.VARCHAR),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="OPERATOR", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="DEPTNO", property="deptno", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATETIME", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="REMARK", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="UPDTTIME", property="updttime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TASKSTATUS", property="taskstatus", jdbcType=JdbcType.CHAR)
    })
    List<CloudTaskCfg> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(cloudTaskCfg);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, cloudTaskCfg);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String taskid_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, cloudTaskCfg)
                .where(taskid, isEqualTo(taskid_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(CloudTaskCfg record) {
        return insert(SqlBuilder.insert(record)
                .into(cloudTaskCfg)
                .map(taskid).toProperty("taskid")
                .map(taskdesc).toProperty("taskdesc")
                .map(url).toProperty("url")
                .map(reqtype).toProperty("reqtype")
                .map(planetype).toProperty("planetype")
                .map(param).toProperty("param")
                .map(authid).toProperty("authid")
                .map(croncfg).toProperty("croncfg")
                .map(parserule).toProperty("parserule")
                .map(status).toProperty("status")
                .map(operator).toProperty("operator")
                .map(deptno).toProperty("deptno")
                .map(createtime).toProperty("createtime")
                .map(remark).toProperty("remark")
                .map(updttime).toProperty("updttime")
                .map(taskstatus).toProperty("taskstatus")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(CloudTaskCfg record) {
        return insert(SqlBuilder.insert(record)
                .into(cloudTaskCfg)
                .map(taskid).toPropertyWhenPresent("taskid", record::getTaskid)
                .map(taskdesc).toPropertyWhenPresent("taskdesc", record::getTaskdesc)
                .map(url).toPropertyWhenPresent("url", record::getUrl)
                .map(reqtype).toPropertyWhenPresent("reqtype", record::getReqtype)
                .map(planetype).toPropertyWhenPresent("planetype", record::getPlanetype)
                .map(param).toPropertyWhenPresent("param", record::getParam)
                .map(authid).toPropertyWhenPresent("authid", record::getAuthid)
                .map(croncfg).toPropertyWhenPresent("croncfg", record::getCroncfg)
                .map(parserule).toPropertyWhenPresent("parserule", record::getParserule)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(operator).toPropertyWhenPresent("operator", record::getOperator)
                .map(deptno).toPropertyWhenPresent("deptno", record::getDeptno)
                .map(createtime).toPropertyWhenPresent("createtime", record::getCreatetime)
                .map(remark).toPropertyWhenPresent("remark", record::getRemark)
                .map(updttime).toPropertyWhenPresent("updttime", record::getUpdttime)
                .map(taskstatus).toPropertyWhenPresent("taskstatus", record::getTaskstatus)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<CloudTaskCfg>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, taskid, taskdesc, url, reqtype, planetype, param, authid, croncfg, parserule, status, operator, deptno, createtime, remark, updttime, taskstatus)
                .from(cloudTaskCfg);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<CloudTaskCfg>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, taskid, taskdesc, url, reqtype, planetype, param, authid, croncfg, parserule, status, operator, deptno, createtime, remark, updttime, taskstatus)
                .from(cloudTaskCfg);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default CloudTaskCfg selectByPrimaryKey(String taskid_) {
        return SelectDSL.selectWithMapper(this::selectOne, taskid, taskdesc, url, reqtype, planetype, param, authid, croncfg, parserule, status, operator, deptno, createtime, remark, updttime, taskstatus)
                .from(cloudTaskCfg)
                .where(taskid, isEqualTo(taskid_))
                .build()
                .execute();
    }


    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(CloudTaskCfg record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskCfg)
                .set(taskid).equalTo(record::getTaskid)
                .set(taskdesc).equalTo(record::getTaskdesc)
                .set(url).equalTo(record::getUrl)
                .set(reqtype).equalTo(record::getReqtype)
                .set(planetype).equalTo(record::getPlanetype)
                .set(param).equalTo(record::getParam)
                .set(authid).equalTo(record::getAuthid)
                .set(croncfg).equalTo(record::getCroncfg)
                .set(parserule).equalTo(record::getParserule)
                .set(status).equalTo(record::getStatus)
                .set(operator).equalTo(record::getOperator)
                .set(deptno).equalTo(record::getDeptno)
                .set(createtime).equalTo(record::getCreatetime)
                .set(remark).equalTo(record::getRemark)
                .set(updttime).equalTo(record::getUpdttime)
                .set(taskstatus).equalTo(record::getTaskstatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(CloudTaskCfg record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskCfg)
                .set(taskid).equalToWhenPresent(record::getTaskid)
                .set(taskdesc).equalToWhenPresent(record::getTaskdesc)
                .set(url).equalToWhenPresent(record::getUrl)
                .set(reqtype).equalToWhenPresent(record::getReqtype)
                .set(planetype).equalToWhenPresent(record::getPlanetype)
                .set(param).equalToWhenPresent(record::getParam)
                .set(authid).equalToWhenPresent(record::getAuthid)
                .set(croncfg).equalToWhenPresent(record::getCroncfg)
                .set(parserule).equalToWhenPresent(record::getParserule)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(operator).equalToWhenPresent(record::getOperator)
                .set(deptno).equalToWhenPresent(record::getDeptno)
                .set(createtime).equalToWhenPresent(record::getCreatetime)
                .set(remark).equalToWhenPresent(record::getRemark)
                .set(updttime).equalToWhenPresent(record::getUpdttime)
                .set(taskstatus).equalToWhenPresent(record::getTaskstatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(CloudTaskCfg record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskCfg)
                .set(taskdesc).equalTo(record::getTaskdesc)
                .set(url).equalTo(record::getUrl)
                .set(reqtype).equalTo(record::getReqtype)
                .set(planetype).equalTo(record::getPlanetype)
                .set(param).equalTo(record::getParam)
                .set(authid).equalTo(record::getAuthid)
                .set(croncfg).equalTo(record::getCroncfg)
                .set(parserule).equalTo(record::getParserule)
                .set(status).equalTo(record::getStatus)
                .set(operator).equalTo(record::getOperator)
                .set(deptno).equalTo(record::getDeptno)
                .set(createtime).equalTo(record::getCreatetime)
                .set(remark).equalTo(record::getRemark)
                .set(updttime).equalTo(record::getUpdttime)
                .set(taskstatus).equalTo(record::getTaskstatus)
                .where(taskid, isEqualTo(record::getTaskid))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(CloudTaskCfg record) {
        return UpdateDSL.updateWithMapper(this::update, cloudTaskCfg)
                .set(taskdesc).equalToWhenPresent(record::getTaskdesc)
                .set(url).equalToWhenPresent(record::getUrl)
                .set(reqtype).equalToWhenPresent(record::getReqtype)
                .set(planetype).equalToWhenPresent(record::getPlanetype)
                .set(param).equalToWhenPresent(record::getParam)
                .set(authid).equalToWhenPresent(record::getAuthid)
                .set(croncfg).equalToWhenPresent(record::getCroncfg)
                .set(parserule).equalToWhenPresent(record::getParserule)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(operator).equalToWhenPresent(record::getOperator)
                .set(deptno).equalToWhenPresent(record::getDeptno)
                .set(createtime).equalToWhenPresent(record::getCreatetime)
                .set(remark).equalToWhenPresent(record::getRemark)
                .set(updttime).equalToWhenPresent(record::getUpdttime)
                .set(taskstatus).equalToWhenPresent(record::getTaskstatus)
                .where(taskid, isEqualTo(record::getTaskid))
                .build()
                .execute();
    }
}