package cn.cncc.caos.data.provider.pubparam.db.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.data.provider.pubparam.db.pojo.PubParam;

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
public interface PubParamMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<PubParam> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("PubParamResult")
    PubParam selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="PubParamResult", value = {
        @Result(column="PARMID", property="parmid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="PARMNAME", property="parmname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PARMVAL", property="parmval", jdbcType=JdbcType.VARCHAR),
        @Result(column="ENCRYPTFLAG", property="encryptflag", jdbcType=JdbcType.CHAR),
        @Result(column="PARMDESC", property="parmdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="OPERATOR", property="operator", jdbcType=JdbcType.VARCHAR),
        @Result(column="UPDTTIME", property="updttime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TYPE", property="type", jdbcType=JdbcType.CHAR),
        @Result(column="CREATETIME", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="STATUS", property="status", jdbcType=JdbcType.CHAR)
    })
    List<PubParam> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(PubParamDynamicSqlSupport.pubParam);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, PubParamDynamicSqlSupport.pubParam);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String parmid_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, PubParamDynamicSqlSupport.pubParam)
                .where(PubParamDynamicSqlSupport.parmid, isEqualTo(parmid_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(PubParam record) {
        return insert(SqlBuilder.insert(record)
                .into(PubParamDynamicSqlSupport.pubParam)
                .map(PubParamDynamicSqlSupport.parmid).toProperty("parmid")
                .map(PubParamDynamicSqlSupport.parmname).toProperty("parmname")
                .map(PubParamDynamicSqlSupport.parmval).toProperty("parmval")
                .map(PubParamDynamicSqlSupport.encryptflag).toProperty("encryptflag")
                .map(PubParamDynamicSqlSupport.parmdesc).toProperty("parmdesc")
                .map(PubParamDynamicSqlSupport.operator).toProperty("operator")
                .map(PubParamDynamicSqlSupport.updttime).toProperty("updttime")
                .map(PubParamDynamicSqlSupport.type).toProperty("type")
                .map(PubParamDynamicSqlSupport.createtime).toProperty("createtime")
                .map(PubParamDynamicSqlSupport.status).toProperty("status")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(PubParam record) {
        return insert(SqlBuilder.insert(record)
                .into(PubParamDynamicSqlSupport.pubParam)
                .map(PubParamDynamicSqlSupport.parmid).toPropertyWhenPresent("parmid", record::getParmid)
                .map(PubParamDynamicSqlSupport.parmname).toPropertyWhenPresent("parmname", record::getParmname)
                .map(PubParamDynamicSqlSupport.parmval).toPropertyWhenPresent("parmval", record::getParmval)
                .map(PubParamDynamicSqlSupport.encryptflag).toPropertyWhenPresent("encryptflag", record::getEncryptflag)
                .map(PubParamDynamicSqlSupport.parmdesc).toPropertyWhenPresent("parmdesc", record::getParmdesc)
                .map(PubParamDynamicSqlSupport.operator).toPropertyWhenPresent("operator", record::getOperator)
                .map(PubParamDynamicSqlSupport.updttime).toPropertyWhenPresent("updttime", record::getUpdttime)
                .map(PubParamDynamicSqlSupport.type).toPropertyWhenPresent("type", record::getType)
                .map(PubParamDynamicSqlSupport.createtime).toPropertyWhenPresent("createtime", record::getCreatetime)
                .map(PubParamDynamicSqlSupport.status).toPropertyWhenPresent("status", record::getStatus)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<PubParam>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, PubParamDynamicSqlSupport.parmid, PubParamDynamicSqlSupport.parmname, PubParamDynamicSqlSupport.parmval, PubParamDynamicSqlSupport.encryptflag, PubParamDynamicSqlSupport.parmdesc, PubParamDynamicSqlSupport.operator, PubParamDynamicSqlSupport.updttime, PubParamDynamicSqlSupport.type, PubParamDynamicSqlSupport.createtime, PubParamDynamicSqlSupport.status)
                .from(PubParamDynamicSqlSupport.pubParam);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<PubParam>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, PubParamDynamicSqlSupport.parmid, PubParamDynamicSqlSupport.parmname, PubParamDynamicSqlSupport.parmval, PubParamDynamicSqlSupport.encryptflag, PubParamDynamicSqlSupport.parmdesc, PubParamDynamicSqlSupport.operator, PubParamDynamicSqlSupport.updttime, PubParamDynamicSqlSupport.type, PubParamDynamicSqlSupport.createtime, PubParamDynamicSqlSupport.status)
                .from(PubParamDynamicSqlSupport.pubParam);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default PubParam selectByPrimaryKey(String parmid_) {
        return SelectDSL.selectWithMapper(this::selectOne, PubParamDynamicSqlSupport.parmid, PubParamDynamicSqlSupport.parmname, PubParamDynamicSqlSupport.parmval, PubParamDynamicSqlSupport.encryptflag, PubParamDynamicSqlSupport.parmdesc, PubParamDynamicSqlSupport.operator, PubParamDynamicSqlSupport.updttime, PubParamDynamicSqlSupport.type, PubParamDynamicSqlSupport.createtime, PubParamDynamicSqlSupport.status)
                .from(PubParamDynamicSqlSupport.pubParam)
                .where(PubParamDynamicSqlSupport.parmid, isEqualTo(parmid_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(PubParam record) {
        return UpdateDSL.updateWithMapper(this::update, PubParamDynamicSqlSupport.pubParam)
                .set(PubParamDynamicSqlSupport.parmid).equalTo(record::getParmid)
                .set(PubParamDynamicSqlSupport.parmname).equalTo(record::getParmname)
                .set(PubParamDynamicSqlSupport.parmval).equalTo(record::getParmval)
                .set(PubParamDynamicSqlSupport.encryptflag).equalTo(record::getEncryptflag)
                .set(PubParamDynamicSqlSupport.parmdesc).equalTo(record::getParmdesc)
                .set(PubParamDynamicSqlSupport.operator).equalTo(record::getOperator)
                .set(PubParamDynamicSqlSupport.updttime).equalTo(record::getUpdttime)
                .set(PubParamDynamicSqlSupport.type).equalTo(record::getType)
                .set(PubParamDynamicSqlSupport.createtime).equalTo(record::getCreatetime)
                .set(PubParamDynamicSqlSupport.status).equalTo(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(PubParam record) {
        return UpdateDSL.updateWithMapper(this::update, PubParamDynamicSqlSupport.pubParam)
                .set(PubParamDynamicSqlSupport.parmid).equalToWhenPresent(record::getParmid)
                .set(PubParamDynamicSqlSupport.parmname).equalToWhenPresent(record::getParmname)
                .set(PubParamDynamicSqlSupport.parmval).equalToWhenPresent(record::getParmval)
                .set(PubParamDynamicSqlSupport.encryptflag).equalToWhenPresent(record::getEncryptflag)
                .set(PubParamDynamicSqlSupport.parmdesc).equalToWhenPresent(record::getParmdesc)
                .set(PubParamDynamicSqlSupport.operator).equalToWhenPresent(record::getOperator)
                .set(PubParamDynamicSqlSupport.updttime).equalToWhenPresent(record::getUpdttime)
                .set(PubParamDynamicSqlSupport.type).equalToWhenPresent(record::getType)
                .set(PubParamDynamicSqlSupport.createtime).equalToWhenPresent(record::getCreatetime)
                .set(PubParamDynamicSqlSupport.status).equalToWhenPresent(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(PubParam record) {
        return UpdateDSL.updateWithMapper(this::update, PubParamDynamicSqlSupport.pubParam)
                .set(PubParamDynamicSqlSupport.parmname).equalTo(record::getParmname)
                .set(PubParamDynamicSqlSupport.parmval).equalTo(record::getParmval)
                .set(PubParamDynamicSqlSupport.encryptflag).equalTo(record::getEncryptflag)
                .set(PubParamDynamicSqlSupport.parmdesc).equalTo(record::getParmdesc)
                .set(PubParamDynamicSqlSupport.operator).equalTo(record::getOperator)
                .set(PubParamDynamicSqlSupport.updttime).equalTo(record::getUpdttime)
                .set(PubParamDynamicSqlSupport.type).equalTo(record::getType)
                .set(PubParamDynamicSqlSupport.createtime).equalTo(record::getCreatetime)
                .set(PubParamDynamicSqlSupport.status).equalTo(record::getStatus)
                .where(PubParamDynamicSqlSupport.parmid, isEqualTo(record::getParmid))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(PubParam record) {
        return UpdateDSL.updateWithMapper(this::update, PubParamDynamicSqlSupport.pubParam)
                .set(PubParamDynamicSqlSupport.parmname).equalToWhenPresent(record::getParmname)
                .set(PubParamDynamicSqlSupport.parmval).equalToWhenPresent(record::getParmval)
                .set(PubParamDynamicSqlSupport.encryptflag).equalToWhenPresent(record::getEncryptflag)
                .set(PubParamDynamicSqlSupport.parmdesc).equalToWhenPresent(record::getParmdesc)
                .set(PubParamDynamicSqlSupport.operator).equalToWhenPresent(record::getOperator)
                .set(PubParamDynamicSqlSupport.updttime).equalToWhenPresent(record::getUpdttime)
                .set(PubParamDynamicSqlSupport.type).equalToWhenPresent(record::getType)
                .set(PubParamDynamicSqlSupport.createtime).equalToWhenPresent(record::getCreatetime)
                .set(PubParamDynamicSqlSupport.status).equalToWhenPresent(record::getStatus)
                .where(PubParamDynamicSqlSupport.parmid, isEqualTo(record::getParmid))
                .build()
                .execute();
    }
}