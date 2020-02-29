package com.yinafjz.cleaning.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yinafjz.cleaning.framework.activemq.ProducerService;
import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.framework.utils.DateUtils;
import com.yinafjz.cleaning.order.dao.OrderDao;
import com.yinafjz.cleaning.order.dao.PayRecordDao;
import com.yinafjz.cleaning.order.model.OrderModel;
import com.yinafjz.cleaning.order.model.OrderServiceModel;
import com.yinafjz.cleaning.order.model.PayRecord;
import com.yinafjz.cleaning.user.dao.CategoryDao;

/**
 * 支付回调
 * 
 * @author linhy 2019-1-1 上午12:39:31
 */
@Service("payRecordService")
public class PayRecordServiceImpl implements PayRecordService {

	private static final Logger LOGGER = LogManager
			.getLogger(PayRecordServiceImpl.class);

	@Autowired
	@Qualifier("payRecordDao")
	private PayRecordDao payRecordDao;

	@Autowired
	@Qualifier("orderDao")
	private OrderDao orderDao;

	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;

	@Resource(name = "producerService")
	private ProducerService producer;

	/**
	 * 处理微信和支付宝支付成功
	 * 
	 * @author linhy 2019-1-1 上午12:44:25
	 * @param paramsMap
	 *            支付回调参数
	 * @param payType
	 *            支付类型 1微信 2支付宝
	 */
	@Override
	public void handlePaySucces(Map<String, String> paramsMap, Integer payType) {
		String payerId; // 支付用户id （微信的openid，支付宝的buyer_id）
		String outTradeNo; // 商户订单编号
		String transactionNo; // 交易号(微信的 transaction_id / 支付宝的 trade_no)
		String dataPackage; // 数据包
		BigDecimal payAmount; // 支付金额
		PayRecord payRecord = new PayRecord();
		if (payType == SystemKeyWord.PAY_WAY_WECHATPAY) {// 微信支付
			payerId = paramsMap.get("openid");// 微信用户的唯一标识
			outTradeNo = paramsMap.get("out_trade_no");// 订单编号
			transactionNo = paramsMap.get("transaction_id");// 微信支付流水编号
			payAmount = new BigDecimal(paramsMap.get("total_fee"))
					.divide(new BigDecimal(100));// 支付金额
			dataPackage = paramsMap.get("attach");
			payRecord.setPayType(SystemKeyWord.PAY_WAY_WECHATPAY);
		} else { // 支付宝支付
			payerId = paramsMap.get("buyer_id");// 支付宝用户唯一标识
			transactionNo = paramsMap.get("trade_no");// 支付宝支付流水编号
			outTradeNo = paramsMap.get("out_trade_no");// 订单编号
			payAmount = new BigDecimal(paramsMap.get("total_amount"));
			dataPackage = paramsMap.get("passback_params");
			payRecord.setPayType(SystemKeyWord.PAY_WAY_ALIPAY);
		}
		payRecord.setOutTradeNo(outTradeNo);
		// 查询 数据库是否已存在该支付记录
		PayRecord temp = payRecordDao.getPayRecord(payRecord);
		String date = DateUtils.formatDate(new Date());
		if (temp == null) {
			payRecord.setPayAmount(payAmount);
			payRecord.setCreateTime(date);
			payRecord.setDataPackage(dataPackage);
		} else {
			if (temp.getStatus() != SystemKeyWord.PAY_STATUS_UN_PAY) { // 如果已存在记录并且不是带支付状态
				// return new ResultModel("8000" , "支付记录状态异常" , false);
			}
			payRecord = temp;
		}
		payRecord.setStatus(SystemKeyWord.PAY_STATUS_PAY_SUCC);// 已支付
		payRecord.setPayerId(payerId);
		payRecord.setTransactionNo(transactionNo);
		payRecord.setPayTime(date);

		// 获取商品信息
		OrderModel orderModel;
		try {
			orderModel = orderDao.findOrderByNum(outTradeNo);
			payRecord.setOrderId(orderModel.getOrderId());
		} catch (YinafjzException e) {
			e.printStackTrace();
		}
		// 保存或更新支付记录
		if (payRecord.getId() == null) {
			payRecordDao.addPayRecord(payRecord);
		} else {
			payRecordDao.editPayRecord(payRecord);
		}
		// 添加MQ消息通知
		producer.sendMessage("发送了一个通知");
	}

	/**
	 * 更新订单状态信息
	 * 
	 * @param params
	 * @param payType
	 * @return
	 * @throws YinafjzException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultModel payAccomplish(Map<String, String> params, Integer payType)
			throws YinafjzException {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("" + JSONObject.toJSONString(params));
			}
			ResultModel result = new ResultModel();
			String transaction_no = "";
			String outTradeNo = "";
			if (SystemKeyWord.PAY_WAY_WECHATPAY == 1) {// 微信支付
				transaction_no = params.get("transaction_id");// 微信支付流水编号
				outTradeNo = params.get("out_trade_no"); // 支付宝商户订单号
			} else {
				transaction_no = params.get("trade_no"); // 支付宝支付流水编号
				outTradeNo = params.get("out_trade_no"); // 支付宝商户订单号
			}
			String date = DateUtils.formatDate(new Date());
			// 修改订单状态
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("payTime", date); // 支付时间
			paramsMap.put("payNum", transaction_no); // 支付流水号
			paramsMap.put("orderStatus", "2"); // 订单状态
			paramsMap.put("payStatus", "1"); // 支付状态
			paramsMap.put("orderNum", outTradeNo); // 商户订单号
			paramsMap.put("payType", payType + "");
			int j = payRecordDao.editOrderStatus(paramsMap);
			if (j == 0) {
				throw new Exception();
			}

			// 修改商品表的商品销量
			OrderModel orderModel = orderDao.findOrderByNum(outTradeNo);
			LOGGER.info(orderModel);
			List<OrderServiceModel> list = orderModel.getOrderServices();
			int num = 0;
			for (OrderServiceModel osm : list) {
				// 销量
				num += osm.getNum();
			}
			LOGGER.info(categoryDao);
			int row = categoryDao.updateItemSales(orderModel.getItemId(), num);
			LOGGER.info("更新了商品:" + orderModel.getItemName() + row + "条记录===销量+"
					+ num);
			result.setSuccess(true);
			result.setCode(Config.getStatusByKey("STATUS_SUCC_CODE"));
			result.setMsg(Config.getMsgByRandom("MSG_PAY_SUCCESS"));
			return result;
		} catch (Exception e) {
			LOGGER.error("订单处理异常", e);
			throw new YinafjzException(
					Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
					Config.getMsgByRandom("MSG_PAY_ERROR"), "");
		}
	}
}
