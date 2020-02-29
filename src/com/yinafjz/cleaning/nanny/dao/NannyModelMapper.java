package com.yinafjz.cleaning.nanny.dao;

import com.yinafjz.cleaning.nanny.model.CompModel;
import com.yinafjz.cleaning.nanny.model.NannyLabelModel;
import com.yinafjz.cleaning.nanny.model.NannyModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface NannyModelMapper {
    int deleteByPrimaryKey(Integer nannyId);

    int insert(NannyModel record);

    int insertSelective(NannyModel record);

    NannyModel selectByPrimaryKey(Integer nannyId);

    int updateByPrimaryKeySelective(NannyModel record);

    int updateByPrimaryKey(NannyModel record);

    /**
     * 根据多条件查询
     * @param paramsMap
     * @return
     */
    List<NannyModel> findByCondition(Map<String,String> paramsMap);

    /**
     * 根据条件排序
     * @param orderBy orderBy子句
     * @param type
     * @return
     */
    List<NannyModel> findByOrder(@Param("orderBy") String orderBy,@Param("type")String type);

    /**
     * 根据保姆查询标签
     * @param nannyId
     * @return
     */
    List<NannyLabelModel> findLabelByNanny(Integer nannyId);

    /**
     * 根据公司ID查询
     * @param compId
     * @return
     */
    @Select("select comp_id compId,comp_name compName,service_phone servicePhone " +
            "from ynf_homemaking.ynf_comp where comp_id = #{compId}")
    CompModel findById(@Param("compId") Integer compId);
}