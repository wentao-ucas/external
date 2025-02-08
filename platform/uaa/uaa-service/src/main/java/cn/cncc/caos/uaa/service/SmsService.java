package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.KDConstant;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SmsService {

//  @Autowired
//  private UserService userService;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private BaseUserMapperEx baseUserMapperEx;

  @Resource
  private ServerConfigHelper serverConfigHelper;

//  @Value("${code.validTime}")
//  private Integer validTime;

//  @Value("${code.smsIntervalTime}")
//  private Long smsIntervalTime;

//  @Value("${kafka.sms.topic}")
//  public String kafkaSendTopicName;

  //  @Autowired
//  private KafkaTemplate kafkaTemplate;
//  @Autowired
//  private PasswordEncoder passwordEncoder;
  @Value("${sms.type}")
  private String smsType;
//  @Value("${sms.ali.template.captcha}")
//  private String aliCaptchaTemplate;

  public JwResponseData<Object> sendVerifyCodeByPhoneNumber(String phone, String userName) throws JsonProcessingException {
/*
    //判断验证码是否存在，存在的话判断有效时间是否超过4分钟，超过就返回稍后重试
    String key = "yzm_" + phone + "_" + userName;
    Long expire = redisTemplate.getExpire(key);
    if (expire != null && expire > serverConfigHelper.getIntegerValue("code.validTime") - serverConfigHelper.getIntegerValue("code.smsIntervalTime")) {
      return JwResponseData.error(DataAuthResponseCode.CODE_HAVE_TIME);
    }

    String code = RandomStringUtils.randomNumeric(6);

    log.info("手机号： " + phone + ", 的验证码：" + code);

    redisTemplate.opsForValue().set(key, code, serverConfigHelper.getIntegerValue("code.validTime"), TimeUnit.SECONDS);
    SmsApiType smsApiType = SmsApiType.typeOf(smsType);
    if (SmsApiType.ALI.equals(smsApiType)) {

      smsSendHelper.sendAliSms(serverConfigHelper.getValue("sms.ali.template.captcha"), phone, code);
    } else {
      //验证码短信模板
      String content = "[精卫]\n您的验证码为：" + code + "，请不要把验证码泄露给其他人！5分钟内有效。";
      smsSendHelper.sendSms(phone, content);
      log.info("send sms to " + phone + ",\t" + content.replace("\n", " "));
    }
    */
    return JwResponseData.success("验证码发送成功");
  }

  public JwResponseData<Object> sendVerifyCodeByUserId(String userName) throws JsonProcessingException {
    //根据userid查询userphone
    if (StringUtils.isEmpty(userName))
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("用户名不能为空"));
    userName = userName.trim();
    BaseUser userByUserName = baseUserMapperEx.getUserByUserName(userName);
    if (userByUserName == null)
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("验证码发送失败"));
    //解密
    EncryptAndDecryptUtil.decryptBaseUser(userByUserName);
    String phone = userByUserName.getPhone();
    if (StringUtils.isEmpty(phone) || phone.equals(KDConstant.PHONE_DEFAULT))
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("手机号为空,请完善手机号"));
    return sendVerifyCodeByPhoneNumber(phone, userName);
  }
/*
  public JwResponseData<String> getCode(String userName) {
    BaseUser userByUserName = baseUserMapperEx.getUserByUserName(userName);
    if (userByUserName == null)
      return null;
    if (StringUtils.isEmpty(userByUserName.getPhone()))
      return JwResponseData.error(DataAuthResponseCode.PHONE_NOT_EXIST);
    //解密
    EncryptAndDecryptUtil.decryptBaseUser(userByUserName);
    String key = "yzm_" + userByUserName.getPhone() + "_" + userName;
    String code = (String) redisTemplate.opsForValue().get(key);
    return JwResponseData.success(code);
  }
  */
}
