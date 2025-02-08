package cn.cncc.caos.external.provider.cloud.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @className: StartCloudTaskReq
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 16:58
 */
@Getter
@Setter
@Schema(description = "启动云平台状态检查任务参数")
public class StartCloudTaskReq {
    @Schema(description = "操作人", type = "string",defaultValue = "无", example = "dengjq")
    private String operator;
    @Schema(description = "部门", type = "string", defaultValue = "无", example = "ywxtb")
    private String deptNo;
    @Schema(description = "任务ID列表", type = "List",defaultValue = "无", example = "['T00001','T00003']")
    private List<String> taskIds;

    @Override
    public String toString() {
        return "StartCloudTaskReq{" +
                "operator='" + operator + '\'' +
                ", deptNo='" + deptNo + '\'' +
                ", taskId=" + taskIds.toString() +
                '}';
    }
}
