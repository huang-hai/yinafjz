package com.yinafjz.cleaning.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.framework.utils.CookiesUtil;
import com.yinafjz.cleaning.framework.utils.Cryto;
import com.yinafjz.cleaning.framework.utils.ResponseTool;
import com.yinafjz.cleaning.framework.utils.ValidateTool;
import com.yinafjz.cleaning.framework.web.AHandleModelAndView;
import com.yinafjz.cleaning.user.model.AddressModel;
import com.yinafjz.cleaning.user.service.AddressService;
import com.yinafjz.cleaning.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AHandleModelAndView {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("addressService")
	private AddressService addressService;

	/**
	 * APP免登陆方法
	 * 
	 * @param request
	 * @param response
	 * @param sessionId
	 * @throws YinafjzException
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/applogin", method = RequestMethod.GET)
	public void appLogin(HttpServletRequest request,
			HttpServletResponse response, String sessionId)
			throws YinafjzException, IOException, ServletException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", sessionId);
			ResultModel result = new ResultModel();
			String key = Cryto.byteArrayToHexString(SystemKeyWord.DES3_DES_KEY);
			LOGGER.info("sessionId=" + sessionId);
			String userId = sessionId;//Cryto.decrypt3DES(sessionId, key);
			if (ValidateTool.checkIsNull(userId)) {
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("userId", userId);
				result = userService.getUserByLogin(paramsMap);
				if (result.isSuccess()) {
					request.getSession().setAttribute(
							SystemKeyWord.USER_KEY_CACHE, result.getObj());
					CookiesUtil.setCookie(response, "sessionId", sessionId, -1); // 设置时间关闭浏览器失效
					Map<String, Object> user = (Map<String, Object>) result
							.getObj();
					LOGGER.info("登录成功：" + user.get("userId") + "-"
							+ user.get("userName"));
					LOGGER.info("用户信息："+result);
//				} else {
//					LOGGER.info("登录失败");
//				}
				}else {
					result.setCode("500");
					result.setMsg(Config.getMsgByRandom("MSG_LOGIN_ERROR"));
					result.setSuccess(false);
				}
			}
			ResponseTool.responseJson(result, request, response);
//			ResponseTool.sendRedirect("../index.html", request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: addAddress
	 * @Description: 新增或编辑服务地址
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrEditAddress", method = RequestMethod.POST)
	public void addAddress(HttpServletRequest request,
			HttpServletResponse response, AddressModel model)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("手机号", model.getPhone());
			ValidateTool.checkParameterIsNull("收件人", model.getReceiver());
			ValidateTool.checkParameterIsNull("详细地址", model.getAddress());
			ValidateTool.checkParameterIsNull("区域", model.getRegion());
			ResultModel result = null;
			if (null != model) {
				if (model.getAuId() == 0) {
					// 新增
					result = addressService.addAddress(model);
				} else {
					result = addressService.editAddress(model);
				}
			}
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: editDefaultAddr
	 * @Description: 设置用户默认地址
	 * @param request
	 * @param response
	 * @param userId
	 * @param adId
	 *            地址id
	 * @throws
	 */
	@RequestMapping(value = "/editDefaultAddr", method = RequestMethod.POST)
	public void editDefaultAddr(HttpServletRequest request,
			HttpServletResponse response, String userId, String adId) {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ValidateTool.checkParameterIsNull("地址", adId);
			ResultModel result = addressService.editDefaultAddr(
					Integer.parseInt(userId), Integer.parseInt(adId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: delAddress
	 * @Description: 删除服务地址
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAddress", method = RequestMethod.POST)
	public void delAddress(HttpServletRequest request,
			HttpServletResponse response, String id) throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("地址标识", id);
			ResultModel result = addressService
					.delAddress(Integer.parseInt(id));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findAddressById
	 * @Description: 根据id查找地址
	 * @param request
	 * @param response
	 * @param auId
	 *            地址id
	 * @throws
	 */
	@RequestMapping(value = "/findAddressById", method = RequestMethod.GET)
	public void findAddressById(HttpServletRequest request,
			HttpServletResponse response, String auId) {
		try {
			ValidateTool.checkParameterIsNull("地址标识", auId);
			ResultModel result = addressService.findAddressById(Integer
					.parseInt(auId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findAddress
	 * @Description: 获取当前成员的服务地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAddress", method = RequestMethod.GET)
	public void findAddress(HttpServletRequest request,
			HttpServletResponse response, String userId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ResultModel result = addressService.findAddresssByUserId(Integer
					.parseInt(userId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findCommunitys
	 * @Description: 查询所有小区
	 * @param request
	 * @param response
	 * @throws
	 */
	@RequestMapping(value = "/findCommunitys", method = RequestMethod.GET)
	public void findCommunitys(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ResultModel result = addressService.findCommunitys();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findDefaultAddrByUserId
	 * @Description: 获取用户的默认地址
	 * @param request
	 * @param response
	 * @param userId
	 * @throws
	 */
	@RequestMapping(value = "/findDefaultAddrByUserId", method = RequestMethod.GET)
	public void findDefaultAddrByUserId(HttpServletRequest request,
			HttpServletResponse response, String userId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ResultModel result = addressService.findDefaultAddrByUserId(Integer
					.parseInt(userId));
			LOGGER.info(result);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findMsgsByUserId
	 * @Description: 获取交易提醒消息
	 * @param request
	 * @param response
	 * @param userId
	 * @param type
	 *            0交易提醒 1平台通知
	 * @throws
	 */
	@RequestMapping(value = "/findMsgsByUserId", method = RequestMethod.GET)
	public void findMsgsByUserId(HttpServletRequest request,
			HttpServletResponse response, String userId, String type)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ValidateTool.checkParameterIsNull("消息类型", type);
			ResultModel result = userService.findMsgsByUserId(
					Integer.parseInt(userId), Integer.parseInt(type));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findNotReaderNumByUserId
	 * @Description: 获取用户未读消息数量
	 * @param request
	 * @param response
	 * @param userId
	 * @throws
	 */
	@RequestMapping(value = "/findNotReaderNumByUserId", method = RequestMethod.GET)
	public void findNotReaderNumByUserId(HttpServletRequest request,
			HttpServletResponse response, String userId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ResultModel result = userService.findNotReaderByUserId(Integer
					.parseInt(userId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: markRead
	 * @Description: 消息标为已读
	 * @param request
	 * @param response
	 * @param userId
	 * @throws
	 */
	@RequestMapping(value = "/markRead", method = RequestMethod.POST)
	public void markRead(HttpServletRequest request,
			HttpServletResponse response, String userId)
			throws YinafjzException {
		try {
			ValidateTool.checkParameterIsNull("用户标识", userId);
			ResultModel result = userService.markRead(Integer.parseInt(userId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: getUser
	 * @Description: 获取用户id
	 * @param request
	 * @param response
	 * @throws
	 */
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public void getUser(HttpServletRequest request, HttpServletResponse response) {
		ResultModel res = new ResultModel();
		Map<String, Object> map = (Map<String, Object>) request.getSession()
				.getAttribute(SystemKeyWord.USER_KEY_CACHE);
		if (null == map) {
			res.setCode(Config.getStatusByKey("STATUS_SIGN_ERROR_CODE"));
			res.setMsg(Config.getMsgByRandom("MSG_LOGIN_ERROR"));
		} else {
			res.setObj(map);
			res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		}
		ResponseTool.responseJson(res, request, response);
	}

	/**
	 * 获取优惠券
	 * @param request
	 * @param response
	 * @param userId
	 * @param goodsId
	 */
	@RequestMapping(value = "/findCoupon", method = RequestMethod.GET)
	public void findCoupon(HttpServletRequest request, HttpServletResponse response,
						   Integer userId,Integer goodsId) {
		try {
			ResultModel res = userService.findCouponByCondition(userId!=null?userId:0,goodsId!=null?goodsId:0);
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}
}
