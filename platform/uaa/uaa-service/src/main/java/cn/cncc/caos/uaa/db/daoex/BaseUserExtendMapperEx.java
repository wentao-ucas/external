package cn.cncc.caos.uaa.db.daoex;

import cn.cncc.caos.uaa.db.dao.BaseUserExtendMapper;
import cn.cncc.caos.uaa.db.pojo.BaseUserExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseUserExtendMapperEx extends BaseUserExtendMapper {

    // 根据指定条件查询拥有生效主值班经理资格的用户
    @Select("SELECT b.id as id,b.user_name userName,b.real_name realName,b.email email,b.dep_id depId,b.location_name locationName,b.phone phone,b.company_name companyName,b.duty_role dutyRole,a.primary_duty_manager pri\n" +
            "FROM base_user_extend a left JOIN base_user b on a.user_id = b.id " +
            "where a.primary_duty_manager = #{pri} and b.is_valid = #{status} and b.location_name = #{local} and b.real_name != #{realName} ")
    List<Map<String, Object>> getManagerUserInfoWithPri(@Param("status") Integer status, @Param("pri") Integer pri, @Param("local") String local, @Param("realName") String realName);

    // 根据指定条件查询拥有生效值班经理资格的用户
    @Select("SELECT b.id as id,b.user_name userName,b.real_name realName,b.email email,b.dep_id depId,b.location_name locationName,b.phone phone,b.company_name companyName,b.duty_role dutyRole,a.primary_duty_manager pri\n" +
            "FROM base_user_extend a left JOIN base_user b on a.user_id = b.id " +
            "where a.duty_manager = #{manager} and b.is_valid = #{status} and b.location_name = #{local} and b.real_name != #{realName}")
    List<Map<String, Object>> getManagerUserInfo(@Param("status") Integer status, @Param("manager") Integer manager, @Param("local") String local, @Param("realName") String realName);


    // 根据用户id查询用户是否拥有生效的值班经理资格
    @Select("select * from base_user_extend where user_id = #{id} and `status` = #{status}")
    BaseUserExtend getBaseUserByRealName(@Param("id") String id, @Param("status") Integer status);

    // 获取审批人联系信息
    @Select("select b.real_name realName,b.phone phone " +
            "FROM base_user_extend a left JOIN base_user b on a.user_id = b.id " +
            "where a.`status` = #{status} and a.approver_mobile = #{approver}")
    List<Map<String,String>> getUserMobileList(@Param("status") Integer status, @Param("approver") Integer approver);

    // 查询非支付所有值班经理资源池用户信息
    @Select("SELECT b.id as id,b.user_name userName,b.real_name realName,b.email email,b.dep_id depId,b.location_name locationName,b.phone phone,b.company_name companyName,b.duty_role dutyRole,a.primary_duty_manager pri\n" +
            "FROM base_user_extend a left JOIN base_user b on a.user_id = b.id " +
            "where a.duty_manager = #{manager} and b.is_valid = #{status}")
    List<Map<String, Object>> getCfidManagerUserInfoWithFlag(@Param("status") Integer status, @Param("manager") Integer manager);

    // 查询非支付所有用户信息
    @Select("SELECT id as id, user_name userName, real_name realName, email email, dep_id depId, location_name locationName, phone phone, company_name companyName, duty_role dutyRole, NULL pri\n" +
            "FROM base_user " +
            "where is_valid = #{status}")
    List<Map<String, Object>> getCfidManagerUserInfo(@Param("status") Integer status);

    // 查询给定列表姓名,是否具有值班经理主值资格,如果有则返回对应名称
    @Select({
            "<script>",
            "select b.real_name",
            "FROM base_user_extend a left join base_user b on a.user_id = b.id ",
            "where a.primary_duty_manager = 1 and b.real_name in ",
            "<foreach collection='realNameList' item='realName' open='(' separator=',' close=')'>",
            "#{realName}",
            "</foreach>",
            "</script>"
    })
    List<String> getPriUserListByRealNameList(@Param("realNameList") List<String> realNameList);

}
