package cn.cncc.caos.uaa.utils;


import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.model.UserHolder;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.ValidationException;
import java.util.List;

public class ValidationTools {

  /**
   * 判断字符串为空抛出异常
   *
   * @param param
   * @param message
   * @throws ValidationException
   */
  public static void checkForNull(String param, String message) {
    if (StringUtil.isEmpty(param)) {
      throw new BapParamsException(message);
    }
  }

  /**
   * 判断Object为空抛出异常
   *
   * @param param
   * @param message
   * @throws ValidationException
   */
  public static void checkForNull(Object param, String message) {
    if (param == null) {
      throw new BapParamsException(message);
    }
  }

  /**
   * 判断lsit为空或size为0，则抛出异常
   */
  public static void checkForNull(List<Object> param, String message) {
    if (CollectionUtils.isEmpty(param)) {
      throw new BapParamsException(message);
    }
  }

  /**
   * 判断当前登录用户信息，如果无登录信息则抛出异常
   */
  public static void isLoginUserNull() {
    BaseUser user = UserHolder.getUser();
    if (user == null) {
      throw new BapParamsException("用户登录超时");
    } else if (user.getId() == null || user.getId() == 0) {
      throw new BapParamsException("用户登录超时");
    }
  }
}
