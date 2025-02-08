package cn.cncc.caos.uaa.exception;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;

public class DataAuthException extends GlobalException {
    public DataAuthException(JwResponseCode cm){
        super(cm);
    }
}
