/**   
 * @Title: CategoryService.java  
 * @Package com.yinafjz.cleaning.user.service  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author Huanghai 
 * @date 2018-12-4 上午11:48:44  
 * @version V1.0   
 */
package com.yinafjz.cleaning.user.service;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.user.model.ServiceUserCollectModel;

/**
 * @ClassName: CategoryService
 * @Description: 服务分类service
 * @author Huanghai
 * @date 2018-12-4 上午11:48:44
 * 
 */
public interface CategoryService {

	/**
	 * @Title: findBanners
	 * @Description: 获取首页广告
	 * @return
	 * @throws
	 */
	public ResultModel findBanners() throws YinafjzException;

	/**
	 * @Title: findCates
	 * @Description: 获取服务分类列表
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel findCates() throws YinafjzException;

	/**
	 * @Title: findIndexCates
	 * @Description: 获取首页显示分类列表
	 * @return
	 * @throws
	 */
	public ResultModel findIndexCates() throws YinafjzException;

	// /**
	// * @Title: findItemsByCatId
	// * @Description: 根据服务分类id获取服务项
	// * @param catId
	// * @return
	// * @throws YinafjzException
	// * @throws
	// */
	// public ResultModel findItemsByCatId(int catId) throws YinafjzException;

	/**
	 * @Title: findDetailByItemId
	 * @Description: 根据服务项id获取服务项
	 * @param itemId
	 * @return
	 * @throws YinafjzException
	 */
	public ResultModel findDetailByItemId(int itemId) throws YinafjzException;

	/**
	 * @Title: findIndexType
	 * @Description: 获取首页推荐、套餐
	 * @param type
	 *            1: 推荐 2: 套餐
	 * @return
	 * @throws
	 */
	public ResultModel findIndexType(int type) throws YinafjzException;

	/**
	 * 获取火热产品
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findByHot() throws YinafjzException;

	/**
	 * 获取畅销产品
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findByCxiao() throws YinafjzException;

	/**
	 * 根据商品ID查询
	 * @param goodsId
	 * @param type -1 全部 0差评 1好评 2中评
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findEvalByGoods(String goodsId,Integer type) throws YinafjzException;

	/**
	 * 根据阿姨查询
	 * @param auntId
	 * @param type -1 全部 0差评 1好评 2中评
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findEvalByAunt(String auntId,Integer type) throws YinafjzException;

	/**
	 * 根据保姆查询评价
	 * @param nannyId
	 * @param type -1 全部 0差评 1好评 2中评
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findEvalByNanny(String nannyId,Integer type) throws YinafjzException;

	/**
	 * 添加收藏
	 * @param serviceUserCollectModel
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel addServiceCollect(ServiceUserCollectModel serviceUserCollectModel) throws YinafjzException ;

	/**
	 * 取消收藏(删除)
	 * @param userId
	 * @param goodsId
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel delServiceCollect(Integer userId,Integer goodsId) throws YinafjzException ;

	/**
	 * 查询用户是否收藏商品
	 * @param userId
	 * @param goodsId
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findCollectByUserAndGoods(Integer userId, Integer goodsId) throws YinafjzException ;
}
