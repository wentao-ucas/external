package cn.cncc.caos.uaa.exception;


import cn.cncc.caos.common.core.response.jw.JwResponseCode;

/**
 * 全局异常类
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private JwResponseCode cm;
    private Object exMsg=""; //额外附加信息

    public GlobalException(JwResponseCode cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public GlobalException(JwResponseCode cm, Object exMsg) {
        super(cm.toString());
        this.cm = cm;
        this.exMsg = exMsg;
    }

    public JwResponseCode getCm() {
        return cm;
    }

    public Object getExMsg() {
        return exMsg;
    }
}
