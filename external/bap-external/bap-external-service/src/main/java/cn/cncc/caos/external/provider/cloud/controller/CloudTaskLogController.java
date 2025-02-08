package cn.cncc.caos.external.provider.cloud.controller;

import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.SuccessResponse;
import cn.cncc.caos.external.client.cloud.api.ICloudTaskLogService;
import cn.cncc.caos.external.client.cloud.dto.CloudTaskCfgDto;
import cn.cncc.caos.external.client.cloud.enums.CloudTaskInstStatusEnum;
import cn.cncc.caos.external.client.cloud.vo.CloudTaskLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: CloudTaskLogController
 * @Description: 云平台状态检查任务实例管理接口
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/15 14:26
 */
@RestController()
@Slf4j
@Tag(name = "云平台状态检查任务管理接口")
public class CloudTaskLogController {
    @Autowired
    private ICloudTaskLogService cloudTaskLogService;
    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/tasklog/list/by/dept", method = RequestMethod.GET)
    @Operation(summary = "查询部门下所有云平台状态检查任务实例列表")
    public BaseResponse<List<CloudTaskLogVO>> getCloudTaskLogByDeptNo(
            @Parameter(name = "deptNo", in = ParameterIn.QUERY, description = "部门ID", required = true, example = "ywxtb")
            @RequestParam("deptNo") String deptNo) {
        log.info("enter CloudTaskController.getCloudTaskLogByDeptNo with deptNo={}", deptNo);
        List<CloudTaskLogVO> cloudTaskLogVOList = cloudTaskLogService.getAllValidCloudTaskInstListByDeptNo(deptNo);
        return new SuccessResponse<>(cloudTaskLogVOList);
    }

    @RequestMapping(value = "/inner_api/v1/caos/bap/external/cloud/tasklog/list/by/inststatus", method = RequestMethod.GET)
    @Operation(summary = "根据状态查询部门下所有云平台状态检查任务实例列表")
    public BaseResponse<List<CloudTaskLogVO>> getCloudTaskLogByDeptNoAInstStatus(
            @Parameter(name = "deptNo", in = ParameterIn.QUERY, description = "部门ID", required = true, example = "ywxtb")
            @RequestParam("deptNo") String deptNo,
            @Parameter(name = "instStatus", in = ParameterIn.QUERY, description = "任务实例状态", required = true, example = "ST00")
            @RequestParam("instStatus")String instStatus) {
        log.info("enter CloudTaskController.getCloudTaskLogByDeptNoAInstStatus with deptNo={}", deptNo);
        CloudTaskInstStatusEnum instStatusEnum = CloudTaskInstStatusEnum.indexOf(instStatus);
        if (instStatusEnum == null){
            String errInfo = String.format("param instStatus=%s is invalid",instStatus);
            log.error(errInfo);
            return new FailResponse<>(errInfo);
        }
        List<CloudTaskLogVO> cloudTaskLogVOList = cloudTaskLogService.getAllValidCloudTaskInstListByDeptNoAStatus(deptNo, CloudTaskInstStatusEnum.indexOf(instStatus));
        return new SuccessResponse<>(cloudTaskLogVOList);
    }
}
