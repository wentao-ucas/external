package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.uaa.db.pojo.BasePost;
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

import static cn.cncc.caos.uaa.db.dao.BasePostDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BasePostMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BasePost> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BasePostResult")
    BasePost selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BasePostResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="post_code", property="postCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="post_name", property="postName", jdbcType=JdbcType.VARCHAR),
        @Result(column="post_sort", property="postSort", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_user", property="createUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user", property="updateUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<BasePost> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(basePost);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, basePost);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, basePost)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BasePost record) {
        return insert(SqlBuilder.insert(record)
                .into(basePost)
                .map(id).toProperty("id")
                .map(postCode).toProperty("postCode")
                .map(postName).toProperty("postName")
                .map(postSort).toProperty("postSort")
                .map(status).toProperty("status")
                .map(createUser).toProperty("createUser")
                .map(createTime).toProperty("createTime")
                .map(updateUser).toProperty("updateUser")
                .map(updateTime).toProperty("updateTime")
                .map(remark).toProperty("remark")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BasePost record) {
        return insert(SqlBuilder.insert(record)
                .into(basePost)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(postCode).toPropertyWhenPresent("postCode", record::getPostCode)
                .map(postName).toPropertyWhenPresent("postName", record::getPostName)
                .map(postSort).toPropertyWhenPresent("postSort", record::getPostSort)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(createUser).toPropertyWhenPresent("createUser", record::getCreateUser)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(updateUser).toPropertyWhenPresent("updateUser", record::getUpdateUser)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .map(remark).toPropertyWhenPresent("remark", record::getRemark)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BasePost>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, postCode, postName, postSort, status, createUser, createTime, updateUser, updateTime, remark)
                .from(basePost);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BasePost>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, postCode, postName, postSort, status, createUser, createTime, updateUser, updateTime, remark)
                .from(basePost);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default BasePost selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, postCode, postName, postSort, status, createUser, createTime, updateUser, updateTime, remark)
                .from(basePost)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BasePost record) {
        return UpdateDSL.updateWithMapper(this::update, basePost)
                .set(id).equalTo(record::getId)
                .set(postCode).equalTo(record::getPostCode)
                .set(postName).equalTo(record::getPostName)
                .set(postSort).equalTo(record::getPostSort)
                .set(status).equalTo(record::getStatus)
                .set(createUser).equalTo(record::getCreateUser)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateUser).equalTo(record::getUpdateUser)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(remark).equalTo(record::getRemark);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BasePost record) {
        return UpdateDSL.updateWithMapper(this::update, basePost)
                .set(id).equalToWhenPresent(record::getId)
                .set(postCode).equalToWhenPresent(record::getPostCode)
                .set(postName).equalToWhenPresent(record::getPostName)
                .set(postSort).equalToWhenPresent(record::getPostSort)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createUser).equalToWhenPresent(record::getCreateUser)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateUser).equalToWhenPresent(record::getUpdateUser)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(remark).equalToWhenPresent(record::getRemark);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BasePost record) {
        return UpdateDSL.updateWithMapper(this::update, basePost)
                .set(postCode).equalTo(record::getPostCode)
                .set(postName).equalTo(record::getPostName)
                .set(postSort).equalTo(record::getPostSort)
                .set(status).equalTo(record::getStatus)
                .set(createUser).equalTo(record::getCreateUser)
                .set(createTime).equalTo(record::getCreateTime)
                .set(updateUser).equalTo(record::getUpdateUser)
                .set(updateTime).equalTo(record::getUpdateTime)
                .set(remark).equalTo(record::getRemark)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BasePost record) {
        return UpdateDSL.updateWithMapper(this::update, basePost)
                .set(postCode).equalToWhenPresent(record::getPostCode)
                .set(postName).equalToWhenPresent(record::getPostName)
                .set(postSort).equalToWhenPresent(record::getPostSort)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(createUser).equalToWhenPresent(record::getCreateUser)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(updateUser).equalToWhenPresent(record::getUpdateUser)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .set(remark).equalToWhenPresent(record::getRemark)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}