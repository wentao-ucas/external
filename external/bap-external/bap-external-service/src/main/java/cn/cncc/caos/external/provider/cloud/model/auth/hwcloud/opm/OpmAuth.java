package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.opm;

import cn.cncc.caos.external.provider.cloud.model.auth.IAuth;
import lombok.Data;

@Data
public class OpmAuth implements IAuth {
    String grantType;
    String userName;
    String value;

    public OpmAuth() {
    }

    public OpmAuth(String grantType, String userName, String value) {
        this.grantType = grantType;
        this.userName = userName;
        this.value = value;
    }

    @Override
    public String getAuthUrl() {
        return null;
    }
}
