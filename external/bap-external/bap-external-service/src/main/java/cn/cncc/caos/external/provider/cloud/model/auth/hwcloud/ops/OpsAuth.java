package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops;

import cn.cncc.caos.external.provider.cloud.model.auth.IAuth;
import lombok.Data;

@Data
public class OpsAuth implements IAuth {
    private OpsAuthBody auth;

    @Override
    public String getAuthUrl() {
        return null;
    }
}

