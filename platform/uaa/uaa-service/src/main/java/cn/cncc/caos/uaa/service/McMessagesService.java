package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BaseUserDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.McMessagesDynamicSqlSupport;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.db.daoex.McMessagesHistoryMapperEx;
import cn.cncc.caos.uaa.db.daoex.McMessagesMapperEx;
import cn.cncc.caos.uaa.enums.McMessagesStatusEnum;
import cn.cncc.caos.uaa.enums.McMessagesSystemEnum;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.helper.RestTemplateHelper;
import cn.cncc.caos.uaa.model.message.BatchUpdateMessageStatusDataSyncVo;
import cn.cncc.caos.uaa.model.message.BatchUpdateMessageStatusReq;
import cn.cncc.caos.uaa.model.message.UpdateMessageStatusReq;
import cn.cncc.caos.uaa.db.pojo.McMessages;
import cn.cncc.caos.uaa.db.pojo.McMessagesHistory;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.utils.DateUtil;
import cn.cncc.caos.uaa.utils.EnvironmentTypeUtil;
import cn.cncc.caos.uaa.utils.HttpUtil;
import cn.cncc.caos.uaa.utils.IDUtil;
import cn.cncc.caos.uaa.vo.McMessagesPushReq;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * @description 消息中心-服务类.
 * @date 2023/8/18 14:34.
 */
@Service
@Slf4j
public class McMessagesService {

  @Resource
  private KDHelper kdHelper;
  @Autowired
  private BaseUserMapperEx baseUserMapperEx;
  @Autowired
  private McMessagesMapperEx mcMessagesMapperEx;
  @Autowired
  private McMessagesHistoryMapperEx mcMessagesHistoryMapperEx;
  @Resource
  private ServerConfigHelper serverConfigHelper;

  @Resource
  private RestTemplateHelper restTemplateHelper;

  public void pushMessages(List<McMessagesPushReq> reqList) {
    List<Integer> userIdList = new ArrayList<>();
    List<String> realNameList = new ArrayList<>();
    for (McMessagesPushReq req : reqList) {
      if (req.getUserId() != null && !StringUtils.isEmpty(req.getUserRealName())) {
        continue;
      }
      if (req.getUserId() != null) {
        userIdList.add(req.getUserId());
      }
      if (!StringUtils.isEmpty(req.getUserRealName())) {
        realNameList.add(req.getUserRealName());
      }
    }
    Map<Integer, BaseUser> userListById = getUserByUserIdList(userIdList);
    Map<String, BaseUser> userListByRealName = getUserByRealNameList(realNameList);
    // 生成消息实体
    for (McMessagesPushReq req : reqList) {
      McMessages mcMessages = buildMsMessages(req, userListById, userListByRealName);
      if (mcMessages == null) {
        continue;
      }
      mcMessagesMapperEx.insert(mcMessages);
    }
  }

  public Map<Integer, BaseUser> getUserByUserIdList(List<Integer> userIdList) {
    Map<Integer, BaseUser> result = new HashMap<>();
    if (CollectionUtils.isEmpty(userIdList)) {
      return result;
    }
    List<BaseUser> list = baseUserMapperEx.selectByExample()
            .where(BaseUserDynamicSqlSupport.id, isIn(userIdList))
            .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
    for (BaseUser baseUser : list) {
      result.put(baseUser.getId(), baseUser);
    }
    return result;
  }

  public Map<String, BaseUser> getUserByRealNameList(List<String> realNameList) {
    Map<String, BaseUser> result = new HashMap<>();
    if (CollectionUtils.isEmpty(realNameList)) {
      return result;
    }
    List<BaseUser> list = baseUserMapperEx.selectByExample()
            .where(BaseUserDynamicSqlSupport.realName, isIn(realNameList))
            .and(BaseUserDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
    for (BaseUser baseUser : list) {
      result.put(baseUser.getRealName(), baseUser);
    }
    return result;
  }

  public void updateMessagesStatus(UpdateMessageStatusReq req) throws Exception {
    // 查询对应消息
    McMessages mcMessage = mcMessagesMapperEx.selectByPrimaryKey(req.getId());
    if (mcMessage == null) {
      log.info("更新消息失败,消息不存在,消息id:{}", req.getId());
      throw new Exception("更新消息失败");
    }
    if (req.getStatus() == mcMessage.getStatus()) {
      log.info("如果前端操作状态和当前消息状态一致,则不进行更新操作,直接返回");
      return;
    }
    mcMessage.setStatus(req.getStatus());
    mcMessage.setUpdateTime(new Date());
    if (req.getStatus() == 1) { // 标为已读
      mcMessage.setReadTime(new Date());
    } else if (req.getStatus() == 2) { // 标为未读
      mcMessage.setReadTime(null);
    }
    log.info("消息更新结果:{}", JacksonUtil.objToJson(mcMessage));
    mcMessagesMapperEx.updateByPrimaryKey(mcMessage);
  }

  public void updateMessagesStatusAll(BatchUpdateMessageStatusReq req) throws Exception {
    McMessagesSystemEnum system = McMessagesSystemEnum.getSystemBySystemName(req.getSystemName());
    if (system == null) {
      log.info("根据所选系统未找到指定枚举,请确认!系统名称systemName:{}", req.getSystemName());
      throw new Exception("无效的系统名");
    }
    // 根据条件更新
    Date updateTime = new Date();
    mcMessagesMapperEx.batchUpdateMessagesBySystem(McMessagesStatusEnum.READ.index, McMessagesStatusEnum.UN_READ.index, req.getUserId(), system.systemId, updateTime);
    // 数据同步
//    Map<String, Object> updateParam = new HashMap<>();
//    updateParam.put("userId", req.getUserId());
//    updateParam.put("system", system.systemId);
//    updateParam.put("updateTime", updateTime);
    BatchUpdateMessageStatusDataSyncVo updateParam = new BatchUpdateMessageStatusDataSyncVo();
    updateParam.setUserId(req.getUserId());
    updateParam.setSystemId(system.systemId);
    updateParam.setUpdateTime(updateTime);
  }

  public List<Map<String, Object>> getSystemMessagesCount(Integer userId, String userRealName) throws Exception {
    List<Map<String, Object>> retMapList = new ArrayList<>();
    List<Map<String, Integer>> countMapList = mcMessagesMapperEx.getSystemMessagesCount(McMessagesStatusEnum.UN_READ.index, userId);

    Map<Byte, Integer> countMap = new HashMap<>(); // 查询到数据库中有未读的数据的统计数量
    for (Map<String, Integer> map : countMapList) {
      countMap.put(map.get("system").byteValue(), map.get("count"));
    }

    for (McMessagesSystemEnum system : McMessagesSystemEnum.values()) {
      Map<String, Object> retMap = new HashMap<>();
      retMap.put("systemName", system.systemName);
      if (countMap.get(system.systemId) == null) {
        retMap.put("count", 0);
      } else {
        retMap.put("count", countMap.get(system.systemId));
      }
      retMapList.add(retMap);
    }
    log.info("用户:{},所有系统未读消息查询结果:{}", userRealName, JacksonUtil.objToJson(retMapList));
    return retMapList;
  }

  public List getSystemMessagesList(Integer userId, String userRealName, String systemName, String queryType, Integer pageNum, Integer pageSize) throws Exception {
    PageHelper.startPage(pageNum, pageSize);
    McMessagesSystemEnum system = McMessagesSystemEnum.getSystemBySystemName(systemName);
    if (system == null) {
      log.info("根据所选系统未找到指定枚举,请确认!系统名称systemName:{}", systemName);
      throw new Exception("无效的系统名");
    }
    byte systemDb = system.systemId;
    if ("1".equals(queryType)) { // 近期数据
      List<McMessages> msgList = mcMessagesMapperEx.selectByExample()
              .where(McMessagesDynamicSqlSupport.userId, isEqualTo(userId))
              .and(McMessagesDynamicSqlSupport.system, isEqualTo(systemDb))
              .and(McMessagesDynamicSqlSupport.isValid, isEqualTo((byte) 1))
              .orderBy(McMessagesDynamicSqlSupport.status.descending(), McMessagesDynamicSqlSupport.createTime.descending()).build().execute();
      log.info("用户:{},查询近期消息中心数据结果:{}", userRealName, JacksonUtil.objToJson(msgList));
      return msgList;
    } else if ("2".equals(queryType)) { // 历史已读数据
      List<McMessagesHistory> msgHistoryList = mcMessagesHistoryMapperEx.selectByExample()
              .where(McMessagesDynamicSqlSupport.userId, isEqualTo(userId))
              .and(McMessagesDynamicSqlSupport.system, isEqualTo(systemDb))
              .and(McMessagesDynamicSqlSupport.isValid, isEqualTo((byte) 1))
              .orderBy(McMessagesDynamicSqlSupport.createTime.descending()).build().execute();
      log.info("用户:{},查询历史消息中心数据结果:{}", userRealName, JacksonUtil.objToJson(msgHistoryList));
      return msgHistoryList;
    } else {
      log.info("无法识别的查询类型【queryType】:{}", queryType);
      throw new Exception("无法识别的查询类型");
    }
  }

  private McMessages buildMsMessages(McMessagesPushReq req, Map<Integer, BaseUser> userListById, Map<String, BaseUser> userListByRealName) {
    // 校验/补全通知人员信息
    if (req.getUserId() == null && StringUtils.isEmpty(req.getUserRealName())) { // 用户id和用户真实姓名均为空,此消息不记录,不通知
      log.error("消息通知人id&真实姓名均为空,跳过当前通知信息记录");
      return null;
    } else if (req.getUserId() == null && !StringUtils.isEmpty(req.getUserRealName())) { // 用户id为空,真实姓名不为空,通过真实姓名查询补全用户id数据
      // 通过真实姓名补全id
//      BaseUser baseUser = baseUserMapperEx.getBaseUserByRealName(req.getUserRealName());
      if (userListByRealName.get(req.getUserRealName()) == null) {
        log.error("通过通知人员用户名查询精卫用户为空,查询用户真实姓名:{}", req.getUserRealName());
        return null;
      }
      req.setUserId(userListByRealName.get(req.getUserRealName()).getId());
    } else if (req.getUserId() != null && StringUtils.isEmpty(req.getUserRealName())) { // 用户id不为空,用户真实姓名为空,用过id查询用户真实姓名补全数据
      // 通过id补全真实姓名
//      BaseUser baseUser = baseUserMapperEx.getUserById(req.getUserId());
      if (userListById.get(req.getUserId()) == null) {
        log.error("通过通知人员用户id查询精卫用户为空,查询用户id:{}", req.getUserId());
        return null;
      }
      req.setUserRealName(userListById.get(req.getUserId()).getRealName());
    }
    McMessages mcMessages = new McMessages();
    mcMessages.setId(IDUtil.getStringNextId(EnvironmentTypeUtil.getIdPre(kdHelper.getJwEnv())));
    mcMessages.setUserId(req.getUserId());
    mcMessages.setUserRealName(req.getUserRealName());
    mcMessages.setSystem(req.getSystem());
    mcMessages.setFunction(req.getFunction());
    mcMessages.setTitle(req.getTitle());
    mcMessages.setContent(req.getContent());
    mcMessages.setIsCanSkip(req.getIsCanSkip());
    mcMessages.setParams(StringUtils.isEmpty(req.getParams()) ? null : req.getParams());
    mcMessages.setStatus(McMessagesStatusEnum.UN_READ.index);
    mcMessages.setReadTime(null);
    mcMessages.setIsValid((byte) 1);
    mcMessages.setCreateTime(new Date());
    mcMessages.setUpdateTime(new Date());
    return mcMessages;
  }



  @Transactional
  public void moveMessagesToHistory(String dateStr) throws Exception {
    // 获取当前日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 获取当前日期和时间
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateStr == null ? new Date() : sdf.parse(dateStr));
    // 将时、分、秒和毫秒设置为零
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Date zeroTime = calendar.getTime();
    Date date = DateUtil.addDays(zeroTime, -15); // 仅保留15天的数据

    List<McMessages> moveList = mcMessagesMapperEx.selectByExample()
            .where(McMessagesDynamicSqlSupport.isValid, isEqualTo((byte) 1))
            .and(McMessagesDynamicSqlSupport.status, isEqualTo((byte) 1))
            .and(McMessagesDynamicSqlSupport.createTime, isLessThan(date))
            .build().execute();

    if (moveList == null || moveList.size() == 0) {
      log.info("不存在15天之前已读数据,数据移动任务结束");
      return;
    }
    // 准备删除数据
    List<String> delList = new ArrayList<>();
    for (McMessages mcMessages : moveList) {
      delList.add(mcMessages.getId());
    }
    // 移动插入数据
    mcMessagesHistoryMapperEx.batchInsert(moveList);
    log.info("历史(15天前)已读消息移动完成,由:【{}】移动至:【{}】", "mc_messages", "mc_messages_history");

    // 删除数据
    mcMessagesMapperEx.batchDelete(delList);
    log.info("历史(15天前)已读消息删除完成,删除表:【{}】", "mc_messages");

  }

  public Map<String, Object> getSystemMessagesCountCNCC(Integer userId, String userRealName, String userName, String cnccToken) {
    Map<String, Object> res = new HashMap<>();
    // 直接请求，返回401的话，获取token，在请求一次
    String ip = serverConfigHelper.getValue("cncc.ip");
    String messageUrl = serverConfigHelper.getValue("cncc.message.url");
    Map<String, Object> params = new HashMap<>();
    params.put("userRealName", userRealName);
    params.put("userId", userId);
    HttpHeaders httpHeaders = HttpUtil.buildTokenHttpHeaders(cnccToken);

    HttpEntity<Object> formEntity = new HttpEntity<>(httpHeaders);

    String loginUrl = serverConfigHelper.getValue("cncc.login.url");
    Map<String, Object> response = restTemplateHelper.requestCNCCApi(loginUrl, ip, messageUrl, formEntity, params, userName);

    Object data = response.get("data");
    if (data != null)
      res.put("data", data);
    res.put("userDetails", response.get("userDetails"));
    return res;
  }
}
