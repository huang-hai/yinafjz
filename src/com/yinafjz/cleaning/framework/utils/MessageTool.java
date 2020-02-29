package com.yinafjz.cleaning.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.model.PageResultModel;
import com.yinafjz.cleaning.framework.model.ResultModel;


public class MessageTool {

	private static final Logger logger = Logger.getLogger(MessageTool.class); 
	
	private static Properties messageProperties = new Properties();
	public void destory() throws Exception {
		logger.debug("[MessageService]->destory()");
	}
	
	public static ResultModel getErrorResultModel(String errorMsg,String errorCode){
		ResultModel result =  new ResultModel();
		result.setCode(errorCode);
		result.setMsg(errorMsg);
		result.setSuccess(false);
		return result;
	}	
	public static PageResultModel getErrorPageResultModel(String errorMsg,String errorCode){
		PageResultModel result =  new PageResultModel();
		result.setCode(errorCode);
		result.setMsg(errorMsg);
		result.setSuccess(false);
		return result;
	}	
	public static void init(List<String> paramList) {
		File file = null;
		try {
			Properties pTemp = new Properties();
			for (int i = 0; i < paramList.size(); i++) {
				Object obj = paramList.get(i);
				String strFileName = SystemKeyWord.APP_WEB_INFO_PATH+"classes"+SystemKeyWord.FILE_SEPARATOR+"config"+SystemKeyWord.FILE_SEPARATOR+obj;
				logger.info("[initialize:MessageTool]"+strFileName);
				file = new File(strFileName);
				pTemp.load(new FileInputStream(file));
				messageProperties.putAll(pTemp);
			}
		} catch (Exception e) {
			logger.error("消息属性文件加载错误", e);
		}
		logger.debug("[MessageService]->init()");
	}
	
	public static void clearProerties(){
		messageProperties.clear();
	}
	
	public static String getMessage(String messageKey) {
		String s = messageProperties.getProperty(messageKey);
		return ((s == null) ? "" : s);
	}
	public static String format(String message,Object ...arg){
		MessageFormat formatter = new MessageFormat(message);
		String output = formatter.format(arg); 
		return output;
	}	
	public static String createResNum() {
		StringBuffer memberNum = new StringBuffer();
		String str = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		memberNum.append(str);
		int random = CommonUtils.random();
		memberNum.append(random);
		return memberNum.toString();
	}
	
}
