package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BasePermissionDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BasePermissionMapper;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseUserIndividuationConfigDynamicSqlSupport;
import cn.cncc.caos.uaa.db.daoex.BaseUserIndividuationConfigMapperEx;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.db.pojo.BasePermission;
import cn.cncc.caos.uaa.db.pojo.BaseUserIndividuationConfig;
import cn.cncc.caos.uaa.model.individuation.IndividuationResp;
import cn.cncc.caos.uaa.utils.UserRoleAndPermissionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

/**
 * @description 用户个性化配置服务类.
 * 用户每个模块儿配置是否接收邮件和短信
 * @date 2022/11/02 14:44.
 */
@Slf4j
@Service
public class BaseUserIndividuationConfigService {

  @Value("#{${user.individuation.layerToOne}}")
  Map<String, String> otherLayerMap = new HashMap<>();
  //  @Autowired
//  private BaseUserIndividuationConfigMapper baseUserIndividuationConfigMapper; // 用户短信&邮件个性化接收配置表
  @Autowired
  private BaseUserIndividuationConfigMapperEx baseUserIndividuationConfigMapperEx; // 用户短信&邮件个性化接收配置表
  @Autowired
  private BaseUserMapperEx baseUserMapperEx; // 获取所有有效用户
  @Autowired
  private BasePermissionMapper basePermissionMapper; // 用户权限列表

  public List getUserIndividuationConfig(Integer userId) {

    // 获取用户和权限
    String roleAndPermission = UserRoleAndPermissionUtil.getRoleAndPermission();
    List<String> permissionList = Arrays.asList(roleAndPermission.split(","));
    // 获取所有权限信息
//    List<BaseUserIndividuationConfig> execute = baseUserIndividuationConfigMapperEx.selectByExample().where(BaseUserIndividuationConfigDynamicSqlSupport.userId, isEqualTo(userId)).build().execute();
    List<BaseUserIndividuationConfig> execute = baseUserIndividuationConfigMapperEx.selectByExample().where(BaseUserIndividuationConfigDynamicSqlSupport.userId, isEqualTo(userId))
        .and(BaseUserIndividuationConfigDynamicSqlSupport.moduleId, isIn(permissionList)).build().execute();
    if (execute == null || execute.size() == 0) {
      return null;
    }
    List retList = new ArrayList(); // 返回list
    Map<String, IndividuationResp> oneMap = new HashMap(); // 一级目录map
    List<BaseUserIndividuationConfig> secondList = new ArrayList(); // 二级目录list
    for (BaseUserIndividuationConfig one : execute) {
      if (one.getSystemId() == null || one.getSystemId().isEmpty()) {
        log.info("取得一级系统目录,目录唯一id:{},目录名称:{}", one.getModuleId(), one.getModuleName());
        IndividuationResp oneLayer = new IndividuationResp();
        oneLayer.setModuleId(one.getModuleId());
        oneLayer.setModuleName(one.getModuleName());
        oneLayer.setSystemId("parent");
        oneLayer.setMailStatus(one.getMailStatus());
        oneLayer.setSmsStatus(one.getSmsStatus());
        oneMap.put(one.getModuleId(), oneLayer);
      } else {
        secondList.add(one);
        log.info("提取二级目录,目录唯一id:{},目录名称:{}", one.getModuleId(), one.getModuleName());
      }
    }
    try {
      log.info("处理一级目录完成,一级目录数量:{},目录详细信息:{}", retList.size(), JacksonUtil.objToJson(retList));
    } catch (JsonProcessingException e) {
      log.error("", e);
    }

    for (BaseUserIndividuationConfig one : secondList) {
      IndividuationResp oneLayer = oneMap.get(one.getSystemId());
      if (oneLayer == null) {
        continue;
      }
      IndividuationResp child = new IndividuationResp();
      child.setModuleId(one.getModuleId());
      child.setModuleName(one.getModuleName());
      child.setSystemId(one.getSystemId());
      child.setMailStatus(one.getMailStatus());
      child.setSmsStatus(one.getSmsStatus());
      if (oneLayer.getChildren() == null) {
        List<IndividuationResp> childList = new ArrayList<>();
        childList.add(child);
        oneLayer.setChildren(childList);
      } else {
        oneLayer.getChildren().add(child);
      }
      log.info("添加二级目录完成,一级目录唯一id:{},二级目录名称:{}", one.getSystemId(), one.getModuleName());
    }

    for (Map.Entry<String, IndividuationResp> one : oneMap.entrySet()) {
      retList.add(one.getValue());
    }
    try {
      log.info("用户id:{},个性化配置信息为:{}", userId, JacksonUtil.objToJson(retList));
    } catch (JsonProcessingException e) {
      log.error("", e);
    }
    return retList;
  }

  public List getUserIndividuationConfigNew(Integer userId) {

    // 获取用户和权限
    String roleAndPermission = UserRoleAndPermissionUtil.getRoleAndPermission();
    List<String> permissionList = Arrays.asList(roleAndPermission.split(","));

    // 获取所有通知菜单选项
    List<BasePermission> allNotice = basePermissionMapper.selectByExample().where(BasePermissionDynamicSqlSupport.notice, isEqualTo((byte) 1)).build().execute();

    // 整理当前用户所有自定义配置
    log.info("筛选整理用户所有的短信邮件自定义信息");
    Map<String, BaseUserIndividuationConfig> userConfigMap = new HashMap<>();
    for (BasePermission one : allNotice) {
      if (permissionList.contains(one.getName())) {
        BaseUserIndividuationConfig config = new BaseUserIndividuationConfig();
        config.setUserId(userId);
        config.setModuleId(one.getName());
        config.setModuleName(one.getTitle());
        config.setSystemId("0".equals(one.getParentName()) ? "parent" : one.getParentName());
        config.setMailStatus((byte) 1);
        config.setSmsStatus((byte) 1);
        userConfigMap.put(one.getName(), config);
      }
    }

    // 获取用户自定义过得配置
    log.info("更新整理用户所有的自定义短信邮件配置信息");
    List<BaseUserIndividuationConfig> indiConfig = baseUserIndividuationConfigMapperEx.selectByExample()
        .where(BaseUserIndividuationConfigDynamicSqlSupport.userId, isEqualTo(userId)).build().execute();
    for (BaseUserIndividuationConfig config : indiConfig) {
      if (config.getMailStatus() != 1 || config.getSmsStatus() != 1) {
        BaseUserIndividuationConfig updateConfig = userConfigMap.get(config.getModuleId());
        if (updateConfig == null) {
          // 如果自定义信息配置过但用户已经没有权限,则不再展示曾经的配置信息
          continue;
        }
        updateConfig.setMailStatus(config.getMailStatus());
        updateConfig.setSmsStatus(config.getSmsStatus());
        userConfigMap.put(config.getModuleId(), updateConfig);
      }
    }

    // 整理用户最后配置结果
    List<BaseUserIndividuationConfig> userConfigList = new ArrayList<>();
    for (Map.Entry<String, BaseUserIndividuationConfig> entry : userConfigMap.entrySet()) {
      userConfigList.add(entry.getValue());
    }
    log.info("用户短信邮件自定义配置信息结果为:{}条", userConfigList.size());

    List retList = new ArrayList(); // 返回list
    Map<String, IndividuationResp> oneMap = new HashMap(); // 一级目录map
    List<BaseUserIndividuationConfig> secondList = new ArrayList(); // 二级目录list
    for (BaseUserIndividuationConfig one : userConfigList) {
      if (one.getSystemId() == null || one.getSystemId().isEmpty() || "parent".equals(one.getSystemId())) {
        log.info("取得一级系统目录,目录唯一id:{},目录名称:{}", one.getModuleId(), one.getModuleName());
        IndividuationResp oneLayer = new IndividuationResp();
        oneLayer.setModuleId(one.getModuleId());
        oneLayer.setModuleName(one.getModuleName());
        oneLayer.setSystemId("parent");
        oneLayer.setMailStatus(one.getMailStatus());
        oneLayer.setSmsStatus(one.getSmsStatus());
        oneMap.put(one.getModuleId(), oneLayer);
      } else {
        secondList.add(one);
        log.info("提取二级目录,目录唯一id:{},目录名称:{}", one.getModuleId(), one.getModuleName());
      }
    }
    try {
      log.info("处理一级目录完成,一级目录数量:{},目录详细信息:{}", retList.size(), JacksonUtil.objToJson(retList));
    } catch (JsonProcessingException e) {
      log.error("", e);
    }

    for (BaseUserIndividuationConfig one : secondList) {
      IndividuationResp oneLayer = oneMap.get(one.getSystemId());
      if (oneLayer == null) { // 如果找不到对应目录则在三级目录映射关系中寻找一级目录
        oneLayer = oneMap.get(otherLayerMap.get(one.getSystemId()));
      }
      if (oneLayer == null) {
        continue;
      }
      IndividuationResp child = new IndividuationResp();
      child.setModuleId(one.getModuleId());
      child.setModuleName(one.getModuleName());
      child.setSystemId(one.getSystemId());
      child.setMailStatus(one.getMailStatus());
      child.setSmsStatus(one.getSmsStatus());
      if (oneLayer.getChildren() == null) {
        List<IndividuationResp> childList = new ArrayList<>();
        childList.add(child);
        oneLayer.setChildren(childList);
      } else {
        oneLayer.getChildren().add(child);
      }
      log.info("添加二级目录完成,一级目录唯一id:{},二级目录名称:{}", one.getSystemId(), one.getModuleName());
    }

    for (Map.Entry<String, IndividuationResp> one : oneMap.entrySet()) {
      retList.add(one.getValue());
    }
    try {
      log.info("用户id:{},个性化配置信息为:{}", userId, JacksonUtil.objToJson(retList));
    } catch (JsonProcessingException e) {
      log.error("", e);
    }
    return retList;
  }

  public void addOrUpdateUserIndividuationConfig(Integer userId, IndividuationResp configInfo) {
    // 处理入参为数据库实体
    List<BaseUserIndividuationConfig> insertList = new ArrayList<>(); // 整理后需要写入数据库集合
    BaseUserIndividuationConfig insOne = new BaseUserIndividuationConfig();
    insOne.setUserId(userId);
    insOne.setModuleId(configInfo.getModuleId());
    insOne.setModuleName(configInfo.getModuleName());
    insOne.setSystemId("parent".equals(configInfo.getSystemId()) ? null : configInfo.getSystemId());
    insOne.setMailStatus(configInfo.getMailStatus());
    insOne.setSmsStatus(configInfo.getSmsStatus());
    insOne.setCreateTime(new Date());
    insOne.setUpdateTime(new Date());
    insertList.add(insOne);

    if (configInfo.getChildren() != null) {
      log.info("存在子节点,添加子节点开始");
      for (IndividuationResp individuationResp : configInfo.getChildren()) {
        insOne = new BaseUserIndividuationConfig();
        insOne.setUserId(userId);
        insOne.setModuleId(individuationResp.getModuleId());
        insOne.setModuleName(individuationResp.getModuleName());
        insOne.setSystemId(individuationResp.getSystemId());
        insOne.setMailStatus(individuationResp.getMailStatus());
        insOne.setSmsStatus(individuationResp.getSmsStatus());
        insOne.setCreateTime(new Date());
        insOne.setUpdateTime(new Date());
        insertList.add(insOne);
      }
      log.info("系统子节点添加完成,系统名称:{}", configInfo.getModuleName());

    }

    baseUserIndividuationConfigMapperEx.insertOrUpdate(insertList);
    log.info("更新用户配置信息完成");

  }


  /**
   * @Description 根据用户id和模块儿唯一id获取用户个性化配置.
   * @Param userId 用户唯一id.
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 获取用户当前配置.
   **/
  public BaseUserIndividuationConfig getUserIndividuationConfigOneByUserId(Integer userId, String moduleId) {

    BaseUserIndividuationConfig config = baseUserIndividuationConfigMapperEx.selectByPrimaryKey(userId, moduleId);
    return config;
  }

  /**
   * @Description 根据用户id和模块儿唯一id获取用户个性化配置.
   * @Param realName 用户realName.
   * @Param moduleId 模块儿唯一id.
   * @Return BaseUserIndividuationConfig 获取用户当前配置.
   **/
  public BaseUserIndividuationConfig getUserIndividuationConfigOneByRealName(String realName, String moduleId) {

    // 获取用户id
    List<BaseUser> baseUserList = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.realName, isEqualTo(realName))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1)).build().execute();
    if (baseUserList == null || baseUserList.size() == 0) {
      return null;
    }
    BaseUserIndividuationConfig config = baseUserIndividuationConfigMapperEx.selectByPrimaryKey(baseUserList.get(0).getId(), moduleId);
    return config;
  }

  /**
   * @Description 根据用户realName列表和模块儿唯一id获取用户个性化配置.
   * @Param realNames 用户realName列表(使用","分割).
   * @Param moduleId 模块儿唯一id.
   * @Return List<BaseUserIndividuationConfig> 获取用户当前配置.
   **/
  public List<BaseUserIndividuationConfig> getUsersIndividuationConfigListByRealNames(String realNames, String moduleId) {
    // 获取用户id
    String replace = realNames.replace(" ", "").replace("*", "");
    List<String> names = Arrays.asList(replace.split(","));
    List<BaseUser> execute = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.realName, isIn(names))
        .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1)).build().execute();

    List<Integer> idList = new ArrayList();
    for (BaseUser user : execute) {
      idList.add(user.getId());
    }

    // 查询用户配置
    List<BaseUserIndividuationConfig> configs = baseUserIndividuationConfigMapperEx.selectByExample().where(BaseUserIndividuationConfigDynamicSqlSupport.moduleId, isEqualTo(moduleId))
        .and(BaseUserIndividuationConfigDynamicSqlSupport.userId, isIn(idList)).build().execute();
    return configs;
  }

  /**
   * @Description 根据用户userIds列表和模块儿唯一id获取用户个性化配置.
   * @Param userIds 用户userIds列表(list的json串).
   * @Param moduleId 模块儿唯一id.
   * @Return List<BaseUserIndividuationConfig> 获取用户当前配置.
   **/
  public List<BaseUserIndividuationConfig> getUsersIndividuationConfigListByIds(String userIds, String moduleId) throws IOException {
    // 获取用户id
    List<Integer> userIdsList = JacksonUtil.jsonToObj(userIds, List.class);

    // 查询用户配置
    List<BaseUserIndividuationConfig> configs = baseUserIndividuationConfigMapperEx.selectByExample().where(BaseUserIndividuationConfigDynamicSqlSupport.moduleId, isEqualTo(moduleId))
        .and(BaseUserIndividuationConfigDynamicSqlSupport.userId, isIn(userIdsList)).build().execute();
    return configs;
  }

  /**
   * @Description 根据部门id和模块儿唯一id获取用户个性化配置.
   * @Param depId 部门id.
   * @Param moduleId 模块儿唯一id.
   * @Return List<BaseUserIndividuationConfig> 获取用户当前配置.
   **/
  public List<BaseUserIndividuationConfig> getUsersIndividuationConfigListByDepId(Integer depId, String moduleId) {
    // 获取用户id
    List<BaseUser> userList = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.depId, isEqualTo(depId)).build().execute();
    List<Integer> paramList = new ArrayList();
    for (BaseUser user : userList) {
      paramList.add(user.getId());
    }
    // 查询用户配置
    List<BaseUserIndividuationConfig> configs = baseUserIndividuationConfigMapperEx.selectByExample().where(BaseUserIndividuationConfigDynamicSqlSupport.moduleId, isEqualTo(moduleId))
        .and(BaseUserIndividuationConfigDynamicSqlSupport.userId, isIn(paramList)).build().execute();
    return configs;
  }

  public void initUserIndividuationConfig(Map req) {
    // 获取系统列表
    List<BasePermission> sysList = basePermissionMapper.selectByExample().where(BasePermissionDynamicSqlSupport.parentName, isEqualTo("0")).build().execute();
    List<BaseUserIndividuationConfig> instList = new ArrayList();
    for (BasePermission basePermission : sysList) {
      BaseUserIndividuationConfig instOne = new BaseUserIndividuationConfig();
      instOne.setModuleId(basePermission.getName());
      instOne.setModuleName(basePermission.getTitle());
      instOne.setSystemId(null);
      instOne.setMailStatus((byte) 1);
      instOne.setSmsStatus((byte) 1);
      instOne.setCreateTime(new Date());
      instOne.setUpdateTime(new Date());
      instList.add(instOne);
      List<BasePermission> secondList = basePermissionMapper.selectByExample().where(BasePermissionDynamicSqlSupport.parentName, isEqualTo(basePermission.getName())).build().execute();
      for (BasePermission permission : secondList) {
        BaseUserIndividuationConfig instTwo = new BaseUserIndividuationConfig();
        instTwo.setModuleId(permission.getName());
        instTwo.setModuleName(permission.getTitle());
        instTwo.setSystemId(basePermission.getName());
        instTwo.setMailStatus((byte) 1);
        instTwo.setSmsStatus((byte) 1);
        instTwo.setCreateTime(new Date());
        instTwo.setUpdateTime(new Date());
        instList.add(instTwo);
      }
    }
    // 获取所有用户列表
    List<BaseUser> userList = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.isValid, isEqualTo(1)).build().execute();
//    Integer depId = req.getInteger("depId");
//    List<BaseUser> userList = baseUserMapperEx.selectByExample().where(BaseUserDynamicSqlSupport.depId, isEqualTo(depId)).build().execute();
    // 填充用户初始化信息
    long startTime = System.currentTimeMillis();
    for (BaseUser user : userList) {
      if (user.getDepId() == null || user.getDepId() == 1 || user.getDepId() == 167 || user.getDepId() == 91 || user.getDepId() == 92) {
        continue;
      }
      for (BaseUserIndividuationConfig config : instList) {
        config.setUserId(user.getId());
        baseUserIndividuationConfigMapperEx.insert(config);
      }
      log.info("用户id:{},用户姓名:{}", user.getId(), user.getRealName());
    }
    long endTime = System.currentTimeMillis();
    log.info("写入用时:{}", endTime - startTime);


  }

}
