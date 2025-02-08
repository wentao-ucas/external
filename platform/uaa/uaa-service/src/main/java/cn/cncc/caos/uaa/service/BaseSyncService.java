package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.StringUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseSyncDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseSyncMapper;
import cn.cncc.caos.uaa.db.pojo.BaseSync;
import cn.cncc.caos.uaa.utils.PushPortalInfoUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@Service
public class BaseSyncService {
  @Autowired
  private BaseSyncMapper baseSyncMapper;
  @Resource
  private PushPortalInfoUtil pushPortalInfoUtil;
  @Resource
  private UserService userService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  //  @Autowired
//  private Environment env;
  @Autowired
  private DepService depService;
  @Autowired
  private ServerConfigHelper serverConfigHelper;

  @SneakyThrows
  @Transactional(rollbackFor = Exception.class)
  public void scheduleTaskUserSync() {

    BaseSync baseSync = this.getBaseSync((byte) 1);
    Date syncTime = baseSync.getSyncTime();
    List<Map> users = new ArrayList<>();
    Date date = new Date();
    syncUserGet(1, syncTime, users, 0);
    for (Map map : users) {
      BaseUser baseUser = userService.getUserByUserName(String.valueOf(map.get("loginid")));
      if (baseUser == null) {
        baseUser = new BaseUser();
        // 新增用户初始化精卫密码
        baseUser.setPassword(passwordEncoder.encode(serverConfigHelper.getValue("defaultUserPassword")));
        baseUser.setIsOnline(0);
        baseUser.setIsAdmin(0);
        baseUser.setIsPublicUser(0);
        baseUser.setCreateTime(new Date());
        baseUser.setUserName(String.valueOf(map.get("loginid")));
        List<BaseUser> baseUserList = new ArrayList<>();
        this.joinIopsUserInfo(baseUser, map, baseUserList);
        userService.batchUpdate(baseUserList);
        BaseUser addBaseUserInfo = userService.getUserByUserName(String.valueOf(map.get("loginid")));
        baseUser.setId(addBaseUserInfo.getId());
      } else {
        List<BaseUser> baseUserList = new ArrayList<>();
        this.joinIopsUserInfo(baseUser, map, baseUserList);
        userService.batchUpdateIops(baseUserList);
      }
    }

    baseSync.setSyncTime(date);
    baseSyncMapper.updateByPrimaryKey(baseSync);
  }

  private void joinIopsUserInfo(BaseUser baseUser, Map map, List<BaseUser> baseUserList) {
    baseUser.setUpdateTime(new Date());
    // 用户中文名处理
    if (!ObjectUtils.isEmpty(map.get("uname"))) {
      if (!String.valueOf(map.get("uname")).equals(baseUser.getRealName())) {
        BaseUser nameBaseUser = userService.getUserByRealName(String.valueOf(map.get("uname")));
        if (nameBaseUser != null) {
          throw new BapParamsException(String.format("用户中文名已存在，不可重复， name: %s", map.get("uname")));
        }
        baseUser.setRealName(String.valueOf(map.get("uname")));
      }
    }
    // 用户邮箱
    if (!ObjectUtils.isEmpty(map.get("email"))) {
      baseUser.setEmail(String.valueOf(map.get("email")));
    }
    // 用户手机号
    if (!ObjectUtils.isEmpty(map.get("tel"))) {
      baseUser.setPhone(String.valueOf(map.get("tel")));
    }
    // 存储部门信息
    BaseDep baseDep = depService.depByCode(String.valueOf(map.get("deptcode")));
    if (baseDep == null) {
      throw new BapParamsException("部门代号不存在: deptcode: %s" + String.valueOf(map.get("deptcode")));
    }
    baseUser.setDepId(baseDep.getId());
    // 用户状态
    if ("US01".equals(String.valueOf(map.get("status")))) {
      baseUser.setIsValid(1);
    } else {
      baseUser.setIsValid(0);
    }
    // 地区
    String areacode = getLocationName(String.valueOf(map.get("areacode")));
    baseUser.setLocationName(areacode);

    baseUserList.add(baseUser);
  }

  @Transactional(rollbackFor = Exception.class)
  public void scheduleTaskDepSync() {
    List<BaseDep> baseDepList = new ArrayList<>();
    BaseSync baseSync = this.getBaseSync((byte) 2);
    Date syncTime = baseSync.getSyncTime();
    List<Map> deps = new ArrayList<>();
    Date date = new Date();
    syncDepGet(1, syncTime, deps, 0);
    log.info("deps: ");
    for (Map user : deps) {
      log.info("dep:  ", user.toString());
    }
    for (Map map : deps) {
      BaseDep baseDep = depService.depByCode(String.valueOf(map.get("deptcode")));
      if (baseDep == null) {
        baseDep = new BaseDep();
      }
      baseDep.setUpdateTime(new Date());
      baseDep.setDepCode(String.valueOf(map.get("deptcode")));
      baseDep.setDepName(String.valueOf(map.get("deptname")));
      baseDep.setDepDesc(String.valueOf(map.get("deptname")));
      if (!ObjectUtils.isEmpty(map.get("ordernum"))) {
        baseDep.setLevel(Integer.parseInt(String.valueOf(map.get("ordernum"))));
      }
      if (!ObjectUtils.isEmpty(map.get("parentid"))) {
        int parentId = Integer.parseInt(String.valueOf(map.get("parentid")));
        if (parentId == 0) {
          baseDep.setParentId(-1);
        } else {
          baseDep.setParentId(parentId);
        }
      }
      if ("0".equals(String.valueOf(map.get("delflag")))) {
        baseDep.setIsValid(1);
      } else {
        baseDep.setIsValid(0);
      }
      baseDepList.add(baseDep);
    }
    if (baseDepList.size() > 0) {
      depService.batchUpdate(baseDepList);
    }
    baseSync.setSyncTime(date);
    depService.deleteDepTreeData();
    baseSyncMapper.updateByPrimaryKey(baseSync);
  }

  private void syncDepGet(int pageNum, Date syncTime, List<Map> deps, int num) {
    Integer pageSize = 100;
    Map<String, Object> retMap = pushPortalInfoUtil.syncDepGet(pageNum, pageSize, syncTime);
    Object total = retMap.get("total");
    Integer totalInt = Integer.parseInt(String.valueOf(total));
    log.info("total: {}", totalInt);
    if (totalInt > 0) {
      deps.addAll((List) retMap.get("rows"));
      num = num + pageSize;
      if (totalInt > num && totalInt > 100) {
        pageNum++;
        syncDepGet(pageNum, syncTime, deps, num);
      }
    }
  }

  private String getLocationName(String areaCode) {
    if (StringUtil.isEmpty(areaCode) || "null".equals(areaCode)) {
      return "";
    }
    switch (areaCode) {
      case "BJ":
        return "北京";
      case "SH":
        return "上海";
      case "WX":
        return "无锡";
      default:
        return areaCode;
    }

  }

  public void syncUserGet(Integer pageNum, Date syncTime, List<Map> users, int num) {
    Integer pageSize = 100;
    Map<String, Object> retMap = pushPortalInfoUtil.syncUserGet(pageNum, pageSize, syncTime);
    Object total = retMap.get("total");
    Integer totalInt = Integer.parseInt(String.valueOf(total));
    log.info("total: {}", totalInt);
    if (totalInt > 0) {
      users.addAll((List) retMap.get("rows"));
      num = num + pageSize;
      if (totalInt > num && totalInt > 100) {
        pageNum++;
        syncUserGet(pageNum, syncTime, users, num);
      }
    }
  }

  public BaseSync getBaseSync(Byte syncType) {
    List<BaseSync> actionList = baseSyncMapper.selectByExample()
        .where(BaseSyncDynamicSqlSupport.syncType, isEqualTo(syncType))
        .and(BaseSyncDynamicSqlSupport.isValid, isEqualTo((byte) 1))
        .build().execute();
    if (actionList == null || actionList.size() == 0) {
      throw new BapParamsException("类型不存在, syncType: " + syncType);
    }
    return actionList.get(0);
  }
}
