package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface BaseRoleInnerControllerService {
    public JwResponseData<List<BaseRole>> getRoleByUserId(@RequestParam(value = "userId", required = true) int userId);

    public JwResponseData<List<BaseRole>> getOperationDepRole();

    public JwResponseData<List<Map<String, Object>>> getOperationDepRoleRelUser();

    public JwResponseData<List<BaseRole>> getAllRole();

    public JwResponseData<List<String>> getRoleListByDepId(@RequestParam(value = "depId", required = true) String depId);


    public JwResponseData<List<BaseRole>> getRolesBySysId (@RequestParam(value = "sysId", required = true) Integer sysId);

//    @RequestMapping(value = "/inner_api/base/role/operation/dep/role/by/user", method = RequestMethod.GET)
    public JwResponseData<List<BaseRole>> getOperationDepRoleByUserId(@RequestParam(value = "userId") Integer userId);
}
