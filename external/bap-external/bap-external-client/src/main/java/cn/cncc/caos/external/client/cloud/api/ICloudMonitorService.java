package cn.cncc.caos.external.client.cloud.api;


import cn.cncc.caos.external.client.cloud.vo.SeriesDataParamVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wb_zyang
 * @Description 云平台监控服务接口
 * @date 2024/10/28
 * @since v1.0.0
 */
public interface ICloudMonitorService {

    /**
     * 根据属性条件查询对象的实例信息，支持一个属性， 精确查询
     *
     * @param authId       环境ID
     * @param resourceType 资源类型
     * @param conditionMap 条件信息
     * @return 实例详细信息
     */
    List<LinkedHashMap> getResourceInstance(String authId, String resourceType, Map<String, Object> conditionMap);

    /**
     * 根据属性条件查询对象的实例信息，支持一个属性， 精确查询
     *
     * @param authId       环境ID
     * @param resourceType 资源类型
     * @param projectId    项目ID
     * @param conditionMap 对象查询条件
     * @return 指标列表
     */
    List<String> getTimeSeries(String authId, String resourceType, String projectId, Map<String, Object> conditionMap);

    /**
     * 查询对象实例的所有指标值
     *
     * @param authId          环境ID
     * @param resourceType    资源类型
     * @param projectId       项目ID
     * @param metricList      指标列表
     * @param objectAttribute 查询对象属性
     * @param paramVO         参数VO
     * @return 指标列表
     */
    List<LinkedHashMap> getTimeSeriesData(String authId, String resourceType, String projectId, List<String> metricList, Map<String, Object> objectAttribute, SeriesDataParamVO paramVO);

}
