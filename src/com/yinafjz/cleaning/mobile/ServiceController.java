package com.yinafjz.cleaning.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yinafjz.cleaning.user.model.ServiceUserCollectModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.ResponseTool;
import com.yinafjz.cleaning.framework.utils.ValidateTool;
import com.yinafjz.cleaning.framework.web.AHandleModelAndView;
import com.yinafjz.cleaning.user.service.CategoryService;

import java.util.List;

/**
 * @ClassName: ServiceController
 * @Description: 服务控制层
 * @author Huanghai
 * @date 2018-12-4 上午11:30:02
 * 
 */
@Controller
@RequestMapping("/service")
public class ServiceController extends AHandleModelAndView {

	private static final Logger LOGGER = Logger
			.getLogger(ServiceController.class);

	@Qualifier("cateService")
	@Autowired
	private CategoryService cateService;

	/**
	 * @Title: findBanners
	 * @Description: 获取首页广告
	 * @param request
	 * @param response
	 * @throws YinafjzException
	 * @throws
	 */
	@RequestMapping(value = "/findBanners", method = RequestMethod.GET)
	public void findBanners(HttpServletRequest request,
			HttpServletResponse response) throws YinafjzException {
		try {
			ResultModel result = cateService.findBanners();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 查询火热产品
	 * @param request
	 * @param response
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/findHot", method = RequestMethod.GET)
	public void findHot(HttpServletRequest request,
			HttpServletResponse response) throws YinafjzException {
		try {
			ResultModel result = cateService.findByHot();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 查询畅销
	 * @param request
	 * @param response
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/findCxiao", method = RequestMethod.GET)
	public void findCxiao(HttpServletRequest request,
			HttpServletResponse response) throws YinafjzException {
		try {
			ResultModel result = cateService.findByCxiao();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findCates
	 * @Description: 获取服务分类列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCates", method = RequestMethod.GET)
	public void findCates(HttpServletRequest request,
			HttpServletResponse response) throws YinafjzException {
		try {
			ResultModel result = cateService.findCates();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findIndexCates
	 * @Description: 获取首页分类显示列表
	 * @param request
	 * @param response
	 * @throws YinafjzException
	 * @throws
	 */
	@RequestMapping(value = "/findIndexCates", method = RequestMethod.GET)
	public void findIndexCates(HttpServletRequest request,
			HttpServletResponse response) throws YinafjzException {
		try {
			ResultModel result = cateService.findIndexCates();
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	// /**
	// * @Title: findItemsByCatId
	// * @Description: 根据服务分类id获取服务项
	// * @param catId
	// * @return
	// * @throws Exception
	// */
	// @RequestMapping(value = "/findItemsByCatId", method = RequestMethod.GET)
	// public void findItemsByCatId(HttpServletRequest request,
	// HttpServletResponse response, Integer catId)
	// throws YinafjzException {
	// try {
	// ResultModel result = cateService.findItemsByCatId(catId);
	// ResponseTool.responseJson(result, request, response);
	// } catch (YinafjzException e) {
	// ResponseTool.exceptionReturn(e, request, response, LOGGER);
	// }
	// }

	/**
	 * @Title: findItemById
	 * @Description: 根据服务项id获取服务详情
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDetailByItemId", method = RequestMethod.GET)
	public void findDetailByItemId(HttpServletRequest request,
			HttpServletResponse response, String itemId) throws Exception {
		try {
			ValidateTool.checkParameterIsNull("商品标识", itemId);
			ResultModel result = cateService.findDetailByItemId(Integer
					.parseInt(itemId));
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 根据商品查询评价
	 * @param request
	 * @param response
	 * @param goodsId
	 * @param type -1全部 0差评 1好评 2中评
	 * @throws Exception
	 */
	@RequestMapping(value = "/findEvalsByGoods", method = RequestMethod.GET)
	public void findEvalsByGoods(HttpServletRequest request,
			HttpServletResponse response, String goodsId, Integer type) throws Exception {
		try {
			ValidateTool.checkParameterIsNull("商品标识", goodsId);
			if(null == type) type = -1;
			ResultModel result = cateService.findEvalByGoods(goodsId,type);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 根据阿姨查询评价
	 * @param request
	 * @param response
	 * @param auntId
	 * @param type -1全部 0差评 1好评 2中评
	 * @throws Exception
	 */
	@RequestMapping(value = "/findEvalsByAunt", method = RequestMethod.GET)
	public void findEvalsByAunt(HttpServletRequest request,
			HttpServletResponse response, String auntId, Integer type) throws Exception {
		try {
			ValidateTool.checkParameterIsNull("阿姨标识", auntId);
			if(null == type) type = -1;
			ResultModel result = cateService.findEvalByAunt(auntId,type);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 根据保姆查询评价
	 * @param request
	 * @param response
	 * @param nannyId
	 * @param type -1全部 0差评 1好评 2中评
	 * @throws Exception
	 */
	@RequestMapping(value = "/findEvalsByNanny", method = RequestMethod.GET)
	public void findEvalsByNanny(HttpServletRequest request,
			HttpServletResponse response, String nannyId,Integer type) throws Exception {
		try {
			ValidateTool.checkParameterIsNull("保姆标识", nannyId);
			if(null == type) type = -1;
			ResultModel result = cateService.findEvalByNanny(nannyId,type);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}



	/**
	 * @Title: findRecommendItems
	 * @Description: 获取推荐服务项
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findRecommends", method = RequestMethod.GET)
	public void findRecommends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ResultModel result = cateService.findIndexType(1);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * @Title: findCombos
	 * @Description: 获取精选套餐列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCombos", method = RequestMethod.GET)
	public void findCombos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ResultModel result = cateService.findIndexType(2);
			ResponseTool.responseJson(result, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 添加收藏
	 * @param request
	 * @param response
	 * @param model
	 * @param bindingResult
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/addServiceCollect", method = RequestMethod.POST)
	public void addServiceCollect(HttpServletRequest request,
								HttpServletResponse response,
								@Validated ServiceUserCollectModel model,
								BindingResult bindingResult) throws YinafjzException {
		try {
			ResultModel res = new ResultModel();
			if(bindingResult.hasErrors()){
				List<ObjectError> errors = bindingResult.getAllErrors();
				res.setSuccess(false);
				res.setCode("500");
				res.setMsg(errors.get(0).getDefaultMessage());
			} else {
				res = cateService.addServiceCollect(model);
			}
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 取消收藏
	 * @param request
	 * @param response
	 * @param userId
	 * @param goodsId
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/delServiceCollect", method = RequestMethod.POST)
	public void delServiceCollect(HttpServletRequest request,
								HttpServletResponse response,Integer userId,Integer goodsId) throws YinafjzException {
		try {
			ResultModel  res = new ResultModel();
			if(null == userId || null == goodsId){
				res.setSuccess(false);
				res.setCode("500");
				res.setMsg("用户ID或商品ID不能为空");
			} else {
				res = cateService.delServiceCollect(userId,goodsId);
			}
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

	/**
	 * 判断是否已收藏
	 * @param request
	 * @param response
	 * @param userId
	 * @param goodsId
	 * @throws YinafjzException
	 */
	@RequestMapping(value = "/isCollect", method = RequestMethod.GET)
	public void isCollect(HttpServletRequest request,
						  HttpServletResponse response,Integer userId,Integer goodsId) throws YinafjzException {
		try {
			ResultModel  res = new ResultModel();
			if(null == userId || null == goodsId){
				res.setSuccess(false);
				res.setCode("500");
				res.setMsg("用户ID或商品ID不能为空");
			} else {
				res = cateService.findCollectByUserAndGoods(userId, goodsId);
			}
			ResponseTool.responseJson(res, request, response);
		} catch (YinafjzException e) {
			ResponseTool.exceptionReturn(e, request, response, LOGGER);
		}
	}

}
