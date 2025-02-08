package cn.cncc.caos.external.provider.cloud.controller;


import cn.cncc.caos.common.core.request.BapRequest;
import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.PartialSuccessResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.external.client.cloud.api.ICloudTaskCfgService;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskLogDto;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskInstStatusEnum;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskStatusEnum;
import cn.cncc.caos.external.provider.cloud.vo.StartCloudTaskReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.cncc.caos.common.core.response.BasePageRes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @className: CloudTaskController
 * @Description: 云平台状态检查任务接口
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 11:14
 */
@RestController()
@Slf4j
@Tag(name = "云平台状态检查接口")
public class CloudTaskController {

    @Autowired
    private ICloudTaskCfgService cloudTaskCfgService;
    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/add", method = RequestMethod.POST)
    @Operation(summary = "添加云平台状态检查任务")
    public BaseResponse<String> addCloudTask(@RequestBody CloudTaskCfgDto cloudTaskCfgDto) {
        log.info("enter CloudTaskController.addCloudTask with param={}",cloudTaskCfgDto.toString());
        boolean bOk = cloudTaskCfgService.addCloudTask(cloudTaskCfgDto);
        if (bOk){
            return new SuccessResponse<>("add new CloudTask success");
        }
        return new FailResponse<>("add new CloudTask failed");
    }

    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/list/by/statusanddept", method = RequestMethod.GET)
    @Operation(summary = "根据任务状态和部门信息查询云平台状态检查任务列表")
    public BaseResponse<List<CloudTaskCfgDto>> getCloudTaskByStatusAndDeptNo(
            @Parameter(name = "taskstatus", in = ParameterIn.QUERY, description = "任务状态", required = true, example = "ST00")
            @RequestParam("taskstatus") String taskstatus,
            @Parameter(name = "deptNo", in = ParameterIn.QUERY, description = "部门ID", required = true, example = "ywxtb")
            @RequestParam("deptNo") String deptNo) {
        log.info("enter CloudTaskController.getCloudTaskByStatusAndDeptNo with taskstatus={},deptNo={}",taskstatus, deptNo);
        List<CloudTaskCfgDto> cloudTaskCfgDtoList = new ArrayList<>();
        CloudTaskStatusEnum cloudTaskStatusEnum = CloudTaskStatusEnum.indexOf(taskstatus);
        if (cloudTaskStatusEnum == null){
            String errInfo = String.format("param taskstatus=%s is invalid",taskstatus);
            log.error(errInfo);
            return new FailResponse<>("errInfo");
        }
        cloudTaskCfgDtoList = cloudTaskCfgService.getCloudTaskCfgListByStatus(cloudTaskStatusEnum, deptNo);
        return new SuccessResponse<>(cloudTaskCfgDtoList);
    }

    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/list/by/dept", method = RequestMethod.GET)
    @Operation(summary = "查询部门下所有云平台状态检查任务列表")
    public BaseResponse<BasePageRes<CloudTaskCfgDto>> getCloudTaskByDeptNo(
            @RequestParam("deptNo") String deptNo,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        log.info("enter CloudTaskController.getCloudTaskByDeptNo with deptNo={}", deptNo);

        // 调用分页查询方法
        BasePageRes<CloudTaskCfgDto> cloudTaskCfgPage = cloudTaskCfgService.getAllCloudTaskCfgPage(pageNum, pageSize, deptNo);

        return new SuccessResponse<>(cloudTaskCfgPage);
    }


//    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/list/by/dept", method = RequestMethod.GET)
//    @Operation(summary = "查询部门下所有云平台状态检查任务列表")
//    public BaseResponse<List<CloudTaskCfgDto>> getCloudTaskByDeptNo(
//            @Parameter(name = "deptNo", in = ParameterIn.QUERY, description = "部门ID", required = true, example = "ywxtb")
//
//            @RequestParam("deptNo") String deptNo) {
//        log.info("enter CloudTaskController.getCloudTaskByDeptNo with deptNo={}", deptNo);
//        List<CloudTaskCfgDto> cloudTaskCfgDtoList = cloudTaskCfgService.getAllCloudTaskCfg(deptNo);
//        return new SuccessResponse<>(cloudTaskCfgDtoList);
//    }



    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/by/id", method = RequestMethod.GET)
    @Operation(summary = "查询云平台状态检查任务详情")
    public BaseResponse<CloudTaskCfgDto> getCloudTaskByTaskId(
            @Parameter(name = "taskId", in = ParameterIn.QUERY, description = "任务ID", required = true, example = "T0001")
            @RequestParam("taskId") String taskId) {
        log.info("enter CloudTaskController.getCloudTaskByTaskId with taskId={}", taskId);
        CloudTaskCfgDto cloudTaskCfgDto = cloudTaskCfgService.getCloudTaskCfgDetailsById(taskId);
        return new SuccessResponse<>(cloudTaskCfgDto);
    }

    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/task/start/sync", method = RequestMethod.POST)
    @Operation(summary = "同步启动云平台状态检查任务")
    public BaseResponse<List<CloudTaskLogDto>> synStartCloudTaskByTaskIds(@RequestBody BapRequest<StartCloudTaskReq> req) {
        log.info("enter CloudTaskController.synStartCloudTaskByTaskIds with taskIds={}", req.toString());
        List<CloudTaskLogDto> cloudTaskLogDtoList = cloudTaskCfgService.synStartCloudTask(req.getData().getTaskIds(),req.getData().getOperator(),req.getData().getDeptNo());
        if (cloudTaskLogDtoList.size() < req.getData().getTaskIds().size()){
            Set<String> taskIdSets = new HashSet<>(req.getData().getTaskIds());
            List<String> faildTaskIds = new ArrayList<>();
            for(CloudTaskLogDto cloudTaskLogDto: cloudTaskLogDtoList){
                if (taskIdSets.contains(cloudTaskLogDto.getTaskid()) && cloudTaskLogDto.getTaskinststatus().equals(CloudTaskInstStatusEnum.SUCC.getStatus())){
                    continue;
                }
                faildTaskIds.add(cloudTaskLogDto.getTaskid());
            }
            log.warn("start CloudTask finished,but partial task faild,taskIds={}",faildTaskIds.toString());
            return new PartialSuccessResponse<>(cloudTaskLogDtoList);
        }
        return new SuccessResponse<>(cloudTaskLogDtoList);
    }

}
