package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BasePermission;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BasePermissionInnerControllerService {
    /**
     * 根据userId获取权限
     */
    public JwResponseData<List<BasePermission>> getPermissionByUserId(@RequestParam("userId") Integer userId);


}
