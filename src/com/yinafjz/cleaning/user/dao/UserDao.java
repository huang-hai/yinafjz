package com.yinafjz.cleaning.user.dao;

import java.util.List;
import java.util.Map;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.user.model.CouponModel;
import com.yinafjz.cleaning.user.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserDao {
	
	/**
	 * APP免登陆查询用户信息
	 * @param paramsMap
	 * @return
	 * @throws YinafjzException
	 */
	public UserModel getUserByLogin(Map<String, String> paramsMap) throws YinafjzException;

	/**
	 * 根据条件查询
	 * @param userId
	 * @param goods_id
	 * @return
	 * @throws YinafjzException
	 */
	List<CouponModel> findByCondition(@Param("userId") int userId, @Param("goodsId") int goods_id) throws YinafjzException;

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws YinafjzException
	 */
	CouponModel findCouponById(Integer id) throws YinafjzException;

	/**
	 * 更新优惠券状态
	 * @param id
	 * @param state
	 * @return
	 * @throws YinafjzException
	 */
	@Update("update ynf_service_coupon set state = #{state} where coupon_id = #{id}")
	int updateCouponState(@Param("id") int id,@Param("state") int state) throws YinafjzException;
}
