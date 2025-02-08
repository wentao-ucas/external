//package cn.cncc.caos.platform.service;
//
//import cn.cncc.caos.common.core.response.jw.JwResponseData;
//import cn.cncc.caos.platform.uaa.client.UaaMojitoClient;
//import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class PlatformService {
//  private UaaMojitoClient uaaMojitoClient;
//
//  @Autowired
//  public PlatformService(UaaMojitoClient uaaMojitoClient) {
//    this.uaaMojitoClient = uaaMojitoClient;
//  }
//
//  public JwResponseData<BaseUser> test1() {
//    return uaaMojitoClient.getBaseUserControllerService().getUserByUserName("yangchunlei");
//  }
//
//  public JwResponseData<DepartmentDTO> test2() {
//    return uaaMojitoClient.getUaaBaseDepService().getDepByName("运维系统部");
//  }
//
//  public JwResponseData<List<DepartmentDTO>> test3() {
//    return uaaMojitoClient.getUaaBaseDepService().getDepAll();
//  }
//}
