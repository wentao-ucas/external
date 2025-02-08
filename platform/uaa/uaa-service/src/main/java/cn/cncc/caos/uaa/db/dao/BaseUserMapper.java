package cn.cncc.caos.uaa.db.dao;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
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

import java.util.List;

import static cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BaseUserMapper {

  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  long count(SelectStatementProvider selectStatement);


  @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
  int delete(DeleteStatementProvider deleteStatement);


  @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
  int insert(InsertStatementProvider<BaseUser> insertStatement);


  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @ResultMap("BaseUserResult")
  BaseUser selectOne(SelectStatementProvider selectStatement);


  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Results(id = "BaseUserResult", value = {
      @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
      @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
      @Result(column = "real_name", property = "realName", jdbcType = JdbcType.VARCHAR),
      @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
      @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
      @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
      @Result(column = "last_login_time", property = "lastLoginTime", jdbcType = JdbcType.TIMESTAMP),
      @Result(column = "image_url", property = "imageUrl", jdbcType = JdbcType.VARCHAR),
      @Result(column = "is_online", property = "isOnline", jdbcType = JdbcType.INTEGER),
      @Result(column = "is_admin", property = "isAdmin", jdbcType = JdbcType.INTEGER),
      @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
      @Result(column = "dep_id", property = "depId", jdbcType = JdbcType.INTEGER),
      @Result(column = "location_name", property = "locationName", jdbcType = JdbcType.VARCHAR),
      @Result(column = "is_valid", property = "isValid", jdbcType = JdbcType.INTEGER),
      @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
      @Result(column = "is_public_user", property = "isPublicUser", jdbcType = JdbcType.INTEGER),
      @Result(column = "company_name", property = "companyName", jdbcType = JdbcType.VARCHAR),
      @Result(column = "duty_role", property = "dutyRole", jdbcType = JdbcType.VARCHAR)
  })
  List<BaseUser> selectMany(SelectStatementProvider selectStatement);


  @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
  int update(UpdateStatementProvider updateStatement);


  default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
    return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
        .from(baseUser);
  }


  default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
    return DeleteDSL.deleteFromWithMapper(this::delete, baseUser);
  }


  default int deleteByPrimaryKey(Integer id_) {
    return DeleteDSL.deleteFromWithMapper(this::delete, baseUser)
        .where(id, isEqualTo(id_))
        .build()
        .execute();
  }


  default int insert(BaseUser record) {
    return insert(SqlBuilder.insert(record)
        .into(baseUser)
        .map(id).toProperty("id")
        .map(userName).toProperty("userName")
        .map(realName).toProperty("realName")
        .map(password).toProperty("password")
        .map(phone).toProperty("phone")
        .map(email).toProperty("email")
        .map(lastLoginTime).toProperty("lastLoginTime")
        .map(imageUrl).toProperty("imageUrl")
        .map(isOnline).toProperty("isOnline")
        .map(isAdmin).toProperty("isAdmin")
        .map(createTime).toProperty("createTime")
        .map(depId).toProperty("depId")
        .map(locationName).toProperty("locationName")
        .map(isValid).toProperty("isValid")
        .map(updateTime).toProperty("updateTime")
        .map(isPublicUser).toProperty("isPublicUser")
        .map(companyName).toProperty("companyName")
        .map(dutyRole).toProperty("dutyRole")
        .build()
        .render(RenderingStrategy.MYBATIS3));
  }


  default int insertSelective(BaseUser record) {
    return insert(SqlBuilder.insert(record)
        .into(baseUser)
        .map(id).toPropertyWhenPresent("id", record::getId)
        .map(userName).toPropertyWhenPresent("userName", record::getUserName)
        .map(realName).toPropertyWhenPresent("realName", record::getRealName)
        .map(password).toPropertyWhenPresent("password", record::getPassword)
        .map(phone).toPropertyWhenPresent("phone", record::getPhone)
        .map(email).toPropertyWhenPresent("email", record::getEmail)
        .map(lastLoginTime).toPropertyWhenPresent("lastLoginTime", record::getLastLoginTime)
        .map(imageUrl).toPropertyWhenPresent("imageUrl", record::getImageUrl)
        .map(isOnline).toPropertyWhenPresent("isOnline", record::getIsOnline)
        .map(isAdmin).toPropertyWhenPresent("isAdmin", record::getIsAdmin)
        .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
        .map(depId).toPropertyWhenPresent("depId", record::getDepId)
        .map(locationName).toPropertyWhenPresent("locationName", record::getLocationName)
        .map(isValid).toPropertyWhenPresent("isValid", record::getIsValid)
        .map(updateTime).toPropertyWhenPresent("updateTime", record::getUpdateTime)
        .map(isPublicUser).toPropertyWhenPresent("isPublicUser", record::getIsPublicUser)
        .map(companyName).toPropertyWhenPresent("companyName", record::getCompanyName)
        .map(dutyRole).toPropertyWhenPresent("dutyRole", record::getDutyRole)
        .build()
        .render(RenderingStrategy.MYBATIS3));
  }


  default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUser>>> selectByExample() {
    return SelectDSL.selectWithMapper(this::selectMany, id, userName, realName, password, phone, email, lastLoginTime, imageUrl, isOnline, isAdmin, createTime, depId, locationName, isValid, updateTime, isPublicUser, companyName, dutyRole)
        .from(baseUser);
  }


  default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<BaseUser>>> selectDistinctByExample() {
    return SelectDSL.selectDistinctWithMapper(this::selectMany, id, userName, realName, password, phone, email, lastLoginTime, imageUrl, isOnline, isAdmin, createTime, depId, locationName, isValid, updateTime, isPublicUser, companyName, dutyRole)
        .from(baseUser);
  }


  default BaseUser selectByPrimaryKey(Integer id_) {
    return SelectDSL.selectWithMapper(this::selectOne, id, userName, realName, password, phone, email, lastLoginTime, imageUrl, isOnline, isAdmin, createTime, depId, locationName, isValid, updateTime, isPublicUser, companyName, dutyRole)
        .from(baseUser)
        .where(id, isEqualTo(id_))
        .build()
        .execute();
  }


  default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(BaseUser record) {
    return UpdateDSL.updateWithMapper(this::update, baseUser)
        .set(id).equalTo(record::getId)
        .set(userName).equalTo(record::getUserName)
        .set(realName).equalTo(record::getRealName)
        .set(password).equalTo(record::getPassword)
        .set(phone).equalTo(record::getPhone)
        .set(email).equalTo(record::getEmail)
        .set(lastLoginTime).equalTo(record::getLastLoginTime)
        .set(imageUrl).equalTo(record::getImageUrl)
        .set(isOnline).equalTo(record::getIsOnline)
        .set(isAdmin).equalTo(record::getIsAdmin)
        .set(createTime).equalTo(record::getCreateTime)
        .set(depId).equalTo(record::getDepId)
        .set(locationName).equalTo(record::getLocationName)
        .set(isValid).equalTo(record::getIsValid)
        .set(updateTime).equalTo(record::getUpdateTime)
        .set(isPublicUser).equalTo(record::getIsPublicUser)
        .set(companyName).equalTo(record::getCompanyName)
        .set(dutyRole).equalTo(record::getDutyRole);
  }


  default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(BaseUser record) {
    return UpdateDSL.updateWithMapper(this::update, baseUser)
        .set(id).equalToWhenPresent(record::getId)
        .set(userName).equalToWhenPresent(record::getUserName)
        .set(realName).equalToWhenPresent(record::getRealName)
        .set(password).equalToWhenPresent(record::getPassword)
        .set(phone).equalToWhenPresent(record::getPhone)
        .set(email).equalToWhenPresent(record::getEmail)
        .set(lastLoginTime).equalToWhenPresent(record::getLastLoginTime)
        .set(imageUrl).equalToWhenPresent(record::getImageUrl)
        .set(isOnline).equalToWhenPresent(record::getIsOnline)
        .set(isAdmin).equalToWhenPresent(record::getIsAdmin)
        .set(createTime).equalToWhenPresent(record::getCreateTime)
        .set(depId).equalToWhenPresent(record::getDepId)
        .set(locationName).equalToWhenPresent(record::getLocationName)
        .set(isValid).equalToWhenPresent(record::getIsValid)
        .set(updateTime).equalToWhenPresent(record::getUpdateTime)
        .set(isPublicUser).equalToWhenPresent(record::getIsPublicUser)
        .set(companyName).equalToWhenPresent(record::getCompanyName)
        .set(dutyRole).equalToWhenPresent(record::getDutyRole);
  }


  default int updateByPrimaryKey(BaseUser record) {
    return UpdateDSL.updateWithMapper(this::update, baseUser)
        .set(userName).equalTo(record::getUserName)
        .set(realName).equalTo(record::getRealName)
        .set(password).equalTo(record::getPassword)
        .set(phone).equalTo(record::getPhone)
        .set(email).equalTo(record::getEmail)
        .set(lastLoginTime).equalTo(record::getLastLoginTime)
        .set(imageUrl).equalTo(record::getImageUrl)
        .set(isOnline).equalTo(record::getIsOnline)
        .set(isAdmin).equalTo(record::getIsAdmin)
        .set(createTime).equalTo(record::getCreateTime)
        .set(depId).equalTo(record::getDepId)
        .set(locationName).equalTo(record::getLocationName)
        .set(isValid).equalTo(record::getIsValid)
        .set(updateTime).equalTo(record::getUpdateTime)
        .set(isPublicUser).equalTo(record::getIsPublicUser)
        .set(companyName).equalTo(record::getCompanyName)
        .set(dutyRole).equalTo(record::getDutyRole)
        .where(id, isEqualTo(record::getId))
        .build()
        .execute();
  }


  default int updateByPrimaryKeySelective(BaseUser record) {
    return UpdateDSL.updateWithMapper(this::update, baseUser)
        .set(userName).equalToWhenPresent(record::getUserName)
        .set(realName).equalToWhenPresent(record::getRealName)
        .set(password).equalToWhenPresent(record::getPassword)
        .set(phone).equalToWhenPresent(record::getPhone)
        .set(email).equalToWhenPresent(record::getEmail)
        .set(lastLoginTime).equalToWhenPresent(record::getLastLoginTime)
        .set(imageUrl).equalToWhenPresent(record::getImageUrl)
        .set(isOnline).equalToWhenPresent(record::getIsOnline)
        .set(isAdmin).equalToWhenPresent(record::getIsAdmin)
        .set(createTime).equalToWhenPresent(record::getCreateTime)
        .set(depId).equalToWhenPresent(record::getDepId)
        .set(locationName).equalToWhenPresent(record::getLocationName)
        .set(isValid).equalToWhenPresent(record::getIsValid)
        .set(updateTime).equalToWhenPresent(record::getUpdateTime)
        .set(isPublicUser).equalToWhenPresent(record::getIsPublicUser)
        .set(companyName).equalToWhenPresent(record::getCompanyName)
        .set(dutyRole).equalToWhenPresent(record::getDutyRole)
        .where(id, isEqualTo(record::getId))
        .build()
        .execute();
  }

  @Select("select * from base_user where phone = #{phoneNumber}")
  List<BaseUser> selectByPhone(@Param("phoneNumber") String phoneNumber);
}