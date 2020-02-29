package com.yinafjz.cleaning.nanny.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.PageResultModel;
import com.yinafjz.cleaning.framework.model.PagerModel;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.nanny.dao.NannyDutyModelMapper;
import com.yinafjz.cleaning.nanny.dao.NannyModelMapper;
import com.yinafjz.cleaning.nanny.dao.NannySubscribeModelMapper;
import com.yinafjz.cleaning.nanny.dao.NannyUserCollectModelMapper;
import com.yinafjz.cleaning.nanny.model.CompModel;
import com.yinafjz.cleaning.nanny.model.NannyModel;
import com.yinafjz.cleaning.nanny.model.NannySubscribeModel;
import com.yinafjz.cleaning.nanny.model.NannyUserCollectModel;
import com.yinafjz.cleaning.order.dao.OrderDao;
import com.yinafjz.cleaning.user.model.EvaluationModel;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NannyServiceImpl implements NannyService {


    @Qualifier("nannyModelMapper")
    @Autowired
    private NannyModelMapper nannyModelMapper;

    @Qualifier("nannySubscribeModelMapper")
    @Autowired
    private NannySubscribeModelMapper nannySubscribeModelMapper;

    @Qualifier("nannyUserCollectModelMapper")
    @Autowired
    private NannyUserCollectModelMapper nannyUserCollectModelMapper;

    @Qualifier("nannyDutyModelMapper")
    @Autowired
    private NannyDutyModelMapper nannyDutyModelMapper;

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResultModel findNannyList(Map<String,String> paramsMap) throws YinafjzException {
        PageResultModel res = new PageResultModel();
        // 默认查询第一页，返回10条数据
        Integer rows = MapUtils.getInteger(paramsMap, "rows", 10);
        Integer page = MapUtils.getInteger(paramsMap, "page", 1);
        PageHelper.startPage(page, rows);

        String name = paramsMap.get("name");
        String val = paramsMap.get("val");
        String type = paramsMap.get("type");
        List<NannyModel> nannys = nannyModelMapper.findByOrder(name+" "+val,type);

        PageInfo<NannyModel> pageInfo = new PageInfo<NannyModel>(nannys);
        int total = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
        PagerModel pageModel = new PagerModel(rows, page, total);
        pageModel.setCurrentPage(page);
        res.setPager(pageModel);
        res.setRows(nannys);
        res.setCode("200");
        res.setMsg("查询成功");
        res.setSuccess(true);
        return res;
    }

    @Override
    public ResultModel findById(Integer id) throws YinafjzException {
        ResultModel res = new ResultModel();

        NannyModel nanny = nannyModelMapper.selectByPrimaryKey(id);

        //查询预约次数
        int subscribeCount = nannySubscribeModelMapper.findSubscribeCountByNanny(nanny.getNannyId());
        nanny.setSubscribeCount(subscribeCount);

        //查询收藏次数
        int collectCount = nannyUserCollectModelMapper.findCollectCountByNanny(nanny.getNannyId());
        nanny.setCollectCount(collectCount);

        //查询总在岗次数
        int onDutyCount = nannyDutyModelMapper.findOnDutyCount(nanny.getNannyId());
        nanny.setOnDutyCount(onDutyCount);

        //获取保姆评价
        List<EvaluationModel> evals = orderDao.findByCondition("nanny_id", nanny.getNannyId() + "","");
        if(evals.size() <= 3) nanny.setEvals(evals);
        else {
            List<EvaluationModel> list = new ArrayList<EvaluationModel>();
            int i = 0;
            int goodEval = 0;
            for(EvaluationModel eval:evals){
                if(i > 3) list.add(eval);
                //大于3颗心为好评
                if(eval.getEval()>3)goodEval++;
            }
            nanny.setEvals(list);
            nanny.setGoodEvalCount(goodEval);
        }

        //查询公司客服电话
        CompModel compModel = nannyModelMapper.findById(nanny.getCompId());
        if(null != compModel) nanny.setServicePhone(compModel.getServicePhone());

        res.setCode("200");
        res.setMsg("查询成功");
        res.setObj(nanny);
        res.setSuccess(true);
        return res;
    }

    @Override
    public ResultModel addNannySubscribe(NannySubscribeModel nannySubscribeModel) throws YinafjzException {
        ResultModel res = new ResultModel();
        //默认有效
        nannySubscribeModel.setState(1);
        nannySubscribeModel.setCreateTime(new Date());
        int row = nannySubscribeModelMapper.insert(nannySubscribeModel);
        if(row>0) {
            res.setCode("200");
            res.setMsg("操作成功");
            res.setSuccess(true);
        } else {
            res.setSuccess(false);
            res.setMsg("操作失败，请联系管理员");
            res.setCode("500");
        }
        return res;
    }

    @Override
    public ResultModel addNannyCollect(NannyUserCollectModel nannyUserCollectModel) throws YinafjzException {
        ResultModel res = new ResultModel();
        //默认有效
        nannyUserCollectModel.setCreateTime(new Date());
        List<NannyUserCollectModel> collects = nannyUserCollectModelMapper.findByUserAndNanny(
                nannyUserCollectModel.getUserId(),
                nannyUserCollectModel.getNanny());
        if(collects.size()>0){
            res.setSuccess(true);
            res.setMsg("已收藏，不需要在收藏咯");
            res.setCode("200");
            return res;
        }
        int row = nannyUserCollectModelMapper.insert(nannyUserCollectModel);
        if(row>0) {
            res.setCode("200");
            res.setMsg("操作成功");
            res.setSuccess(true);
        } else {
            res.setSuccess(false);
            res.setMsg("操作失败，请联系管理员");
            res.setCode("500");
        }
        return res;
    }

    @Override
    public ResultModel delNannyCollect(Integer userId, Integer nannyId) throws YinafjzException {
        ResultModel res = new ResultModel();
        int row = nannyUserCollectModelMapper.delByCondition(userId, nannyId);
        if(row>0) {
            res.setCode("200");
            res.setMsg("操作成功");
            res.setSuccess(true);
        } else {
            res.setSuccess(false);
            res.setMsg("操作失败，请联系管理员");
            res.setCode("500");
        }
        return res;
    }

    @Override
    public ResultModel findCollectByUserAndNanny(Integer userId, Integer nannyId) throws YinafjzException {
        ResultModel res = new ResultModel();
        List<NannyUserCollectModel> collects = nannyUserCollectModelMapper.findByUserAndNanny(userId, nannyId);
        res.setCode("200");
        res.setMsg("查询成功");
        res.setSuccess(true);
        if(collects.size()>0) res.setObj(true);
        else res.setObj(false);
        return res;
    }

    @Override
    public ResultModel findSimilarityByComp(Integer compId) throws YinafjzException {
        ResultModel res = new ResultModel();
        List<NannyModel> nannys = new ArrayList<NannyModel>();
        Map<String,String> paramMap = new HashMap<String,String>();
        if(null != compId && compId > 0){
            paramMap.put("comp_id",compId+"");
            nannys = nannyModelMapper.findByCondition(paramMap);
        }
        if(nannys.size()==0) {
            paramMap.clear();
            nannys = nannyModelMapper.findByCondition(paramMap);
        }
        res.setObj(nannys);
        res.setCode("200");
        res.setMsg("查询成功");
        res.setSuccess(true);
        return res;
    }
}
