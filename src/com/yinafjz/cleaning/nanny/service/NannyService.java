package com.yinafjz.cleaning.nanny.service;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.PageResultModel;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.nanny.model.NannySubscribeModel;
import com.yinafjz.cleaning.nanny.model.NannyUserCollectModel;

import java.util.Map;

public interface NannyService {

    /**
     * 获取保姆列表
     * @param paramsMap
     * @return
     * @throws YinafjzException
     */
    PageResultModel findNannyList(Map<String,String> paramsMap) throws YinafjzException;

    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws YinafjzException
     */
    ResultModel findById(Integer id) throws YinafjzException ;

    /**
     * 添加预约
     * @param nannySubscribeModel
     * @return
     * @throws YinafjzException
     */
    ResultModel addNannySubscribe(NannySubscribeModel nannySubscribeModel) throws YinafjzException ;

    /**
     * 添加收藏
     * @param nannyUserCollectModel
     * @return
     * @throws YinafjzException
     */
    ResultModel addNannyCollect(NannyUserCollectModel nannyUserCollectModel) throws YinafjzException ;

    /**
     * 取消收藏(删除)
     * @param userId
     * @param nannyId
     * @return
     * @throws YinafjzException
     */
    ResultModel delNannyCollect(Integer userId,Integer nannyId) throws YinafjzException ;

    /**
     * 查询用户是否收藏保姆
     * @param userId
     * @param nannyId
     * @return
     * @throws YinafjzException
     */
    ResultModel findCollectByUserAndNanny(Integer userId,Integer nannyId) throws YinafjzException ;


    /**
     * 查询相似保姆
     * @param compId
     * @return
     * @throws YinafjzException
     */
    ResultModel findSimilarityByComp(Integer compId) throws YinafjzException ;
}
