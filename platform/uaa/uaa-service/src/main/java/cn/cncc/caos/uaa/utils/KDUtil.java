package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.utils.BapUtil;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;

import java.util.List;

public class KDUtil {
  /**
   * 通过运行变量判断当前环境
   *
   * @param env local-本机开发测试 dev-开发测试环境 test-测试环境
   * @return
   */
  public static boolean isTest(String env) {
    return "test".equals(env) || env.startsWith("dev") || "local".equals(env);
  }

  public static boolean isProd(String env) {
    return !isTest(env);
  }

  public static boolean isOffice(String env) {
    return "office".equalsIgnoreCase(env);
  }

  public static boolean isLocal(String env) {
    return env.contains("local");
  }

  public static boolean isNpc(String env) {
    return !StringUtils.isEmpty(env) && env.startsWith("npc");
  }

  public static boolean isNpcBeijing(String env) {
    return "npc-bj".equalsIgnoreCase(env);
  }

  public static boolean isNpcShanghai(String env) {
    return "npc-sh".equalsIgnoreCase(env);
  }

  public static StringBuilder buildErrorMessage(List<ObjectError> errors) {
    return BapUtil.buildErrorMessage(errors);
  }

  public static String getRegulationTypeEnum(String regulationType) {
    if ("支付系统".equals(regulationType)) {
      return "cncc";
    } else if ("总行".equals(regulationType)) {
      return "pboc";
    } else if ("其他".equals(regulationType)) {
      return "cfid";
    }
    return "cfid";
  }
}
