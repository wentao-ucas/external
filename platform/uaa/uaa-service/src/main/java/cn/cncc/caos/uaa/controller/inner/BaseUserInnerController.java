package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.controller.base.BaseBaseUserController;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.service.BaseSyncService;
import cn.cncc.caos.uaa.service.DepService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.uaa.service.UserService;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import cn.cncc.caos.uaa.utils.PushPortalInfoUtil;
import cn.cncc.caos.platform.uaa.client.api.UserInfoDepsAndLocalhostNamesReq;
import cn.cncc.caos.platform.uaa.client.api.pojo.AssigneeIdAndGroupDTO;
import cn.cncc.caos.platform.uaa.client.api.pojo.AssigneeIdAndGroupUserDTO;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.platform.uaa.client.api.pojo.UserUpdatePassDto;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@RestController
@Slf4j
@MojitoSchema(schemaId = "inner_api/baseUserInnerController")
public class BaseUserInnerController extends BaseBaseUserController {

    @Resource
    private ServerConfigHelper serverConfigHelper;
    @Autowired
    private BaseUserMapperEx baseUserMapperEx;
    //private BaseUserMapper baseUserMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DepService depService;
    //  @Autowired
//  private Environment env;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PushPortalInfoUtil pushPortalInfoUtil;
    //  @Value("${system.instance}")
//  private String systemInstance;
    @Resource
    private KDHelper kdHelper;
    @Autowired
    private BaseSyncService baseSyncService;

    private String operTypeAdd = "userAdd";
    private String operTypeUpdate = "userUpdate";
    private String operTypeDelete = "userDelete";
    private String operTypeRestPwd = "restUserPwd";

    private final String IOPS_OPERATOR = "门户推送";

    @Autowired
    private OperationHistoryService operationHistoryService;


    private class UserListInfoWithTotalNum {
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


    @ResponseBody
    @RequestMapping(value = "/api/admin/user/get_by_condition", method = RequestMethod.GET)
    public JwResponseData<UserListInfoWithTotalNum> getUserListByDepId4(
            @RequestParam(value = "depIds", required = false, defaultValue = "") String depIdsInput,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "searchString", required = false, defaultValue = "") String searchString
    ) {

        try {
            if (depIdsInput.equals("")) {
                Long total = baseUserMapperEx.countByExample().where(BaseUserDynamicSqlSupport.realName, isLike("%" + searchString + "%")).build().execute();
//            log.info("total" + total.toString());
                PageHelper.startPage(pageNum, pageSize);
                List<BaseUser> list = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.realName, isLike("%" + searchString + "%")).orderBy(BaseUserDynamicSqlSupport.isValid, BaseUserDynamicSqlSupport.id).build().execute();
                UserListInfoWithTotalNum result = new UserListInfoWithTotalNum();
                result.setTotal(total);
                result.setList(list);
                for (BaseUser b : list) {
                    //解密
                    EncryptAndDecryptUtil.decryptBaseUser(b);
                    b.setPassword("");
                }
                return JwResponseData.success("获得所有用户列表成功", result);
            } else {
                String[] listStringInput = depIdsInput.split(",");

                List<Integer> listInput = new ArrayList<Integer>();
                for (int i = 0; i < listStringInput.length; i++) {
                    listInput.add(Integer.parseInt(listStringInput[i]));
                }

                Long total = baseUserMapperEx.countByExample().where(BaseUserDynamicSqlSupport.depId, isIn(listInput)).and(BaseUserDynamicSqlSupport.realName, isLike("%" + searchString + "%")).build().execute();

                PageHelper.startPage(pageNum, pageSize);
                List<BaseUser> list = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.depId, isIn(listInput)).and(BaseUserDynamicSqlSupport.realName, isLike("%" + searchString + "%")).orderBy(BaseUserDynamicSqlSupport.isValid, BaseUserDynamicSqlSupport.id).build().execute();

                UserListInfoWithTotalNum result = new UserListInfoWithTotalNum();
                result.setTotal(total);
                result.setList(list);
                for (BaseUser b : list) {
                    //解密
                    EncryptAndDecryptUtil.decryptBaseUser(b);
                    b.setPassword("");
                }
                return JwResponseData.success("根据部门获得用户列表成功", result);
            }
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    // @RequestMapping(value = {"/inner_api/user/get_user_by_name"}, method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserByUserName(@RequestParam(value = "userName") String userName) {
        return super.getUserByUserName(userName);
    }

    // @RequestMapping(value = "/api/user/get_users_by_role_id", method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUserListByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId) {
        try {
            // 调用FeignClient查询用户
            List<BaseUser> list = baseUserMapperEx.selectUsersByRoleId(roleId);
            for (BaseUser baseUser : list) {
                //解密
                EncryptAndDecryptUtil.decryptBaseUser(baseUser);
                baseUser.setPassword("******");
            }
            return JwResponseData.success("getUsersByRoleId获取用户列表成功", list);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    // @RequestMapping(value = {"/inner_api/user/get_user_list_by_real_name_list"}, method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUsersByRealNames(@RequestParam(value = "useRealNames") String useRealNames) {
        try {
            // 调用FeignClient查询用户
            List<BaseUser> list = baseUserMapperEx.selectByExample()
                    .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
                    .and(BaseUserDynamicSqlSupport.realName, isIn(Arrays.asList(useRealNames.split(",")))).build().execute();
            if (!CollectionUtils.isEmpty(list)) {
                for (BaseUser baseUser : list) {
                    EncryptAndDecryptUtil.decryptBaseUser(baseUser);
                }
            }
            return JwResponseData.success("getUsersByRealNames获取成功", list);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    // @RequestMapping(value = {"/inner_api/user/get/user/list/by/real/name/list"}, method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUserListByRealNameList(@RequestBody List<String> realNameList) {
        try {
            List<BaseUser> baseUserList = baseUserMapperEx.selectByExample()
                    .where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
                    .and(BaseUserDynamicSqlSupport.realName, isIn(realNameList)).build().execute();
            //获取到list后，新建list，遍历rolenamelist，然后再循环中遍历baseuserandrolenamelist，
            for (BaseUser baseUser : baseUserList) {
                //解密
                EncryptAndDecryptUtil.decryptBaseUser(baseUser);
                baseUser.setPassword("******");
            }
            log.info("/inner_api/user/get/user/list/by/real/name/list 返回结果:{}", JacksonUtil.objToJson(baseUserList));
            return JwResponseData.success("根据realName获取用户列表成功", baseUserList);
        } catch (IOException e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    // 查询给定列表姓名,是否具有值班经理主值资格,如果有则返回对应名称
    // @RequestMapping(value = {"/inner_api/user/get/pri/user/list/by/real/name/list"}, method = RequestMethod.GET)
    public JwResponseData<List<String>> getPriUserListByRealNameList(@RequestBody List<String> realNameList) {
        try {
            List<String> list = userService.getPriUserListByRealNameList(realNameList);
            return JwResponseData.success("根据真实姓名集合获取拥有主值资格的人员信息集合成功", list);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }


    // @RequestMapping(value = {"/inner_api/user/get_user_phone_list_by_real_name_list"}, method = RequestMethod.GET)
    public JwResponseData<String> getUserPhonesByUserRealNames(@RequestParam(value = "useRealNames") String useRealNames) {
        return super.getUserPhonesByUserRealNames(useRealNames);
    }

    // @RequestMapping(value = {"/inner_api/user/get_users_by_role_name_list"}, method = RequestMethod.POST)
    public JwResponseData<List<BaseUser>> getUserListByRoleNameList(@RequestParam String roleNameListInput) {
        return super.getUserListByRoleNameList(roleNameListInput);
    }

     @RequestMapping(value = "/open_api/admin/user/get_user_by_id", method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserById(@RequestParam(value = "id") String id) {
        if (id.equals("undefined") || StringUtils.isEmpty(id))
            return JwResponseData.success("", null);
        int userId = Integer.parseInt(id);
        BaseUser userById = baseUserMapperEx.getUserById(userId);
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(userById);
        return JwResponseData.success("", userById);
    }

    public JwResponseData<String> getUserDepRoleNameById(@RequestParam(value = "id") Integer id) {
        BaseUser userById = baseUserMapperEx.getUserById(id);
        Map<Object, Object> map = depService.getDepMap();
        Object depRoleName = map.get(String.valueOf(userById.getDepId()));
        return JwResponseData.success("", depRoleName.toString());
    }

    // @RequestMapping(value = "/inner_api/user/get/by/id", method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserInfoById(@RequestParam(value = "id") Integer userId) {
        BaseUser userById = baseUserMapperEx.getUserById(userId);
        if (userById == null) {
            return JwResponseData.success("", null);
        }
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(userById);
        return JwResponseData.success("", userById);
    }

    // @RequestMapping(value = "/inner_api/admin/user/get_user_by_id", method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserByIdSms(@RequestParam(value = "id") String id) {
        if (id.equals("undefined") || StringUtils.isEmpty(id))
            return JwResponseData.success("", null);
        int userId = Integer.parseInt(id);
        BaseUser userById = baseUserMapperEx.getUserById(userId);
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(userById);
        return JwResponseData.success("", userById);
    }

    // @RequestMapping(value = {"/inner_api/user/get_user_by_real_name"}, method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserByRealName(@RequestParam(value = "realName") String realName) {
        return super.getUserByRealName(realName);
    }

    // @RequestMapping(value = "/inner_api/user/role-and-user-id", method = RequestMethod.POST)
    public JwResponseData<AssigneeIdAndGroupUserDTO> getUserByRoleNameAndUserId(@RequestBody AssigneeIdAndGroupDTO assigneeIdAndGroupDTO) {
        Set<String> assigneeGroups = assigneeIdAndGroupDTO.getAssigneeGroups();
        Set<Integer> assigneeIds = assigneeIdAndGroupDTO.getAssigneeIds();
        //根据id获取用户，并组装成map
        AssigneeIdAndGroupUserDTO assigneeIdAndGroupUserDTO = new AssigneeIdAndGroupUserDTO();
        Map<Integer, BaseUser> idUserMap = new HashMap<>();
        Map<String, List<BaseUser>> groupUserListMap = new HashMap<>();
        assigneeIdAndGroupUserDTO.setAssigneeGroupUserMap(groupUserListMap);
        assigneeIdAndGroupUserDTO.setAssigneeIdUserMap(idUserMap);

        if (!CollectionUtils.isEmpty(assigneeIds)) {
            userService.getUserByIds(assigneeIds, idUserMap);
        }

        //根据rolename获取用户，并组装成map
        if (!CollectionUtils.isEmpty(assigneeGroups)) {
            userService.getUserAndRoleNameByGroups(assigneeGroups, groupUserListMap);
        }
        return JwResponseData.success("获取用户成功", assigneeIdAndGroupUserDTO);
    }

    // @RequestMapping(value = "/inner_api/user/get_users_list_by_manager_name", method = RequestMethod.GET)
    public JwResponseData<List<Map<String, Object>>> getUserListByManagerName(@RequestParam(value = "applyName") String applyName, @RequestParam(value = "isPri") String isPri) {
        try {
            log.info("根据申请人姓名:{},是否主值:{},查询对应满足条件的被申请列表", applyName, isPri);
            List<Map<String, Object>> list = userService.getUserListByManagerName(applyName, isPri);
            return JwResponseData.success("获取数据列表成功", list);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
        }
    }

    // 非支付-值班经理换班申请,根据是否适配资源池用户返回可换班用户
    // @RequestMapping(value = "/inner_api/user/get_cfid_users_list_by_manager_name", method = RequestMethod.GET)
    public JwResponseData<List<Map<String, Object>>> getCfidUserListByManagerName(@RequestParam(value = "flag") String flag) {
        try {
            log.info("非支付-查询换班用户是否适配换班资源池用户信息标识:{}", flag);
            List<Map<String, Object>> list = userService.getCfidUserListByManagerName(flag);
            return JwResponseData.success("获取数据列表成功", list);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
        }
    }

    // @RequestMapping(value = "/inner_api/user/get_users_list_by_dep_id", method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUserListByDepId1(@RequestParam(value = "depId") Integer depId) {

        try {
            List<BaseUser> userList = userService.getUserByDepId(depId);
            for (BaseUser baseUser : userList) {
                EncryptAndDecryptUtil.decryptBaseUser(baseUser);
            }
            return JwResponseData.success("根据部门id获取全部部门成员成功", userList);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }


    // @RequestMapping(value = "/inner_api/user/get_users_list_map_by_dep_id", method = RequestMethod.GET)
    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepId(@RequestParam(value = "depId") Integer depId) {
        try {
            List<Map<String, Object>> userList = userService.getUserMapByDepId(depId);
            return JwResponseData.success("根据部门id获取全部部门成员成功", userList);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }


    // @RequestMapping(value = "/inner_api/user/get_users_list_map_by_dep_id_list", method = RequestMethod.GET)
    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepIdList(@RequestBody List<Integer> depIdList) {
        try {
            List<Map<String, Object>> userList = userService.getUserMapByDepIdList(depIdList);
            return JwResponseData.success("根据部门id列表获取全部部门成员成功", userList);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    // @RequestMapping(value = "/inner_api/user/get_users_list_map_by_dep_id_list1", method = RequestMethod.GET)
    public JwResponseData<List<Map<String, Object>>> getUserListMapByDepIdList1(@RequestBody List<Integer> depIdList) {
        try {
            List<Map<String, Object>> userList = userService.getUserMapByDepIdList1(depIdList);
            return JwResponseData.success("根据部门id列表获取全部(包含属地)部门成员成功", userList);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }


    // @RequestMapping(value = {"/inner_api/user/get_user_list_by_real_name_json_list"}, method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUsersByRealNameList(@RequestParam(value = "nameList") String nameList) {
        try {
            // TODO 值班辅助系统调用超时，查原因
            List<String> list = JacksonUtil.jsonToObj(nameList, List.class);
            List<BaseUser> retList = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
                    .and(BaseUserDynamicSqlSupport.realName, isIn(list)).build().execute();
            if (!CollectionUtils.isEmpty(list)) {
                for (BaseUser baseUser : retList) {
                    EncryptAndDecryptUtil.decryptBaseUser(baseUser);
                }
            }
            return JwResponseData.success("根据真实姓名集合获取用户信息集合成功", retList);
        } catch (IOException e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
        }
    }

    @ResponseBody
    // @RequestMapping(value = {"/inner_api/user/get_users_name_all"}, method = RequestMethod.GET)
    public JwResponseData<List> getAllUsers(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString) {
        return super.getAllUsers(searchString);
    }

    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/user", method = RequestMethod.POST)
    public JwResponseData<String> addOneUser(@RequestBody BaseUser baseUser) {
        try {
            userService.apiAddOneUser(baseUser, IOPS_OPERATOR);
            return JwResponseData.success("新增用户成功");
        } catch (BapParamsException e) {
            return JwResponseData.error(JwResponseCode.INSERT_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.INSERT_ERROR.fillArgs("系统服务异常"));
        }
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/user", method = RequestMethod.DELETE)
    public JwResponseData<String> deleteUserOne(@RequestParam(value = "userName") String userName) {
        try {
            userService.apiDeleteOneUser(userName, IOPS_OPERATOR);
            return JwResponseData.success("删除用户成功", userName);
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.DELETE_ERROR.fillArgs("系统服务异常"));
        }
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/user", method = RequestMethod.PUT)
    public JwResponseData<BaseUser> apiUpdateOneUser(@RequestBody BaseUser baseUser) {
        try {
            baseUser = userService.apiUpdateOneUser(baseUser, IOPS_OPERATOR);
            return JwResponseData.success("修改用户成功", baseUser);
        } catch (BapParamsException e) {
            return JwResponseData.error(JwResponseCode.UPDATE_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.UPDATE_ERROR.fillArgs("系统服务异常"));
        }
    }

    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/user/pass", method = RequestMethod.POST)
    public JwResponseData<String> updateOneUserPass(@RequestBody UserUpdatePassDto userUpdatePassDto) {
        try {
            log.info("修改用户密码， userName:{}", userUpdatePassDto.getUserName());
            String userName = userUpdatePassDto.getUserName();
            String password = userUpdatePassDto.getPassword();
            userService.updateOneUserPass(userName, password, IOPS_OPERATOR);
            return JwResponseData.success("修改用户成功");
        } catch (BapParamsException e) {
            return JwResponseData.error(JwResponseCode.UPDATE_ERROR.fillArgs(e.getMessage()));
        } catch (Exception e) {
            log.error("", e);
            return JwResponseData.error(JwResponseCode.DELETE_ERROR.fillArgs("系统服务异常"));
        }
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/user", method = RequestMethod.POST)
    public JwResponseData<Map<String, BaseUser>> getUserByRealNames(@RequestBody List<String> req) {
        Map<String, BaseUser> result = userService.getUserByRealNames(req);
        return JwResponseData.success("", result);
    }

    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/user/portal/candidate/groups", method = RequestMethod.GET)
    public JwResponseData<String> getCandidateGroups(@RequestParam(value = "userRealName") String userRealName) {
        try {
            String ret = userService.candidateGroups(userRealName);
            return JwResponseData.success("success", ret);
        } catch (Exception e) {
            e.printStackTrace();
            return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("验证请求授权码失败"));
        }
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/admin/system/instance", method = RequestMethod.GET)
    public JwResponseData<String> getSystemInstance() {
        try {
            return JwResponseData.success("success", serverConfigHelper.getValue("system.instance"));
        } catch (Exception e) {
            e.printStackTrace();
            return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("获取信息失败"));
        }
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/user", method = RequestMethod.GET)
    public JwResponseData<BaseUser> getUserByRealNameAndDepId(@RequestParam("depId") Integer depId, @RequestParam("realName") String realName) {
        BaseUser baseUser = userService.getUserByRealNameAndDepId(depId, realName);
        return JwResponseData.success("", baseUser);
    }


    @ResponseBody
    // @RequestMapping(value = "/inner_api/user/get_dep_id_localhost_name", method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUserByDepIdAndLocalhostName(@RequestBody List<Integer> depIdList, @RequestParam("localhostName") String localhostName) {
        List<BaseUser> baseUsers = userService.getUserByDepIdAndLocalhostName(depIdList, localhostName);
        return JwResponseData.success("", baseUsers);
    }

    @ResponseBody
    // @RequestMapping(value = "/inner_api/user/get_dep_ids_localhost_names", method = RequestMethod.GET)
    public JwResponseData<List<BaseUser>> getUserByDepIdsAndLocalhostNames(@RequestBody UserInfoDepsAndLocalhostNamesReq req) {
        List<String> localhostNames = req.getLocalhostNames();
        List<Integer> depIdList = req.getDepIdList();
        List<BaseUser> baseUsers = userService.getUserByDepIdsAndLocalhostNames(depIdList, localhostNames);
        return JwResponseData.success("", baseUsers);
    }

    // @RequestMapping("/inner_api/user/all")
    public JwResponseData<List<BaseUser>> getUserAll() {
        List<BaseUser> baseUsers = baseUserMapperEx.selectAll();
        return JwResponseData.success("", baseUsers);
    }

  // @RequestMapping(value = "/inner_api/user/loc", method = RequestMethod.GET)
  public JwResponseData<Map<String, String>> getUserLoc() {
    List<BaseUser> baseUsers = baseUserMapperEx.selectAll();
    Map<String, String> userRealNameAndLocMap = new HashMap<>();
    for (BaseUser baseUser : baseUsers)
      userRealNameAndLocMap.put(baseUser.getRealName(), baseUser.getLocationName());
    return JwResponseData.success("获取成功", userRealNameAndLocMap);
  }

  public JwResponseData<List> getUsersByRoleId(@RequestParam(value = "roleId", required = true) Integer roleId) {
    return super.getUsersByRoleId(roleId);
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/user/leader_user_list",method = RequestMethod.GET)
  public  JwResponseData<List<BaseUser>> getLeaderUserList() {
    try {
      List<BaseUser> leaderList = baseUserMapperEx.getLeaderUserList();
      return JwResponseData.success("", leaderList);
    } catch (Exception e) {
      log.error("获取领导用户信息失败",e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

