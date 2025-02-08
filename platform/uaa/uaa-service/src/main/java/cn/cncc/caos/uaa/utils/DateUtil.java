package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.exception.BapParamsException;
import cn.cncc.caos.common.core.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class DateUtil {

	public static String ptn_18 = "yyyy-MM-dd HH:mm:ss";
	public static String ptn_10 = "yyyy-MM-dd";
	public static String ptn_8 = "HH:mm:ss";

	/**
	 * 获取制定时间所在年份
	 *
	 * @param nowDate
	 * @return
	 */
	public static int getNowYear(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取本周第一天
	 *
	 * @return
	 */
	public static Date getWeekStart() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, 0);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		Date time = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String format = simpleDateFormat.format(time);
		return simpleDateFormat.parse(format);
	}

	/**
	 * 获取本周最后一天
	 *
	 * @return
	 */
	public static Date getWeekEnd() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		Date time = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String format = simpleDateFormat.format(time);
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.parse(format);
	}

	public static Date getDayStart(Date startTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String format = sdf.format(startTime);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(format);
	}

	public static Date getDayEnd(Date endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String format = sdf.format(endTime);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(format);
	}

	public static Date getDate(String date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getNowDate(String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String format = sdf.format(new Date());
		Date parse = sdf.parse(format);
		return parse;
	}

	/**
	 * 获取上个月第一天
	 */
	public static String getBeforeFirstMonthDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());
	}

	/**
	 * 获取上个月最后
	 */
	public static String getBeforeLastMonthDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sf.format(calendar.getTime());
	}

	public static String DateToString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Long getDateLong(String date) {
		return getDate(date, ptn_18).getTime();
	}

	/**
	 * 两个时间差计算
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}

	public static String getLongPoor(Long diff) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}

	public static Date timeUtcFormat(String time) throws ParseException {
		String tempTime = time.replace("Z", " UTC");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		return sdf.parse(tempTime);
	}

	public static String timeUtcFormatStr(String time) throws ParseException {
		Date d = timeUtcFormat(time);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf1.format(d);
		return str;
	}

	public static String getDateString(Date oriDate, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isEmpty(format)?ptn_18:format);
		return sdf.format(oriDate);
	}

	/**
	 * 将 yyyy-MM-dd HH:mm:ss 格式的时间字符串转换为指定格式的时间字符串
	 *
	 * @param dateTimeStr yyyy-MM-dd HH:mm:ss 格式的时间字符串
	 * @param pattern     yyyy-MM-dd  转换时间格式
	 * @return pattern格式的时间字符串
	 */
	public static String dateTimeStrToDateString(String dateTimeStr, String pattern) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			Date dateTime = dateTimeFormat.parse(dateTimeStr);
			return dateFormat.format(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将指定时间的时间字符串加上指定分钟数，并返回新的时间字符串
	 *
	 * @param date    指定格式的时间
	 * @param minutes 要增加的分钟数
	 * @return 增加分钟数后的时间字符串
	 */
	public static String addMinutes(Date date, int minutes) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return dateTimeFormat.format(calendar.getTime());
	}

	/**
	 * 将指定时间的时间字符串加上指定分钟数，并返回新的时间字符串
	 *
	 * @param date    指定格式的时间
	 * @param minutes 要增加的分钟数
	 * @return 增加分钟数后的时间字符串
	 */
	public static String addMinutes(String date, int minutes) throws ParseException {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = dateTimeFormat.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse);
		calendar.add(Calendar.MINUTE, minutes);
		return dateTimeFormat.format(calendar.getTime());
	}

	/**
	 * 将指定的时间类型日期减去指定分钟数
	 *
	 * @param date    指定格式的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * @param minutes 需要减去的分钟数
	 * @return 减去指定分钟数后的日期字符串，格式为 yyyy-MM-dd HH:mm:ss
	 */
	public static String subtractMinutes(Date date, int minutes) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -minutes);
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}

	/**
	 * 将指定的时间类型日期减去指定分钟数
	 *
	 * @param date    指定格式的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * @param minutes 需要减去的分钟数
	 * @return 减去指定分钟数后的日期字符串，格式为 yyyy-MM-dd HH:mm:ss
	 */
	public static String subtractMinutes(String date, int minutes) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = sdf.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse);
		calendar.add(Calendar.MINUTE, -minutes);
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}

	/**
	 * 将指定的时间类型日期减去指定分钟数
	 *
	 * @param date    指定格式的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * @param minutes 需要减去的分钟数
	 * @return 减去指定分钟数后的日期，格式为 yyyy-MM-dd HH:mm:ss
	 */
	public static Date subtractMinutesFromDate(Date date, int minutes) {
		String dateStr = DateUtil.subtractMinutes(date, minutes);
		return DateUtil.getDate(dateStr, DateUtil.ptn_18);
	}

	/**
	 * 将指定的时间类型日期增加指定分钟数
	 *
	 * @param date    指定格式的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * @param minutes 需要减去的分钟数
	 * @return 减去指定分钟数后的日期，格式为 yyyy-MM-dd HH:mm:ss
	 */
	public static Date addMinutesFromDate(Date date, int minutes) {
		String dateStr = DateUtil.addMinutes(date, minutes);
		return DateUtil.getDate(dateStr, DateUtil.ptn_18);
	}

	/**
	 * 获取指定日期的 HH:mm:ss 格式时间字符串
	 *
	 * @param date 指定日期
	 * @return HH:mm:ss 格式时间字符串
	 */
	public static String getTimeStr(Date date) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		return timeFormat.format(date);
	}

	/**
	 * 获取指定日期的 yyyy-MM-dd 格式日期的 Date 类型
	 *
	 * @param date 指定日期
	 * @return yyyy-MM-dd 格式日期的 Date 类型
	 */
	public static Date getDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(date);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定日期的 HH:mm:ss 格式时间的 Date 类型
	 *
	 * @param date 指定日期
	 * @return HH:mm:ss 格式时间的 Date 类型
	 */
	public static Date getTime(Date date) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String timeStr = timeFormat.format(date);
		try {
			return timeFormat.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将指定的时间增加到对应的 yyyy-MM-dd 日期上
	 *
	 * @param date 需要增加的时间
	 * @param time 时间，格式为 HH:mm:ss
	 * @return 增加后的日期字符串，格式为 yyyy-MM-dd
	 */
	public static String addTimeToDate(Date date, Date time) {
		String timeStr = DateUtil.getTimeStr(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String[] timeArr = timeStr.split(":");
		calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
		calendar.add(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
		calendar.add(Calendar.SECOND, Integer.parseInt(timeArr[2]));
		Date newDate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(newDate);
	}

	/**
	 * 将指定的字符串类型日期增加指定天数
	 *
	 * @param dateStr
	 * @param daysToAdd
	 * @return
	 * @throws ParseException
	 */
	public static String addDays(String dateStr, int daysToAdd) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.ptn_10);
		Date date = dateFormat.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		Date newDate = calendar.getTime();
		return dateFormat.format(newDate);
	}

	public static Date addDays(Date date, int daysToAdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		return calendar.getTime();
	}

	/**
	 * 将指定的字符串类型日期减去指定天数
	 *
	 * @param dateStr
	 * @param days
	 * @return
	 */
	public static String minusDays(String dateStr, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -days);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据yyyy-MM日期获取周几
	 *
	 * @param month
	 * @return
	 */
	public static String getWeekDayByMonth(String month) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM").parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();
		LocalDate date = LocalDate.parse(month, formatter);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
	}

	/**
	 * 判断两个日期是否在同一天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str1 = sdf.format(date1);
		String str2 = sdf.format(date2);
		return str1.equals(str2);
	}

	/**
	 * 获取开始时间和结束时间之内所有日期
	 */
	public static Set<String> getDays(String startTime, String endTime) {
		LocalDate startDate = LocalDate.parse(startTime);
		LocalDate endDate = LocalDate.parse(endTime);
		Set<String> dates = new HashSet<>();
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= numOfDaysBetween; i++) {
			LocalDate date = startDate.plusDays(i);
			dates.add(date.toString());
		}
		return dates;
	}

	public static Set<String> getDays(Date startTime, Date endTime) {
		String startTimeStr = TimeUtil.getDateNormalFormatString(startTime);
		String endTimeStr = TimeUtil.getDateNormalFormatString(endTime);
		LocalDate startDate = LocalDate.parse(startTimeStr);
		LocalDate endDate = LocalDate.parse(endTimeStr);
		Set<String> dates = new HashSet<>();
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= numOfDaysBetween; i++) {
			LocalDate date = startDate.plusDays(i);
			dates.add(date.toString());
		}
		return dates;
	}

	public static Date getAfterManyMonthDate(Date now, int numMonths) {
		LocalDate startDate = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = startDate.plusMonths(numMonths);
		return Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getAfterManyMonthDateEnd(Date now, int numMonths) throws ParseException {
		LocalDate today = LocalDate.now();
		LocalDate threeMonthsLater = today.plusMonths(3);
		LocalDateTime lastDayOfMonth = threeMonthsLater.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();
		LocalDateTime lastDayOfMonth235959 = lastDayOfMonth.with(LocalTime.of(23, 59, 59));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = lastDayOfMonth235959.format(formatter);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(formatDateTime);
		return date;
	}

	public static boolean isNightShift(Date startDate, Date endDate) {
		Calendar nightStart = Calendar.getInstance();
		nightStart.setTime(startDate);
		nightStart.set(Calendar.HOUR_OF_DAY, 22);
		nightStart.set(Calendar.MINUTE, 0);
		nightStart.set(Calendar.SECOND, 0);

		Calendar nightEnd = Calendar.getInstance();
		nightEnd.setTime(endDate);
		nightEnd.set(Calendar.HOUR_OF_DAY, 4);
		nightEnd.set(Calendar.MINUTE, 0);
		nightEnd.set(Calendar.SECOND, 0);
		if (endDate.before(startDate)) {
			// If end time is before start time, assume it's on the next day
			nightEnd.add(Calendar.DATE, 1);
		}

		return (startDate.compareTo(nightStart.getTime()) >= 0 && startDate.compareTo(nightEnd.getTime()) <= 0) ||
				(startDate.compareTo(nightStart.getTime()) <= 0 && endDate.compareTo(nightStart.getTime()) > 0) ||
				(endDate.compareTo(nightStart.getTime()) >= 0 && endDate.compareTo(nightEnd.getTime()) <= 0) ||
				(endDate.compareTo(nightEnd.getTime()) >= 0 && startDate.compareTo(nightEnd.getTime()) < 0);
	}

	public static String formatMonthYear(Integer year, Integer month) {
		LocalDate date = LocalDate.of(year, month, 1);
		return date.toString().substring(0, 7);
	}

	/**
	 * 获取指定 yyyy-MM 对应月份的第一天
	 *
	 * @param yearMonth yyyy-MM 格式的年月
	 * @return 指定 yyyy-MM 对应月份的第一天
	 */
	public static String getFirstDayOfMonth(String yearMonth) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(yearMonth + "-01", formatter);
		LocalDate localDate = date.withDayOfMonth(1);
		return localDate.toString();
	}

	/**
	 * 获取指定 yyyy-MM 对应月份的最后一天
	 *
	 * @param yearMonth yyyy-MM 格式的年月
	 * @return 指定 yyyy-MM 对应月份的最后一天
	 */
	public static String getLastDayOfMonth(String yearMonth) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(yearMonth + "-01", formatter);
		LocalDate localDate = date.withDayOfMonth(date.lengthOfMonth());
		return localDate.toString();
	}

  public static Date getLastDayOfMonth(Integer year, Integer month, Integer day) throws ParseException {
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    LocalDate date = LocalDate.of(year, month, day);
    LocalDate localDate = date.withDayOfMonth(date.lengthOfMonth());
	  String lastDayStr = localDate.toString();
	  return sdf.parse(lastDayStr);
  }

	/**
	 * 判断时间是否在指定时间范围之内
	 *
	 * @param startTimeStr
	 * @param endTimeStr
	 * @param currentTimeStr
	 * @return
	 * @throws ParseException
	 */
	public static boolean isTimeWithininterval(String startTimeStr, String endTimeStr, String currentTimeStr) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.ptn_18);
		Date startTime = simpleDateFormat.parse(startTimeStr);
		Date endTime = simpleDateFormat.parse(endTimeStr);
		Date currentTime = simpleDateFormat.parse(currentTimeStr);
		return (currentTime.after(startTime) && currentTime.before(endTime)) || DateUtil.compareTimes(startTimeStr, currentTimeStr) == 0 || DateUtil.compareTimes(endTimeStr, currentTimeStr) == 0 ;
	}

	/**
	 * 比较两个时间大小
	 *
	 * @param time1Str
	 * @param time2Str
	 * @return
	 */
	public static int compareTimes(String time1Str, String time2Str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time1 = format.parse(time1Str);
			Date time2 = format.parse(time2Str);
			return time1.compareTo(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getYearDifference(String date1, String date2) {

		return 1;
	}

	public static long getMinuteDifference(String timeString1, String timeString2) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.ptn_18);
		try {
			Date time1 = format.parse(timeString1);
			Date time2 = format.parse(timeString2);
			long differenceInMillis = time2.getTime() - time1.getTime();
			return differenceInMillis / (60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String convertDateFormat(String dateString) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
		try {
			Date date = inputFormat.parse(dateString);
			return outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String timeCycle(String dateString) {
		String[] replaceString = new String[]{"GMT+0800", "GMT+08:00"};
		String splitString = "(中国标准时间)";
		String formatString2 = "EEE MMM dd yyyy HH:mm:ss z";
		String ptn7 = "yyyy-MM";
		try {
			dateString = dateString.split(Pattern.quote(splitString))[0].replace(replaceString[0], replaceString[1]);
			//转换为date
			SimpleDateFormat sf1 = new SimpleDateFormat(formatString2, Locale.ENGLISH);
			Date date = sf1.parse(dateString);
			return new SimpleDateFormat(ptn7).format(date);
		} catch (Exception e) {
			throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + ptn7 + "]");
		}
	}

	/**
   * 校验日期、周、月范围
   * @param judgeStr 拼接后的字符串
   * @param split 分割字符
   * @param max 最大值
   * @param min 最小值
   */
  public static void judgeValueScope(String judgeStr, String split, Integer max, Integer min){
    List<String> judgeArray = Arrays.asList(judgeStr.split(split));
    for (String s : judgeArray) {
		if (StringUtils.isEmpty(s))
			continue;
		Integer input;
		try {
			input = Integer.valueOf(s);
		} catch (Exception e) {
			log.error("", e);
			throw new BapParamsException("定时内容格式不符，请检查，错误内容：" + judgeStr);
		}
      if (!(input.compareTo(max) < 0 && input.compareTo(min) > 0)) {
        log.error("input judgeStr={}, max={}, min={}", judgeStr, max, min);
        throw new BapParamsException("定时内容有误，请检查，错误内容：" + judgeStr);
      }
    }
  }

	public static Integer getMonth(String noWorkDay, SimpleDateFormat sdf, Calendar calendar) throws ParseException {
		Date parse = sdf.parse(noWorkDay);
		calendar.setTime(parse);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static Date getFirstDayOfMonth(Integer year, Integer month) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate date = LocalDate.of(year, month, 1);
		LocalDate localDate = date.withDayOfMonth(1);
		String firstDayStr = localDate.toString();
		return sdf.parse(firstDayStr);
	}

	public static Date firstTimeOfYear(Integer year) throws ParseException {
		String firstDateStr = year + "-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01 00:00:00");
		return sdf.parse(firstDateStr);
	}

	public static Date lastTimeOfYear(Integer year) throws ParseException {
		String lastDateStr = year + 1 + "-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01 00:00:00");
		return sdf.parse(lastDateStr);
	}

	public static boolean isDate(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDateTime(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.parse(time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date object2Date(Object expectedStartTimeObj) {
		if (expectedStartTimeObj instanceof LocalDateTime)
			return Date.from(((LocalDateTime) expectedStartTimeObj).atZone(ZoneId.systemDefault()).toInstant());
		else if (expectedStartTimeObj instanceof Date)
			return (Date) expectedStartTimeObj;
		else
			throw new BapParamsException("未知的时间类型");
	}

	public static Date getAfterManyHourDate(Date date, Double shieldHour) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		double hour = Math.floor(shieldHour);
		double decimals = shieldHour - hour;
		int second = (int) (decimals * 60);
		instance.add(Calendar.HOUR, (int) hour);
		instance.add(Calendar.MINUTE, second);
		return instance.getTime();
	}

	/**
	 * 要根据日期（例如"2024-01-01"）获取对应的星期几（用一、二、三、四、五、六、日表示）
	 * @param dateString
	 * @return
	 */
	public static String getDayOfWeek(String dateString) {
		String[] weekdays = {"日", "一", "二", "三", "四", "五", "六"};

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = sdf.parse(dateString);
			calendar.setTime(date);
			int dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return weekdays[dayOfWeekIndex];
		} catch (ParseException e) {
			e.printStackTrace();
			return "日期格式错误";
		}
	}

	/**
	 * 要将年月日（例如"2024-01-01"）或（"2024-12-12"）转换为"1-1"、"12-12"的格式
	 * @param dateStr
	 * @return
	 */
	public static String convertDateFormatToMonthDay(String dateStr) {
		LocalDate date = LocalDate.parse(dateStr);
		int year = date.getYear() % 100; // 取年份的后两位
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();

		return String.format("%d-%d", month, day);
	}

	/**
	 * 获取一段时间内所有的日期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDateRange(String startDate, String endDate) {
		List<String> dateRange = new ArrayList<>();

		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);

		long daysBetween = ChronoUnit.DAYS.between(start, end);

		for (int i = 0; i <= daysBetween; i++) {
			LocalDate currentDate = start.plusDays(i);
			dateRange.add(currentDate.toString());
		}

		return dateRange;
	}

	public static Date joinDateTime(Date date1, Date date2){
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		// 获取date1的年月日
		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH) + 1; // 月份是从0开始计数的，需要加1
		int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

		// 获取date2的时分秒
		int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
		int minute2 = calendar2.get(Calendar.MINUTE);
		int second2 = calendar2.get(Calendar.SECOND);

		// 拼接成完整的年月日时分秒字符串
		String fullDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year1, month1, day1, hour2, minute2, second2);
		return DateUtil.getDate(fullDateTime, DateUtil.ptn_18);

	}

	public static Date getAfterWorkDayDate(Date startDate, Map<String, HashSet<String>> allNotWorkDayMap, int addNum){
		HashSet<String> set0 = allNotWorkDayMap.get("0") == null ? new HashSet<>() : allNotWorkDayMap.get("0"); // 周末(不包含节假日)
		HashSet<String> set1 = allNotWorkDayMap.get("1") == null ? new HashSet<>() : allNotWorkDayMap.get("1"); // 节假日
		String oridateStr = getDateString(startDate, "yyyy-MM-dd HH:mm:ss");
		String keyDateStr;
		Integer addedNum = 0; // 以计算天数
		for (int i = 0; ; i++) {
			startDate = addDays(startDate, 1);
			keyDateStr = getDateString(startDate, "yyyy-MM-dd") + " 00:00:00";
			if (!set0.contains(keyDateStr) && !set1.contains(keyDateStr)) {
				addedNum++;
			}
			if (addedNum == addNum) {
				break;
			}
		}
		String resultStr = keyDateStr.substring(0, 10) + oridateStr.substring(10);
		return getDate(resultStr, "yyyy-MM-dd HH:mm:ss");
	}

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime strToLocalDateTime(String date){
		return LocalDateTime.parse(date, formatter);
	}

	public static List<LocalDateTime[]> excludeTimeRange(
			String startDate1, String endDate1,
			String startDate2, String endDate2) {
		// 将字符串转换为LocalDateTime对象
		LocalDateTime startRange1 = LocalDateTime.parse(startDate1, formatter);
		LocalDateTime endRange1 = LocalDateTime.parse(endDate1, formatter);
		LocalDateTime startRange2 = LocalDateTime.parse(startDate2, formatter);
		LocalDateTime endRange2 = LocalDateTime.parse(endDate2, formatter);
		return excludeTimeRange(startRange1, endRange1, startRange2, endRange2);
	}

	public static List<LocalDateTime[]> excludeTimeRange(
			LocalDateTime startRange1, LocalDateTime endRange1,
			LocalDateTime startRange2, LocalDateTime endRange2) {
		List<LocalDateTime[]> adjustedRanges = new ArrayList<>();
		// 2023-01-01 00:00:00
		// 2023-01-01 23:59:00
		// 2023-01-01 00:00:00
		// 2023-01-01 12:59:00
		// before 在 ... 之前
		// after  在 ... 之后
		// 判断是否有交集
		if (startRange1.isBefore(endRange2) && startRange2.isBefore(endRange1)) {
			// 计算非重叠部分的开始和结束时间
			LocalDateTime newStart = startRange1.isBefore(startRange2) ? startRange2 : startRange1;
			LocalDateTime newEnd = endRange1.isBefore(endRange2) ? endRange1 : endRange2;

			// 根据重叠情况调整时间范围
			if (newStart.isBefore(newEnd)) {
				// 部分重叠，去除重叠部分
				// 开始时间与最小开始时间对比，如果在开始时间之前则取开始时间到最小开始时间范围
				if (startRange1.isBefore(newStart)) {
					adjustedRanges.add(new LocalDateTime[]{startRange1, newStart});
				}
				// 结束时间与最小结束时间对比，如果在最小结束时间之前则取最小结束时间时间到结束时间范围
				if (endRange1.isAfter(newEnd)) {
					adjustedRanges.add(new LocalDateTime[]{newEnd, endRange1});
				}
				// 开始时间与最小开始时间 && 结束时间与最小结束时间相等，时间完全重叠
				if(startRange1.equals(newStart) && endRange1.equals(newEnd)){
					adjustedRanges.add(new LocalDateTime[]{newEnd, endRange1});
				}
			} else {
				// 无实际交集情况，但逻辑上应不会走到这一步，属于防御性编程
				adjustedRanges.add(new LocalDateTime[]{startRange1, endRange1});
			}
		} else {
			// 无交集，直接返回原时间范围
			adjustedRanges.add(new LocalDateTime[]{startRange1, endRange1});
		}
		// 返回空，则完全重叠

		return adjustedRanges;

	}


	public static String addMonths(String inputDate, int monthsToAdd) {
		YearMonth yearMonth = YearMonth.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM"));
		YearMonth resultYearMonth = yearMonth.plusMonths(monthsToAdd);
		return resultYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	}

	public static String subtractMonths(String inputDate, int monthsToSubtract) {
		YearMonth yearMonth = YearMonth.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM"));
		YearMonth resultYearMonth = yearMonth.minusMonths(monthsToSubtract);
		return resultYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	}

	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		return now.getTime();
	}

	/**
	 * 计算两个 Date 对象之间的小时差异，返回 Double 类型。
	 *
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return 小时差（Double 类型）
	 */
	public static double calculateHoursDifference(Date startDate, Date endDate) {
		// 计算毫秒差
		long differenceInMillis = endDate.getTime() - startDate.getTime();
		// 将毫秒差转换为小时，使用浮点数以支持小数部分
		return differenceInMillis / (1000.0 * 60 * 60);
	}

	/**
	 * 获取本月1号0点的 Date 对象
	 *
	 * @return 本月1号0点的 Date 对象
	 */
	public static Date getFirstDayOfCurrentMonth() {
		// 创建一个 Calendar 实例
		Calendar calendar = Calendar.getInstance();
		// 设置为本月1号
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 设置时间为0点0分0秒
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// 获取 Date 对象
		return calendar.getTime();
	}

	/**
	 * 比较两个日期的大小。
	 *
	 * @param date1 第一个日期
	 * @param date2 第二个日期
	 * @return 如果 date1 在 date2 之前返回 -1；如果 date1 等于 date2 返回 0；如果 date1 在 date2 之后返回 1
	 */
	public static int compareDates(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("日期不能为空");
		}
		return date1.compareTo(date2);
	}
}
