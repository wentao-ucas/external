package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface BaseUserRelRoleInnerControllerService {
    public JwResponseData<Map<String, String>> getUsersByRoles(
            @RequestBody List<String> roleNameList
    );

    public JwResponseData<List<BaseRole>> getRolesByUserId(@RequestParam(value = "userId") Integer userId);

    public JwResponseData<String> addOneRole(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "roleId") Integer roleId);

    public JwResponseData<String> handoverRole(@RequestParam(value = "handoverUserId") Integer handoverUserId,
                                             @RequestParam(value = "candidateUserId") Integer candidateUserId);
}
