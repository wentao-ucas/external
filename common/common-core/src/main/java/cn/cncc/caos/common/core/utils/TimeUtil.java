package cn.cncc.caos.common.core.utils;


import cn.cncc.caos.common.core.exception.BapParamsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class TimeUtil {

  public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public final static String DAY_FORMAT = "yyyy-MM-dd";
  private static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
  public static String timePatternS = "2[0-3]:[0-5][0-9]:[0-5][0-9]";
  public static String timePatternS2 = "[0-1][0-9]:[0-5][0-9]:[0-5][0-9]";
  public static String timePatternM = "2[0-3]:[0-5][0-9]";
  public static String timePatternM2 = "[0-1][0-9]:[0-5][0-9]";
  private static String[] dayOfWeekMondayFirst = {"周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};

  public static Date getCurrentTime() {
    return new Date();
  }

  public static String getDateWithoutFormat(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    return formatter.format(date);
  }

  public static String getDayFormat(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    return formatter.format(date);
  }

  public static String getDateTimeWithoutFormat(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    return formatter.format(date);
  }

  public static String getDateTimeNormalFormatStringWithUnderLine() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    return formatter.format(date);
  }

  public static String getDateNormalFormatString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(date);
  }

  public static Date getDateNormalFormatString(String dateStr) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.parse(dateStr);
  }

  public static String getDateTimeNormalFormatString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);
  }

  public static Date getDateTimeNormalFormatString(String date) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.parse(date);
  }

  public static Date getDateTimeMinuteFormatString(String date) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return formatter.parse(date);
  }

  public static String getDateTimeNormalFormatStringWithMill(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    return formatter.format(date);
  }

  public static String getTimeFormatString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    return formatter.format(date);
  }

  public static String getCurrentTimeNormalFormatString() {
    Date date = new Date(System.currentTimeMillis());
    return getDateTimeNormalFormatString(date);
  }

  public static String getCurrentTimeNormalFormatStringWithMill() {
    Date date = new Date(System.currentTimeMillis());
    return getDateTimeNormalFormatStringWithMill(date);
  }
  //***************************时间交叉判断*******************
  //相交
//    private static List<Integer> getInterleavedStartAndEndDayFuctionIntersect(int start1,int end1,int start2,int end2){
//        List<Integer> result = new ArrayList<>();
//        if(start1<=start2){
//            result.add(start2);
//            result.add(end1);
//        }else{
//            result.add(start1);
//            result.add(end2);
//        }
//        return result;
//    }
//
//    //包含
//    private static List<Integer> getInterleavedStartAndEndDayFuctionContain(int start1,int end1,int start2,int end2){
//        List<Integer> result = new ArrayList<>();
//        if(start1<=start2){
//            result.add(start2);
//            result.add(end2);
//        }else{
//            result.add(start1);
//            result.add(end1);
//        }
//        return result;
//    }
//
//    //判断两组数的关系
//    public static List<Integer> getInterleavedStartAndEndDay(int start1, int end1, int start2, int end2){
//        if(start1>end2||start2>end1){
//            return null;
//        }else if(start1<=start2&&end1>=end2){
//            return getInterleavedStartAndEndDayFuctionContain(start1, end1, start2, end2);
//        }else{
//            return getInterleavedStartAndEndDayFuctionIntersect(start1, end1, start2, end2);
//        }
//    }

  public static String getCurrentDayString() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
  }
  //***************************时间交叉判断*******************

  public static String getCurrentTimeString() {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
  }

  public static String getTimeStringFromDateInput(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    return formatter.format(date);
  }

  public static String getFrequencyDayOfWeek(String dayOfWeekInput) {
    StringBuilder returnString = new StringBuilder();
    String dayOfWeek = dayOfWeekInput.replaceAll(" ", "");
    if (dayOfWeek.equals(""))
      return returnString.toString();
    String[] dayOfWeekArray = dayOfWeek.split(",");
    for (String tmp : dayOfWeekArray) {
      if (returnString.toString().equals("")) {
        if (StringUtils.isNotEmpty(tmp))
          returnString.append(dayOfWeekMondayFirst[Integer.parseInt(tmp)]);
//                returnString += Integer.parseInt(tmp);
      } else {
        returnString.append(",").append(dayOfWeekMondayFirst[Integer.parseInt(tmp)]);
//                returnString = returnString + "," + Integer.parseInt(tmp);
      }

    }
    return returnString.toString();
  }

  public static long calcTimeSpanOfFormatHHmm(String time1, String time2) {
    DateFormat df = new SimpleDateFormat("HH:mm");
    long minutes = 0L;
    try {
      Date d1 = df.parse(time1);
      Date d2 = df.parse(time2);
      long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
      minutes = diff / (1000 * 60);
//            log.info("calcTimeSpanOfFormatHHmm" + time1 +"~~"+ time2 + "---"+ minutes);
    } catch (ParseException e) {
      log.info("抱歉，时间日期解析出错。");
    }
    return minutes;
  }

  public static String getWeek(Calendar calendar) {
    int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    if (week_index < 0) {
      week_index = 0;
    }
    return WEEKS[week_index];
  }

  public static List<String> getDateListBetweenTwoDates(String start, String end) {
    List<String> result = new ArrayList<String>();
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date start_date = sdf.parse(start);
      Date end_date = sdf.parse(end);
      Calendar tempStart = Calendar.getInstance();
      tempStart.setTime(start_date);
      Calendar tempEnd = Calendar.getInstance();
      tempEnd.setTime(end_date);
      while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
        result.add(sdf.format(tempStart.getTime()));
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
      }
    } catch (ParseException e) {
      log.error("", e);
    }
//        Collections.reverse(result);
    return result;

  }

  /**
   * 根据日期获取 星期 （2019-05-06 ——> 星期一）
   */
  public static String getDayOfWeekFromDate(String datetime) {

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    Calendar cal = Calendar.getInstance();
    Date date;
    try {
      date = f.parse(datetime);
      cal.setTime(date);
    } catch (ParseException e) {
      log.error("", e);
    }
    //一周的第几天
    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
    if (w < 0)
      w = 0;
    return weekDays[w];
  }

  public static String getNextDay(String today) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date tempD = dateFormat.parse(today);//获取当前日期
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(tempD);//获取当前日期
    calendar.set(Calendar.HOUR_OF_DAY, +24);
    Date nextDate = calendar.getTime();
    String nextDay = dateFormat.format(nextDate).toString();
    return nextDay;
  }

  public static String getLastDay(String today) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date tempD = dateFormat.parse(today);//获取当前日期
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(tempD);//获取当前日期
    calendar.set(Calendar.HOUR_OF_DAY, -24);
    Date lastDate = calendar.getTime();
    return dateFormat.format(lastDate);
  }

  public static String getLastDayOfThisMonth(String yearMonth) {
    int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
    int month = Integer.parseInt(yearMonth.split("-")[1]); //月
    Calendar cal = Calendar.getInstance();
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    // cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.MONTH, month); //设置当前月的上一个月
    // 获取某月最大天数
    //int lastDay = cal.getActualMaximum(Calendar.DATE);
    int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
    // 设置日历中月份的最大天数
    //cal.set(Calendar.DAY_OF_MONTH, lastDay);
    cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(cal.getTime());
  }

  public static String getLastDayOfLastMonth(String yearMonth) {
    int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
    int month = Integer.parseInt(yearMonth.split("-")[1]) - 1; //月
    Calendar cal = Calendar.getInstance();
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    // cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.MONTH, month); //设置当前月的上一个月
    // 获取某月最大天数
    //int lastDay = cal.getActualMaximum(Calendar.DATE);
    int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
    // 设置日历中月份的最大天数
    //cal.set(Calendar.DAY_OF_MONTH, lastDay);
    cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(cal.getTime());
  }

  public static Date getStartOfTodayDate(Date dateInput) {
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String startDayString = ft.format(dateInput).substring(0, 11) + "00:00:00";
//        log.info("startDayString："+ startDayString );
    try {
      return ft.parse(startDayString);
    } catch (ParseException e) {
      log.error("", e);
    }
    return null;
  }

  public static String getStartOfTodayStr(Date dateInput) {
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    return ft.format(dateInput);
  }

  public static Date getEndOfTodayDate(Date dateInput) {
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String endDayString = ft.format(dateInput).substring(0, 11) + "23:59:59";
//        log.info("endDayString："+ endDayString );
    try {
      return ft.parse(endDayString);
    } catch (ParseException e) {
      log.error("", e);
    }
    return null;
  }

  public static String getCurrentYear() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    Date date = new Date();
    return sdf.format(date);
  }

  public static Date getDateBeforeOrAfterDate(Date date, int num) {
    Calendar calendar = Calendar.getInstance();//获取日历
    calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
    calendar.add(Calendar.DATE, num);
    Date d = calendar.getTime();//把日历转换为Date
    return d;
  }

  public static Date phraseTime(String dateString) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
    return sdf.parse(dateString);
  }

  public static Date phraseDay(String dateString) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
    return sdf.parse(dateString);
  }

  public static Date phraseTimeFromFileTime(FileTime fileTime) {
    return new Date(fileTime.toMillis());
  }

  public static String dayOfWeekToCron(String dayOfWeekInput) {
    dayOfWeekInput = dayOfWeekInput.replaceAll(" ", "");
    List<String> dayList = Arrays.asList(dayOfWeekInput.split(","));
    String result = "";
    for (String dayStr : dayList) {
      result = result + "," + dayStr;
    }
    if (result.startsWith(","))
      result = result.substring(1);
    return result;
  }

  public static void judgeTimeSIsInvalid(String timeStr) {
    boolean flag = Pattern.compile(timePatternS).matcher(timeStr).matches();
    boolean flag2 = Pattern.compile(timePatternS2).matcher(timeStr).matches();
    if (!(flag || flag2)) {
      log.error("startTime to cron error");
      throw new BapParamsException("定时任务触发时间无效，格式为：00:00:00");
    }
  }

  public static void judgeTimeMIsInvalid(String timeStr) {
    boolean flag = Pattern.compile(timePatternM).matcher(timeStr).matches();
    boolean flag2 = Pattern.compile(timePatternM2).matcher(timeStr).matches();
    if (!(flag || flag2)) {
      log.error("startTime to cron error");
      throw new BapParamsException("定时任务触发时间无效，格式为：00:00");
    }
  }

  public static String dateToDateTimeDayStartStr(String value) {
    return value + " 00:00:00";
  }

  public static String dateToDateTimeDayEndStr(String value) {
    return value + " 23:59:59";
  }
}
