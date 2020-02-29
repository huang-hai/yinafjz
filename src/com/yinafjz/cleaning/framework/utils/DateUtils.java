package com.yinafjz.cleaning.framework.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 时间工具类
 * 
 * @author admin
 */
public class DateUtils {
	
	private static final Logger LOGGER = Logger.getLogger(DateUtils.class);
	
	public DateUtils() {
	}

	// 日期时间的输出样式字符串
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";

	// 时间日期输出是样的初始化对象
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			DATE_TIME_PATTERN);
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_PATTERN);
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			TIME_PATTERN);

	/**
	 * 获取当前时间指定偏移类的时间对象
	 * 
	 * @param field
	 *            偏移的域， 以Calendar常量取值， 如 Calendar.DATE
	 * @param offset
	 *            正负数偏移量
	 * @return
	 */
	public static Date getOffsetDate(int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(field, offset);
		return cal.getTime();
	}

	/**
	 * 获取日期对象的指定域值
	 * 
	 * @param dt
	 *            日期对象，如果为空将使用当前时间
	 * @param field
	 *            指定域， 以Calendar常量取值
	 * @param offset
	 *            正负数偏移量，不偏移取0
	 * @return
	 */
	public static int getDateField(Date dt, int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt == null ? new Date() : dt);
		if (offset != 0)
			;
		cal.add(field, offset);
		return cal.get(field);
	}

	public static int getDateField(Date dt, int field) {
		return getDateField(dt, field, 0);
	}

	public static int getDateField(int field) {
		return getDateField(null, field, 0);
	}

	public static int getDateField(int field, int offset) {
		return getDateField(null, field, offset);
	}

	/**
	 * 格式化SQL中的字串参数(将'号改为'')
	 * 
	 * @param str
	 * @return
	 */
	public static String formatSQLString(String str) {
		if (str == null)
			return "";
		return str.replaceAll("'", "''");
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日期+时间字符串
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String 指定的格式字符串
	 * @return String date或pattern为空,返回空串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd)的日期字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return DATE_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日期+时间字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return DATE_TIME_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(HH:mm:ss)的时间字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (date == null)
			return "";
		return TIME_FORMAT.format(date);
	}

	/**
	 * 将字符串转化为日期对象,应用格式 yyyy-MM-dd
	 * 
	 * @param dateStr String
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			LOGGER.info("[CommonUtils-parseDate]",ex);
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用指定的格式
	 * 
	 * @param dateStr
	 *            String 日期字符串
	 * @param pattern
	 *            String 格式字符串
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			LOGGER.info("[CommonUtils-parseDate]",ex);
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateString
	 *            String
	 * @return Date
	 */
	public static Date parseDateTime(String dateStr) {
		try {
			return DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			LOGGER.info("[CommonUtils-parseDateTime]",ex);
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用格式 HH:mm:ss
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parseTime(String dateStr) {
		try {
			return TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			LOGGER.info("[CommonUtils-parseTime]",ex);
			return null;
		}
	}

	/**
	 * 比较两个日期间隔多少天
	 * @author linhy
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int compareDate(String startDate, String endDate){
		try {
			//算两个日期间隔多少天
			SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_PATTERN);
			Date date1 = format.parse(startDate);
			Date date2 = format.parse(endDate);
			return (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
		} catch (ParseException e) {
			LOGGER.error("[CommonUtils-parseTime]",e);
			return -1;
		}
	}
	
	/**
	 * 将时间转换为时间戳
	 * @author linhy
	 * @param strDate
	 * @return
	 */
	public static String dateToStamp(String strDate){
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
			Date date = simpleDateFormat.parse(strDate);
			long ts = date.getTime();
			String res = String.valueOf(ts);
			return res;
		} catch (ParseException e) {
			LOGGER.error("[CommonUtils-dateToStamp]",e);
			return null;
		}
	}
	
	/**
	 * 将时间戳转换为时间
	 * @author linhy
	 * @param strStamp
	 * @return
	 */
	public static String stampToDate(String strStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
        long lt = new Long(strStamp);
        Date date = new Date(lt);
        return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取指定时间一周数据
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static String[] getWeekDate(String day){
        try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
            Calendar cal=Calendar.getInstance(Locale.CHINA);
			cal.setTime(format.parse(day));
			cal.setFirstDayOfWeek(Calendar.MONDAY);
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	        String monday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
	        String tuesday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
	        String wednesday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	        String thursday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
	        String friday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	        String saturday = format.format(cal.getTime());
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	        String sunday = format.format(cal.getTime());
	        String[] result = {monday,tuesday,wednesday,thursday,friday,saturday,sunday};
	        return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	/**
	 * 获取昨天时间
	 * @return
	 */
	public static String getYesterday(String dayStr){
		Date date=new Date();//取时间
		Date parseDate = DateUtils.parseDate(dayStr);
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(parseDate);
	    calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
	    date=calendar.getTime(); 
	    return DATE_FORMAT.format(date);
	}
	
	/**
	 * 检验字串是否为空，或为空白串(包含全为空白字符情况)
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 解析宏表达式，
	 * 
	 * @param exp
	 *            String 包含有${key}的宏表达式
	 * @param String
	 *            key 宏名
	 * @param String
	 *            value 宏值
	 * @return String
	 */
	public static String parseMacroExp(String exp, String key, String value) {
		Map map = new HashMap();
		map.put(key, value);
		return parseMacroExp(exp, map);
	}

	/**
	 * 解析宏表达式，
	 * 
	 * @param exp
	 *            String 包含有${key}的宏表达式
	 * @param values
	 *            Map 对应于宏的值列表
	 * @return String
	 */
	public static String parseMacroExp(String exp, Map values) {
		if (DateUtils.isEmpty(exp))
			return "";
		if (values == null || values.size() == 0)
			return exp;

		Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
		Matcher m = p.matcher(exp);
		StringBuffer sbRet = new StringBuffer();
		Object obj = null;
		String key = null;
		String strTmp = null;
		while (m.find()) {
			key = m.group(1);
			if (values.containsKey(key)) {
				obj = values.get(key);
				strTmp = (obj == null ? "" : obj.toString());
			} else {
				strTmp = "${" + key + "}";
			}
			m.appendReplacement(sbRet, strTmp);
		}
		m.appendTail(sbRet);
		return sbRet.toString();
	}

	// 钱保留两位
	public static Double getMoney(String money) {
		if (null != money) {
			BigDecimal bd = new BigDecimal(money);
			BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
			Double pDouble = bd1.doubleValue();
			long ll = Double.doubleToLongBits(pDouble);
			return pDouble;
		}
		return 0.00;
	}

	public static String annotateOfClsName(Class<?> clazz) {
		String clsName = clazz.getSimpleName();
		clsName = clsName.toLowerCase().charAt(0) + clsName.substring(1);
		return clsName;
	}

	/**
     * 指定日期加上天数后的日期
     * @author chenb
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusDay(int num,String newDate){
    	try {
        Date  currdate = DATE_FORMAT.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        String enddate = DATE_FORMAT.format(currdate);
        return enddate;
    	} catch (ParseException ex) {
			LOGGER.info("[CommonUtils-parseDate]",ex);
			return null;
		}
    }
 

    /**
     * 当前日期加上天数后的日期
     * @author chenb
     * @param num 为增加的天数
     * @return
     */
    public static String plusDayToday(int num){
        Date  currdate = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        String enddate = DATE_FORMAT.format(currdate);
        return enddate;
    }
}
