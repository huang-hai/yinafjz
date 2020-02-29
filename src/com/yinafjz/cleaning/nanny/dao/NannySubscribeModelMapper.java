package com.yinafjz.cleaning.nanny.dao;

import com.yinafjz.cleaning.nanny.model.NannySubscribeModel;

public interface NannySubscribeModelMapper {
    int deleteByPrimaryKey(Integer subscribeId);

    int insert(NannySubscribeModel record);

    int insertSelective(NannySubscribeModel record);

    NannySubscribeModel selectByPrimaryKey(Integer subscribeId);

    int updateByPrimaryKeySelective(NannySubscribeModel record);

    int updateByPrimaryKey(NannySubscribeModel record);

    /**
     * 查询保姆预约次数
     * @param nannyId
     * @return
     */
    int findSubscribeCountByNanny(Integer nannyId);
}