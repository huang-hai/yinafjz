package com.yinafjz.cleaning.order.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.yinafjz.cleaning.framework.utils.*;
import com.yinafjz.cleaning.nanny.dao.NannyModelMapper;
import com.yinafjz.cleaning.nanny.model.CompModel;
import com.yinafjz.cleaning.user.dao.UserDao;
import com.yinafjz.cleaning.user.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.order.dao.OrderDao;
import com.yinafjz.cleaning.order.model.OrderModel;
import com.yinafjz.cleaning.order.model.OrderServiceModel;
import com.yinafjz.cleaning.user.dao.AddressDao;
import com.yinafjz.cleaning.user.dao.CategoryDao;
import com.yinafjz.cleaning.user.dao.MsgDao;

/**
 * @ClassName: OrderServiceImpl
 * @Description: 订单service实现类
 * @author Huanghai
 * @date 2018-12-4 下午5:38:00
 * 
 */
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LogManager
			.getLogger(OrderServiceImpl.class);

	@Qualifier("orderDao")
	@Autowired
	private OrderDao orderDao;

	@Qualifier("categoryDao")
	@Autowired
	private CategoryDao cateDao;

	@Qualifier("addressDao")
	@Autowired
	private AddressDao addressDao;
	@Qualifier("msgDao")
	@Autowired
	private MsgDao msgDao;

	@Qualifier("userDao")
	@Autowired
	private UserDao userDao;

	@Qualifier("nannyModelMapper")
	@Autowired
	private NannyModelMapper nannyModelMapper;

	// @Resource(name = "producerService")
	// private ProducerService producer;

	@Transactional(noRollbackFor = YinafjzException.class)
	@Override
	public ResultModel addOrder(OrderModel model, List<String> dtIds,
			List<String> names, List<String> prices, List<String> pics,
			List<String> nums) throws YinafjzException {
		ResultModel result = new ResultModel();
		if ((dtIds.size() != nums.size()) || dtIds.size() == 0) {
			result.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			result.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			result.setSuccess(false);
			return result;
		}
		// 订单状态默认： 1 待支付
		model.setState(OrderModel.ORDER_STATE_UNPAID);
		// 订单支付状态默认：false 未支付
		model.setPayStatus(false);
		model.setOrderNumber(CommonUtils.getRandomNum());
		ItemModel item = cateDao.findItemById(model.getItemId());
		Integer goodsType = item.getGoodsType();
		model.setOrderType(goodsType);
		//商品类型(0单次收费 1定金商品 2抢单)
		if(goodsType!=0){
			String serverTime = model.getServiceTime();
			if (serverTime.contains("~")) {
				String[] times = serverTime.split("~");
				model.setBeginTime(times[0]);
				model.setEndTime(times[1]);
			} else {
				model.setBeginTime(serverTime);
				try {
					SimpleDateFormat fmt = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = fmt.parse(serverTime);
					Calendar instance = Calendar.getInstance();
					instance.setTime(date);
					instance.set(Calendar.HOUR_OF_DAY, 23);
					instance.set(Calendar.MINUTE, 59);
					instance.set(Calendar.SECOND, 59);
					model.setEndTime(fmt.format(instance.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		//查询优惠券
		if(null != model.getCouponId()){
			CouponModel coupon = userDao.findCouponById(model.getCouponId());
			if(null != coupon){
				//优惠券是否能用 默认能用
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				boolean flag = true;
				//没有有效期时间的不能用
				if(StringUtils.isEmpty(coupon.getBeginTime())){
					flag = false;
				}
				if(StringUtils.isEmpty(coupon.getEndTime())){
					flag = false;
				}
				if(flag){
					try {
						long beginTime = sdf.parse(coupon.getBeginTime()).getTime();
						long endTime = sdf.parse(coupon.getEndTime()).getTime();
						long currentTime = new Date().getTime();
						if(currentTime > beginTime && currentTime < endTime){
							flag = true;
						} else {
							flag = false;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				//有没有达到满减条件
				if(model.getTotalMoney() < coupon.getPull()) flag = false;
				//状态是否有效
				if(model.getState()!=1) flag = false;
				//可以用
				if(flag){
					//优惠金额
					model.setDiscounts(coupon.getMoney());
					double actualPayment = model.getTotalMoney() - coupon.getMoney();
					model.setActualPayment(actualPayment);
					//更新优惠券状态(已使用)
					userDao.updateCouponState(coupon.getCouponId(),2);
				}

			}
		}
		int row = orderDao.addOrder(model);
		if (row > 0) {
			// 新增订单服务(订单属性)
			int size = dtIds.size();
			if (0 == size || size != names.size() || size != prices.size()
					|| size != pics.size() || size != nums.size()) {
				// 只要有一个数目不相同，则不生成订单
				throw new YinafjzException(
						Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
						Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
			}
			int osRow = 0;
			List<OrderServiceModel> osms = new ArrayList<OrderServiceModel>();
			for (int i = 0; i < dtIds.size(); i++) {
				OrderServiceModel osm = new OrderServiceModel();
				osm.setOrderId(model.getOrderId());
				osm.setDtId(Integer.parseInt(dtIds.get(i)));
				osm.setNum(Integer.parseInt(nums.get(i)));
				osm.setName(names.get(i));
				osm.setPic(pics.get(i));
				osm.setPrice(Double.parseDouble(prices.get(i)));
				osRow += orderDao.addOrderService(osm);

				DetailModel detailModel = cateDao.findDetailById(osm.getDtId());
				ItemModel itemModel = cateDao.findItemById(detailModel
						.getItemId());
				detailModel.setItem(itemModel);
				osm.setDetailModel(detailModel);
				osms.add(osm);
			}
			if (osRow > 0) {
				// 添加MQ消息通知 放到支付成功后发送
				// producer.sendMessage("发送了一个通知");
				model.setOrderServices(osms);
				result.setMsg(Config.getMsgByRandom("MSG_ORDER_SUCCESS"));
				result.setObj(model);
			} else {
				throw new YinafjzException(
						Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
						Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
			}
		} else {
			result.setCode(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"));
			result.setMsg(Config.getMsgByRandom("MSG_ORDER_ERROR"));
			result.setObj(0);
		}
		return result;
	}

	@Transactional(noRollbackFor = YinafjzException.class)
	@Override
	public ResultModel payAttestation(Map<String, Object> params)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (null == params) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			res.setSuccess(false);
			return res;
		} else {
			Object obj = params.get("orderId");
			OrderModel orderModel = orderDao.findOrderById(Integer.parseInt(obj
					+ ""));
			params.put("payAmount", orderModel.getActualPayment());
			params.put("orderNum", orderModel.getOrderNumber());
			params.put("goodsName", orderModel.getItemName());
			params.put("dataPackage", "");
			res = CommonUtils.returnPay(params);
		}
		return res;
	}

	// 此方法已不需要
	@Override
	public ResultModel getPayInfo(String orderNum, String payWay,
			String prepayId) throws YinafjzException {
		ResultModel res = new ResultModel();
		OrderModel orderModel = orderDao.findOrderByNum(orderNum);
		int num = Integer.parseInt(payWay);
		StringBuffer sb = new StringBuffer();
		if (num == SystemKeyWord.PAY_WAY_WECHATPAY) {
			// 微信支付
			if (StringUtils.isBlank(prepayId)) {
				res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
				res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
				return res;
			}
			String appId = Config.getValueByKey("wechat.appid.app");
			String partnerId = Config.getValueByKey("wechat.pay.app.mchid");
			String noncestr = WechatSignUtil.getNonceStr();
			String pack = "Sign=WXPay";
			String timestamp = (new Date().getTime() / 1000) + "";

			SortedMap<String, String> parameters = new TreeMap<String, String>();
			parameters.put("appid", appId);
			parameters.put("partnerid", partnerId);// 微信支付分配的商户号
			parameters.put("noncestr", noncestr);// 随机字符串，不长于32位
			parameters.put("package", pack);// 商户系统内部的订单号,32个字符内、可包含字母
			parameters.put("partnerid", prepayId);//
			parameters.put("timestamp", timestamp);//

			String sign = WechatSignUtil.sign(parameters,
					Config.getValueByKey("wechat.pay.app.signKey"));

			// sb.append("appid=" + appId + ";");
			// sb.append("partnerid=" + partnerId + ";");
			// sb.append("noncestr=" + noncestr + ";");
			// sb.append("package=" + pack + ";");
			// sb.append("prepayid=" + prepayId + ";");
			// sb.append("timestamp=" + timestamp + ";");
			// sb.append("sign=" + sign + ";");
			parameters.put("sign", sign);
			res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
			res.setObj(parameters);
		} else if (num == SystemKeyWord.PAY_WAY_ALIPAY) {
			// 支付宝支付
			try {

				String alipaySdk = URLEncoder.encode(
						"alipay-sdk-java-dynamicVersionNo", "UTF-8");
				String appId = URLEncoder.encode(
						Config.getValueByKey("AliPay.appId"), "UTF-8");
				StringBuffer bizContentSb = new StringBuffer();
				bizContentSb.append("{\"body\":\"" + orderModel.getItemName()
						+ "\",");
				bizContentSb.append("\"out_trade_no\":\""
						+ orderModel.getOrderNumber() + "\",");
				bizContentSb.append("\"subject\":\"" + orderModel.getItemName()
						+ "\",");
				bizContentSb
						.append("\"product_code\":\"QUICK_MSECURITY_PAY\",");
				bizContentSb.append("\"timeout_express\":\"30m\",");
				bizContentSb.append("\"total_amount\":\""
						+ orderModel.getActualPayment() + "\"}");
				String bizContent = URLEncoder.encode(bizContentSb.toString(),
						"UTF-8");
				String charset = URLEncoder.encode("utf-8", "UTF-8");
				String format = URLEncoder.encode("json", "UTF-8");
				String method = URLEncoder.encode("alipay.trade.app.pay",
						"UTF-8");
				String notifyUrl = URLEncoder.encode(
						Config.getValueByKey("AliPay.notify.url"), "UTF-8");
				String signType = URLEncoder.encode(
						Config.getValueByKey("AliPay.signType"), "UTF-8");
				String datatime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				String timestamp = URLEncoder.encode(datatime, "UTF-8");
				String version = URLEncoder.encode("1.0", "UTF-8");
				// sb.append("{");//
				// sb.append("obj = \"");//
				sb.append("alipay_sdk=alipay-sdk-java-dynamicVersionNo");//
				sb.append("&app_id=" + Config.getValueByKey("AliPay.appId"));// 2017031806275609
				// biz_content={"timeout_express":"30m","product_code":"QUICK_MSECURITY_PAY","total_amount":"0.01","subject":"1","body":"我是测试数据","out_trade_no":"IQJZSRC1YMQB5HU"}
				sb.append("&biz_content=" + bizContentSb);
				sb.append("&charset=utf-8");//
				sb.append("&format=json");//
				sb.append("&method=alipay.trade.app.pay");//
				sb.append("&notify_url="
						+ Config.getValueByKey("AliPay.notify.url"));// http%3A%2F%2Fwww.javatest.top%2Fmall%2Fpay%2FaliNotify
				// sb.append("&sign="+Config.getValueByKey("AliPay.publicKey"));//
				// pgUBBX5pGt52AvXaintmOkdT41QuEvk8ENg4qp%2Bvl%2F0xUm6uisNaGOSowJ3Mi4v0OWjcltkiGbQydhIiZvMMFlxHQw0k5DSisvrb63jVfWpU9CZlIJdRA9UQr%2F96qc7tDdx2YVLQGu9dMKwXfTZ7w4w1jCLJgbDLF8BjbX2wX983nV1IruHFiAxSOrFmz8IVq5491F6sgKH53grtBUEXk%2BvPwCbsUg3WU9bj5EWbwU2XhacSkZogKqu3%2BdNwliEfxSyxNi256pf%2FnWWVSw3owk9VN%2BKItKTKU9Xrch%2BPEAjuAtgPyCcLIjLc9%2BkCT8VGIrgzkgzbCT%2B2wYf0rmN5lw%3D%3D
				sb.append("&sign_type="
						+ Config.getValueByKey("AliPay.signType"));// RSA2
				sb.append("&timestamp=" + datatime);// 2018-05-10+15%3A52%3A13
				sb.append("&version=1.0");// ";
				// sb.append("}");//
				String sign = AlipaySignature.rsaSign(sb.toString(),
						Config.getValueByKey("AliPay.privateKey"), "UTF-8",
						Config.getValueByKey("AliPay.signType"));
				sign = URLEncoder.encode(sign, "UTF-8");
				StringBuffer objSb = new StringBuffer();
				objSb.append("{obj=\"");
				objSb.append("alipay_sdk=" + alipaySdk);
				objSb.append("&app_id=" + appId);
				objSb.append("&biz_content=" + bizContent);
				objSb.append("&charset=" + charset);
				objSb.append("&format=" + format);
				objSb.append("&method=" + method);
				objSb.append("&notify_url=" + notifyUrl);
				objSb.append("&sign_type=" + signType);
				objSb.append("&timestamp=" + timestamp);
				objSb.append("&sign=" + sign);
				objSb.append("&version=" + version);
				objSb.append("\";}");
				res.setObj(objSb.toString());
				System.out.println(objSb.toString());
				res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
			} catch (AlipayApiException e) {
				e.printStackTrace();
				res.setCode(Config.getStatusByKey("STATUS_SIGN_ERROR_CODE"));
				res.setMsg(Config.getStatusByKey("STATUS_SIGN_ERROR"));
				return res;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new YinafjzException(
						Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
						Config.getMsgByRandom("URL编码错误"), "");
			}
		} else {
			res.setSuccess(false);
			res.setCode("8000");
			res.setMsg(Config.getMsgByRandom("MSG_PAY_TYPE"));
			return res;
		}
		return res;
	}

	@Transactional(noRollbackFor = YinafjzException.class)
	@Override
	public ResultModel cancelOrder(int orderId, String cancelCause)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		OrderModel orderModel = orderDao.findOrderById(orderId);
		int orderStatus = orderModel.getState();
		if (orderStatus == OrderModel.ORDER_STATE_COMPLETED) {
			res.setMsg("订单已完成，不能取消");
			res.setSuccess(false);
			return res;
		}
		// 新增订单取消消息
		OrderModel model = orderDao.findOrderById(orderId);
		model.setCancelCause(cancelCause);
		int row = orderDao.cancelOrder(orderId, cancelCause);
		if (row > 0) {
			res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
			MsgModel msgModel = new MsgModel();
			StringBuffer sb = new StringBuffer();
			sb.append("您的「");
			sb.append(model.getItemName());
			sb.append("」订单已成功取消，具体退款进度请查看订单详情。");
			msgModel.setMsgTitle("您的订单已取消");
			msgModel.setMsgContent(sb.toString());
			msgModel.setUserId(model.getUserId());
			msgModel.setMsgType(0);
			msgModel.setReader(false);
			msgDao.addMsg(msgModel);

			String url = Config.getValueByKey("message.url");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId",model.getUserId());
			map.put("msgType",0);
			map.put("msgTitle","您的订单已取消");
			map.put("msgContent",sb.toString());
			try {
				String mesRes = HttpClientUtil.getInstance().doPost(url, map);
				LOGGER.info("订单取消发送通知结果："+mesRes);
			} catch (Exception e) {
				e.printStackTrace();
			}


			if (model.getAttemperStatus() == OrderModel.ATTEMPERSTATUS_SUCCESSFULLY
					|| model.getAttemperStatus() == OrderModel.ATTEMPERSTATUS_DISPATCH) {
				// 阿姨端消息
				msgModel.setMsgTitle("取消订单通知");
				msgModel.setUserId(model.getAuntId());
				msgModel.setMsgType(1);
				msgModel.setReader(false);
				StringBuffer sb2 = new StringBuffer();
				sb2.append("订单编号");
				sb2.append(model.getOrderNumber());
				sb2.append("的业主已取消订单，请重新接单。");
				msgModel.setMsgContent(sb2.toString());
				// 订单编号 此处用img来装
				msgModel.setMsgImg(model.getOrderNumber());
				msgDao.addAuntMsg(msgModel);
			}
			// 退款操作
			if (model.isPayStatus()) {
				LOGGER.info("进入退款操作");
				// 已支付
				int payType = model.getPayType();
				// 退款金额
				double money = 0;
				// 当前时间是否大于预约时间
				Date begin = null;
				try {
					begin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(model.getBeginTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date curr = new Date();
				if (curr.getTime() > begin.getTime()) {
					// 当前时间大于预约时间 全额退款
					money = model.getActualPayment();
				} else {
					// 是否在当天
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String beginDate = model.getBeginTime().substring(0, 10);
					String curDate = sdf.format(new Date());
					if (beginDate.equals(curDate)) {
						// 当天 扣除30%的订单金额
						money = model.getActualPayment() * 0.7;
					} else {
						// 不在当天 全额退款
						money = model.getActualPayment();
					}
				}

				Map<String, String> params = new HashMap<String, String>();
				if (payType == SystemKeyWord.PAY_WAY_WECHATPAY) {
					LOGGER.info("微信退款：" + String.format("%.0f", (money * 100))
							+ "分");
					// 微信支付
					params.put("orderNum", model.getOrderNumber());
					// 支付金额 单位元转为分
					params.put(
							"totalFee",
							String.format("%.0f",
									(model.getActualPayment() * 100)) + "");
					// 退款金额 需要重新计算 保留0位小数
					params.put("refundFee",
							String.format("%.0f", (money * 100)) + "");
					params.put("refundDesc", "取消订单退款");
					boolean result = RefundUtil.refundWechat(params);
					LOGGER.info("微信退款结果：" + result);
				} else if (payType == SystemKeyWord.PAY_WAY_ALIPAY) {
					LOGGER.info("支付宝退款：" + String.format("%.2f", money) + "元");
					// 支付宝支付
					params.put("orderNum", model.getOrderNumber());
					boolean result = RefundUtil.refundQuery(params);
					if (result) {
						// 退款金额 需要重新计算 保留1位小数
						params.put("refundAmount", String.format("%.2f", money)
								+ "");
						params.put("refundReason", "取消订单退款");
						boolean flag = RefundUtil.refund(params);
						LOGGER.info("支付宝退款结果：" + flag);
					}
				}
			}
		} else {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			res.setSuccess(false);
		}
		return res;
	}

	@Override
	public ResultModel findOrderByState(int userId, int state)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (userId <= 0) {
			res.setMsg(Config.getMsgByRandom("MSG_LOGIN_ERROR"));
			return res;
		}
		List<OrderModel> orders = orderDao.findOrderByState(userId, state);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(orders);
		return res;
	}

	@Override
	public ResultModel findOrderById(int orderId) throws YinafjzException {
		ResultModel res = new ResultModel();
		OrderModel model = orderDao.findOrderById(orderId);
		if (null != model) {
			if (model.getAttemperStatus() != OrderModel.ATTEMPERSTATUS_SUCCESSFULLY) {
				// 订单已被接单, 查询接单阿姨信息
				model.setAuntModel(orderDao.findAuntById(model.getAuntId()));
			}
			res.setObj(model);
			//判断订单是否定金商品
			if(1==model.getOrderType()){
				ItemModel itemModel = cateDao.findItemById(model.getItemId());
				CompModel compModel = nannyModelMapper.findById(itemModel.getCompId());
				model.setCompModel(compModel);
			}

			return res;
		}
		res.setCode(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"));
		res.setMsg(Config.getMsgByRandom("MSG_UNKNOWN_ERROR"));
		return res;
	}

	@Transactional(noRollbackFor = YinafjzException.class)
	@Override
	public ResultModel addEvalutation(EvaluationModel model)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		int row = orderDao.addEvaluation(model);
		if (row > 0) {
			// 评价成功, 修改订单中的评价时间
			orderDao.editEvaluateTime(model.getOrderId());
			res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
			res.setObj(model);
		}
		return res;
	}

	@Transactional(noRollbackFor = YinafjzException.class)
	@Override
	public ResultModel closeOrder(int orderId) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (orderId <= 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			return res;
		}
		// 更改订单状态为关闭
		int row = orderDao.editState(orderId, OrderModel.ORDER_STATE_CLOSE);
		if (row > 0) {
			res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
			res.setObj(orderId);
		} else {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
		}
		return res;
	}

	@Override
	public ResultModel findAuntById(int id) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (id <= 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			return res;
		}
		AuntModel aunt = orderDao.findAuntById(id);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(aunt);
		return res;
	}

	@Override
	public ResultModel isFull(int id, String time) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (id == 0 || StringUtils.isBlank(time)) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			res.setSuccess(false);
			return res;
		}
		int auntCount = orderDao.findAuntCount(id);

		// =====================================
		Map<String, String> map = new HashMap<String, String>();
		String[] times = time.split("~");
		for (String timeStr : times) {

			int orderReceNum = orderDao.findOrderReceNum(timeStr);
			if (orderReceNum >= auntCount) {
				// 接单数量已超过阿姨数量 约满
				map.put(timeStr, "1");
			} else {
				map.put(timeStr, "0");
			}
		}
		res.setObj(map);
		// =====================================

		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));

		return res;
	}

}
