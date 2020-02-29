/**   
 * @Title: CategoryServiceImpl.java  
 * @Package com.yinafjz.cleaning.user.service  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author Huanghai 
 * @date 2018-12-4 上午11:50:04  
 * @version V1.0   
 */
package com.yinafjz.cleaning.user.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yinafjz.cleaning.order.dao.OrderDao;
import com.yinafjz.cleaning.user.dao.ServiceUserCollectModelMapper;
import com.yinafjz.cleaning.user.model.EvaluationModel;
import com.yinafjz.cleaning.user.model.ServiceUserCollectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.user.dao.CategoryDao;
import com.yinafjz.cleaning.user.model.CategoryModel;
import com.yinafjz.cleaning.user.model.ItemModel;

/**
 * @ClassName: CategoryServiceImpl
 * @Description: 服务分类service实现
 * @author Huanghai
 * @date 2018-12-4 上午11:50:04
 * 
 */
@Transactional
@Service("cateService")
public class CategoryServiceImpl implements CategoryService {

	@Qualifier("categoryDao")
	@Autowired
	private CategoryDao cateDao;

	@Qualifier("orderDao")
	@Autowired
	private OrderDao orderDao;

	@Qualifier("serviceUserCollectModelMapper")
	@Autowired
	private ServiceUserCollectModelMapper serviceUserCollectModelMapper;

	@Override
	public ResultModel findCates() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<CategoryModel> cates = cateDao.findCates();
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(cates);
		return res;
	}

	// @Override
	// public ResultModel findItemsByCatId(int catId) throws YinafjzException {
	// ResultModel res = new ResultModel();
	// if(catId <= 0){
	// throw new
	// YinafjzException(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
	// Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
	// }
	// List<ItemModel> items = cateDao.findItemsByCatId(catId);
	// res.setMsg("获取成功");
	// res.setObj(items);
	// return res;
	// }

	@Override
	public ResultModel findDetailByItemId(int itemId) throws YinafjzException {
		ResultModel res = new ResultModel();
		ItemModel item = cateDao.findDetailByItemId(itemId);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(item);
		return res;
	}

	@Override
	public ResultModel findIndexType(int type) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (type != 1 && type != 2) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			return res;
		}

		List<ItemModel> items = cateDao.findIndexType(type);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(items);
		return res;
	}

	@Override
	public ResultModel findByHot() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<ItemModel> items = cateDao.findByCondition("is_hot","1");
		res.setMsg("查询成功");
		res.setObj(items);
		return res;
	}

	@Override
	public ResultModel findByCxiao() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<ItemModel> items = cateDao.findByCondition("is_cxiao","1");
		res.setMsg("查询成功");
		res.setObj(items);
		return res;
	}

	@Override
	public ResultModel findEvalByGoods(String goodsId,Integer type) throws YinafjzException {
		ResultModel res = new ResultModel();
		String eval = getEvalString(type);
		List<EvaluationModel> evals = orderDao.findByCondition("goods_id",goodsId,eval);
		res.setMsg("查询成功");
		res.setObj(evals);
		return res;
	}

	//获取好差中评星数
	private String getEvalString(Integer type) {
		List<String> list = Arrays.asList("1,2,3,4,5","5","4,3","2,1");
		String eval = "";
		if(-1 == type){
			eval = list.get(0);
		} else if(0 == type){
			eval = list.get(3);
		} else if(1 == type){
			eval = list.get(1);
		} else {
			eval = list.get(2);
		}
		return eval;
	}

	@Override
	public ResultModel findEvalByAunt(String auntId,Integer type) throws YinafjzException {
		ResultModel res = new ResultModel();
		String eval = getEvalString(type);
		List<EvaluationModel> evals = orderDao.findByCondition("aunt_id",auntId,eval);
		res.setMsg("查询成功");
		res.setObj(evals);
		return res;
	}

	@Override
	public ResultModel findEvalByNanny(String nannyId,Integer type) throws YinafjzException {
		ResultModel res = new ResultModel();
		String eval = getEvalString(type);
		List<EvaluationModel> evals = orderDao.findByCondition("nanny_id",nannyId,eval);
		res.setMsg("查询成功");
		res.setObj(evals);
		return res;
	}

	@Override
	public ResultModel addServiceCollect(ServiceUserCollectModel serviceUserCollectModel) throws YinafjzException {
		ResultModel res = new ResultModel();
		//默认有效
		serviceUserCollectModel.setCreateTime(new Date());
		List<ServiceUserCollectModel> collects = serviceUserCollectModelMapper.findByUserAndGoods(
				serviceUserCollectModel.getUserId(),
				serviceUserCollectModel.getGoodsId());
		if(collects.size()>0){
			res.setSuccess(true);
			res.setMsg("已收藏，不需要在收藏咯");
			res.setCode("200");
			return res;
		}
		int row = serviceUserCollectModelMapper.insert(serviceUserCollectModel);
		if(row>0) {
			res.setCode("200");
			res.setMsg("操作成功");
			res.setSuccess(true);
		} else {
			res.setSuccess(false);
			res.setMsg("操作失败，请联系管理员");
			res.setCode("500");
		}
		return res;
	}

	@Override
	public ResultModel delServiceCollect(Integer userId, Integer goodsId) throws YinafjzException {
		ResultModel res = new ResultModel();
		int row = serviceUserCollectModelMapper.delByCondition(userId, goodsId);
		if(row>0) {
			res.setCode("200");
			res.setMsg("操作成功");
			res.setSuccess(true);
		} else {
			res.setSuccess(false);
			res.setMsg("操作失败，请联系管理员");
			res.setCode("500");
		}
		return res;
	}

	@Override
	public ResultModel findCollectByUserAndGoods(Integer userId, Integer goodsId) throws YinafjzException {
		ResultModel res = new ResultModel();
		List<ServiceUserCollectModel> collects = serviceUserCollectModelMapper.findByUserAndGoods(userId,goodsId);
		res.setCode("200");
		res.setMsg("查询成功");
		res.setSuccess(true);
		if(collects.size()>0) res.setObj(true);
		else res.setObj(false);
		return res;
	}

	@Override
	public ResultModel findIndexCates() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<CategoryModel> cates = cateDao.findIndexCates();
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(cates);
		return res;
	}

	@Override
	public ResultModel findBanners() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<Map<String, String>> cates = cateDao.findBanners();
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(cates);
		return res;
	}

}
