package com.yinafjz.cleaning.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.framework.utils.WechatSignUtil;
import com.yinafjz.cleaning.framework.utils.XmlUtil;
import com.yinafjz.cleaning.framework.web.AHandleModelAndView;
import com.yinafjz.cleaning.order.service.PayRecordService;
import common.Logger;

@RestController
@RequestMapping("/pay")
public class PayNotifyController extends AHandleModelAndView{
	
	private static final Logger LOGGER =Logger.getLogger(PayNotifyController.class);
	
	@Autowired
	@Qualifier("payRecordService")
	private PayRecordService payRecordService ;
	
	/**
	 * 微信支付回调接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/wechatPayNotify",method=RequestMethod.POST)
	public String wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 读取数据
			String data = readData(request) ;
			if (data == null) {
				return notifyReturnXML("FAIL" , "read data error");
			}
			//解析xml参数
			TreeMap<String, String> parameters = (TreeMap)XmlUtil.parseToMap(data ,new TreeMap<String, String>());
			// 获得微信的签名
			String wx_sign = parameters.remove("sign");
			// 生成签名
			String my_sign = WechatSignUtil.sign(parameters, Config.getValueByKey("wechat.pay.app.signKey"));
			// 验证签名
			if (!my_sign.equalsIgnoreCase(wx_sign)) {
				return notifyReturnXML("FAIL", "sign error");
			}
			if (!"SUCCESS".equals(parameters.get("return_code"))) {
				LOGGER.error("微信支付通知返回错误信息:" + parameters.get("return_msg"));
				return notifyReturnXML("FAIL", "receive error msg");
			}
			// 判断订单是否成功
			if (parameters.get("result_code").equals("SUCCESS")) {
				try {
					//更新支付记录
					payRecordService.handlePaySucces(parameters, SystemKeyWord.PAY_WAY_WECHATPAY);

				} catch (Exception e) {
					LOGGER.error("保存微信支付记录异常" , e);
					LOGGER.error(JSONObject.toJSONString(parameters));
				}
				//更新订单状态信息
				ResultModel resultModel = payRecordService.payAccomplish(parameters, SystemKeyWord.PAY_WAY_WECHATPAY);
				
				if (resultModel.isSuccess()) {
					return notifyReturnXML("SUCCESS", "OK");
				}
				return notifyReturnXML("FAIL", resultModel.getMsg());
			} else {
				return notifyReturnXML("FAIL ", "receive error msg");
			}
		} catch (Exception e) {
			LOGGER.error("微信支付回调异常" , e);
			return notifyReturnXML("FAIL ", "system error");
		}
	}

	/**
	 * 支付宝异步通知
	 * @author chenb
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/aliNotify",method=RequestMethod.POST)
	@ResponseBody
	public String aliNotify(HttpServletRequest request, 
			HttpServletResponse response) {
		try {
			LOGGER.info("---------支付宝回调aliNotify----------时间:"+new Date());
			Map<String, String> paramsMap = paramsMap(request);
			// 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			boolean flag = true; // 校验公钥正确性防止意外
			try {
				flag = AlipaySignature.rsaCheckV1(paramsMap,
						Config.getValueByKey("AliPay.publicKey"), "utf-8",
						Config.getValueByKey("AliPay.signType"));
			} catch (AlipayApiException e) {
				LOGGER.error("验证支付宝签名异常" , e);
				return "failure";
			}
			String tradeStatus = paramsMap.get("trade_status");
			if (flag && ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus))) {
				LOGGER.info("------------支付宝回调校验成功------------时间:"+new Date());
				try {
					//保存支付记录
					payRecordService.handlePaySucces(paramsMap, SystemKeyWord.PAY_WAY_ALIPAY);
				} catch (Exception e) {
					LOGGER.error("保存支付记录异常" , e);
                    LOGGER.error(JSONObject.toJSONString(paramsMap));
				}
				//更新订单状态
				ResultModel result = payRecordService.payAccomplish(paramsMap, SystemKeyWord.PAY_WAY_ALIPAY);
				if (result.isSuccess()) {
					return "success";
				}else {
					return "failure";
				}
			} else {
				return "success";
			}
		} catch (Exception e) {
			LOGGER.error("支付宝支付回调异常" ,e);
			return "failure";
		}
	}
	
	/**
	 * 读取请求数据
	 * @param request
	 * @return
	 */
	private String readData(HttpServletRequest request){
		BufferedReader reader = null ;
		try {
			reader = request.getReader();
			StringBuilder data = new StringBuilder();
			String line ;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
			return data.toString();
		} catch (Exception e) {
			LOGGER.error("读取请求数据异常" , e);
			return null ;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/**
	 * 微信支付回调接口返回参数
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	private String notifyReturnXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

}
