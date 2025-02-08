package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringUtil {
  private static Pattern humpPattern = Pattern.compile("[A-Z]");

  public static String convertHumpStringToUnderLineString(String str) {
    Matcher matcher = humpPattern.matcher(str);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String convertToCamelCase(String input) {
    if (StringUtil.isEmpty(input) || !input.contains("_"))
      return input;
    StringBuilder camelCaseStr = new StringBuilder();
    boolean nextUpperCase = false;

    for (char c : input.toCharArray()) {
      if (c == '_' || c == ' ' || c == '-') {
        nextUpperCase = true;
      } else {
        if (nextUpperCase) {
          camelCaseStr.append(Character.toUpperCase(c));
          nextUpperCase = false;
        } else {
          camelCaseStr.append(Character.toLowerCase(c));
        }
      }
    }

    return camelCaseStr.toString();
  }

  private static DateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");

  private static int counter = 0;

  public static synchronized String getNextSequence() {
    StringBuilder sb = new StringBuilder();
    sb.append(df.format(new Date()));
    if (counter > 999) {
      counter = 0;
    } else {
      counter++;
    }
    sb.append(String.format("%03d", counter));
    return sb.toString();
  }

  public static boolean isValidMobile(String mobile) {
    return !isEmpty(mobile) && mobile.matches("^([1][3-9][0-9]{9})$");
  }

  public static boolean isEmpty(String str) {
    return StringUtils.isEmpty(str);
  }

  public static String getStrValue(Object value) {
    if (value == null) return "";
    if (value instanceof Date) {
      return TimeUtil.getDateTimeNormalFormatString((Date) value);
    } else {
      return value.toString();
    }
  }

  public static boolean isEqual(String... values) {
    String orgStr = values[0];
    for (int i = 1; i < values.length; i++) {
      if (orgStr == null) {
        if (values[i] != null) return false;
      } else if (!orgStr.equals(values[i])) {
        return false;
      }
    }
    return true;
  }

  public static Map<String, String> fromQueryString(String queryStr){
    if(isEmpty(queryStr)){
      return Collections.emptyMap();
    }
    String[] params = queryStr.split("&");
    Map<String, String> _params = new HashMap<>();
    for (String param : params) {
      String[] keyValue = param.split("=");
      _params.put(keyValue[0], keyValue[1]);
    }
    return _params;
  }

  public static boolean isNotEqual(String... values) {
    return !isEqual(values);
  }

  public static void checkEmailValid(String userName, String email) {
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches()) {
      log.error("email is invalid userName:" + userName);
      throw new BapParamsException("请输入正确格式的邮箱");
    }
  }

  public static void checkPhoneValid(String userName, String phone) {
    Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");
    Matcher matcher = pattern.matcher(phone);
    if (!matcher.matches()) {
      log.error("phone is invalid userName:" + userName);
      throw new BapParamsException("请输入正确格式的手机号");
    }
  }

  public static void checkPasswordValid(String userName, String password) {
    Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,30}");
    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) {
      log.error("password is invalid userName:" + userName);
      throw new BapParamsException("密码中必须包含大小写字母、数字、特殊字符，8位以上");
    }
  }

}
