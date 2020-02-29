package com.yinafjz.cleaning.user.service;

import java.util.Map;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;

public interface UserService {

	/**
	 * APP免登陆方法
	 * 
	 * @param paramsMap
	 * @return
	 * @throws YinafjzException
	 */
	public ResultModel getUserByLogin(Map<String, String> paramsMap)
			throws YinafjzException;

	/**
	 * @Title: findMsgsByUserId
	 * @Description: 获取用户的消息列表
	 * @param userId
	 * @param type
	 *            0交易提醒 1平台通知
	 * @return
	 * @throws
	 */
	public ResultModel findMsgsByUserId(int userId, int type)
			throws YinafjzException;

	/**
	 * @Title: findNotReaderByUserId
	 * @Description: 查询用未读信息数量
	 * @param userId
	 * @return
	 * @throws
	 */
	public ResultModel findNotReaderByUserId(int userId)
			throws YinafjzException;

	/**
	 * @Title: markRead
	 * @Description: 标为已读
	 * @param userId
	 * @return
	 * @throws
	 */
	public ResultModel markRead(int userId) throws YinafjzException;

	/**
	 * 根据条件查询
	 * @param userId
	 * @param goodsId
	 * @return
	 * @throws YinafjzException
	 */
	ResultModel findCouponByCondition(int userId,int goodsId) throws YinafjzException;
}
