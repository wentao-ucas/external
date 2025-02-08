package cn.cncc.caos.external.client.cloud.enums;

import cn.cncc.caos.common.core.enums.RecordStatus;

/**
 * @enumName: CloudTaskStatusEnum
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 14:01
 */
public enum CloudTaskStatusEnum {
    ON("ST00","启用，任务可以被调度"),
    OFF("ST01","关闭，任务不可以被调度"),
    RUNNING("ST02","运行中"),
    INVALID("ST03","失效");
    private String status;
    private String desc;

    CloudTaskStatusEnum(String type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public static CloudTaskStatusEnum indexOf(String  status) {
        for (CloudTaskStatusEnum recordStatus : CloudTaskStatusEnum.values()) {
            if (recordStatus.status.equals(status)) return recordStatus;
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
