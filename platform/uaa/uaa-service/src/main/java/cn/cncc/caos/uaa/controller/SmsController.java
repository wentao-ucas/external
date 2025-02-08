package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "验证码相关接口")
public class SmsController {

  @Autowired
  private SmsService smsService;


  @RequestMapping(value = "/sms/code_by_user_name", method = RequestMethod.GET)
  @Operation(summary = "根据用户姓名发送验证码")
  public JwResponseData<Object> sendVerifyCodeByUserId(@RequestParam(value = "userName", required = true) String userName) {
    try {
      return smsService.sendVerifyCodeByUserId(userName);
    } catch (JsonProcessingException e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }

  }

//  @RequestMapping(value = "/sms/get_code", method = RequestMethod.GET)
//  @Operation(summary = "获取code")
//  public JwResponseData<String> getCode(@RequestParam(value = "userName")String userName){
//    return smsService.getCode(userName);
//  }

}
