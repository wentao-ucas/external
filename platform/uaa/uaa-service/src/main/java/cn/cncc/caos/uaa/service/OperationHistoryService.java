package cn.cncc.caos.uaa.service;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.HistoryOperMapper;
import cn.cncc.caos.uaa.db.pojo.HistoryOper;
import cn.cncc.caos.uaa.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class OperationHistoryService {

//  @Value("${sync.id.prefix}")
//  private String idPrefix;

  @Resource
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private HistoryOperMapper historyOperMapper;

  public void insertHistoryOper(BaseUser user, String operType, String operateContent) {
    HistoryOper historyOper = new HistoryOper();
    historyOper.setId(IDUtil.getStringNextId("H" + serverConfigHelper.getValue("sync.id.prefix")));
    historyOper.setOperTime(new Date());
    historyOper.setUserName(user.getUserName());
    historyOper.setUserId(user.getId());
    historyOper.setOperType(operType);
    historyOper.setRealName(user.getRealName());
    historyOper.setUserId(user.getId());
    historyOper.setOperData(operateContent);
    historyOperMapper.insertSelective(historyOper);
    log.info("insert historyOper end");
  }


}
