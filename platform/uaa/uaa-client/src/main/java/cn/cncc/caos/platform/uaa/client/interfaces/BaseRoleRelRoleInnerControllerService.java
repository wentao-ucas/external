package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BaseRoleRelRoleInnerControllerService {
    public JwResponseData<List<BaseUser>> getUserListByRoleIdKeyAndUsage(@RequestParam(value = "roleIdKey", required = true) Integer roleIdKey,
                                                                       @RequestParam(value = "roleRelUsage", required = true) String roleRelUsage);

}
