package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.UserInfoRes;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface BaseUserComplexInnerControllerService {
    public JwResponseData<String> addHistoryLogout(@RequestParam(value = "token") String token,
                                                 @RequestParam(value = "realName") String realName);

    public JwResponseData<Map<String, Object>> getUserDetails(@RequestParam(value = "userName", required = true) String userName);

    public JwResponseData<UserInfoRes> getUserInfoByUserNameFromAuth(
            @RequestParam(value = "userName") String userName
    );


}
