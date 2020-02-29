/**   
 * @Title: OrderDao.java  
 * @Package com.yinafjz.cleaning.user.dao  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author Huanghai 
 * @date 2018-12-4 下午4:49:33  
 * @version V1.0   
 */
package com.yinafjz.cleaning.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.order.model.OrderModel;
import com.yinafjz.cleaning.order.model.OrderServiceModel;
import com.yinafjz.cleaning.user.model.AuntModel;
import com.yinafjz.cleaning.user.model.EvaluationModel;

/**
 * @ClassName: OrderDao
 * @Description: 订单dao接口
 * @author Huanghai
 * @date 2018-12-4 下午4:49:33
 * 
 */
public interface OrderDao {

	/**
	 * @Title: addOrder
	 * @Description: 添加订单
	 * @param model
	 * @return
	 * @throws
	 */
	public int addOrder(OrderModel model) throws YinafjzException;

	/**
	 * @Title: addOrderService
	 * @Description: 添加订单中的服务
	 * @param model
	 * @return
	 * @throws
	 */
	public int addOrderService(OrderServiceModel model) throws YinafjzException;

	/**
	 * @Title: findOrderByState
	 * @Description: 根据订单状态获取当前用户订单
	 * @param userId
	 * @param state
	 *            -1: 全部订单
	 * @return
	 * @throws
	 */
	public List<OrderModel> findOrderByState(@Param("userId") int userId,
			@Param("state") int state) throws YinafjzException;

	/**
	 * @Title: findOrderById
	 * @Description: 根据id获取订单详情
	 * @param orderId
	 * @param orderId
	 * @return
	 * @throws
	 */
	public OrderModel findOrderById(Integer orderId) throws YinafjzException;

	/**
	 * @Title: findOrderByNum
	 * @Description: 根据订单号获取订单
	 * @param orderNum
	 * @return
	 * @throws
	 */
	public OrderModel findOrderByNum(String orderNum) throws YinafjzException;

	/**
	 * @Title: editState
	 * @Description: 编辑订单状态
	 * @param orderId
	 * @param state
	 * @return
	 * @throws
	 */
	public int editState(@Param("orderId") int orderId,
			@Param("state") int state) throws YinafjzException;

	/**
	 * @Title: cancelOrder
	 * @Description: 订单取消
	 * @param orderId
	 * @param cancelCause
	 * @return
	 * @throws
	 */
	public int cancelOrder(@Param("orderId") int orderId,
			@Param("cancelCause") String cancelCause) throws YinafjzException;

	/**
	 * @Title: findAuntById
	 * @Description: 根据id查找阿姨
	 * @param id
	 * @return
	 * @throws
	 */
	public AuntModel findAuntById(int id);

	/**
	 * @Title: findOrderReceNum
	 * @Description: 根据服务开始时间查询已接单数量
	 * @param beginTime
	 * @return
	 * @throws
	 */
	public int findOrderReceNum(String beginTime);

	/**
	 * @Title: findAuntCount
	 * @Description: 根据小区id查找工作中的阿姨数量
	 * @param id
	 * @return
	 * @throws
	 */
	public int findAuntCount(int id);

	/**
	 * @Title: findComplete
	 * @Description: 查询已完成未评价的订单
	 * @param userId
	 * @return
	 * @throws
	 */
	// public List<Map<String, String>> findComplete(int userId);

	/**
	 * @Title: addEvaluation
	 * @Description: 添加评价
	 * @param model
	 * @return
	 * @throws
	 */
	public int addEvaluation(EvaluationModel model) throws YinafjzException;

	/**
	 * @Title: editEvaluateTime
	 * @Description: 编辑订单评价时间
	 * @param orderId
	 * @return
	 * @throws
	 */
	public int editEvaluateTime(int orderId) throws YinafjzException;

	/**
	 * 根据商品查询评价
	 * @param name
	 * @param val
	 * @param eval
	 * @return
	 * @throws YinafjzException
	 */
	List<EvaluationModel> findByCondition(@Param("name") String name,
										  @Param("val") String val,
										  @Param("eval") String eval) throws YinafjzException;
}
