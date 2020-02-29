package com.yinafjz.cleaning.framework.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * @Title: RefundUtil.java
 * @Package com.yinafjz.cleaning.framework.utils
 * @Description: 支付宝、微信退款工具类
 * @author Huanghai
 * @date 2019-1-11 上午10:02:01
 * @version V1.0
 */
public class RefundUtil {

	private static final Logger LOGGER = Logger.getLogger(RefundUtil.class);

	// 公众账号ID appid 是 String(32) wx8888888888888888
	// 微信分配的公众账号ID（企业号corpid即为此appId）
	// 商户号 mch_id 是 String(32) 1900000109 微信支付分配的商户号
	// 随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
	// 随机字符串，不长于32位。推荐随机数生成算法
	// 签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法
	// 签名类型 sign_type 否 String(32) HMAC-SHA256 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	// 微信订单号 transaction_id 二选一 String(32) 1217752501201407033233368018
	// 微信生成的订单号，在支付通知中有返回
	// 商户订单号 out_trade_no String(32) 1217752501201407033233368018
	// 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
	// 商户退款单号 out_refund_no 是 String(64) 1217752501201407033233368018
	// 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
	// 订单金额 total_fee 是 Int 100 订单总金额，单位为分，只能为整数，详见支付金额
	// 退款金额 refund_fee 是 Int 100 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
	// 退款货币种类 refund_fee_type 否 String(8) CNY 退款货币类型，需与支付一致，或者不填。符合ISO
	// 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	// 退款原因 refund_desc 否 String(80) 商品已售完
	// 若商户传入，会在下发给用户的退款消息中体现退款原因
	// 注意：若订单退款金额≤1元，且属于部分退款，则不会在退款消息中体现退款原因
	// 退款资金来源 refund_account 否 String(30) REFUND_SOURCE_RECHARGE_FUNDS
	// 仅针对老资金流商户使用
	// REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
	// REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
	// 退款结果通知url notify_url 否 String(256) https://weixin.qq.com/notify/
	// 异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
	// 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。

	// 微信退款
	public static boolean refundWechat(Map<String, String> map) {
		try {
			SortedMap<String, String> params = new TreeMap<String, String>();
			String appId = Config.getValueByKey("wechat.appid.app");
			String mchId = Config.getValueByKey("wechat.pay.app.mchid");
			String nonceStr = WechatSignUtil.getNonceStr();
			String num = CommonUtils.getRandomNum();
			params.put("appid", appId);
			params.put("mch_id", mchId);
			params.put("nonce_str", nonceStr);
			params.put("out_trade_no", map.get("orderNum"));
			params.put("out_refund_no", num);
			params.put("total_fee", map.get("totalFee"));
			params.put("refund_fee", map.get("refundFee"));
			params.put("refund_desc", map.get("refundDesc"));
			params.put("sign", WechatSignUtil.sign(params,
					"chenyanyinaf123456fujianyinafuzh"));// 签名

			String path = RefundUtil.class.getResource("/").toString()
					+ "config/apiclient_cert.p12";
			LOGGER.info("证书路径：" + path.replace("file:/", ""));
			// System.out.println(path.replace("file:/", ""));
			// 证书路径 当 useCert 为 true 时 必须传入（微信支付部分接口需要证书）
			String result = requestOnce(
					"https://api.mch.weixin.qq.com/secapi/pay/refund", mchId,
					XmlUtil.parseToXmlStr(params), true,
					path.replace("file:", ""));
			System.out.println(result);
			Map<String, String> resultMap = XmlUtil.parseToMap(result);
			Set<String> keySet = resultMap.keySet();
			for (String key : keySet) {
				LOGGER.info(key + ":" + resultMap.get(key));
				System.out.println(key + ":" + resultMap.get(key));
			}

			if ("SUCCESS".equals(resultMap.get("result_code"))
					&& "SUCCESS".equals(resultMap.get("return_code"))) {
				LOGGER.info("订单号" + map.get("orderNum") + "：退款成功");
				LOGGER.info("退款金额：" + resultMap.get("refund_fee"));
				return true;
			} else {
				LOGGER.info("订单号" + map.get("orderNum") + "：退款失败");
				return false;
			}
		} catch (DocumentException e) {
			LOGGER.info("退款出错了");
			LOGGER.debug(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.info("退款出错了");
			LOGGER.debug(e.toString());
			e.printStackTrace();
		}
		return false;
	}

	// 支付宝退款查询
	public static boolean refundQuery(Map<String, String> map) {
		try {
			String num = CommonUtils.getRandomNum();
			String privateKey = Config.getValueByKey("AliPay.privateKey");
			String publicKey = Config.getValueByKey("AliPay.publicKey");
			AlipayClient alipayClient = new DefaultAlipayClient(
					Config.getValueByKey("AliPay.payURL"),
					Config.getValueByKey("AliPay.appId"), privateKey, "json",
					"utf-8", publicKey, Config.getValueByKey("AliPay.signType"));
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
			AlipayTradeRefundModel model = new AlipayTradeRefundModel();
			model.setOutTradeNo(map.get("orderNum"));
			// model.setTradeNo("4200000255201901107124022696");
			model.setOutRequestNo(num);

			request.setBizModel(model);
			AlipayTradeFastpayRefundQueryResponse response = alipayClient
					.execute(request);
			LOGGER.info("订单号" + map.get("orderNum") + "状态："
					+ response.getSubMsg());
			System.out.println(response.getSubMsg());
			if (response.isSuccess()) {
				System.out.println("退款查询调用成功");
			} else {
				System.out.println("退款查询调用失败");
			}
			return response.isSuccess();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 支付宝退款
	public static boolean refund(Map<String, String> map) {
		try {
			String num = CommonUtils.getRandomNum();
			String privateKey = Config.getValueByKey("AliPay.privateKey");
			String publicKey = Config.getValueByKey("AliPay.publicKey");
			AlipayClient alipayClient = new DefaultAlipayClient(
					Config.getValueByKey("AliPay.payURL"),
					Config.getValueByKey("AliPay.appId"), privateKey, "json",
					"utf-8", publicKey, Config.getValueByKey("AliPay.signType"));
			// 支付宝api文档: https://docs.open.alipay.com/api_1/alipay.trade.refund/
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			AlipayTradeRefundModel model = new AlipayTradeRefundModel();
			model.setOutTradeNo(map.get("orderNum"));
			// model.setTradeNo("");
			model.setOutRequestNo(num);
			// 退款金额
			model.setRefundAmount(map.get("refundAmount"));
			// 退款币种
			// model.setRefundCurrency("USD");
			model.setRefundReason(map.get("refundReason"));
			// 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
			// model.setOutRequestNo("");
			// List<GoodsDetail> list = new ArrayList<GoodsDetail>();
			// GoodsDetail detail = new GoodsDetail();
			// 商品编号
			// detail.setAlipayGoodsId("1901101001195371");
			// detail.setGoodsName("清洗洗衣机");
			// 商品数量
			// detail.setQuantity(1L);
			// detail.setPrice("0.02");
			// dodel.setGoodsDetail(list);
			request.setBizModel(model);
			// request.setBizContent("{" +
			// "\"out_trade_no\":\"20150320010101001\"," +
			// "\"trade_no\":\"2014112611001004680073956707\"," +
			// "\"refund_amount\":200.12," +
			// "\"refund_currency\":\"USD\"," +
			// "\"refund_reason\":\"正常退款\"," +
			// //"\"out_request_no\":\"HZ01RF001\"," +
			// "\"operator_id\":\"OP001\"," +
			// "\"store_id\":\"NJ_S_001\"," +
			// "\"terminal_id\":\"NJ_T_001\"," +
			// "      \"goods_detail\":[{" +
			// "        \"goods_id\":\"apple-01\"," +
			// "\"alipay_goods_id\":\"20010001\"," +
			// "\"goods_name\":\"ipad\"," +
			// "\"quantity\":1," +
			// "\"price\":2000," +
			// "\"goods_category\":\"34543238\"," +
			// "\"categories_tree\":\"124868003|126232002|126252004\"," +
			// "\"body\":\"特价手机\"," +
			// "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
			// "        }]," +
			// "      \"refund_royalty_parameters\":[{" +
			// "        \"royalty_type\":\"transfer\"," +
			// "\"trans_out\":\"2088101126765726\"," +
			// "\"trans_out_type\":\"userId\"," +
			// "\"trans_in_type\":\"userId\"," +
			// "\"trans_in\":\"2088101126708402\"," +
			// "\"amount\":0.1," +
			// "\"amount_percentage\":100," +
			// "\"desc\":\"分账给2088101126708402\"" +
			// "        }]," +
			// "\"org_pid\":\"2088101117952222\"" +
			// "  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			LOGGER.info("买家id：" + response.getBuyerUserId());
			LOGGER.info("订单号" + map.get("orderNum") + "状态："
					+ response.getSubMsg());
			System.out.println(response.getBuyerUserId());
			if (response.isSuccess()) {
				LOGGER.info("退款成功");
				System.out.println("调用成功");
			} else {
				LOGGER.info("退款失败");
				System.out.println("调用失败");
			}
			return response.isSuccess();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 请求，只请求一次，不做重试
	 * 
	 * @param data
	 *            请求数据
	 * @param useCert
	 *            是否使用证书，针对退款、撤销等操作
	 * @return
	 * @throws Exception
	 */
	public static String requestOnce(final String url, String mchid,
			String data, boolean useCert, String certFilePath) throws Exception {
		BasicHttpClientConnectionManager connManager;
		String wechatMchid = mchid;// 商户号
		if (useCert) {
			// 证书
			char[] password = wechatMchid.toCharArray();
			InputStream certStream = new FileInputStream(certFilePath);
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(certStream, password);

			// 实例化密钥库 & 初始化密钥工厂
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, password);

			// 创建 SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
					sslContext, new String[] { "TLSv1" }, null,
					new DefaultHostnameVerifier());

			connManager = new BasicHttpClientConnectionManager(RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http",
							PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslConnectionSocketFactory).build(),
					null, null, null);
		} else {
			connManager = new BasicHttpClientConnectionManager(RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http",
							PlainConnectionSocketFactory.getSocketFactory())
					.register("https",
							SSLConnectionSocketFactory.getSocketFactory())
					.build(), null, null, null);
		}
		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(connManager).build();
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpPost.setConfig(requestConfig);

		StringEntity postEntity = new StringEntity(data, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + wechatMchid); // TODO:
																				// 很重要，用来检测
																				// sdk
																				// 的使用情况
		httpPost.setEntity(postEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		return EntityUtils.toString(httpEntity, "UTF-8");
	}
}
