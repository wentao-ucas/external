package cn.cncc.caos.common.core.enums;

/**
 * @enumName: RecordStatus
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/8 17:05
 */
public enum RecordStatus {

    NORMAL("00","有效"),
    DELETE("01","失效");
    private String status;
    private String desc;

    RecordStatus(String type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public static RecordStatus indexOf(String  status) {
        for (RecordStatus recordStatus : RecordStatus.values()) {
            if (recordStatus.status == status) return recordStatus;
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
