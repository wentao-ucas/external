package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Identity {
    private List<String> methods = Arrays.asList("password");
    private Password password;
}
