package cn.cncc.caos.data.client;

import cn.cncc.caos.data.client.pubparam.api.IPubParamService;
import cn.cncc.mojito.rpc.invoker.annotation.MojitoReference;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: BaseDataClient
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/9 10:29
 */
@Component
@Slf4j
@Getter
public class BaseDataClient {
    @MojitoReference(serviceName = "data-service", schemaId = "inner_api/pubParamService")
    private IPubParamService pubParamService;
}
