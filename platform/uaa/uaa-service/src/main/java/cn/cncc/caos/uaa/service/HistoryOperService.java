package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.BasePageRes;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.HistoryOperMapper;
import cn.cncc.caos.uaa.db.daoex.HistoryOperMapperEx;
import cn.cncc.caos.uaa.db.pojo.HistoryOper;
import cn.cncc.caos.uaa.vo.BaseOperationLogSearchParameter;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.utils.IDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HistoryOperService {
    @Resource
    private HistoryOperMapper historyOperMapper;
    @Resource
    private ServerConfigHelper serverConfigHelper;
    @Resource
    private HistoryOperMapperEx historyOperMapperEx;


    public BasePageRes<HistoryOper> getOperationLogPage(Integer pageNum, Integer pageSize, BaseOperationLogSearchParameter searchParameter, String sortString) {
        PageHelper.startPage(pageNum, pageSize);
        List<HistoryOper> operationLogList = historyOperMapperEx.selectPage(searchParameter, sortString);
        PageInfo<HistoryOper> pageInfo = new PageInfo<>(operationLogList, pageSize);
        return new BasePageRes<>(pageInfo.getTotal(), operationLogList);
    }

    public void insertHistoryUserLogin(BaseUser baseUserData, String operationType) {
        HistoryOper historyOper = new HistoryOper();
        historyOper.setId(IDUtil.getStringNextId("H" + serverConfigHelper.getValue("sync.id.prefix")));
        historyOper.setDepId(baseUserData.getDepId());
        historyOper.setOperTime(new Date());
        historyOper.setUserName(baseUserData.getUserName());
        historyOper.setRealName(baseUserData.getRealName());
        historyOper.setOperType(operationType);
        historyOper.setUserId(baseUserData.getId());
        historyOperMapper.insert(historyOper);
        log.info("insert historyOperation end");
    }
}
