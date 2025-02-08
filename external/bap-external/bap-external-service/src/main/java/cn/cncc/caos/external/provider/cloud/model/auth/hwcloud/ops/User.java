package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops;

import lombok.Data;

@Data
public class User {
    private String name;
    private String password;
    private Domain domain;
}
