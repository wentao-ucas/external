package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.uaa.db.dao.*;
import cn.cncc.caos.uaa.db.daoex.BaseRuleMapperEx;
import cn.cncc.caos.uaa.exception.DataAuthException;
import cn.cncc.caos.uaa.model.rule.BaseRuleReq;
import cn.cncc.caos.uaa.model.rule.BaseRuleRes;
import cn.cncc.caos.uaa.model.rule.BaseRuleUpdateReq;
import cn.cncc.caos.uaa.db.pojo.BaseRule;
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
public class BaseRuleService {

  @Autowired
  private BaseRuleMapper baseRuleMapper;

  @Autowired
  private BaseRuleMapperEx baseRuleMapperEx;

  @Resource
  private RedisTemplate<String, BaseRuleRes> redisTemplate;

  @Autowired
  private IdHelper idHelper;

  @Value("${rule.redis.key}")
  private String ruleRedisKey;

  public void addRule(BaseRuleReq baseRuleReq) {
    //校验父目录
    checkParentResource(baseRuleReq.getParentId());
    checkExist(baseRuleReq);
    //入库
    BaseRule baseRule = new BaseRule();
    baseRule.setId(idHelper.generateRuleId());
    BeanUtils.copyProperties(baseRuleReq, baseRule);
    baseRule.setCreateTime(new Date());
    baseRule.setUpdateTime(new Date());
    baseRule.setStatus((byte) 1);
    baseRuleMapper.insert(baseRule);
    log.info("baseRule insert to db success baseRuleTitle:" + baseRule.getTitle());
    //删除redis中的缓存
    redisTemplate.delete(ruleRedisKey);
    log.info("delete redis value");
  }

  private void checkExist(BaseRuleReq baseRuleReq) {
    if (!StringUtils.isEmpty(baseRuleReq.getName())) {
      List<BaseRule> execute = baseRuleMapper.selectByExample()
          .where(BaseRuleDynamicSqlSupport.name, isEqualTo(baseRuleReq.getName()))
          .and(BaseRuleDynamicSqlSupport.status, isEqualTo((byte) 1))
          .build().execute();
      if (!CollectionUtils.isEmpty(execute)) {
        throw new DataAuthException(JwResponseCode.NAME_DUPLICATE.fillArgs("规则标识重复"));
      }
    }
  }

  private void checkParentResource(String parentId) {
    if (parentId == null || parentId.equals(String.valueOf(0))) {
      return;
    }
    List<BaseRule> baseRuleList = baseRuleMapper.selectByExample()
        .where(BaseRuleDynamicSqlSupport.id, isEqualTo(parentId))
        .and(BaseRuleDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
    if (CollectionUtils.isEmpty(baseRuleList))
      throw new DataAuthException(JwResponseCode.RULE_PARENT_ERROR);
  }

  public void updateRule(BaseRuleUpdateReq baseRuleUpdateReq) {
    //1.校验该权限是否存在
    checkRuleExist(baseRuleUpdateReq);
    //2.更新
    BaseRule baseRule = new BaseRule();
    BeanUtils.copyProperties(baseRuleUpdateReq, baseRule);
    baseRule.setUpdateTime(new Date());
    baseRuleMapper.updateByPrimaryKeySelective(baseRule);
    log.info("insert baseRule end baseRuleTitle:" + baseRule.getTitle());
    //删除redis中的缓存
    redisTemplate.delete(ruleRedisKey);
    log.info("delete redis value");
  }

  private void checkRuleExist(BaseRuleUpdateReq baseRuleUpdateReq) {
    BaseRule baseRule = baseRuleMapper.selectByPrimaryKey(baseRuleUpdateReq.getId());
    if (baseRule == null)
      throw new DataAuthException(JwResponseCode.DB_DATA_NOT_EXIST);
  }

  public List<BaseRuleRes> getAllRule(String searchColumn) {
    BaseRuleRes result;
    if (StringUtils.isEmpty(searchColumn)) {
      result = getAllRuleRes();
    } else {
      result = getAllRuleResSearch(searchColumn);
    }
    if (result != null) {
      return result.getChildren();
    }
    return null;
  }

  private BaseRuleRes getAllRuleRes() {
    //从redis中获取
    Object o = redisTemplate.opsForValue().get(ruleRedisKey);
    if (o != null) {
      return (BaseRuleRes) o;
    }
    //查询
    List<BaseRule> baseRulesFromDb = baseRuleMapper.selectByExample().
        where(BaseRuleDynamicSqlSupport.status, isEqualTo((byte) 1))
        .orderBy(BaseRuleDynamicSqlSupport.seq)
        .build().execute();
    if (CollectionUtils.isEmpty(baseRulesFromDb))
      return null;
    //1.组装map
    Map<String, List<BaseRule>> parentIdRulesMap = getBaseRuleParentIdRuleMap(baseRulesFromDb);
    //2.组装tree
    BaseRuleRes result = getBaseRuleResTree(parentIdRulesMap);
    //3.放入redis中
    redisTemplate.opsForValue().set(ruleRedisKey, result);
    return result;
  }

  private BaseRuleRes getAllRuleResSearch(String searchColumn) {
    List<BaseRule> baseRulesFromDb = baseRuleMapper.selectByExample().
        where(BaseRuleDynamicSqlSupport.status, isEqualTo((byte) 1))
        .orderBy(BaseRuleDynamicSqlSupport.seq)
        .build().execute();
    if (CollectionUtils.isEmpty(baseRulesFromDb))
      return null;
    //筛选，并组装idbaseRuleMap
    Map<String, BaseRule> idBaseRuleMap = baseRulesFromDb.stream().collect(Collectors.toMap(BaseRule::getId, Function.identity()));
    Set<BaseRule> baseRuleSetFromDbSearch = new HashSet<>();
    for (BaseRule baseRule : baseRulesFromDb) {
      if (baseRule.getTitle().contains(searchColumn)) {
        //首先把符合搜索条件的加入set
        baseRuleSetFromDbSearch.add(baseRule);
        //获取所有筛选结果的父类，如果有父类，就加入到set中
        checkHasParentRule(baseRule, baseRuleSetFromDbSearch, idBaseRuleMap);
      }
    }
    List<BaseRule> baseRulesFromDbSearch = new ArrayList<>(baseRuleSetFromDbSearch);
    if (CollectionUtils.isEmpty(baseRulesFromDbSearch))
      return null;
    //1.组装map 根据parentid分成listmap<Integer,List<rule>>
    Map<String, List<BaseRule>> baseRuleParentIdMap = getBaseRuleParentIdRuleMap(baseRulesFromDbSearch);
    //2.组装tree
    return getBaseRuleResTree(baseRuleParentIdMap);
  }

  private void checkHasParentRule(BaseRule baseRule, Set<BaseRule> baseRuleSetFromDbSearch, Map<String, BaseRule> idBaseRuleMap) {
    if (baseRule.getParentId().equals(String.valueOf(0)))
      return;
    BaseRule baseRuleParent = idBaseRuleMap.get(baseRule.getParentId());
    if (baseRuleParent != null) {
      baseRuleSetFromDbSearch.add(baseRuleParent);
      checkHasParentRule(baseRuleParent, baseRuleSetFromDbSearch, idBaseRuleMap);
    }
  }

  private BaseRuleRes getBaseRuleResTree(Map<String, List<BaseRule>> baseRuleParentIdMap) {
    BaseRuleRes baseRuleRes = new BaseRuleRes();
    baseRuleRes.setId(String.valueOf(0));
    baseRuleRes.setName("根节点");
    baseRuleRes.setFormula("/");
    baseRuleRes.setSeq(0);
    baseRuleRes.setTitle("根节点");
    //新建map，遍历所有的rule，如果他的parentid在listmap中，name不能作为key
    Map<Integer, BaseRuleRes> result = new HashMap<>();
    setBaseRuleRes(baseRuleRes, baseRuleParentIdMap);
    return baseRuleRes;
  }

  private void setBaseRuleRes(BaseRuleRes baseRuleRes, Map<String, List<BaseRule>> baseRuleParentIdMap) {
    List<BaseRule> baseRules = baseRuleParentIdMap.get(baseRuleRes.getId());
    if (CollectionUtils.isEmpty(baseRules)) {
      return;
    }
    //装换res
    List<BaseRuleRes> baseRuleResList = baseRules.stream().map(baseRule -> {
      BaseRuleRes baseRuleResChild = new BaseRuleRes();
      BeanUtils.copyProperties(baseRule, baseRuleResChild);
      return baseRuleResChild;
    }).collect(Collectors.toList());
    baseRuleRes.setChildren(baseRuleResList);
    for (BaseRuleRes ruleRes : baseRuleResList) {
      setBaseRuleRes(ruleRes, baseRuleParentIdMap);
    }
  }

  private Map<String, List<BaseRule>> getBaseRuleParentIdRuleMap(List<BaseRule> baseRulesFromDb) {
    Map<String, List<BaseRule>> parentIdBaseRuleMap = new HashMap<>();
    //根据parentid分成listmap<Integer,List<rule>>
    for (BaseRule baseRule : baseRulesFromDb) {
      if (parentIdBaseRuleMap.containsKey(baseRule.getParentId())) {
        List<BaseRule> baseRules = parentIdBaseRuleMap.get(baseRule.getParentId());
        baseRules.add(baseRule);
      } else {
        List<BaseRule> baseRuleList = new ArrayList<>();
        baseRuleList.add(baseRule);
        parentIdBaseRuleMap.put(baseRule.getParentId(), baseRuleList);
      }
    }
    return parentIdBaseRuleMap;
  }

  public void deleteRule(String id) {
    Set<String> baseRuleIdsDelete = new HashSet<>();
    baseRuleIdsDelete.add(id);
    //删除该节点下所有rule
    //查询所有的权限
    List<BaseRule> baseRules = baseRuleMapper.selectByExample()
        .where(BaseRuleDynamicSqlSupport.status, isEqualTo((byte) 1))
        .build().execute();
    if (CollectionUtils.isEmpty(baseRules))
      return;
    Map<String, List<BaseRule>> baseRuleParentIdMap = getBaseRuleParentIdRuleMap(baseRules);
    getDeleteRuleList(id, baseRuleIdsDelete, baseRuleParentIdMap);
    //批量更新
    if (CollectionUtils.isEmpty(baseRuleIdsDelete))
      return;
    baseRuleMapperEx.batchUpdateBaseRules(baseRuleIdsDelete, new Date());
    log.info("delete baseRule end deleteIds:" + baseRuleIdsDelete);
    redisTemplate.delete(ruleRedisKey);
    log.info("delete redis value");
  }

  private void getDeleteRuleList(String id, Set<String> baseRuleIdsDelete, Map<String, List<BaseRule>> baseRuleParentIdMap) {
    List<BaseRule> baseRulesChild = baseRuleParentIdMap.get(id);
    if (CollectionUtils.isEmpty(baseRulesChild))
      return;
    for (BaseRule baseRule : baseRulesChild) {
      baseRuleIdsDelete.add(baseRule.getId());
      getDeleteRuleList(baseRule.getId(), baseRuleIdsDelete, baseRuleParentIdMap);
    }
  }

  public Map<String, Map<String, Map<String, String>>> getSysRule() {
    //1.获取ruletree
    List<BaseRuleRes> allRule = getAllRule(null);
    //2.遍历，获取sys，put.(name,new Map) ，getchild,遍历 put，遍历 add
    if (CollectionUtils.isEmpty(allRule))
      return null;
    Map<String, Map<String, Map<String, String>>> sysRuleMap = new HashMap<>();
    for (BaseRuleRes baseRuleRes : allRule) {
      Map<String, Map<String, String>> menuMap = new HashMap<>();
      sysRuleMap.put(baseRuleRes.getName(), menuMap);
      List<BaseRuleRes> menuRuleList = baseRuleRes.getChildren();
      if (CollectionUtils.isEmpty(menuRuleList))
        continue;
      for (BaseRuleRes ruleRes : menuRuleList) {
        Map<String, String> ruleMap = new HashMap<>();
        menuMap.put(ruleRes.getName(), ruleMap);
        List<BaseRuleRes> ruleList = ruleRes.getChildren();
        if (CollectionUtils.isEmpty(ruleList)) {
          continue;
        }
        ruleList.forEach(rule -> {
          ruleMap.put(rule.getName(), rule.getFormula());
        });
      }
    }
    return sysRuleMap;
  }

  public void deleteRuleCache() {
    redisTemplate.delete(ruleRedisKey);
    log.info("delete redis value");
  }
}
