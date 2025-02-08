package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface BaseDataPermissionInnerControllerService {
    /**
     * 根据用户获取数据权限
     */
    public JwResponseData<Map<String, Map<String, String>>> getDataPermissionByUser(@RequestBody BaseUser baseUser);


}
