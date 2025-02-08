package cn.cncc.caos.external.client.cloud.api;

import cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskStatusEnum;
import cn.cncc.caos.common.core.response.BasePageRes;


import java.util.List;

/**
 * @className: ICloudTaskCfgService
 * @Description: 云平台状态检查任务管理接口服务
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 11:17
 */
public interface ICloudTaskCfgService {
    /**
     * @Author dengjq
     * @Description 查询指定部门deptNo且状态为status的云平台状态检查任务
     * @version: v1.0.0
     * @Date 14:12 2024/11/12
     * @Param [status：任务状态, deptNo：部门ID]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto>
     **/
    List<CloudTaskCfgDto> getCloudTaskCfgListByStatus(CloudTaskStatusEnum statusEnum, String deptNo);
    /**
     * @Author dengjq
     * @Description 查询指定部门deptNo的所有云平台状态检查任务
     * @version: v1.0.0
     * @Date 14:11 2024/11/12
     * @Param [deptNo]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto>
     **/
    List<CloudTaskCfgDto> getAllCloudTaskCfg(String deptNo);
    /**
     * @Author dengjq
     * @Description 查询云平台状态检查任务详情
     * @version: v1.0.0
     * @Date 14:09 2024/11/12
     * @Param [taskId]
     * @return cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto
     **/
    CloudTaskCfgDto getCloudTaskCfgDetailsById(String taskId);

    /**
     * 分页查询云平台状态检查任务
     *
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param deptNo 部门
     * @return 分页结果
     */
    BasePageRes<CloudTaskCfgDto> getAllCloudTaskCfgPage(Integer pageNum, Integer pageSize, String deptNo);

    /**
     * @Author dengjq
     * @Description 启动云平台检查任务
     * @version: v1.0.0
     * @Date 13:45 2024/11/13
     * @Param [taskLists, deptNo, operator]
     * @return java.util.List<cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto>
     **/
    List<CloudTaskLogDto> synStartCloudTask(List<String> taskLists, String deptNo,String operator);

    /**
     * @Author dengjq
     * @Description 添加云平台状态检查任务
     * @version: v1.0.0
     * @Date 10:35 2024/11/13
     * @Param [cloudTaskCfgDto]
     * @return boolean
     **/
    boolean addCloudTask(CloudTaskCfgDto cloudTaskCfgDto);
}
