package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.UserInfoDepsAndLocalhostNamesReq;
import cn.cncc.caos.platform.uaa.client.api.pojo.AssigneeIdAndGroupDTO;
import cn.cncc.caos.platform.uaa.client.api.pojo.AssigneeIdAndGroupUserDTO;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.UserUpdatePassDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface BaseUserInnerControllerService {

    public JwResponseData<UserListInfoWithTotalNum> getUserListByDepId4(
            @RequestParam(value = "depIds", required = false, defaultValue = "") String depIdsInput,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "searchString", required = false, defaultValue = "") String searchString
    );

    public JwResponseData<BaseUser> getUserByUserName(@RequestParam(value = "userName") String userName);

    public JwResponseData<List<BaseUser>> getUserListByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId);

    public JwResponseData<List<BaseUser>> getUsersByRealNames(@RequestParam(value = "useRealNames") String useRealNames);

    public JwResponseData<List<BaseUser>> getUserListByRealNameList(@RequestBody List<String> realNameList);

    // 查询给定列表姓名,是否具有值班经理主值资格,如果有则返回对应名称
    public JwResponseData<List<String>> getPriUserListByRealNameList(@RequestBody List<String> realNameList);

    public JwResponseData<String> getUserPhonesByUserRealNames(@RequestParam(value = "useRealNames") String useRealNames);

    public JwResponseData<List<BaseUser>> getUserListByRoleNameList(@RequestParam String roleNameListInput) ;

    public JwResponseData<BaseUser> getUserById(@RequestParam(value = "id") String id);

    public JwResponseData<String> getUserDepRoleNameById (@RequestParam(value = "id") Integer id);

    public JwResponseData<BaseUser> getUserByIdSms(@RequestParam(value = "id") String id);

    public JwResponseData<BaseUser> getUserByRealName(@RequestParam(value = "realName") String realName);

    public JwResponseData<AssigneeIdAndGroupUserDTO> getUserByRoleNameAndUserId(@RequestBody AssigneeIdAndGroupDTO assigneeIdAndGroupDTO);

    public JwResponseData<List<Map<String, Object>>> getUserListByManagerName(@RequestParam(value = "applyName") String applyName, @RequestParam(value = "isPri") String isPri);

    public JwResponseData<List<Map<String, Object>>> getCfidUserListByManagerName(@RequestParam(value = "flag") String flag);

    public JwResponseData<List<BaseUser>> getUserListByDepId1(@RequestParam(value = "depId") Integer depId);

    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepId(@RequestParam(value = "depId") Integer depId);

    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepIdList(@RequestBody List<Integer> depIdList);

    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepIdList1(@RequestBody List<Integer> depIdList);

    public JwResponseData<List<BaseUser>> getUsersByRealNameList(@RequestParam(value = "nameList") String nameList);

    public JwResponseData<List<Map>> getAllUsers(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString);

    public JwResponseData<String> addOneUser(@RequestBody BaseUser baseUser);

    public JwResponseData<String> deleteUserOne(@RequestParam(value = "userName") String userName);

    public JwResponseData<BaseUser> apiUpdateOneUser(@RequestBody BaseUser baseUser);

    public JwResponseData<String> updateOneUserPass(@RequestBody UserUpdatePassDto userUpdatePassDto);

    public JwResponseData<Map<String, BaseUser>> getUserByRealNames(@RequestBody List<String> req);

    public JwResponseData<String> getCandidateGroups(@RequestParam(value = "userRealName") String userRealName);

    public JwResponseData<String> getSystemInstance();

    public JwResponseData<BaseUser> getUserByRealNameAndDepId(@RequestParam("depId") Integer depId, @RequestParam("realName") String realName);

    public JwResponseData<List<BaseUser>> getUserByDepIdAndLocalhostName(@RequestBody List<Integer> depIdList, @RequestParam("localhostName") String localhostName);

    public JwResponseData<List<BaseUser>> getUserByDepIdsAndLocalhostNames(@RequestBody UserInfoDepsAndLocalhostNamesReq req);

    public JwResponseData<List<BaseUser>> getUserAll();

    public JwResponseData<Map<String, String>> getUserLoc();

    public JwResponseData<BaseUser> getUserInfoById(@RequestParam(value = "id") Integer id);

    public  JwResponseData<List<BaseUser>> getLeaderUserList();

    public JwResponseData<List> getUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId);

}


class UserListInfoWithTotalNum {
    Long total;
    List<BaseUser> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<BaseUser> getList() {
        return list;
    }

    public void setList(List<BaseUser> list) {
        this.list = list;
    }
}

