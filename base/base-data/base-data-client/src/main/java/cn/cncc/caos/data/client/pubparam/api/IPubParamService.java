package cn.cncc.caos.data.client.pubparam.api;

import cn.cncc.caos.data.client.pubparam.dto.PubParamDto;
import cn.cncc.caos.common.core.enums.PubParamTypeEnum;

import java.util.List;

/**
 * @className: PubParamControllerService
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/9 10:24
 */
public interface IPubParamService {
    /**
     * @Author dengjq
     * @Description 根据类型获取公共参数，例如获取华为云认证参数列表
     * @version: v1.0.0
     * @Date 13:18 2024/12/13
     * @Param [paramType]
     * @return java.util.List<cn.cncc.caos.data.client.pubparam.dto.PubParamDto>
     **/
    public List<PubParamDto> getParamlistByType(String paramType);
    /**
     * @Author dengjq
     * @Description 根据参数ID获取公共参数详情
     * @version: v1.0.0
     * @Date 10:28 2024/11/9
     * @Param [paramId]
     * @return cn.cncc.caos.common.core.response.BaseResponse<cn.cncc.caos.bap.data.client.pubparam.dto.PubParamDto>
     **/
    public PubParamDto getParamDetailById(String paramId);
}
