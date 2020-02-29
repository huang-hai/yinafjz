package com.yinafjz.cleaning.order.service;

import java.util.List;
import java.util.Map;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.order.model.OrderModel;
import com.yinafjz.cleaning.user.model.EvaluationModel;

/**
 * @ClassName: OrderService
 * @Description: 订单service
 * @author Huanghai
 * @date 2018-12-4 下午5:36:49
 * 
 */
public interface OrderService {

	/**
	 * @Title: addOrder
	 * @Description: 添加订单
	 * @param model
	 * @param dtIds
	 * @param nums
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel addOrder(OrderModel model, List<String> dtIds,
			List<String> names, List<String> prices, List<String> pics,
			List<String> nums) throws YinafjzException;

	/**
	 * @Title: editPayState
	 * @Description: 编辑订单支付状态
	 * @param params
	 *            (orderId,payNum,payState,payWay)
	 * @return
	 * @throws
	 */
	public ResultModel payAttestation(Map<String, Object> params)
			throws YinafjzException;

	/**
	 * @Title: getPayInfo
	 * @Description: 获取支付参数信息 (此方法已不需要)
	 * @param orderNum
	 * @param payWay
	 * @param prepayId
	 * @return
	 * @throws
	 */
	public ResultModel getPayInfo(String orderNum, String payWay,
			String prepayId) throws YinafjzException;

	/**
	 * @Title: findOrderByState
	 * @Description: 根据订单状态获取当前用户的订单
	 * @param userId
	 * @param state
	 *            -1: 全部订单
	 * @return
	 * @throws
	 */
	public ResultModel findOrderByState(int userId, int state)
			throws YinafjzException;

	/**
	 * @Title: findOrderById
	 * @Description: 根据订单id获取订单详情
	 * @param orderId
	 * @return
	 * @throws
	 */
	public ResultModel findOrderById(int orderId) throws YinafjzException;

	/**
	 * @Title: cancelOrder
	 * @Description: 取消订单
	 * @param orderId
	 *            订单id
	 * @param cancelCause
	 *            取消原因
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel cancelOrder(int orderId, String cancelCause)
			throws YinafjzException;

	/**
	 * @Title: closeOrder
	 * @Description: 关闭订单
	 * @param orderId
	 * @return
	 * @throws
	 */
	public ResultModel closeOrder(int orderId) throws YinafjzException;

	/**
	 * @Title: findAuntById
	 * @Description: 根据订单id查找阿姨
	 * @param orderId
	 * @return
	 * @throws
	 */
	public ResultModel findAuntById(int id) throws YinafjzException;

	/**
	 * @Title: isFull
	 * @Description: 根据小区和时间查询是否已经约满
	 * @param id
	 * @param time
	 * @return
	 * @throws
	 */
	public ResultModel isFull(int id, String time) throws YinafjzException;

	/**
	 * @Title: addEvalutation
	 * @Description: 添加评价
	 * @param model
	 * @return
	 * @throws
	 */
	public ResultModel addEvalutation(EvaluationModel model)
			throws YinafjzException;
}
