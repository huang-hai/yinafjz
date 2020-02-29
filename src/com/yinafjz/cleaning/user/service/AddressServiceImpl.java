package com.yinafjz.cleaning.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.user.dao.AddressDao;
import com.yinafjz.cleaning.user.model.AddressModel;

@Transactional
@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	@Qualifier("addressDao")
	private AddressDao addressDao;

	@Transactional(rollbackFor = YinafjzException.class)
	@Override
	public ResultModel addAddress(AddressModel model) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (null == model) {
			throw new YinafjzException(
					Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
					Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
		}
		int row = addressDao.addAddress(model);
		if (row > 0) {
			// 新增成功 返回id值
			res.setObj(model.getAuId());
			res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
		} else {
			res.setSuccess(false);
			res.setMsg(Config.getMsgByRandom("MSG_UNKNOWN_ERROR"));
			res.setCode(ResultModel.STATIC_CODE_FAILURE);
		}
		return res;
	}

	@Transactional(rollbackFor = YinafjzException.class)
	@Override
	public ResultModel editAddress(AddressModel model) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (model.getAuId() <= 0) {
			throw new YinafjzException(
					Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
					Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
		}
		int row = addressDao.editAddress(model);
		if (row > 0) {
			res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
		} else {
			res.setCode(ResultModel.STATIC_CODE_FAILURE);
			res.setSuccess(false);
			res.setMsg(Config.getMsgByRandom("MSG_UNKNOWN_ERROR"));
		}
		return res;
	}

	@Transactional(rollbackFor = YinafjzException.class)
	@Override
	public ResultModel delAddress(int id) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (id <= 0) {
			throw new YinafjzException(
					Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
					Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
		}

		int row = addressDao.delAddress(id);
		if (row > 0) {
			res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
		} else {
			res.setSuccess(false);
			res.setCode(ResultModel.STATIC_CODE_FAILURE);
			res.setMsg(Config.getMsgByRandom("MSG_UNKNOWN_ERROR"));
		}
		return res;
	}

	@Override
	public ResultModel findAddresssByUserId(int userId) throws YinafjzException {
		ResultModel res = new ResultModel();
		List<AddressModel> addresss = addressDao.findAddresssByUserId(userId);
		if (null == addresss) {
			res.setMsg("您还没有服务地址");
		} else {
			res.setMsg("获取成功");
		}
		res.setObj(addresss);
		return res;
	}

	@Transactional(rollbackFor = YinafjzException.class)
	@Override
	public ResultModel editDefaultAddr(int userId, int adId)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (userId <= 0 || adId <= 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			res.setSuccess(false);
			return res;
		}
		// 先设置该用户的所有地址为非默认
		int row = addressDao.editNotDefaultAddr(userId);
		if (row > 0) {
			// 在设置一个默认地址
			int num = addressDao.editDefaultAddr(adId);
			if (num > 0) {
				res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
			} else {
				throw new YinafjzException(
						Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"),
						Config.getMsgByRandom("MSG_ORDER_ERROR"), "");
			}
		}
		return res;
	}

	@Override
	public ResultModel findAddressById(int id) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (id <= 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setSuccess(false);
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			return res;
		}
		AddressModel address = addressDao.findAddressById(id);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(address);
		return res;
	}

	@Override
	public ResultModel findDefaultAddrByUserId(int userId)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (userId <= 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setSuccess(false);
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			return res;
		}
		AddressModel address = addressDao.findDefultAddrByUserId(userId);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(address);
		return res;
	}

	@Override
	public ResultModel findCommunitys() throws YinafjzException {
		ResultModel res = new ResultModel();
		List<Map<Integer, String>> list = addressDao.findCommunitys();
		if (null != list) {
			res.setObj(list);
			res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		} else {
			res.setCode(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"));
			res.setSuccess(false);
			res.setMsg(Config.getMsgByRandom("MSG_UNKNOWN_ERROR"));
		}
		return res;
	}
}
