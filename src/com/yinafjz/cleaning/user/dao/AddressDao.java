package com.yinafjz.cleaning.user.dao;

import java.util.List;
import java.util.Map;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.user.model.AddressModel;

public interface AddressDao {

	/**
	 * @Title: addAddress
	 * @Description: 添加服务地址
	 * @param model
	 * @return
	 * @throws
	 */
	public int addAddress(AddressModel model) throws YinafjzException;

	/**
	 * @Title: editAddress
	 * @Description: 编辑服务地址
	 * @param model
	 * @return
	 * @throws
	 */
	public int editAddress(AddressModel model) throws YinafjzException;

	/**
	 * @Title: editDefaultAddr
	 * @Description: 修改为默认地址
	 * @param id
	 * @return
	 * @throws
	 */
	public int editDefaultAddr(int id) throws YinafjzException;

	/**
	 * @Title: editNotDefaultAddr
	 * @Description: 修改为不是默认地址
	 * @param userId
	 * @return
	 * @throws
	 */
	public int editNotDefaultAddr(int userId) throws YinafjzException;

	/**
	 * @Title: delAddress
	 * @Description: 删除服务地址
	 * @param id
	 * @return
	 * @throws
	 */
	public int delAddress(int id) throws YinafjzException;

	/**
	 * @Title: findAddresssByUserId
	 * @Description: 根据成员id获取服务地址列表
	 * @param userId
	 * @return
	 * @throws
	 */
	public List<AddressModel> findAddresssByUserId(int userId)
			throws YinafjzException;

	/**
	 * @Title: findCommunitys
	 * @Description: 查询所有小区名称
	 * @return
	 * @throws
	 */
	public List<Map<Integer, String>> findCommunitys();

	/**
	 * @Title: findAddressById
	 * @Description: 根据id获取地址
	 * @param id
	 * @return
	 * @throws
	 */
	public AddressModel findAddressById(int id) throws YinafjzException;

	/**
	 * @Title: findDefultAddrByUserId
	 * @Description: 获取用户默认地址
	 * @param userId
	 * @return
	 * @throws
	 */
	public AddressModel findDefultAddrByUserId(int userId)
			throws YinafjzException;
}
