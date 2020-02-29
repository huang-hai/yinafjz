package com.yinafjz.cleaning.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.user.model.CategoryModel;
import com.yinafjz.cleaning.user.model.DetailModel;
import com.yinafjz.cleaning.user.model.ItemModel;

/**
 * @Title: CategoryDao.java
 * @Package com.yinafjz.cleaning.user.dao
 * @Description: 服务分类dao
 * @author Huanghai
 * @date 2018-12-4 上午11:36:39
 * @version V1.0
 */
public interface CategoryDao {

	/**
	 * @Title: findCates
	 * @Description: 获取服务分类列表
	 * @return
	 * @throws
	 */
	public List<CategoryModel> findCates() throws YinafjzException;

	/**
	 * @Title: findIndexCates
	 * @Description: 获取首页显示的分类列表
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public List<CategoryModel> findIndexCates() throws YinafjzException;

	/**
	 * @Title: findBanners
	 * @Description: 获取首页广告
	 * @return
	 * @throws
	 */
	public List<Map<String, String>> findBanners();

	// /**
	// * @Title: findItemsByCatId
	// * @Description: 根据服务分类id获取服务项列表
	// * @param catId
	// * @return
	// * @throws
	// */
	// public List<ItemModel> findItemsByCatId(int catId) throws
	// YinafjzException;

	/**
	 * @Title: updateItemSales
	 * @Description: 更新商品销量
	 * @param id
	 * @param num
	 * @return
	 * @throws
	 */
	public int updateItemSales(@Param("id") int id, @Param("num") int num)
			throws YinafjzException;

	/**
	 * @Title: findItemById
	 * @Description: 根据id获取服务项(包含服务详情)
	 * @param id
	 * @return
	 * @throws
	 */
	public ItemModel findItemById(int id) throws YinafjzException;

	/**
	 * @Title: findDetailByItemId
	 * @Description: 根据服务项获取服务详情
	 * @param itemId
	 * @return
	 * @throws
	 */
	public ItemModel findDetailByItemId(int itemId) throws YinafjzException;

	/**
	 * @Title: findDetailById
	 * @Description: 根据详情id获取服务详情
	 * @param id
	 * @return
	 * @throws
	 */
	public DetailModel findDetailById(int id) throws YinafjzException;

	/**
	 * @Title: findIndexType
	 * @Description: 获取首页推荐、套餐
	 * @param type
	 *            1:推荐 2:套餐
	 * @return
	 * @throws
	 */
	public List<ItemModel> findIndexType(int type);

	/**
	 * 根据属性=值查询
	 * @param name 属性名
	 * @param val 属性值
	 * @return
	 * @throws YinafjzException
	 */
	List<ItemModel> findByCondition(@Param("n") String name,@Param("v") String val) throws YinafjzException;

	/**
	 * 根据商品ID查询
	 * @param goodsId
	 * @return
	 * @throws YinafjzException
	 */
	List<DetailModel> findAttrByGoods(Integer goodsId) throws YinafjzException;
}
