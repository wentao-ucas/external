package cn.cncc.caos.external.provider.cloud.model;

import java.rmi.activation.UnknownObjectException;

/**
 * @className: FieldParseResult
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 15:59
 */
public class FieldParseResult {
    private String fieldName;
    private String fieldValue;
    private String expect;

    private boolean bSuccess;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public boolean isbSuccess() {
        return bSuccess;
    }

    public void setbSuccess(boolean bSuccess) {
        this.bSuccess = bSuccess;
    }

    @Override
    public String toString() {
        if (bSuccess){
            return "{" + fieldName +
                    "='" + fieldValue + '\'' +
                    ", expect='" + expect + '\'' +
                    '}';
        }else{
            return "{" + fieldName +
                    "='Unknown VALUE'" +
                    ", expect='" + expect + '\'' +
                    '}';
        }

    }
}
