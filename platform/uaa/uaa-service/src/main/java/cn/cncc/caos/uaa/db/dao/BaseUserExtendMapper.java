package cn.cncc.caos.uaa.db.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static cn.cncc.caos.uaa.db.dao.BaseUserExtendDynamicSqlSupport.*;

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
import cn.cncc.caos.uaa.db.pojo.BaseUserExtend;

@Mapper
public interface BaseUserExtendMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseUserExtend> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseUserExtendResult")
    BaseUserExtend selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseUserExtendResult", value = {
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="duty_manager", property="dutyManager", jdbcType=JdbcType.TINYINT),
        @Result(column="primary_duty_manager", property="primaryDutyManager", jdbcType=JdbcType.TINYINT),
        @Result(column="approver_mobile", property="approverMobile", jdbcType=JdbcType.TINYINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseUserExtend> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseUserExtend);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserExtend);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer userId_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseUserExtend)
                .where(userId, isEqualTo(userId_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseUserExtend record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserExtend)
                .map(userId).toProperty("userId")
                .map(dutyManager).toProperty("dutyManager")
                .map(primaryDutyManager).toProperty("primaryDutyManager")
                .map(approverMobile).toProperty("approverMobile")
                .map(status).toProperty("status")
                .map(createTime).toProperty("createTime")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseUserExtend record) {
        return insert(SqlBuilder.insert(record)
                .into(baseUserExtend)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .map(dutyManager).toPropertyWhenPresent("dutyManager", record::getDutyManager)
                .map(primaryDutyManager).toPropertyWhenPresent("primaryDutyManager", record::getPrimaryDutyManager)
                .map(approverMobile).toPropertyWhenPresent("approverMobile", record::getApproverMobile)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserExtend>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, userId, dutyManager, primaryDutyManager, approverMobile, status, createTime, updateTime)
                .from(baseUserExtend);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUserExtend>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, userId, dutyManager, primaryDutyManager, approverMobile, status, createTime, updateTime)
                .from(baseUserExtend);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseUserExtend selectByPrimaryKey(Integer userId_) {
        return SelectDSL.selectWithMapper(this::selectOne, userId, dutyManager, primaryDutyManager, approverMobile, status, createTime, updateTime)
                .from(baseUserExtend)
                .where(userId, isEqualTo(userId_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseUserExtend record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserExtend)
                .set(userId).equalTo(record::getUserId)
                .set(dutyManager).equalTo(record::getDutyManager)
                .set(primaryDutyManager).equalTo(record::getPrimaryDutyManager)
                .set(approverMobile).equalTo(record::getApproverMobile)
                .set(status).equalTo(record::getStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseUserExtend record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserExtend)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(dutyManager).equalToWhenPresent(record::getDutyManager)
                .set(primaryDutyManager).equalToWhenPresent(record::getPrimaryDutyManager)
                .set(approverMobile).equalToWhenPresent(record::getApproverMobile)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseUserExtend record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserExtend)
                .set(dutyManager).equalTo(record::getDutyManager)
                .set(primaryDutyManager).equalTo(record::getPrimaryDutyManager)
                .set(approverMobile).equalTo(record::getApproverMobile)
                .set(status).equalTo(record::getStatus)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(userId, isEqualTo(record::getUserId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseUserExtend record) {
        return UpdateDSL.updateWithMapper(this::update, baseUserExtend)
                .set(dutyManager).equalToWhenPresent(record::getDutyManager)
                .set(primaryDutyManager).equalToWhenPresent(record::getPrimaryDutyManager)
                .set(approverMobile).equalToWhenPresent(record::getApproverMobile)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(userId, isEqualTo(record::getUserId))
                .build()
                .execute();
    }
}