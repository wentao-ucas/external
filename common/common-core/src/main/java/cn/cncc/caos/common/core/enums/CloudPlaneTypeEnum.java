package cn.cncc.caos.common.core.enums;

import cn.cncc.caos.common.core.exception.BapParamsException;

/**
 * @Author dengjq
 * @Description 云平台操作平面类型，例如运维面和运营面
 * @version: v1.0.0
 * @Date 9:24 2024/11/27
 * @Param
 * @return
 **/
public enum CloudPlaneTypeEnum {
    //定义云平台操作平面枚举值
    HWCLOUD_OPS(0, "华为云运营面"),
    HWCLOUD_OPM(1, "华为云运维面");

    private int type;
    private String value;

    CloudPlaneTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
    public static CloudPlaneTypeEnum getNameByIndex(int type) {
        for (CloudPlaneTypeEnum cloudPlaneTypeEnum : CloudPlaneTypeEnum.values()) {
            if (cloudPlaneTypeEnum.type == type) {
                return cloudPlaneTypeEnum;
            }
        }
        throw new BapParamsException("暂不支持该类型");
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
