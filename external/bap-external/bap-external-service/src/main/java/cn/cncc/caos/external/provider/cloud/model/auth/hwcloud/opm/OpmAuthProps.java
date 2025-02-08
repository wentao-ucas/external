package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.opm;

public class OpmAuthProps {
    String url;
    String grantType;
    String userName;
    String value;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OpmAuthProps{" +
                "url='" + url + '\'' +
                ", grantType='" + grantType + '\'' +
                ", userName='" + userName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
