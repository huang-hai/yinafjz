package com.yinafjz.cleaning.nanny.dao;

import com.yinafjz.cleaning.nanny.model.NannyUserCollectModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NannyUserCollectModelMapper {
    int deleteByPrimaryKey(Integer collectId);

    int insert(NannyUserCollectModel record);

    int insertSelective(NannyUserCollectModel record);

    NannyUserCollectModel selectByPrimaryKey(Integer collectId);

    int updateByPrimaryKeySelective(NannyUserCollectModel record);

    int updateByPrimaryKey(NannyUserCollectModel record);

    /**
     * 查询保姆被收藏次数
     * @param nannyId
     * @return
     */
    int findCollectCountByNanny(Integer nannyId);

    /**
     * 删除收藏
     * @param userId
     * @param nannyId
     * @return
     */
    int delByCondition(@Param("user") Integer userId, @Param("nanny")Integer nannyId);

    /**
     * 根据用户和保姆查询是否收藏
     * @param userId
     * @param nannyId
     * @return
     */
    List<NannyUserCollectModel> findByUserAndNanny(@Param("userId")Integer userId,
                                                   @Param("nanny") Integer nannyId);
}