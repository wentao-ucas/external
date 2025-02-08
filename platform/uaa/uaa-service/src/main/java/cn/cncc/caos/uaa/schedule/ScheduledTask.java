package cn.cncc.caos.uaa.schedule;

import cn.cncc.caos.uaa.service.McMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @description data-auth服务定时任务拉起类.
 * @date 2023/8/23 15:05.
 */
@Component
@Slf4j
public class ScheduledTask {

  @Autowired
  private McMessagesService mcMessagesService;

  @Autowired
  @Qualifier("consumerTokenServices")
  private ConsumerTokenServices consumerTokenServices;

  @Autowired
  private RedisTokenStore redisTokenStore;

//  @Scheduled(cron="0 0 0 1 8 ?") // 测试-每天执行一次,每次处理前15天数据
  @Scheduled(cron="0 15 0 * * ?") // 生产-每天执行一次,每次处理前15天数据
  public void moveMessagesToHistory() throws Exception {
    // 生产网和办公网同时跑,互不干预
    log.info("--------moveMessagesToHistory-----start");
    // 处理历史消息
    mcMessagesService.moveMessagesToHistory(null);
    log.info("--------moveMessagesToHistory-----end");
  }

  @Scheduled(cron = "0 0 0 * * *") //每天凌晨0 点执行一次
  public Boolean removeAllToken() {
    log.info("====removeAllToken=====");
    Collection<OAuth2AccessToken> collection = redisTokenStore.findTokensByClientId("open_client");
    for (OAuth2AccessToken oAuth2AccessToken : collection) {
      String token = oAuth2AccessToken.getValue();
      log.info(token);
      consumerTokenServices.revokeToken(token);
    }
    return true;
  }

}
