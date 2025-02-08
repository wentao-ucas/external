package cn.cncc.caos.external.client;

import cn.cncc.caos.external.client.cloud.api.ICloudAuthService;
import cn.cncc.caos.external.client.cloud.api.ICloudMonitorService;
import cn.cncc.caos.external.client.cloud.api.ICloudTaskCfgService;
import cn.cncc.caos.external.client.cloud.api.ICloudTaskLogService;
import cn.cncc.mojito.rpc.invoker.annotation.MojitoReference;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: ExternalServiceClient
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/12 14:43
 */
@Component
@Slf4j
@Getter
public class ExternalServiceClient {

    @MojitoReference(serviceName = "external-service", schemaId = "inner_api/cloudAuthService")
    private ICloudAuthService cloudAuthService;
    @MojitoReference(serviceName = "external-service", schemaId = "inner_api/cloudTaskCfgService")
    private ICloudTaskCfgService cloudTaskCfgService;

    @MojitoReference(serviceName = "external-service", schemaId = "inner_api/cloudTaskLogService")
    private ICloudTaskLogService cloudTaskLogService;

    @MojitoReference(serviceName = "external-service", schemaId = "inner_api/cloudMonitorService")
    private ICloudMonitorService cloudMonitorService;

}
