package com.yinafjz.cleaning.nanny.dao;

import com.yinafjz.cleaning.nanny.model.NannyDutyModel;

public interface NannyDutyModelMapper {
    int deleteByPrimaryKey(Integer dutyId);

    int insert(NannyDutyModel record);

    int insertSelective(NannyDutyModel record);

    NannyDutyModel selectByPrimaryKey(Integer dutyId);

    int updateByPrimaryKeySelective(NannyDutyModel record);

    int updateByPrimaryKey(NannyDutyModel record);

    /**
     * 查询保姆总在岗天数
     * @param nannyId
     * @return
     */
    int findOnDutyCount(Integer nannyId);
}