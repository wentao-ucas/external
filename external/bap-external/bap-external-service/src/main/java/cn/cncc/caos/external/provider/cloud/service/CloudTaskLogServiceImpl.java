package cn.cncc.caos.external.provider.cloud.service;

import cn.cncc.caos.external.client.cloud.api.ICloudTaskLogService;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskInstStatusEnum;
import cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO;
import cn.cncc.caos.external.provider.cloud.db.dao.CloudTaskLogMapper;
import cn.cncc.caos.external.provider.cloud.db.daoex.CloudTaskLogMapperEx;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: CloudTaskLogService
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 14:45
 */
@Service
@Slf4j
@MojitoSchema(schemaId = "inner_api/cloudTaskLogService")
public class CloudTaskLogServiceImpl implements ICloudTaskLogService {
    @Autowired
    private CloudTaskLogMapperEx cloudTaskLogMapperEx;
    @Autowired
    private CloudTaskLogMapper cloudTaskLogMapper;
    @Override
    public CloudTaskLogVO getLastCloudTaskLogByTaskId(String taskId, String deptNo) {
        CloudTaskLogVO cloudTaskLogVO = cloudTaskLogMapperEx.getLastCloudTaskLogByTaskIdDeptNo(deptNo, taskId);
        return cloudTaskLogVO;
    }

    @Override
    public List<CloudTaskLogVO> getAllCloudTaskLogByTaskId(String taskId, String deptNo) {
        List<CloudTaskLogVO> cloudTaskLogVOList = cloudTaskLogMapperEx.getAllCloudTaskLogByTaskIdDeptNo(deptNo,taskId);
        return cloudTaskLogVOList;
    }

    @Override
    public CloudTaskLogVO getCloudTaskLogDetailsById(String taskInstId) {
        return null;
    }

    @Override
    public List<CloudTaskLogDto> getAllValidCloudTaskInstList() {
        return null;
    }

 

    @Override
    public List<CloudTaskLogVO> getAllValidCloudTaskInstListByDeptNoAStatus(String deptNo, CloudTaskInstStatusEnum instStatus) {
        List<CloudTaskLogVO> cloudTaskLogVOList = cloudTaskLogMapperEx.getCloudTaskLogByDeptNoAndInstStatus(deptNo,instStatus.getStatus());
        return cloudTaskLogVOList;
    }
}
