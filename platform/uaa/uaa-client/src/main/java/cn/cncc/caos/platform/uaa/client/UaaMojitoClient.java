package cn.cncc.caos.platform.uaa.client;

import cn.cncc.caos.platform.uaa.client.interfaces.*;
import cn.cncc.mojito.rpc.invoker.annotation.MojitoReference;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class UaaMojitoClient {
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseUserInnerController")
  private BaseUserInnerControllerService baseUserControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseDepInnerController")
  private BaseDepInnerControllerService baseDepInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseRoleInnerController")
  private BaseRoleInnerControllerService baseRoleInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseRoleRelRoleInnerController")
  private BaseRoleRelRoleInnerControllerService baseRoleRelRoleInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/basePermissionInnerController")
  private BasePermissionInnerControllerService basePermissionInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseDataPermissionInnerController")
  private BaseDataPermissionInnerControllerService baseDataPermissionInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseRoleAuthInnerController")
  private BaseRoleAuthInnerControllerService baseRoleAuthInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseUserComplexInnerController")
  private BaseUserComplexInnerControllerService baseUserComplexInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseUserIndividuationConfigInnerController")
  private BaseUserIndividuationConfigInnerControllerService baseUserIndividuationConfigInnerControllerService;
  @MojitoReference(serviceName = "uaa-service", schemaId = "inner_api/baseUserRelRoleInnerController")
  private BaseUserRelRoleInnerControllerService baseUserRelRoleInnerControllerService;
}
