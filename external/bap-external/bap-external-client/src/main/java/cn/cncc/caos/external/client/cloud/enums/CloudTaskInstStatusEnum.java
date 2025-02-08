package cn.cncc.caos.external.client.cloud.enums;

/**
 * @enumName: CloudTaskInstStatusEnum
 * @Description: 云平台状态检查任务实例的状态
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 14:32
 */
public enum CloudTaskInstStatusEnum {
    SUCC("ST00","成功"),
    FAIL("ST01","失败"),
    RUNNING("ST02","运行中");
    private String status;
    private String desc;

    CloudTaskInstStatusEnum(String type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public static CloudTaskInstStatusEnum indexOf(String  status) {
        for (CloudTaskInstStatusEnum recordStatus : CloudTaskInstStatusEnum.values()) {
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
