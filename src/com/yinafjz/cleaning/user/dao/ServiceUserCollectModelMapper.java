package com.yinafjz.cleaning.user.dao;

import com.yinafjz.cleaning.user.model.ServiceUserCollectModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceUserCollectModelMapper {
    int deleteByPrimaryKey(Integer collectId);

    int insert(ServiceUserCollectModel record);

    int insertSelective(ServiceUserCollectModel record);

    ServiceUserCollectModel selectByPrimaryKey(Integer collectId);

    int updateByPrimaryKeySelective(ServiceUserCollectModel record);

    int updateByPrimaryKey(ServiceUserCollectModel record);

    /**
     * 取消收藏
     * @param userId
     * @param goodsId
     * @return
     */
    int delByCondition(@Param("user")Integer userId,@Param("goods")Integer goodsId);

    /**
     * 根据用户和商品查询是否收藏
     * @param userId
     * @param goodsId
     * @return
     */
    List<ServiceUserCollectModel> findByUserAndGoods(@Param("userId")Integer userId,
                                                     @Param("goodsId") Integer goodsId);
}