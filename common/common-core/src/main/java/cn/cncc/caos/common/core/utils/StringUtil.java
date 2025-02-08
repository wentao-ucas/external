package cn.cncc.caos.common.core.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {
  private static DateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");
  private static int counter = 0;


  public static boolean isDouble(String name) {
    return !isEmpty(name) && name.matches("^([-\\+]?\\d+)(\\.\\d+)?$");
  }

  public static boolean isValidName(String name) {
    //3-50
    return !isEmpty(name) && name.matches("^[a-zA-Z][a-zA-Z0-9_]{2,49}$");
  }

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


  public static boolean isEmpty(String str) {
    return StringUtils.isEmpty(str);
  }

  public static boolean isNotEmpty(String str) {
    return !StringUtils.isEmpty(str);
  }


  public static boolean isInteger(String str) {
    return !isEmpty(str) && str.matches("^([0-9]{1,11})$");
  }

  public static boolean isLong(String str) {
    return !isEmpty(str) && str.matches("^([0-9]{1,20})$");
  }

  public static boolean isValidMobile(String mobile) {
    return !isEmpty(mobile) && mobile.matches("^([1][3-9][0-9]{9})$");
  }

  public static boolean isChinese(String str, int min, int max) {
    String reg = String.format("^([\\u4e00-\\u9fa5]{%d,%d})$", min, max);
    return !isEmpty(str) && str.matches(reg);
  }

  public static boolean hasEmpty(String... strArr) {
    for (String str : strArr) {
      if (isEmpty(str)) {
        return true;
      }
    }
    return false;
  }

  public static String format(String separator, Object... params) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Object obj : params) {
      if (i++ > 0) sb.append(separator);
      sb.append(obj);
    }
    return sb.toString();
  }

  public static String formatLog(String desc, Object... params) {
    StringBuilder sb = new StringBuilder();
    sb.append(desc);
    sb.append(" - [");
    if (params == null) return sb.toString();
    int i = 0;
    for (Object obj : params) {
      if (obj == null) continue;
      if (i++ > 0) sb.append(",");
      sb.append(obj);
    }
    sb.append("]");
    return sb.toString();
  }

  public static LinkedList<Integer> strToIntList(String str) {
    LinkedList<Integer> list = new LinkedList<>();
    String[] arr = str.split(",");
    for (String v : arr) {
      list.add(Integer.parseInt(v));
    }
    return list;
  }

  public static List<String> strToList(String str) {
    return strToList(str, ",");
  }

  public static List<String> strToList(String str, String sep) {
    return Arrays.asList(str.split(sep));
  }

  public static String generateUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

}
