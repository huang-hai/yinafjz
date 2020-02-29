package com.yinafjz.cleaning.user.service;

import java.util.List;
import java.util.Map;

import com.alipay.api.domain.Coupon;
import com.yinafjz.cleaning.user.model.CouponModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.Config;
import com.yinafjz.cleaning.framework.utils.ReflectTool;
import com.yinafjz.cleaning.user.dao.MsgDao;
import com.yinafjz.cleaning.user.dao.UserDao;
import com.yinafjz.cleaning.user.model.MsgModel;
import com.yinafjz.cleaning.user.model.UserModel;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("msgDao")
	private MsgDao msgDao;

	/**
	 * APP免登陆方法
	 * 
	 * @param paramsMap
	 * @return
	 * @throws YinafjzException
	 */
	@Override
	public ResultModel getUserByLogin(Map<String, String> paramsMap)
			throws YinafjzException {
		ResultModel result = new ResultModel();
		UserModel user = userDao.getUserByLogin(paramsMap);
		if (user == null) {
			result.setCode(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"));
			result.setMsg(Config.getMsgByRandom("MSG_LOGIN_ERROR"));
			result.setSuccess(false);
			return result;
		}
		if (user.getUserState().intValue() == 0) {
			result.setCode(Config.getStatusByKey("STATUS_UNKNOWN_ERROR_CODE"));
			result.setMsg(Config.getMsgByRandom("MSG_LOGIN_OFF_ERROR"));
			result.setSuccess(false);
			return result;
		} else {
			result.setCode(Config.getStatusByKey("STATUS_SUCC_CODE"));
			result.setMsg(Config.getMsgByRandom("MSG_LOGIN_SUCCESS"));
			Map<String, Object> resultMap = ReflectTool.getMapFromBean(user,
					new String[] { "userId", "userName", "realName", "sex" });
			result.setObj(resultMap);
			result.setSuccess(true);
		}
		return result;
	}

	@Override
	public ResultModel findMsgsByUserId(int userId, int type)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (type == 0) {
			if (userId == 0) {
				res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
				res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
				res.setSuccess(false);
				return res;
			}
		}
		List<MsgModel> list = msgDao.findMsgsByUserId(userId, type);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(list);
		return res;
	}

	@Override
	public ResultModel findNotReaderByUserId(int userId)
			throws YinafjzException {
		ResultModel res = new ResultModel();
		if (userId == 0) {
			res.setCode(Config.getStatusByKey("STATUS_PARAM_CODE"));
			res.setMsg(Config.getStatusByKey("STATUS_PARAM"));
			res.setSuccess(false);
			return res;
		}
		int num = msgDao.findNotReaderByUserId(userId);
		res.setMsg(Config.getMsgByRandom("MSG_QUERY_SUCCESS"));
		res.setObj(num);
		return res;
	}

	@Override
	public ResultModel markRead(int userId) throws YinafjzException {
		ResultModel res = new ResultModel();
		msgDao.markRead(userId);
		res.setMsg(Config.getMsgByRandom("MSG_DATA_SUCCESS"));
		return res;
	}

	@Override
	public ResultModel findCouponByCondition(int userId, int goodsId) throws YinafjzException {
		ResultModel res = new ResultModel();
		if (userId == 0 && goodsId == 0) {
			res.setCode("400");
			res.setMsg("参数错误，查询失败");
			res.setSuccess(false);
			return res;
		}
		List<CouponModel> coupons = userDao.findByCondition(userId, goodsId);
		res.setMsg("查询成功");
		res.setObj(coupons);
		return res;
	}

}
