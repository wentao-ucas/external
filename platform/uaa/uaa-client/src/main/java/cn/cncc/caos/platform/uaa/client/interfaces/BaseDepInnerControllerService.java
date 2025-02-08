package cn.cncc.caos.platform.uaa.client.interfaces;

import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface BaseDepInnerControllerService {
    /**
     * 根据部门id获取部门
     */
    public JwResponseData<BaseDep> getDepById(@RequestParam(value = "depId") Integer depId);

    /**
     * 根据部门名获取部门
     */
    public JwResponseData<BaseDep> getDepByName(@RequestParam("name") String name);

    /* 根据部门ID获取子部门信息 */
    public JwResponseData<List<BaseDep>> getDepSubdivision(@RequestParam("depName") String depName);

    public JwResponseData<BaseDep> addOneDep(@RequestBody BaseDep baseDep);

    public JwResponseData<String> deleteOneDep(@RequestParam(value = "depCode") String depCode);

    public JwResponseData<BaseDep> updateOneDep(@RequestBody BaseDep baseDep);

    public JwResponseData<List<BaseDep>> getDepAll();

    public JwResponseData<BaseDep> getDepByCode(@RequestParam("depCode") String depCode);

    public JwResponseData<Map<Integer, String>> getDepCodeByIds(@RequestBody List<Integer> idList);

    public JwResponseData<Boolean> judgeIsPayDep(@RequestParam("depId") Integer depId);

    public JwResponseData<List<String>> getDepIdByNames(@RequestBody List<String> depNames);

}
