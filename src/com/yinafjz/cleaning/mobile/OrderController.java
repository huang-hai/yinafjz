package com.yinafjz.cleaning.mobile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.ResponseTool;
import com.yinafjz.cleaning.framework.utils.ValidateTool;
import com.yinafjz.cleaning.framework.web.AHandleModelAndView;
import com.yinafjz.cleaning.order.model.OrderModel;
import com.yinafjz.cleaning.order.service.OrderService;
import com.yinafjz.cleaning.user.model.EvaluationModel;

@Controller
@RequestMapping("/order")
public class OrderController extends AHandleModelAndView {

	private static final Logger LOGGER = Logger
			.getLogger(OrderController.class);

	@Qualifier("orderService")
	@Autowired
	private OrderService orderService;

	/**
	 * @Title: addOrder
	 * @Description: 添加订单
	 * @param model
	 * @return
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public void addOrder(HttpServletRequest request,
			HttpServletResponse response, OrderModel model)
			throws YinafjzException {
		try {
			List<String> dtIds = Arrays.asList(request
					.getParameterValues("dtIds"));
			List<String> names = Arrays.asList(request
					.getParameterValues("names"));
			List<String> prices = Arrays.asList(request
					.getParameterValues("prices"));
			List<String> pics = Arrays.asList(request
					.getParameterValues("pics"));
			List<String> nums = Arrays.asList(request
					.getParameterValues("nums"));
			ResultModel result = orderService.addOrder(model, dtIds, names,
					prices, pics, nums);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: payAttestation
	 * @Description: 支付验签
	 * @param request
	 * @param response
	 * @param orderId
	 * @param payWay
	 * @throws
	 */
	@RequestMapping(value = "/payAttestation", method = RequestMethod.POST)
	public void toPay(HttpServletRequest request, HttpServletResponse response,
			String orderId, String payWay) throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("订单标识", orderId);
			ValidateTool.checkParameterIsNull("支付方式", payWay);
			String ipAddr = getIpAddr(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId);
			params.put("payWay", payWay);
			params.put("userIp", ipAddr);
			ResultModel res = orderService.payAttestation(params);
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: getPayInfo
	 * @Description: 获取支付参数信息 (此接口已不需要)
	 * @param request
	 * @param response
	 * @param orderNum
	 *            订单号
	 * @param payWay
	 *            支付方式 1微信 2支付宝
	 * @param prepayId
	 *            微信预支付交易会话ID(支付宝不传值)
	 * @throws
	 */
	// @RequestMapping(value = "/getPayInfo", method = RequestMethod.POST)
	// public void getPayInfo(HttpServletRequest request,
	// HttpServletResponse response, String orderNum, String payWay,
	// String prepayId) throws YinafjzException {
	// try {
	// ValidateTool.checkParameterIsNull("订单标识", orderNum);
	// ValidateTool.checkParameterIsNull("支付方式", payWay);
	// ResultModel res = orderService.getPayInfo(orderNum, payWay,
	// prepayId);
	// ResponseTool.responseJson(res, request, response);
	// } catch (YinafjzException e) {
	// ResponseTool.exceptionReturn(e, request, response, LOGGER);
	// }
	// }

	/**
	 * @Title: findOrderByState
	 * @Description: 根据状态获取当前用户订单
	 * @param state
	 *            -1: 全部订单
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/findOrderByState", method = RequestMethod.GET)
	public void findOrderByState(HttpServletRequest request,
			HttpServletResponse response, String userId, String state)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ValidateTool.checkParameterIsNull("订单状态", state);
			ResultModel result = orderService.findOrderByState(
					Integer.parseInt(userId), Integer.parseInt(state));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}

	}

	/**
	 * @Title: findOrderById
	 * @Description: 根据id获取订单
	 * @param orderId
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/findOrderById", method = RequestMethod.GET)
	public void findOrderById(HttpServletRequest request,
			HttpServletResponse response, String orderId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("订单标识", orderId);
			ResultModel result = orderService.findOrderById(Integer
					.parseInt(orderId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}

	}

	/**
	 * @Title: cancelOrder
	 * @Description: 取消订单
	 * @param request
	 * @param response
	 * @param orderId
	 *            订单id
	 * @param cause
	 *            取消原因
	 * @throws
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public void cancelOrder(HttpServletRequest request,
			HttpServletResponse response, String orderId, String cause)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("订单标识", orderId);
			ValidateTool.checkParameterIsNull("取消原因", cause);
			ResultModel res = orderService.cancelOrder(
					Integer.parseInt(orderId), cause);
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: closeOrder
	 * @Description: 关闭订单
	 * @param request
	 * @param response
	 * @param orderId
	 * @throws
	 */
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	public void closeOrder(HttpServletRequest request,
			HttpServletResponse response, String orderId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("订单标识", orderId);
			ResultModel res = orderService
					.closeOrder(Integer.parseInt(orderId));
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findAuntById
	 * @Description: 根据id获取阿姨
	 * @param request
	 * @param response
	 * @param id
	 * @throws
	 */
	@RequestMapping(value = "/findAuntById", method = RequestMethod.GET)
	public void findAuntById(HttpServletRequest request,
			HttpServletResponse response, String id) throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", id);
			ResultModel res = orderService.findAuntById(Integer.parseInt(id));
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: isFull
	 * @Description: 查询所在小区指定的时间是否已经约满
	 * @param request
	 * @param response
	 * @param id
	 * @param time
	 * @throws
	 */
	@RequestMapping(value = "/isFull", method = RequestMethod.GET)
	public void isFull(HttpServletRequest request,
			HttpServletResponse response, String id, String time) {
		try {
			ValidateTool.checkParameterIsNull("用户标识", id);
			ResultModel res = orderService.isFull(Integer.parseInt(id), time);
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: addEvaluation
	 * @Description: 添加评价
	 * @param request
	 * @param response
	 * @param model
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/addEvaluation", method = RequestMethod.POST)
	public void addEvaluation(HttpServletRequest request,
			HttpServletResponse response, EvaluationModel model)
			throws YinafjzException {
		try {
			ResultModel res = orderService.addEvalutation(model);
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
