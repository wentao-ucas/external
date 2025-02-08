package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseCompanyDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.uaa.db.pojo.BaseCompany;
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
public interface BaseCompanyMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseCompany> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseCompanyResult")
    BaseCompany selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseCompanyResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="company_name", property="companyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_info", property="contactInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_list", property="chargeList", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseCompany> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseCompany);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseCompany);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseCompany)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseCompany record) {
        return insert(SqlBuilder.insert(record)
                .into(baseCompany)
                .map(id).toProperty("id")
                .map(companyName).toProperty("companyName")
                .map(contactInfo).toProperty("contactInfo")
                .map(chargeList).toProperty("chargeList")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseCompany record) {
        return insert(SqlBuilder.insert(record)
                .into(baseCompany)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(companyName).toPropertyWhenPresent("companyName", record::getCompanyName)
                .map(contactInfo).toPropertyWhenPresent("contactInfo", record::getContactInfo)
                .map(chargeList).toPropertyWhenPresent("chargeList", record::getChargeList)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseCompany>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, companyName, contactInfo, chargeList, isValid, updateTime)
                .from(baseCompany);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseCompany>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, companyName, contactInfo, chargeList, isValid, updateTime)
                .from(baseCompany);
    }


    default BaseCompany selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, companyName, contactInfo, chargeList, isValid, updateTime)
                .from(baseCompany)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseCompany record) {
        return UpdateDSL.updateWithMapper(this::update, baseCompany)
                .set(id).equalTo(record::getId)
                .set(companyName).equalTo(record::getCompanyName)
                .set(contactInfo).equalTo(record::getContactInfo)
                .set(chargeList).equalTo(record::getChargeList)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseCompany record) {
        return UpdateDSL.updateWithMapper(this::update, baseCompany)
                .set(id).equalToWhenPresent(record::getId)
                .set(companyName).equalToWhenPresent(record::getCompanyName)
                .set(contactInfo).equalToWhenPresent(record::getContactInfo)
                .set(chargeList).equalToWhenPresent(record::getChargeList)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }


    default int updateByPrimaryKey(BaseCompany record) {
        return UpdateDSL.updateWithMapper(this::update, baseCompany)
                .set(companyName).equalTo(record::getCompanyName)
                .set(contactInfo).equalTo(record::getContactInfo)
                .set(chargeList).equalTo(record::getChargeList)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseCompany record) {
        return UpdateDSL.updateWithMapper(this::update, baseCompany)
                .set(companyName).equalToWhenPresent(record::getCompanyName)
                .set(contactInfo).equalToWhenPresent(record::getContactInfo)
                .set(chargeList).equalToWhenPresent(record::getChargeList)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}