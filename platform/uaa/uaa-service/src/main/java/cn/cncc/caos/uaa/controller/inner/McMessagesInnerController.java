package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.McMessagesService;
import cn.cncc.caos.uaa.vo.McMessagesPushReq;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 消息中心-controller.
 * @date 2023/8/18 11:11.
 */
// @RestController
@Slf4j
// @Tag(name = "消息中心相关接口")
@MojitoSchema(schemaId = "inner_api/mcMessagesInnerController")
public class McMessagesInnerController {

  @Autowired
  private McMessagesService mcMessagesService;

  @Operation(summary = "推送消息记录")
  // @RequestMapping(value = {"/inner_api/message/center/push"}, method = RequestMethod.POST)
  @Async("recordPushMessagesAsyncExecutor")
  public JwResponseData<String> pushMessages(@RequestBody List<McMessagesPushReq> reqList) {
    try {
      mcMessagesService.pushMessages(reqList);
      return JwResponseData.success("消息推送成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
    }
  }
}
