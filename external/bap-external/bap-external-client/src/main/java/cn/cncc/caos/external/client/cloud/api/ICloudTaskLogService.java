package cn.cncc.caos.external.client.cloud.api;

import cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskInstStatusEnum;
import cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO;

import java.util.List;

/**
 * @className: ICloudTaskLogService
 * @Description: 云平台状态检查任务执行记录管理接口服务
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 11:17
 */
public interface ICloudTaskLogService {
    /**
     * @Author dengjq
     * @Description 查询指定部门的特定云平台状态检查任务最后一个运行实例信息
     * @version: v1.0.0
     * @Date 14:37 2024/11/12
     * @Param [taskId, deptNo]
     * @return cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto
     **/
    CloudTaskLogVO getLastCloudTaskLogByTaskId(String taskId, String deptNo);
    /**
     * @Author dengjq
     * @Description 查询指定部门的特定云平台状态检查任务所有运行实例信息
     * @version: v1.0.0
     * @Date 14:38 2024/11/12
     * @Param [taskId, deptNo]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto>
     **/
    List<CloudTaskLogVO> getAllCloudTaskLogByTaskId(String taskId, String deptNo);

    /**
     * @Author dengjq
     * @Description 查询某个云平台检查任务详情
     * @version: v1.0.0
     * @Date 14:38 2024/11/12
     * @Param [taskInstId]
     * @return cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto
     **/
    CloudTaskLogVO getCloudTaskLogDetailsById(String taskInstId);

    /**
     * @Author dengjq
     * @Description 查询所有状态为启用的云平台检查任务的最后一次任务运行实例
     * @version: v1.0.0
     * @Date 14:40 2024/11/12
     * @Param []
     * @return java.util.List<cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto>
     **/
    List<CloudTaskLogDto> getAllValidCloudTaskInstList();

    /**
     * @Author dengjq
     * @Description 查询指定部门下所有云平台状态检查任务实例
     * @version: v1.0.0
     * @Date 14:22 2024/11/15
     * @Param [deptNo]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO>
     **/
    List<CloudTaskLogVO> getAllValidCloudTaskInstListByDeptNo(String deptNo);

    /**
     * @Author dengjq
     * @Description 根据状态查询指定部门下所有云平台状态检查任务实例
     * @version: v1.0.0
     * @Date 14:22 2024/11/15
     * @Param [deptNo, instStatus]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO>
     **/
    List<CloudTaskLogVO> getAllValidCloudTaskInstListByDeptNoAStatus(String deptNo, CloudTaskInstStatusEnum instStatus);
}
