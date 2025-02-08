package cn.cncc.caos.uaa.db.dao;

import static cn.cncc.caos.uaa.db.dao.BaseSysDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSys;
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
public interface BaseSysMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);


    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);


    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<BaseSys> insertStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BaseSysResult")
    BaseSys selectOne(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BaseSysResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="sys_name", property="sysName", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_title", property="sysTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_desc", property="sysDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_built_in", property="isBuiltIn", jdbcType=JdbcType.INTEGER),
        @Result(column="sys_url", property="sysUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_status", property="sysStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_version", property="sysVersion", jdbcType=JdbcType.VARCHAR),
        @Result(column="developer", property="developer", jdbcType=JdbcType.VARCHAR),
        @Result(column="image_name", property="imageName", jdbcType=JdbcType.VARCHAR),
        @Result(column="interface_num", property="interfaceNum", jdbcType=JdbcType.INTEGER),
        @Result(column="interface_help_url", property="interfaceHelpUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BaseSys> selectMany(SelectStatementProvider selectStatement);


    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(baseSys);
    }


    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSys);
    }


    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, baseSys)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default int insert(BaseSys record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSys)
                .map(id).toProperty("id")
                .map(sysName).toProperty("sysName")
                .map(sysTitle).toProperty("sysTitle")
                .map(sysDesc).toProperty("sysDesc")
                .map(isBuiltIn).toProperty("isBuiltIn")
                .map(sysUrl).toProperty("sysUrl")
                .map(sysStatus).toProperty("sysStatus")
                .map(sysVersion).toProperty("sysVersion")
                .map(developer).toProperty("developer")
                .map(imageName).toProperty("imageName")
                .map(interfaceNum).toProperty("interfaceNum")
                .map(interfaceHelpUrl).toProperty("interfaceHelpUrl")
                .map(isValid).toProperty("isValid")
                .map(updateTime).toProperty("updateTime")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default int insertSelective(BaseSys record) {
        return insert(SqlBuilder.insert(record)
                .into(baseSys)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(sysName).toPropertyWhenPresent("sysName", record::getSysName)
                .map(sysTitle).toPropertyWhenPresent("sysTitle", record::getSysTitle)
                .map(sysDesc).toPropertyWhenPresent("sysDesc", record::getSysDesc)
                .map(isBuiltIn).toPropertyWhenPresent("isBuiltIn", record::getIsBuiltIn)
                .map(sysUrl).toPropertyWhenPresent("sysUrl", record::getSysUrl)
                .map(sysStatus).toPropertyWhenPresent("sysStatus", record::getSysStatus)
                .map(sysVersion).toPropertyWhenPresent("sysVersion", record::getSysVersion)
                .map(developer).toPropertyWhenPresent("developer", record::getDeveloper)
                .map(imageName).toPropertyWhenPresent("imageName", record::getImageName)
                .map(interfaceNum).toPropertyWhenPresent("interfaceNum", record::getInterfaceNum)
                .map(interfaceHelpUrl).toPropertyWhenPresent("interfaceHelpUrl", record::getInterfaceHelpUrl)
                .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
                .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSys>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, sysName, sysTitle, sysDesc, isBuiltIn, sysUrl, sysStatus, sysVersion, developer, imageName, interfaceNum, interfaceHelpUrl, isValid, updateTime)
                .from(baseSys);
    }


    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseSys>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, sysName, sysTitle, sysDesc, isBuiltIn, sysUrl, sysStatus, sysVersion, developer, imageName, interfaceNum, interfaceHelpUrl, isValid, updateTime)
                .from(baseSys);
    }


    default BaseSys selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, sysName, sysTitle, sysDesc, isBuiltIn, sysUrl, sysStatus, sysVersion, developer, imageName, interfaceNum, interfaceHelpUrl, isValid, updateTime)
                .from(baseSys)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseSys record) {
        return UpdateDSL.updateWithMapper(this::update, baseSys)
                .set(id).equalTo(record::getId)
                .set(sysName).equalTo(record::getSysName)
                .set(sysTitle).equalTo(record::getSysTitle)
                .set(sysDesc).equalTo(record::getSysDesc)
                .set(isBuiltIn).equalTo(record::getIsBuiltIn)
                .set(sysUrl).equalTo(record::getSysUrl)
                .set(sysStatus).equalTo(record::getSysStatus)
                .set(sysVersion).equalTo(record::getSysVersion)
                .set(developer).equalTo(record::getDeveloper)
                .set(imageName).equalTo(record::getImageName)
                .set(interfaceNum).equalTo(record::getInterfaceNum)
                .set(interfaceHelpUrl).equalTo(record::getInterfaceHelpUrl)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime);
    }


    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseSys record) {
        return UpdateDSL.updateWithMapper(this::update, baseSys)
                .set(id).equalToWhenPresent(record::getId)
                .set(sysName).equalToWhenPresent(record::getSysName)
                .set(sysTitle).equalToWhenPresent(record::getSysTitle)
                .set(sysDesc).equalToWhenPresent(record::getSysDesc)
                .set(isBuiltIn).equalToWhenPresent(record::getIsBuiltIn)
                .set(sysUrl).equalToWhenPresent(record::getSysUrl)
                .set(sysStatus).equalToWhenPresent(record::getSysStatus)
                .set(sysVersion).equalToWhenPresent(record::getSysVersion)
                .set(developer).equalToWhenPresent(record::getDeveloper)
                .set(imageName).equalToWhenPresent(record::getImageName)
                .set(interfaceNum).equalToWhenPresent(record::getInterfaceNum)
                .set(interfaceHelpUrl).equalToWhenPresent(record::getInterfaceHelpUrl)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime);
    }


    default int updateByPrimaryKey(BaseSys record) {
        return UpdateDSL.updateWithMapper(this::update, baseSys)
                .set(sysName).equalTo(record::getSysName)
                .set(sysTitle).equalTo(record::getSysTitle)
                .set(sysDesc).equalTo(record::getSysDesc)
                .set(isBuiltIn).equalTo(record::getIsBuiltIn)
                .set(sysUrl).equalTo(record::getSysUrl)
                .set(sysStatus).equalTo(record::getSysStatus)
                .set(sysVersion).equalTo(record::getSysVersion)
                .set(developer).equalTo(record::getDeveloper)
                .set(imageName).equalTo(record::getImageName)
                .set(interfaceNum).equalTo(record::getInterfaceNum)
                .set(interfaceHelpUrl).equalTo(record::getInterfaceHelpUrl)
                .set(isValid).equalTo(record::getIsValid)
                .set(updateTime).equalTo(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }


    default int updateByPrimaryKeySelective(BaseSys record) {
        return UpdateDSL.updateWithMapper(this::update, baseSys)
                .set(sysName).equalToWhenPresent(record::getSysName)
                .set(sysTitle).equalToWhenPresent(record::getSysTitle)
                .set(sysDesc).equalToWhenPresent(record::getSysDesc)
                .set(isBuiltIn).equalToWhenPresent(record::getIsBuiltIn)
                .set(sysUrl).equalToWhenPresent(record::getSysUrl)
                .set(sysStatus).equalToWhenPresent(record::getSysStatus)
                .set(sysVersion).equalToWhenPresent(record::getSysVersion)
                .set(developer).equalToWhenPresent(record::getDeveloper)
                .set(imageName).equalToWhenPresent(record::getImageName)
                .set(interfaceNum).equalToWhenPresent(record::getInterfaceNum)
                .set(interfaceHelpUrl).equalToWhenPresent(record::getInterfaceHelpUrl)
                .set(isValid).equalToWhenPresent(record::getIsValid)
                .set(updateTime).equalToWhenPresent(record::getUpdateTime)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}