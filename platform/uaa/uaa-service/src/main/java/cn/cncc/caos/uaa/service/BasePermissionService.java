package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.uaa.EnvTypeEnum;
import cn.cncc.caos.uaa.db.dao.BasePermissionDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BasePermissionMapper;
import cn.cncc.caos.uaa.db.daoex.BasePermissionMapperEx;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.model.permission.BasePermissionReq;
import cn.cncc.caos.uaa.model.permission.BasePermissionRes;
import cn.cncc.caos.uaa.model.permission.BasePermissionUpdateReq;
import cn.cncc.caos.uaa.db.pojo.BasePermission;
import cn.cncc.caos.uaa.db.pojo.BaseRoleAuthPermission;
import cn.cncc.caos.uaa.utils.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Slf4j
@Service
public class BasePermissionService {

  @Autowired
  private BasePermissionMapper basePermissionMapper;

  @Autowired
  private BasePermissionMapperEx basePermissionMapperEx;

  @Resource
  private RedisTemplate<String, BasePermissionRes> redisTemplate;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private BaseRoleAuthPermissionService baseRoleAuthPermissionService;

  @Value("${permission.redis.key}")
  private String permissionRedisKey;

  @Resource
  private KDHelper kdHelper;


  public List<String> getPermissionNamesByRoleId(String id) {
    List<BaseRoleAuthPermission> baseRolePermissions = baseRoleAuthPermissionService.getPermissionNamesByRoleId(id);
    if (!CollectionUtils.isEmpty(baseRolePermissions)) {
      List<String> permissionIds = baseRolePermissions.stream().map(BaseRoleAuthPermission::getPermissionId).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(permissionIds)) {
        return permissionIds;
      }
    }
    return null;
  }

  public void addPermission(BasePermissionReq basePermissionReq) {
    //校验父目录
    checkParentResource(basePermissionReq.getParentName());
    checkExist(basePermissionReq);
    //入库
    BasePermission basePermission = new BasePermission();
    basePermission.setId(idHelper.generatePermissionId());
    BeanUtils.copyProperties(basePermissionReq, basePermission);
    basePermission.setCreateTime(new Date());
    basePermission.setUpdateTime(new Date());
    basePermission.setStatus((byte) 1);
    basePermissionMapper.insert(basePermission);
    log.info("basePermission insert to db success basePermissionTitle:" + basePermission.getTitle());
    //删除redis中的缓存
    redisTemplate.delete(permissionRedisKey);
    log.info("delete redis value");
  }

  private void checkExist(BasePermissionReq basePermissionReq) {
    if (!StringUtils.isEmpty(basePermissionReq.getName())) {
      List<BasePermission> execute = basePermissionMapper.selectByExample()
              .where(BasePermissionDynamicSqlSupport.name, isEqualTo(basePermissionReq.getName()))
              .and(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
              .build().execute();
      if (!CollectionUtils.isEmpty(execute)) {
        throw new DataAuthException(JwResponseCode.NAME_DUPLICATE.fillArgs("权限标识重复"));
      }
    }
  }

  private void checkParentResource(String parentName) {
    if (parentName == null || parentName.equals(String.valueOf(0))) {
      return;
    }
    List<BasePermission> basePermissionList = basePermissionMapper.selectByExample()
            .where(BasePermissionDynamicSqlSupport.name, isEqualTo(parentName))
            .and(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
            .build().execute();
    if (CollectionUtils.isEmpty(basePermissionList))
      throw new DataAuthException(JwResponseCode.PERMISSION_PARENT_ERROR);
  }

  public void updatePermission(BasePermissionUpdateReq basePermissionUpdateReq) {
    //1.校验该权限是否存在
    checkPermissionExist(basePermissionUpdateReq);
    //2.更新
    BasePermission basePermission = new BasePermission();
    BeanUtils.copyProperties(basePermissionUpdateReq, basePermission);
    basePermission.setUpdateTime(new Date());
    basePermissionMapper.updateByPrimaryKeySelective(basePermission);
    log.info("insert basePermission end basePermissionTitle:" + basePermission.getTitle());
    //删除redis中的缓存
    redisTemplate.delete(permissionRedisKey);
    log.info("delete redis value");
  }

  private void checkPermissionExist(BasePermissionUpdateReq basePermissionUpdateReq) {
    BasePermission basePermission = basePermissionMapper.selectByPrimaryKey(basePermissionUpdateReq.getId());
    if (basePermission == null)
      throw new DataAuthException(JwResponseCode.DB_DATA_NOT_EXIST);
  }

  public List<BasePermissionRes> getAllPermission(String searchColumn) {
    BasePermissionRes result;
    if (StringUtils.isEmpty(searchColumn)) {
      result = getAllPermissionRes();
    } else {
      result = getAllPermissionResSearch(searchColumn);
    }
    if (result != null) {
      return result.getChildren();
    }
    return null;
  }

  private BasePermissionRes getAllPermissionRes() {
    //从redis中获取
    Object o = redisTemplate.opsForValue().get(permissionRedisKey);
    if (o != null) {
      return (BasePermissionRes) o;
    }
    //查询
    List<BasePermission> basePermissionsFromDb = basePermissionMapper.selectByExample().
            where(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
            .orderBy(BasePermissionDynamicSqlSupport.seq)
            .build().execute();
    if (CollectionUtils.isEmpty(basePermissionsFromDb))
      return null;
    //1.组装map
    Map<String, List<BasePermission>> basePermissionParentNameMap = getBasePermissionParentNameMap(basePermissionsFromDb);
    //2.组装tree
    BasePermissionRes result = getBasePermissionResTree(basePermissionParentNameMap);
    //3.放入redis中
    redisTemplate.opsForValue().set(permissionRedisKey, result);
    return result;
  }

  private BasePermissionRes getAllPermissionResSearch(String searchColumn) {
    List<BasePermission> basePermissionsFromDb = basePermissionMapper.selectByExample().
            where(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
            .orderBy(BasePermissionDynamicSqlSupport.seq)
            .build().execute();
    if (CollectionUtils.isEmpty(basePermissionsFromDb))
      return null;
    //筛选，并组装nameBasepermissionmap
    Map<String, BasePermission> nameBasePermissionMap = basePermissionsFromDb.stream().collect(Collectors.toMap(BasePermission::getName, Function.identity()));
    Set<BasePermission> basePermissionSetFromDbSearch = new HashSet<>();
    for (BasePermission basePermission : basePermissionsFromDb) {
      if (basePermission.getTitle().contains(searchColumn)) {
        //首先把符合搜索条件的加入set
        basePermissionSetFromDbSearch.add(basePermission);
        //获取所有筛选结果的父类，如果有父类，就加入到set中
        checkHasParentPermission(basePermission, basePermissionSetFromDbSearch, nameBasePermissionMap);
      }
    }
    List<BasePermission> basePermissionsFromDbSearch = new ArrayList<>(basePermissionSetFromDbSearch);
    if (CollectionUtils.isEmpty(basePermissionsFromDbSearch))
      return null;
    //1.组装map 根据parentName分成listmap<Integer,List<permission>>
    Map<String, List<BasePermission>> basePermissionParentNameMap = getBasePermissionParentNameMap(basePermissionsFromDbSearch);
    //2.组装tree
    return getBasePermissionResTree(basePermissionParentNameMap);
  }

  private void checkHasParentPermission(BasePermission basePermission, Set<BasePermission> basePermissionSetFromDbSearch, Map<String, BasePermission> nameBasePermissionMap) {
    if (basePermission.getParentName().equals(String.valueOf(0)))
      return;
    BasePermission basePermissionParent = nameBasePermissionMap.get(basePermission.getParentName());
    if (basePermissionParent != null) {
      basePermissionSetFromDbSearch.add(basePermissionParent);
      checkHasParentPermission(basePermissionParent, basePermissionSetFromDbSearch, nameBasePermissionMap);
    }
  }

  private BasePermissionRes getBasePermissionResTree(Map<String, List<BasePermission>> basePermissionParentNameMap) {
    BasePermissionRes basePermissionRes = new BasePermissionRes();
    basePermissionRes.setId("0");
    basePermissionRes.setName("0");
    basePermissionRes.setPath("/");
    basePermissionRes.setSeq(0);
    basePermissionRes.setTitle("根节点");
    //新建map，遍历所有的permission，如果他的parentName在listmap中，name不能作为key
    setBasePermissionRes(basePermissionRes, basePermissionParentNameMap);
    return basePermissionRes;
  }

  private void setBasePermissionRes(BasePermissionRes basePermissionRes, Map<String, List<BasePermission>> basePermissionParentNameMap) {
    List<BasePermission> basePermissions = basePermissionParentNameMap.get(basePermissionRes.getName());
    if (CollectionUtils.isEmpty(basePermissions)) {
      return;
    }
    //装换res
    List<BasePermissionRes> basePermissionResList = basePermissions.stream().map(basePermission -> {
      BasePermissionRes basePermissionResChild = new BasePermissionRes();
      BeanUtils.copyProperties(basePermission, basePermissionResChild);
      return basePermissionResChild;
    }).collect(Collectors.toList());
    basePermissionRes.setChildren(basePermissionResList);
    for (BasePermissionRes permissionRes : basePermissionResList) {
      setBasePermissionRes(permissionRes, basePermissionParentNameMap);
    }
  }

  private Map<String, List<BasePermission>> getBasePermissionParentNameMap(List<BasePermission> basePermissionsFromDb) {
    Map<String, List<BasePermission>> parentNameBasePermissionMap = new HashMap<>();
    //根据parentName`分成listmap<Integer,List<permission>>
    for (BasePermission basePermission : basePermissionsFromDb) {
      if (parentNameBasePermissionMap.containsKey(basePermission.getParentName())) {
        List<BasePermission> basePermissions = parentNameBasePermissionMap.get(basePermission.getParentName());
        basePermissions.add(basePermission);
      } else {
        List<BasePermission> basePermissionList = new ArrayList<>();
        basePermissionList.add(basePermission);
        parentNameBasePermissionMap.put(basePermission.getParentName(), basePermissionList);
      }
    }
    return parentNameBasePermissionMap;
  }

  public void deletePermission(String id) {
    Set<String> basePermissionIdsDelete = new HashSet<>();
    basePermissionIdsDelete.add(id);
    //删除该节点下所有permission
    //查询所有parentName为当前权限name的权限,并查询其子集
    BasePermission basePermission = basePermissionMapper.selectByPrimaryKey(id);
//    if (basePermission==null)
//      throw new DataAuthException(JwResponseCode.DB_DATA_NOT_EXIST);
//    getChildPermissionIds(basePermissionIdsDelete,basePermission);
    List<BasePermission> basePermissions = basePermissionMapper.selectByExample()
            .where(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
            .build().execute();
    if (CollectionUtils.isEmpty(basePermissions))
      return;
    Map<String, List<BasePermission>> basePermissionParentNameMap = getBasePermissionParentNameMap(basePermissions);
    getDeletePermissionList(basePermission.getName(), basePermissionIdsDelete, basePermissionParentNameMap);
    //批量更新
    if (CollectionUtils.isEmpty(basePermissionIdsDelete))
      return;
    basePermissionMapperEx.batchUpdateBasePermissions(basePermissionIdsDelete, new Date());
    log.info("delete basePermission end deleteIds:" + basePermissionIdsDelete);
    //批量更新同步
    redisTemplate.delete(permissionRedisKey);
    log.info("delete redis value");
  }

//  private void getChildPermissionIds(Set<String> basePermissionIdsDelete, BasePermission basePermission) {
//    List<BasePermission> childPermissionList = basePermissionMapper.selectByExample()
//        .where(BasePermissionDynamicSqlSupport.parentName, isEqualTo(basePermission.getName()))
//        .and(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
//        .build().execute();
//    if (!CollectionUtils.isEmpty(childPermissionList)){
//      for (BasePermission permission : childPermissionList) {
//        basePermissionIdsDelete.add(permission.getId());
//        getChildPermissionIds(basePermissionIdsDelete,permission);
//      }
//    }
//  }

  private void getDeletePermissionList(String name, Set<String> basePermissionIdsDelete, Map<String, List<BasePermission>> basePermissionParentNameMap) {
    List<BasePermission> basePermissionsChild = basePermissionParentNameMap.get(name);
    if (CollectionUtils.isEmpty(basePermissionsChild))
      return;
    for (BasePermission basePermission : basePermissionsChild) {
      basePermissionIdsDelete.add(basePermission.getId());
      getDeletePermissionList(basePermission.getName(), basePermissionIdsDelete, basePermissionParentNameMap);
    }
  }

  public Map<String, Map<String, Map<String, String>>> getSysPermissionByRole(Integer userId) {
    //根据角色id搜索所有的rolePermission
    List<String> permissionIdsByUserId = baseRoleAuthPermissionService.getPermissionIdsByUserId(userId);
    if (CollectionUtils.isEmpty(permissionIdsByUserId))
      return null;
    List<BasePermission> basePermissions = basePermissionMapper.selectByExample()
            .where(BasePermissionDynamicSqlSupport.id, isIn(permissionIdsByUserId))
            .and(BasePermissionDynamicSqlSupport.status, isEqualTo((byte) 1))
            .build().execute();
    if (CollectionUtils.isEmpty(basePermissions))
      return null;
    //获取所有的权限，然后查询出所有的权限，调用getBasePermissionParentIdMap，getBasePermissionResTree，getchild获取list，
    Map<String, List<BasePermission>> basePermissionParentNameMap = getBasePermissionParentNameMap(basePermissions);
    BasePermissionRes basePermissionResTree = getBasePermissionResTree(basePermissionParentNameMap);
    List<BasePermissionRes> sysPermissionList = basePermissionResTree.getChildren();
    if (CollectionUtils.isEmpty(sysPermissionList))
      return null;
    // 遍历出系统，map<String,Map<String,List<Map<String,String>>>>,put系统和new
    Map<String, Map<String, Map<String, String>>> result = new HashMap<>();
    for (BasePermissionRes basePermissionRes : sysPermissionList) {
      if (!EnvTypeEnum.hashPermission(basePermissionRes.getEnv(), kdHelper.getJwEnv())) {
        continue;
      }
      Map<String, Map<String, String>> menuMap = new HashMap<>();
      result.put(basePermissionRes.getName(), menuMap);
      // getchild 获取list，遍历，获取第二层map<String,List<Map<String,String>>>
      List<BasePermissionRes> menuPermissionList = basePermissionRes.getChildren();
      if (CollectionUtils.isEmpty(menuPermissionList))
        continue;
      for (BasePermissionRes permissionRes : menuPermissionList) {
        if (!EnvTypeEnum.hashPermission(permissionRes.getEnv(), kdHelper.getJwEnv())) {
          continue;
        }
        Map<String, String> permissionMap = new HashMap<>();
        menuMap.put(permissionRes.getName(), permissionMap);
        List<BasePermissionRes> permissionList = permissionRes.getChildren();
        // 遍历第二层时getchild 获取list，遍历 组装List<Map<String,String>>
        if (CollectionUtils.isEmpty(permissionList))
          continue;
        for (BasePermissionRes permission : permissionList) {
          if (!EnvTypeEnum.hashPermission(permission.getEnv(), kdHelper.getJwEnv())) {
            continue;
          }
          permissionMap.put(permission.getName(), "true");
        }
      }
    }
    return result;
  }

  public List<cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission> getPermissionByUserId(Integer userId) {
    return basePermissionMapperEx.getPermissionsByUserId(userId);
  }

  public void judgeRelRoleAuth(String id) {
    baseRoleAuthPermissionService.judgePermissionRelRoleAuth(id);
  }

  public void deletePermissionCache() {
    redisTemplate.delete(permissionRedisKey);
    log.info("delete redis value");
  }
}
