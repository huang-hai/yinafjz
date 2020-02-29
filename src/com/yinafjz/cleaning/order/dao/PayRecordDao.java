package com.yinafjz.cleaning.order.dao;

import java.util.Map;

import com.yinafjz.cleaning.order.model.PayRecord;


public interface PayRecordDao {
    
	/**
	 * 查询支付回调记录
	 * @author linhy
	 * 2019-1-1 上午12:39:13
	 * @param model
	 * @return
	 */
	public PayRecord getPayRecord(PayRecord model);
	
	/**
	 * 编辑支付回调记录
	 * @author linhy
	 * 2019-1-1 上午12:38:38
	 * @param model
	 * @return
	 */
	public int editPayRecord(PayRecord model);
   
	/**
	 * 新增支付回调记录
	 * @author linhy
	 * 2019-1-1 上午12:38:58
	 * @param model
	 * @return
	 */
	public int addPayRecord(PayRecord model);
	
	/**
	 * 修改订单状态
	 * @author linhy
	 * 2019-1-2 上午9:40:38
	 * @param paramsMap
	 * @return
	 */
	public int editOrderStatus(Map<String, String> paramsMap);
    
}