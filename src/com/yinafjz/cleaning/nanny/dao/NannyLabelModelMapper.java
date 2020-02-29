package com.yinafjz.cleaning.nanny.dao;

import com.yinafjz.cleaning.nanny.model.NannyLabelModel;

public interface NannyLabelModelMapper {
    int deleteByPrimaryKey(Integer lableId);

    int insert(NannyLabelModel record);

    int insertSelective(NannyLabelModel record);

    NannyLabelModel selectByPrimaryKey(Integer lableId);

    int updateByPrimaryKeySelective(NannyLabelModel record);

    int updateByPrimaryKey(NannyLabelModel record);
}