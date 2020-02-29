package com.yinafjz.cleaning.mobile;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.PageResultModel;
import com.yinafjz.cleaning.framework.model.ResultModel;
import com.yinafjz.cleaning.framework.utils.ResponseTool;
import com.yinafjz.cleaning.framework.utils.ValidateTool;
import com.yinafjz.cleaning.framework.web.AHandleModelAndView;
import com.yinafjz.cleaning.nanny.model.NannySubscribeModel;
import com.yinafjz.cleaning.nanny.model.NannyUserCollectModel;
import com.yinafjz.cleaning.nanny.service.NannyService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/nanny")
public class NannyController extends AHandleModelAndView {

    private static final Logger LOGGER = Logger
            .getLogger(NannyController.class);

    @Autowired
    private NannyService nannyService;

    /**
     * 获取保姆列表
     * @param request
     * @param response
     * //@param name 字段名
     * //@param val 关键字 asc 升序 desc降序
     * //@param rows 页条数
     * //@param page 当前页
     * //@param type 类型 0住家保姆 1非住家保姆 2月嫂 3护工 4育儿嫂
     * @throws YinafjzException
     */
    @RequestMapping(value = "/findNannys", method = RequestMethod.GET)
    public void findNannys(HttpServletRequest request,
                            HttpServletResponse response) throws YinafjzException {
        try {
            Map<String, String> paramsMap = paramsMap(request);
            ValidateTool.checkParameterIsNull("类型", MapUtils.getString(paramsMap,"type",""));
            PageResultModel result = nannyService.findNannyList(paramsMap);

            ResponseTool.responseJson(result, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 根据ID查询保姆
     * @param request
     * @param response
     * @param id
     * @throws YinafjzException
     */
    @RequestMapping(value = "/findNanny", method = RequestMethod.GET)
    public void findNanny(HttpServletRequest request,
                            HttpServletResponse response,Integer id) throws YinafjzException {
        try {
            ResultModel result = nannyService.findById(id);
            ResponseTool.responseJson(result, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 添加预约
     * @param request
     * @param response
     * @param model
     * @param bindingResult
     * @throws YinafjzException
     */
    @RequestMapping(value = "/addNannySubscribe", method = RequestMethod.POST)
    public void addNannySubscribe(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @Validated NannySubscribeModel model,
                                  BindingResult bindingResult) throws YinafjzException {
        try {
            ResultModel res = new ResultModel();
            if(bindingResult.hasErrors()){
                List<ObjectError> errors = bindingResult.getAllErrors();
                res.setSuccess(false);
                res.setCode("500");
                res.setMsg(errors.get(0).getDefaultMessage());
            } else {
                res = nannyService.addNannySubscribe(model);
            }
            ResponseTool.responseJson(res, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @param model
     * @param bindingResult
     * @throws YinafjzException
     */
    @RequestMapping(value = "/addNannyCollect", method = RequestMethod.POST)
    public void addNannyCollect(HttpServletRequest request,
                                HttpServletResponse response,
                                @Validated NannyUserCollectModel model,
                                BindingResult bindingResult) throws YinafjzException {
        try {
            ResultModel res = new ResultModel();
            if(bindingResult.hasErrors()){
                List<ObjectError> errors = bindingResult.getAllErrors();
                res.setSuccess(false);
                res.setCode("500");
                res.setMsg(errors.get(0).getDefaultMessage());
            } else {
                res = nannyService.addNannyCollect(model);
            }
            ResponseTool.responseJson(res, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 取消收藏
     * @param request
     * @param response
     * @param userId
     * @param nannyId
     * @throws YinafjzException
     */
    @RequestMapping(value = "/delNannyCollect", method = RequestMethod.POST)
    public void delNannyCollect(HttpServletRequest request,
                                HttpServletResponse response,Integer userId,Integer nannyId) throws YinafjzException {
        try {
            ResultModel  res = new ResultModel();
            if(null == userId || null == nannyId){
                res.setSuccess(false);
                res.setCode("500");
                res.setMsg("用户ID或保姆ID不能为空");
            } else {
                res = nannyService.delNannyCollect(userId,nannyId);
            }
            ResponseTool.responseJson(res, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 查询相似保姆
     * @param request
     * @param response
     * @param compId
     * @throws YinafjzException
     */
    @RequestMapping(value = "/findSimilarity", method = RequestMethod.GET)
    public void findSimilarity(HttpServletRequest request,
                                HttpServletResponse response,Integer compId) throws YinafjzException {
        try {
            ResultModel  res = nannyService.findSimilarityByComp(compId);
            ResponseTool.responseJson(res, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    /**
     * 判断是否已收藏
     * @param request
     * @param response
     * @param userId
     * @param nannyId
     * @throws YinafjzException
     */
    @RequestMapping(value = "/isCollect", method = RequestMethod.GET)
    public void isCollect(HttpServletRequest request,
                                HttpServletResponse response,Integer userId,Integer nannyId) throws YinafjzException {
        try {
            ResultModel  res = new ResultModel();
            if(null == userId || null == nannyId){
                res.setSuccess(false);
                res.setCode("500");
                res.setMsg("用户ID或保姆ID不能为空");
            } else {
                res = nannyService.findCollectByUserAndNanny(userId,nannyId);
            }
            ResponseTool.responseJson(res, request, response);
        } catch (YinafjzException e) {
            ResponseTool.exceptionReturn(e, request, response, LOGGER);
        }
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }
}
