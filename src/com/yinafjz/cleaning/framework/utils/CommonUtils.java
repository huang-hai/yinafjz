package com.yinafjz.cleaning.framework.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.model.ResultModel;

/**
 * 通用的工具类
 * 
 * @author admin
 */
public class CommonUtils {

	private static final Logger LOGGER = Logger.getLogger(CommonUtils.class);

	public CommonUtils() {
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
		if (CommonUtils.isEmpty(exp))
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
	 * 判断list是否有重复的值
	 * 
	 * @param list
	 * @param param
	 * @return
	 */
	public static boolean getResultBool(List<String> list, String param) {
		boolean result = false;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (!list.contains(param)) { // 查看新集合中是否有指定的元素，如果没有则加入
					list.add(param);
					result = true;
					break;
				}
			}
		} else {
			list.add(param);
			result = true;
		}
		return result;
	}

	/**
	 * 随机生成4位数字
	 * 
	 * @author linhy
	 * @date 2017-12-12
	 * @return
	 */
	public static int random() {
		int randint = (int) ((Math.random() * 9 + 1) * 1000);
		return randint;
	}

	/**
	 * 订单编号
	 * 
	 * @author linhy
	 * @return
	 */
	public static String getRandomNum() {
		// 生成规则 年月日时分秒12位数+4位随机数 例如：1804111500012564
		StringBuffer memberNum = new StringBuffer();
		String dateYeas = DateUtils.formatDate(new Date(), "yyMMddHHmmss");
		memberNum.append(dateYeas);
		int r = CommonUtils.random();
		;
		memberNum.append(r);
		return memberNum.toString();
	}

	/**
	 * 支付返回参数
	 * 
	 * @author linhy 2019-1-2 下午2:52:15
	 * @param map
	 * @return
	 */
	public static ResultModel returnPay(Map<String, Object> map) {
		try {
			String notifyUrl = "";
			Integer payWay = map.get("payWay") == null ? null : Integer
					.valueOf(map.get("payWay") + "");
			if (payWay == null
					|| !(payWay == SystemKeyWord.PAY_WAY_WECHATPAY || (payWay == SystemKeyWord.PAY_WAY_ALIPAY))) {
				return new ResultModel(false,
						Config.getMsgByRandom("MSG_PAY_TYPE"), "8000");
			}
			if (payWay == SystemKeyWord.PAY_WAY_WECHATPAY) {
				notifyUrl = Config.getValueByKey("wechat.pay.notify.url");
			} else {
				notifyUrl = Config.getValueByKey("AliPay.notify.url");
			}
			map.put("notifyUrl", notifyUrl);
			String url = Config.getValueByKey("pay.url");
			LOGGER.info("url:" + url);
			return HttpClientUtil.getInstance().doPostResult(url, map);
		} catch (Exception e) {
			LOGGER.error("发起支付失败", e);
			return new ResultModel(false, "发起支付失败", "8000");
		}
	}

}
