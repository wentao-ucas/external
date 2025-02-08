package cn.cncc.caos.external.provider.cloud.enums;

import cn.cncc.caos.common.core.enums.CloudPlaneTypeEnum;
import cn.cncc.caos.common.core.exception.BapParamsException;

/**
 * @enumName: ResultParseTypeEnum
 * @Description: 云平台状态检查任务结果解析类型枚举
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/26 16:05
 */
public enum ResultParseTypeEnum {
    //定义枚举值
    EXTRACT_VALUE(0, "直接提取值"),
    EXTRACT_COUNT(1, "统计数据个数"),
    EXTRACT_SUM(2, "统计数据数量总和"),
    EXTRACT_EXPR(3, "正则表达式提取");

    //定义成员变量
    /**
     * 数据提取方式
     */
    private int type;
    /**
     * 提取方式描述
     */
    private String value;

    ResultParseTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
    public static ResultParseTypeEnum getNameByIndex(int type) {
        for (ResultParseTypeEnum cloudPlaneTypeEnum : ResultParseTypeEnum.values()) {
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
