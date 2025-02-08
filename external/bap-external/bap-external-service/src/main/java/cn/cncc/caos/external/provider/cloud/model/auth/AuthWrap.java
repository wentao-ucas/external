package cn.cncc.caos.external.provider.cloud.model.auth;

/**
 * @className: AuthWrap
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/8 13:43
 */
public class AuthWrap {
    private IAuth auth;
    private String url;

    public IAuth getAuth() {
        return auth;
    }

    public void setAuth(IAuth auth) {
        this.auth = auth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
