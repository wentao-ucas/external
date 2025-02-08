package cn.cncc.caos.common.core.enums;

import cn.cncc.caos.common.core.exception.BapParamsException;

/**
 * @enumName: CloudTypeEnum
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/8 9:20
 */
public enum CloudTypeEnum {
    //定义枚举值
    CloudTypeEnum("huawei","华为云");

    private String type;
    private String desc;

    CloudTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CloudTypeEnum getByType(String type) {
        for (CloudTypeEnum cloudTypeEnum : CloudTypeEnum.values()) {
            if (cloudTypeEnum.type.equals(type)) {
                return cloudTypeEnum;
            }
        }
        throw new BapParamsException("暂不支持该类型");
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
