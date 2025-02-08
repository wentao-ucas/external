package cn.cncc.caos.uaa.service;


import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseDepDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseDepMapper;
import cn.cncc.caos.uaa.db.daoex.BaseDepMapperEx;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.uaa.utils.Tree;
import cn.cncc.caos.uaa.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseDepDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Service
@Slf4j
public class DepService implements BaseDataService {

//  @Value("${system.instance}")
//  private String systemInstance;

  @Resource
  private ServerConfigHelper serverConfigHelper;

  @Autowired
  private BaseDepMapper baseDepMapper;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private OperationHistoryService operationHistoryService;

  @Autowired
  private BaseDepMapperEx baseDepMapperEx;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public boolean hasBuildDepTreeData() {
    if (redisTemplate.opsForHash().hasKey("jwsso_dep:tree", "dep_tree_string")
//        &&redisTemplate.opsForHash().hasKey("jwsso_dep:parent_dep_json", "parent_dep_map_json")
        && redisTemplate.hasKey("jwsso_dep:parent_dep_map") && redisTemplate.hasKey("dep_name_id_map"))
      return true;
    else
      return false;
  }

  public List<Tree<BaseDep>> buildDepTreeData() {
    log.info("==================buildDepTreeData=================================");
    List<BaseDep> list = baseDepMapper.selectByExample().where(isValid, isEqualTo(1)).orderBy(level, id).build().execute();

//    log.info("select dep from db " + list.size());
    List<Tree<BaseDep>> trees = new ArrayList<Tree<BaseDep>>();

    for (BaseDep one : list) {
      Tree<BaseDep> tree = new Tree<BaseDep>();
      tree.setText(one.getDepName());
      tree.setId(one.getId().toString());
      tree.setParentId(one.getParentId().toString());
      tree.setExpand(true);
      Map<String, Object> map = new HashMap<String, Object>();
//      map.put("level", one.getLevel().toString());
      tree.setOtherField(map);

      trees.add(tree);
    }
    try {
      log.info("build trees " + trees.size());
//      log.info("before process " + JacksonUtil.objToJson(trees));
      Tree<BaseDep> root = TreeUtil.build(trees);
      log.info("build root tree  " + trees.size());
//      log.info("before process " + JacksonUtil.objToJson(trees));//看看数据结构，下面的方法可能有死循环
      Map<String, String> map = TreeUtil.getAllParentText(trees, 1);

      log.info("put jwsso_dep:parent_dep_map to redis");
      redisTemplate.opsForHash().putAll("jwsso_dep:parent_dep_map", map);
//    log.info("put jwsso_dep:tree to redis");
      redisTemplate.opsForHash().put("jwsso_dep:tree", "dep_tree_string", root.toString());

      Map<String, String> depNameAndIdMap = buildNameAndIdMap(list, map);
      log.info("put dep_name_id_map to redis");
      stringRedisTemplate.opsForValue().set("dep_name_id_map", JacksonUtil.objToJson(depNameAndIdMap));
    } catch (Exception e) {
      log.error("", e);
    }
    return trees;
  }

  private Map<String, String> buildNameAndIdMap(List<BaseDep> list, Map<String, String> map) {
    Map<String, String> depNameAndIdMap = new HashMap<>();

    for (BaseDep dep : list) {
      Integer id = dep.getId();
      String depName = dep.getDepName();
      Integer parentId = dep.getParentId();
      if (parentId == null)
        continue;

      // 改，支付有总行
//      String parentDepName = map.get(parentId.toString());
//      if (RegulationTypeEnum.CENTER.id.equals(serverConfigHelper.getValue("system.instance"))
//              && (StringUtils.isEmpty(parentDepName) || parentDepName.contains("总行系统事业部"))
//      ) {
//        log.info("depName:" + depName);
//        continue;
//      }

//      if (RegulationTypeEnum.NON_CENTER.id.equals(serverConfigHelper.getValue("system.instance"))
//              && (StringUtils.isEmpty(parentDepName) || parentDepName.contains("生产中心"))
//      )
//        continue;

      String parentDepName = map.get(parentId.toString());
      if (depName.equals("基础设施部") && !parentDepName.equals("总行系统事业部"))
        depNameAndIdMap.put(parentDepName + depName, id.toString());
      else depNameAndIdMap.put(depName, id.toString());
    }
    return depNameAndIdMap;
  }

  public void deleteDepTreeData() {
    redisTemplate.delete("jwsso_dep:parent_dep_map");
    redisTemplate.opsForHash().delete("jwsso_dep:tree", "dep_tree_string");
    redisTemplate.delete("dep_name_id_map");

    buildDepTreeData();
  }

  public Map<Object, Object> getDepMap() {
    if (!hasBuildDepTreeData()) {
      this.buildDepTreeData();
    }
    return redisTemplate.opsForHash().entries("jwsso_dep:parent_dep_map");
  }

  public Map getDepTreeData() throws IOException {
    if (!hasBuildDepTreeData()) {
      this.buildDepTreeData();
    }
    return JacksonUtil.jsonToObj(redisTemplate.opsForHash().get("jwsso_dep:tree", "dep_tree_string").toString(), Map.class);
  }

  public String getParentDepNames(Integer depId) {
    if (!hasBuildDepTreeData()) {
      this.buildDepTreeData();
    }
    log.info("redisTemplate.hasKey(\"jwsso_dep:parent_dep_map\")" + redisTemplate.hasKey("jwsso_dep:parent_dep_map"));
    log.info("depId" + depId);
    Object result = redisTemplate.opsForHash().get("jwsso_dep:parent_dep_map", depId.toString());
    return result == null ? "" : result.toString();
  }

  @Override
  public void reload() {
    this.buildDepTreeData();
  }

  public JwResponseData<BaseDep> getDepByName(String name) {
    List<BaseDep> execute = baseDepMapper.selectByExample().where(BaseDepDynamicSqlSupport.depName, isEqualTo(name)).build().execute();
    if (execute.size() > 0) {
      return JwResponseData.success("", execute.get(0));
    }
    return null;
  }

  public BaseDep getById(Integer depId) {
    List<BaseDep> execute = baseDepMapper.selectByExample()
        .where(BaseDepDynamicSqlSupport.id, isEqualTo(depId))
        .build().execute();
    if (CollectionUtils.isEmpty(execute)) {
      return null;
    }
    return execute.get(0);
  }

  public List<BaseDep> getDepSubdivision(String depName) {
    List<BaseDep> depParentList = baseDepMapper.selectByExample().where(BaseDepDynamicSqlSupport.depName, isEqualTo(depName)).build().execute();
    if (depParentList.size() > 0) {
      List<BaseDep> execute = baseDepMapper.selectByExample()
          .where(BaseDepDynamicSqlSupport.parentId, isEqualTo(depParentList.get(0).getId()))
          .and(BaseDepDynamicSqlSupport.isValid, isEqualTo(1))
          .build().execute();
      return execute;
    }
    return null;
  }

  public Long getCountByParentId(Integer id) {
    return baseDepMapper.countByExample().where(isValid, isEqualTo(1)).and(parentId, isEqualTo(id)).build().execute();
  }

  public Long getCountByParentDepCode(String depCode) {
    return baseDepMapper.countByExample().where(isValid, isEqualTo(1)).and(parentId, isEqualTo(id)).build().execute();
  }

  @Transactional(readOnly = true)
  public BaseDep getParentLevel(Integer depId) {
    BaseDep baseDep = getById(depId);
    if (baseDep == null) {
      throw new BapParamsException("部门不存在");
    }
    return getById(baseDep.getParentId());
  }

  /**
   * 修改部门信息
   *
   * @param baseDep
   */
  public void apiUpdateOneDep(BaseDep baseDep, String operator) {
    BaseDep selectDep = depByCode(baseDep.getDepCode());
    if (selectDep == null) {
      throw new BapParamsException(String.format("部门编码不存在"));
    }
    baseDep.setId(selectDep.getId());
    baseDepMapper.updateByPrimaryKeySelective(baseDep);
    this.deleteDepTreeData();
    this.insertHistoryOper(operator, "apiUpdateOneDep", baseDep.toString());
  }

  private void insertHistoryOper(String operator, String operType, String content) {
    BaseUser baseUser = new BaseUser();
    baseUser.setRealName(operator);
    baseUser.setUserName(operator);
    operationHistoryService.insertHistoryOper(baseUser, operType, content);
  }

  @Transactional(rollbackFor = Exception.class)
  public void apiDeleteOneDep(String depCode, String operator) {
    BaseDep byCodeDep = this.depByCode(depCode);
    if (byCodeDep == null) {
      throw new BapParamsException("部门编码不存在");
    }
    Long count = this.getCountByParentId(byCodeDep.getId());
    if (count > 0) {
      throw new BapParamsException("因为存在未删除的子部门所以无法删除本部门");
    }
    BaseDep baseDep = new BaseDep();
    baseDep.setId(byCodeDep.getId());
    baseDep.setUpdateTime(TimeUtil.getCurrentTime());
    baseDep.setIsValid(0);
    baseDepMapper.updateByPrimaryKeySelective(baseDep);
    this.deleteDepTreeData();
    this.insertHistoryOper(operator, "apiDeleteOneDep", id.toString());
  }

  @Transactional(rollbackFor = Exception.class)
  public BaseDep apiAddOneDep(BaseDep baseDep, String operator) {
    BaseDep byCodeDep = this.depByCode(baseDep.getDepCode());
    if (byCodeDep != null) {
      throw new BapParamsException("部门编号已存在，不可重复");
    }
    baseDep.setIsValid(1);
    baseDepMapper.insert(baseDep);
    this.deleteDepTreeData();
    this.insertHistoryOper(operator, "apiAddOneDep", baseDep.toString());
    return baseDep;
  }

  public BaseDep depByCode(String depCode) {
    List<BaseDep> depList = baseDepMapper.selectByExample()
        .where(BaseDepDynamicSqlSupport.depCode, isEqualTo(depCode)).build().execute();
    if (depList.size() != 0) {
      return depList.get(0);
    } else {
      return null;
    }

  }

  public void batchUpdate(List<BaseDep> baseDepList) {
    baseDepMapperEx.batchUpdate(baseDepList);
  }

  public void syncBatchUpdate(String value) throws IOException {
    List<BaseDep> baseDepList = JacksonUtil.jsonToObj(value, List.class, BaseDep.class);
    this.batchUpdate(baseDepList);
    this.deleteDepTreeData();
  }

  public void syncInsert(String value) throws IOException {
    this.deleteDepTreeData();
    BaseDep baseDep = JacksonUtil.jsonToObj(value, BaseDep.class);
    baseDepMapper.insert(baseDep);
  }

  public void syncUpdate(String value) throws IOException {
    this.deleteDepTreeData();
    BaseDep baseDep = JacksonUtil.jsonToObj(value, BaseDep.class);
    baseDepMapper.updateByPrimaryKeySelective(baseDep);
  }

  public List<BaseDep> getDepAll() {
    return baseDepMapperEx.selectAll();
  }


	public BaseDep getDepByCode(String depCode) {
		List<BaseDep> execute = baseDepMapper.selectByExample()
						.where(BaseDepDynamicSqlSupport.depCode, isEqualTo(depCode))
						.and(BaseDepDynamicSqlSupport.isValid, isEqualTo(1))
						.build().execute();
		if (CollectionUtils.isEmpty(execute) && execute.size() < 1) {
			return null;
		} else {
			return execute.get(0);
		}
	}

  public List<BaseDep> getByIdList(List<Integer> idList) {
    return baseDepMapper.selectByExample()
            .where(BaseDepDynamicSqlSupport.id, isIn(idList))
            .build().execute();
  }

  public List<String> getDepIdByNames(List<String> depNames){
    List<BaseDep> baseDeps = baseDepMapperEx.getDepIdByNames(depNames);
    List<String> deptIds = new ArrayList<>();
    for(BaseDep baseDep: baseDeps){
      deptIds.add(String.valueOf(baseDep.getId()));
    }
    return deptIds;
  }
}
