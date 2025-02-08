package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.uaa.model.message.BatchUpdateMessageStatusReq;
import cn.cncc.caos.uaa.model.message.UpdateMessageStatusReq;
import cn.cncc.caos.uaa.service.McMessagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description 消息中心-controller.
 * @date 2023/8/18 11:11.
 */
@RestController
@Slf4j
@Tag(name = "消息中心相关接口")
public class McMessagesController {

  @Autowired
  private McMessagesService mcMessagesService;


  @PreAuthorize("hasAuthority('message-center')")
  @Operation(summary = "更新消息状态")
  @RequestMapping(value = {"/open_api/message/center/status/update"}, method = RequestMethod.POST)
  JwResponseData<String> updateMessageStatus(@RequestBody UpdateMessageStatusReq req) {
    try {
      log.info("接口:{},更新消息状态==>处理开始,请求参数:{}", "/open_api/message/center/status/update", JacksonUtil.objToJson(req));
      mcMessagesService.updateMessagesStatus(req);
      log.info("接口:{},更新消息状态==>处理完成", "/open_api/user/comment/delete");
      return JwResponseData.success("消息状态更新成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
    }
  }

  @PreAuthorize("hasAuthority('message-center')")
  @Operation(summary = "消息全部已读")
  @RequestMapping(value = {"/open_api/message/center/status/update/all"}, method = RequestMethod.POST)
  JwResponseData<String> updateMessageStatusAll(@RequestBody BatchUpdateMessageStatusReq req) {
    try {
      log.info("接口:{},消息全部已读==>处理开始,请求参数:{}", "/open_api/message/center/status/update/all", JacksonUtil.objToJson(req));
      mcMessagesService.updateMessagesStatusAll(req);
      log.info("接口:{},消息全部已读==>处理完成", "/open_api/message/center/status/update/all");
      return JwResponseData.success("消息(全部)已读完成");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
    }
  }

  @PreAuthorize("hasAuthority('message-center')")
  @Operation(summary = "获得所有系统树和对应系统通知消息数量")
  @Parameters({
      @Parameter(name = "userId", schema = @Schema(type = "Integer"), description = "用户id", required = true, example = "605"),
      @Parameter(name = "userRealName", schema = @Schema(type = "String"), description = "用户真实姓名", required = true, example = "张三")
  })
  @RequestMapping(value = {"/open_api/message/center/system/messages/count"}, method = RequestMethod.GET)
  JwResponseData<List<Map<String, Object>>> getSystemMessagesCount(@RequestParam(value = "userId") Integer userId,
                                                                   @RequestParam(value = "userRealName") String userRealName) {
    try {
      log.info("接口:{},获得所有系统树和对应系统通知消息数量==>处理开始,请求参数userId:{},userRealName:{}", "/open_api/message/center/system/messages/count", userId, userRealName);
      List<Map<String, Object>> messagesCount = mcMessagesService.getSystemMessagesCount(userId, userRealName);
      log.info("接口:{},获得所有系统树和对应系统通知消息数量==>处理完成", "/open_api/message/center/system/messages/count");
      return JwResponseData.success("获取通知消息概览数量成功", messagesCount);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
    }
  }

  @PreAuthorize("hasAuthority('message-center')")
  @Operation(summary = "获得所选系统树和对应系统通知消息")
  @Parameters({
      @Parameter(name = "userId", schema = @Schema(type = "Integer"), description = "用户id", required = true, example = "605"),
      @Parameter(name = "userRealName", schema = @Schema(type = "String"), description = "用户真实姓名", required = true, example = "张三"),
      @Parameter(name = "systemName", schema = @Schema(type = "String"), description = "所属系统", required = true, example = "1:流程平台 2:值班辅助系统 3:流程小助手"),
      @Parameter(name = "queryType", schema = @Schema(type = "String"), description = "查询类型", required = true, example = "1:近期数据 2:非近期数据"),
      @Parameter(name = "pageNum", schema = @Schema(type = "byte"), description = "查询页", required = true, example = "1"),
      @Parameter(name = "pageSize", schema = @Schema(type = "byte"), description = "每页数据", required = true, example = "10")
  })
  @RequestMapping(value = {"/open_api/message/center/system/messages/list"}, method = RequestMethod.GET)
  JwResponseData<List> getSystemMessagesList(@RequestParam(value = "userId", required = true) Integer userId,
                                             @RequestParam(value = "userRealName", required = true) String userRealName,
                                             @RequestParam(value = "systemName", required = true) String systemName,
                                             @RequestParam(value = "queryType", required = true) String queryType,
                                             @RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
    try {
      log.info("接口:{},获得所选系统树和对应系统通知消息==>处理开始,请求参数userId:{},userRealName:{},systemName:{},queryType:{},pageNum:{},pageSize:{}", "/open_api/message/center/system/messages/list", userId, userRealName, systemName, queryType, pageNum, pageSize);
      List messagesList = mcMessagesService.getSystemMessagesList(userId, userRealName, systemName, queryType, pageNum, pageSize);
      log.info("接口:{},获得所选系统树和对应系统通知消息==>处理结束,查询结果数量:{}", "/open_api/message/center/system/messages/list", messagesList.size());
      return JwResponseData.success("获取通知消息列表成功", messagesList);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.COMPLETE_ERROR.fillArgs(e.getMessage()));
    }
  }

  /**
   * @Description 消息中心数据定时移动到历史表手动触发接口.
   * @Param nowDate 当前时间(移动15天之前的已读数据).
   * @Return void null.
   **/
  @PreAuthorize("hasAuthority('message-center')")
  @RequestMapping(value = {"/inner_api/message/center/schedule/move"}, method = RequestMethod.GET)
  public void taskExecuteMethod(@RequestParam("nowDate") String nowDate) throws Exception {
    log.info("/inner_api/message/center/schedule/move 定时任务手动触发==>开始");
    mcMessagesService.moveMessagesToHistory(nowDate);
    log.info("/inner_api/message/center/schedule/move 定时任务手动触发==>完成");
  }

  @PreAuthorize("hasAuthority('message-center')")
  @Operation(summary = "cncc获得所有系统树和对应系统通知消息数量")
  @Parameters({
      @Parameter(name = "userId", schema = @Schema(type = "Integer"), description = "用户id", required = true, example = "605"),
      @Parameter(name = "userRealName", schema = @Schema(type = "String"), description = "用户真实姓名", required = true, example = "张三"),
      @Parameter(name = "userName", schema = @Schema(type = "String"), description = "账号", required = true, example = "zhangsan"),
      @Parameter(name = "cnccToken", schema = @Schema(type = "String"), description = "支付token", required = true, example = "sadf")
  })
  @RequestMapping(value = {"/open_api/message/center/system/messages/count/cncc"}, method = RequestMethod.GET)
  JwResponseData<Map<String, Object>> getSystemMessagesCount(
      @RequestParam(value = "userId") Integer userId,
      @RequestParam(value = "userRealName") String userRealName,
      @RequestParam(value = "userName") String userName,
      @RequestParam(value = "cnccToken") String cnccToken
  ) {
    Map<String, Object> res = mcMessagesService.getSystemMessagesCountCNCC(userId, userRealName, userName, cnccToken);
    return JwResponseData.success("", res);
  }

}
