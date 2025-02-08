package cn.cncc.caos.common.core.enums;

import cn.cncc.caos.common.core.exception.BapParamsException;

/**
 * @enumName: CloudTypeEnum
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/8 9:20
 */
public enum PubParamTypeEnum {
    COMMON_PARAM("00","普通参数"),
    HWCLOUD_AUTH("01","华为云认证参数");

    private String type;
    private String desc;

    PubParamTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static PubParamTypeEnum getByType(String type) {
        for (PubParamTypeEnum pubParamTypeEnum : PubParamTypeEnum.values()) {
            if (pubParamTypeEnum.type.equals(type)) {
                return pubParamTypeEnum;
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
