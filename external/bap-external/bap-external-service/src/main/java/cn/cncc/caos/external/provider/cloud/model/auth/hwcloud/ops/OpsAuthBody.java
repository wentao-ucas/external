package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops;

import lombok.Data;

@Data

public class OpsAuthBody {
    private Identity identity;
    private Scope scope;
}
