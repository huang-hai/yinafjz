package com.yinafjz.cleaning.user.service;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.user.model.AddressModel;

public interface AddressService {

	/**
	 * @Title: addAddress
	 * @Description: TODO(添加服务地址)
	 * @param model
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel addAddress(AddressModel model) throws YinafjzException;

	/**
	 * @Title: editAddress
	 * @Description: 编辑服务地址
	 * @param model
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel editAddress(AddressModel model) throws YinafjzException;

	/**
	 * @Title: editDefaultAddr
	 * @Description: 修改用户默认地址
	 * @param userId
	 *            用户id
	 * @param adId
	 *            地址id
	 * @return
	 * @throws
	 */
	public ResultModel editDefaultAddr(int userId, int adId)
			throws YinafjzException;

	/**
	 * @Title: delAddress
	 * @Description: 删除服务地址
	 * @param id
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel delAddress(int id) throws YinafjzException;

	/**
	 * @Title: findAddresssByUserId
	 * @Description: 根据成员id获取服务地址列表
	 * @param userId
	 * @return
	 * @throws YinafjzException
	 * @throws
	 */
	public ResultModel findAddresssByUserId(int userId) throws YinafjzException;

	/**
	 * @Title: findAddressById
	 * @Description: 根据id获取
	 * @param id
	 * @return
	 * @throws
	 */
	public ResultModel findAddressById(int id) throws YinafjzException;

	/**
	 * @Title: findCommunitys
	 * @Description: 查询所有小区
	 * @return
	 * @throws
	 */
	public ResultModel findCommunitys() throws YinafjzException;

	/**
	 * @Title: findDefaultAddrByUserId
	 * @Description: 获取用户默认地址
	 * @param userId
	 * @return
	 * @throws
	 */
	public ResultModel findDefaultAddrByUserId(int userId)
			throws YinafjzException;
}
