package cn.cncc.caos.uaa.schedule;

import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.helper.KDHelper;
import cn.cncc.caos.uaa.service.BaseSyncService;
import cn.cncc.caos.uaa.utils.KDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 拉取门户变更用户信息定时任务
 */
@Component
@Slf4j
//@PropertySource("classpath:/application.yml")
public class IopsSyncScheduleTask {
  private static String CNCC = "cncc";
//  @Value("${system.instance}")
//  private String systemInstance;
  @Resource
  private ServerConfigHelper serverConfigHelper;
  @Resource
  private KDHelper kdHelper;
  @Resource
  private BaseSyncService baseSyncService;

  @Scheduled(cron = "${app.portal.dep.sync.cron}")
  public void depSync() {
    String onOffStr = serverConfigHelper.getValue("app.portal.on-off");
    if ("on".equals(onOffStr)) {
      // 支付生产网进行拉取门户用户信息，支付办公网不拉取
      if (CNCC.equals(serverConfigHelper.getValue("system.instance"))) {
        if (KDUtil.isOffice(kdHelper.getJwEnv()) || KDUtil.isLocal(kdHelper.getJwEnv())) {
          log.info("当前环境:{} ,为支付办公网环境 不拉取门户、部门信息进行同步", kdHelper.getJwEnv());
        } else {
          log.info("当前环境:{} 为支付生产网环境, 拉取门户部门信息进行同步", kdHelper.getJwEnv());
          baseSyncService.scheduleTaskDepSync();
          log.info("当前环境:{} 为支付生产网环境, 拉取门户用户信息进行同步", kdHelper.getJwEnv());
          baseSyncService.scheduleTaskUserSync();
        }
      }
    } else {
      log.info("onOff: {}, not dep sync", "on".equals(onOffStr));
    }
  }
}
