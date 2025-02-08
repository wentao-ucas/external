package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.BaseSync;
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

import static cn.cncc.caos.uaa.db.dao.BaseSyncDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseSyncMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseSync> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseSyncResult")
    BaseSync selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseSyncResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="sync_time", property="syncTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="sync_type", property="syncType", jdbcType=JdbcType.TINYINT),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.TINYINT)
    })
    List<BaseSync> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseSync);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSync);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSync)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BaseSync record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSync)
                .map(id).toProperty("id")
                .map(syncTime).toProperty("syncTime")
                .map(syncType).toProperty("syncType")
                .map(isValid).toProperty("isValid")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BaseSync record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSync)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(syncTime).toPropertyWhenPresent("syncTime", record::getSyncTime)
                .map(syncType).toPropertyWhenPresent("syncType", record::getSyncType)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSync>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, syncTime, syncType, isValid)
                .from(baseSync);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSync>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, syncTime, syncType, isValid)
                .from(baseSync);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BaseSync selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, syncTime, syncType, isValid)
                .from(baseSync)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseSync record) {
        return UpdateDSL.updateWithMapper(this::update, baseSync)
                .set(id).equalTo(record::getId)
                .set(syncTime).equalTo(record::getSyncTime)
                .set(syncType).equalTo(record::getSyncType)
                .set(isValid).equalTo(record::getIsValid);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseSync record) {
        return UpdateDSL.updateWithMapper(this::update, baseSync)
                .set(id).equalToWhenPresent(record::getId)
                .set(syncTime).equalToWhenPresent(record::getSyncTime)
                .set(syncType).equalToWhenPresent(record::getSyncType)
                .set(isValid).equalToWhenPresent(record::getIsValid);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BaseSync record) {
        return UpdateDSL.updateWithMapper(this::update, baseSync)
                .set(syncTime).equalTo(record::getSyncTime)
                .set(syncType).equalTo(record::getSyncType)
                .set(isValid).equalTo(record::getIsValid)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BaseSync record) {
        return UpdateDSL.updateWithMapper(this::update, baseSync)
                .set(syncTime).equalToWhenPresent(record::getSyncTime)
                .set(syncType).equalToWhenPresent(record::getSyncType)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}