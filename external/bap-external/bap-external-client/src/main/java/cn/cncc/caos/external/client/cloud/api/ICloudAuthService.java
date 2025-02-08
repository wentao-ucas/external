package cn.cncc.caos.external.client.cloud.api;


import cn.cncc.caos.external.client.cloud.dto.TokenCacheItem;
import cn.cncc.caos.external.client.cloud.vo.CloudAuthParamVO;

import java.util.List;

/**
 * @className: ICloudAuthService
 * @Description: 云平台认证授权接口
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/8 14:48
 */
public interface ICloudAuthService {
    /**
     * @Author dengjq
     * @Description 获取云平台token
     * @version: v1.0.0
     * @Date 12:40 2024/11/11
     * @Param [authId]: 示例：huawei_beijing_ops_0,说明：huawei:华为云，beijing:站点，ops: 运营面
     * @return cn.cncc.caos.bap.external.client.cloud.dto.TokenCacheItem
     **/
    public TokenCacheItem getToken(String authId);

    /**
     * @Author dengjq
     * @Description 使用authId验证是否已经授权，如未授权则查询云平台认证参数然后调用云平台接口获取认证授权，之后可以通过getToken获取到token值
     * @version: v1.0.0
     * @Date 12:38 2024/11/11
     * @Param [authId]，示例：huawei_beijing_opm_0,说明：huawei:华为云，beijing:站点，opm: 运维面
     * @return boolean: true 已认证，false  认证失败
     **/
    public boolean isValid(String authId);

    /**
     * @Author dengjq
     * @Description 获取平台配置云平台认证参数列表
     * @version: v1.0.0
     * @Date 16:10 2024/11/13
     * @Param []
     * @return java.util.List<cn.cncc.caos.external.client.cloud.vo.CloudAuthParamVO>
     **/
    public List<CloudAuthParamVO> getCloudAuthList();
}
