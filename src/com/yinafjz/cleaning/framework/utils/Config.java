package com.yinafjz.cleaning.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

//配置文件:配置文件是config.properties放在classes目录下
public class Config {

	private static final ResourceBundle config = java.util.ResourceBundle.getBundle("config/config");
	
	private static final ResourceBundle status = java.util.ResourceBundle.getBundle("config/status");
	
	private static final ResourceBundle msg = java.util.ResourceBundle.getBundle("config/msg");
	
	public static String getValueByKey(String key){
		return config.getString(key);
	}
	
	public static String getStatusByKey(String key){
		return status.getString(key);
	}
	
	/**
	 * 随机获取返回值信息
	 * @param key
	 * @return
	 */
	public static String getMsgByRandom(String key){
		Random random = new Random();
		List<String> strList = new ArrayList<String>();
		String str = msg.getString(key);
		if(ValidateTool.checkIsNull(str)){
			String[] array = str.split("-");
			if(array.length > 0){
				for (String sa : array) {
					strList.add(sa);
				}
			}
		}
		return strList.get(random.nextInt(strList.size()));
	}
	
}
