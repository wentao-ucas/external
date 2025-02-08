package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.opm;

public class OpmAuthResp {
    private String accessSession;
    private String roaRand;
    private int expires;
    private String additionalInfo;

    public OpmAuthResp() {

    }

    public OpmAuthResp(String accessSession, String roaRand, int expires, String additionalInfo) {
        this.accessSession = accessSession;
        this.roaRand = roaRand;
        this.expires = expires;
        this.additionalInfo = additionalInfo;
    }

    public String getAccessSession() {
        return accessSession;
    }

    public void setAccessSession(String accessSession) {
        this.accessSession = accessSession;
    }

    public String getRoaRand() {
        return roaRand;
    }

    public void setRoaRand(String roaRand) {
        this.roaRand = roaRand;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

