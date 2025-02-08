package cn.cncc.caos.log.provider;

import java.util.Map;

public class RPCParam {
    private String apikey;
    private String dsl;
    private String dscode;
    private Map param;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
    }

    public String getDscode() {
        return dscode;
    }

    public void setDscode(String dscode) {
        this.dscode = dscode;
    }

    public Map getParam() {
        return param;
    }

    public void setParam(Map param) {
        this.param = param;
    }



}
