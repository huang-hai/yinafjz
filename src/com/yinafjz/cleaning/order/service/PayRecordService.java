package com.yinafjz.cleaning.order.service;


import java.util.Map;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;

/**
 * @author linhy
 * @date 2019-01-01
 */
public interface PayRecordService {
	
    /**
     * 处理微信和支付宝支付成功
     * @author linhy
     * 2019-1-1 上午12:44:25
     * @param paramsMap	支付回调参数
     * @param payType	支付类型 1微信   2支付宝
     */
    public void handlePaySucces(Map<String , String> paramsMap, Integer payType);

    /**
     * 更新订单状态信息
     * @param params
     * @param payType
     * @return
     * @throws YinafjzException
     */
    public ResultModel payAccomplish(Map<String , String> params, Integer payType) throws YinafjzException;
}
