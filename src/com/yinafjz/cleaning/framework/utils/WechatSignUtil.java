package com.yinafjz.cleaning.framework.utils;

import org.apache.commons.lang.math.RandomUtils;


import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * 微信签名工具
 */
public class WechatSignUtil {

    private static final String CHAR_SET = "utf-8";

    /**
     * 签名
     * @param params
     * @param signKey
     * @return
     */
    public static String sign(SortedMap<String , String> params , String signKey){
        StringBuilder sb = new StringBuilder(100);
        //所有参与传参的参数按照accsii排序（升序）
        Iterator<Map.Entry<String , String>> it = params.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String , String> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v) .append("&");
            }
        }
        sb.append("key=" + signKey);
        return MD5Util.MD5Encode(sb.toString(),CHAR_SET).toUpperCase();
    }

    /**
     * 获取随机字符串
     * @return
     */
    public static String getNonceStr() {
        return MD5Util.MD5Encode(String.valueOf(RandomUtils.nextInt(10000000)),CHAR_SET);
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
