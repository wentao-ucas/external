package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface BaseRoleAuthInnerControllerService {
    public JwResponseData<List<BaseRoleAuth>> getRoleAuthByUserId(@RequestParam(value = "userId") int userId);

    public JwResponseData<String> handoverRoleAuth(@RequestParam(value = "handoverUserId") Integer handoverUserId,
                                                 @RequestParam(value = "candidateUserId") Integer candidateUserId);
}
