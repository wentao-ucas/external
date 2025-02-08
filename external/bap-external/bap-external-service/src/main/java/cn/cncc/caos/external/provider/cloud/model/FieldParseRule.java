package cn.cncc.caos.external.provider.cloud.model;

import cn.cncc.caos.common.core.utils.StringUtil;

/**
 * @className: FieldParseRule
 * @Description: 云平台状态检查任务的结果字段解析规则定义
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 15:36
 */
public class FieldParseRule {
    private String fieldName;
    private String fieldExpr;
    private String exprType;
    private String expect;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldExpr() {
        return fieldExpr;
    }

    public void setFieldExpr(String fieldExpr) {
        this.fieldExpr = fieldExpr;
    }

    public String getExprType() {
        return exprType;
    }

    public void setExprType(String exprType) {
        this.exprType = exprType;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public boolean isValid(){
        if (StringUtil.isNotEmpty(fieldName) && StringUtil.isNotEmpty(fieldExpr) &&
                StringUtil.isNotEmpty(exprType)){
            return true;
        }
        return false;
    }
}
