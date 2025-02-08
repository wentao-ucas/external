package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.exception.BapParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
public class SearchListUtil {

  public static void judgeSortIsValid(Class<?> cls, Object sortOrder, Object sortValue) {
    if (ObjectUtils.isEmpty(sortValue) && ObjectUtils.isEmpty(sortOrder)) {
      return;
    } else if (ObjectUtils.isEmpty(sortValue) || ObjectUtils.isEmpty(sortOrder)){
      log.error("judgeSortIsValid error, sortOrder={}, sortValue={}", sortOrder, sortValue);
      throw new BapParamsException("排序参数无效，请检查");
    }
//    if ((sortValue == null || StringUtil.isEmpty(sortValue.toString())) && (sortOrder == null || StringUtil.isEmpty(sortOrder.toString())))
//      return;
//    if (sortOrder == null || sortValue == null) {
//      log.error("judgeSortIsValid error, sortOrder={}, sortValue={}", sortOrder, sortValue);
//      throw new BapParamsException("排序参数无效，请检查");
//    }

    if ("normal".equalsIgnoreCase(sortOrder.toString().trim())) return;

    try {
      if ("expectedTime".equalsIgnoreCase(sortValue.toString().trim()))
        sortValue = "expectedStartTime";
      if (sortValue.toString().contains("_"))
        sortValue = StringUtil.convertToCamelCase(sortValue.toString());
      cls.getDeclaredField(sortValue.toString().trim());
    } catch (NoSuchFieldException e) {
      log.error("judgeSortIsValid error, field is not exist, sortOrder={}, sortValue={}", sortOrder, sortValue);
      throw new BapParamsException("排序参数无效，请检查");
    }

    if (!(sortOrder.toString().trim().equalsIgnoreCase("asc") || sortOrder.toString().trim().equalsIgnoreCase("desc"))) {
      log.error("judgeSortIsValid error, sortOrder is not exist, sortOrder={}, sortValue={}", sortOrder, sortValue);
      throw new BapParamsException("排序参数无效，请检查");
    }
  }
  public static String buildExpectedTimeSortString(String sortOrder, String sortValue){
    String sortString = "";
    if ("normal".equalsIgnoreCase(sortOrder.trim())) {
      sortString = "";
    } else {
      if ("expectedTime".equalsIgnoreCase(sortValue.trim())) {
        sortString = "order by " + StringUtil.convertHumpStringToUnderLineString("expectedStartTime" + " " + sortOrder);
      } else {
        sortString = "order by " + StringUtil.convertHumpStringToUnderLineString(sortValue) + " " + sortOrder;
      }
    }
    return sortString;
  }

  public static String buildActionSortString(Map jsonObject){
    String sortString = "";
    if (jsonObject.containsKey("sort_value")) {
      String sortRawString = String.valueOf(jsonObject.get("sort_value"));
      String[] sortRawStringArray = sortRawString.split("~");
      if (sortRawStringArray[1].equals("asc") || sortRawStringArray[1].equals("desc"))
        sortString = "order by " + StringUtil.convertHumpStringToUnderLineString(sortRawStringArray[0]) + " " + sortRawStringArray[1];
    }
    return sortString;
  }

  /**
   * 用于关联查询，列名前需加表别名排序拼接， doc_v2 表别名为 d.
   * @param prefix
   * @param jsonObject
   * @return
   */
  public static String buildSortStringAddPrefix(String prefix, Map jsonObject){
    String sortString = "";
    if (jsonObject.containsKey("sort_value")) {
      String sortRawString = String.valueOf(jsonObject.get("sort_value"));
      String[] sortRawStringArray = sortRawString.split("~");
      if (sortRawStringArray[1].equals("asc") || sortRawStringArray[1].equals("desc"))
        sortString = String.format("order by %s%s %s", prefix, StringUtil.convertHumpStringToUnderLineString(sortRawStringArray[0]), sortRawStringArray[1]);
    }
    return sortString;
  }

  /**
   * 用于关联查询，列名前需加表别名排序拼接， doc_v2 表别名为 d.
   * @param prefix
   * @return
   */
  public static String buildSortStringAddPrefix(String prefix, String sortOrder, String sortValue){
    String sortString = "";
    if ("normal".equalsIgnoreCase(sortOrder.trim())) {
      sortString = "";
    } else {
      if ("expectedTime".equalsIgnoreCase(sortValue.trim())) {
        String.format("order by %s%s %s");
        sortString = String.format("order by %s%s",prefix, StringUtil.convertHumpStringToUnderLineString("expectedStartTime" + " " + sortOrder));
      } else {
        sortString = String.format("order by %s%s %s", prefix, StringUtil.convertHumpStringToUnderLineString(sortValue) , sortOrder);
      }
    }
    return sortString;
  }
}
