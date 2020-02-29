/**
 * @File name:  SendMessageUtil.java 发送短信工具类
 * @Create on:  2018-01-12
 * @Author   :  linhy
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date          Editor              ChangeReasons
 * 
 */
package com.yinafjz.cleaning.framework.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.yinafjz.cleaning.framework.constants.SystemKeyWord;


public class SendMessageUtil {

	private static final Logger LOGGER = Logger.getLogger(SendMessageUtil.class);
	
	/**
	 * 短信验证码:保存短信15分钟
	 * @param userNo
	 * @return
	 */
	public static String getSendCode(String userNo){
		String strSendCode = null;
		try {
			if(userNo == null || userNo.length() <= 0){
				LOGGER.info("[SendMessageUtil-getSendCode]userNo="+userNo);
				return null;
			}
			//发送验证码
			String serverUrl = Config.getValueByKey("send_server_url");
			String appKey = Config.getValueByKey("send_app_key");
			String appSecret = Config.getValueByKey("send_app_secret");
			String templateid = Config.getValueByKey("send_temlateid");
			String resultJson = SendMessageUtil.wyyxSendCode(serverUrl, appKey, appSecret, SystemKeyWord.MOBILE_SEND_NONCE, templateid, userNo, SystemKeyWord.MOBILE_SEND_CODE_LENGTH);
			JSONObject json = JSONObject.parseObject(resultJson);
			if(json.get("code").equals(200)){
				strSendCode = (String) json.get("obj");
			}
		} catch (Exception e) {
			LOGGER.debug("[SendMessageUtil-getSendCode]error=", e);
		}
		return strSendCode;
	}
	
	/**
	 * 发送和短信
	 * @param mobile
	 * @param content
	 * @return 1:成功 其他：失败
	 */
//	public static int  sendMessage(String mobile,String content,String Ip) throws housewifeException{
//		String flag = Config.getValueByKey("thp_is_send_message");
//		if (flag.equals("0")) {
//			return 1;
//		}
//		String url = Config.getValueByKey("msg_send_url");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("sendContent", content);
//		paramMap.put("destAddr", mobile);
//		paramMap.put("srcAddr", Ip);
//		String returnStr = HttpClientInsideTool.httpInvokeToStr(url , paramMap);
//		JSONObject jsonObj = JSONObject.fromObject(returnStr);
//		if (jsonObj!=null&&jsonObj.getString("success")!=null&&jsonObj.getString("success").toString().equals("true")) {
//			return 1;
//		}
//		return 0;
//	}
	
	/**
	 * 发送短信验证码文字描述
	 * @param mobile
	 * @return
	 */
	public static String getMessageText(String mobile,String strSmsValidCode){
		return "您的验证码为:" + strSmsValidCode + "，有效时间为5分钟，输入验证码和密码后点击右上角按钮即可完成！【颐纳福】";
	}
	/**
	 * 发送找回密码短信验证码文字描述
	 * @param mobile
	 * @return
	 */
	public static String getUpdatePwdNextMessageText(String mobile,String strSmsValidCode){
		return "您正在找回密码，验证码为:" + strSmsValidCode + "，请在5分钟之内输入。同时为了账户安全，请勿将验证码泄露给其他人，如非本人操作，请忽略【颐纳福】";
	}
	

	public static String wyyxSendCode(String serverUrl, String appKey, String appSecret, 
			String nonce, String templateid, String moblie, String codeLen){
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(serverUrl);
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			/*
			 * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
			 */
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
			System.out.println(checkSum);
			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的的参数，requestBody参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			/*
			 * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
			 * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
			 * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
			 */
			nvps.add(new BasicNameValuePair("templateid", templateid));
			nvps.add(new BasicNameValuePair("mobile", moblie));
			nvps.add(new BasicNameValuePair("codeLen", codeLen));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			/*
			 * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
			 * 2.具体的code有问题的可以参考官网的Code状态表
			 * 3.返回结果{"code":200,"msg":"15903","obj":"105917"}
			 */
			return EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}
