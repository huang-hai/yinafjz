/**
 * @File name:  ValidateTool.java Java代码验证方法
 * @Create on:  2017-08-01
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date          Editor              ChangeReasons
 * 
 */
package com.yinafjz.cleaning.framework.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.yinafjz.cleaning.framework.exception.YinafjzException;

public class ValidateTool {
	private static final Logger logger = Logger.getLogger(ValidateTool.class);
	
	public static boolean iswatchNO(String watchs){
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(watchs);
		return m.matches();
	}
	
	public static boolean isTelephone(String tel){
		Pattern p = Pattern.compile("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
		Matcher m = p.matcher(tel);
		return m.matches();
	}
	
	public static boolean isEmail(String email){
		Pattern p = Pattern.compile("^(\\w+\\.*)\\w+@\\w+\\.[a-zA-z]{2,6}$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static boolean checkCharOrNum(String value,int begin,int end){
		String pattern = "^[A-Za-z0-9]{"+begin+","+end+"}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();	
	}
	
	public static boolean isNum(String value){
		String pattern = "^[0-9]+$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();	
	}	
	
	public static boolean checkCharOrNumOrChinese(String value,int begin,int end){
		String pattern = "^([A-Za-z0-9]|[\\u4E00-\\u9FA5]){"+begin+","+end+"}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();	
	}
	
	public static boolean check(String str,String pattern){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();	
	}
	
	public static boolean isWordAndNum(String value) {
		// String express="^[a-zA-Z0-9_]{3,16}$";
		String pattern =  "^[a-zA-Z0-9_]+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.matches();	
	}
	
	/**
	 * 验证参数是否为空
	 * @param code  参数名称
	 * @param value   参数值
	 * @throws YinafjzException
	 */
	public static boolean checkIsNull(String code,Object obj) throws YinafjzException{
		if(obj==null){
			return false;
		}
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDecimalValid(String str, int start, int end) {
		if (str == null) {
			return false;
		}
		int s = 0;
		try {
			s = Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}

		if (end == start) {
			if (end == s) {
				return true;
			} else {
				return false;
			}
		} else {
			if (s <= end && s >= start) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证参数是否为空
	 * @param obj
	 * @return 不为空:true,为空false
	 * @throws YinafjzException
	 */
	public static boolean checkIsNull(Object obj){
		if(obj==null){
			return false;
		}
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkLength(String str, int begin, int end) {
		if (str == null) {
			return false;
		}
		if (str.length() <= end && str.length() >= begin) {
			return true;
		}
		return false;
	}

	/**
	 * 验证参数是否为空
	 * @param code  参数名称
	 * @param value   参数值
	 * @throws YinafjzException
	 */
	public static boolean checkParameterIsNull(String code,Object obj) throws YinafjzException{
		if(obj==null){
			logger.warn(code+"参数异常！");
			throw new YinafjzException("-1",code+"参数异常！","");
		}
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) {
				logger.warn(code+"参数异常！");
				throw new YinafjzException("-1",code+"参数异常！","");
			}
		}
		if (obj.toString().trim().equals("")) {
			logger.warn(code+"参数异常！");
			throw new YinafjzException("-1",code+"参数异常！","");
		}
		return true;
	}
	
	/**
	 * 判断map key值是否为空
	 * @author linhy
	 * @param code
	 * @param paramsMap
	 * @return
	 * @throws YinafjzException
	 */
	public static boolean getMapParamsIsNull(String code,Map<String, String> paramsMap) throws YinafjzException{
		String value = MapUtils.getString(paramsMap, code, "");
		if(value==null){
			logger.warn(code+"参数异常！");
			throw new YinafjzException("-1",code+"参数异常！","");
		}
		if (value instanceof String) {
			if (((String) value).trim().equals("")) {
				logger.warn(code+"参数异常！");
				throw new YinafjzException("-1",code+"参数异常！","");
			}
		}
		if (value.toString().trim().equals("")) {
			logger.warn(code+"参数异常！");
			throw new YinafjzException("-1",code+"参数异常！","");
		}
		return true;
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	 public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }
	 
	 /** 
	  * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
	   规则是按日期订的例如：2.10.15   项目启动第2年的8月15号
	  * @param version1 
	  * @param version2 
	  * @return 
	  */  
	 public static int compareVersion(String version1, String version2) throws Exception {  
			if (version1 == null || version2 == null) { 
				throw new Exception("compareVersion error:illegal params."); 
			} 
			String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."； 
			for(int i = 0 ; i<versionArray1.length ; i++){ //如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
				if(versionArray1[i].length() == 1){
					versionArray1[i] = "0" + versionArray1[i];
				}
			}
			String[] versionArray2 = version2.split("\\."); 
			for(int i = 0 ; i<versionArray2.length ; i++){//如果位数只有一位则自动补零
				if(versionArray2[i].length() == 1){
					versionArray2[i] = "0" + versionArray2[i];
				}
			}
			int idx = 0; 
			int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值 
			int diff = 0; 
			while (idx < minLength 
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度 
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符 
				++idx; 
			} 
			//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大； 
			diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length; 
			return diff; 
		}
}
