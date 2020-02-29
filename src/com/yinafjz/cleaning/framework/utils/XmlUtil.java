package com.yinafjz.cleaning.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * @author zhengsc
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/5/313:43
 */
public class XmlUtil {

    /**
     * 将Map解析成XML字符串
     * @param params
     * @return
     */
    public static String parseToXmlStr(Map<String , String> params){
        Element rootElement = DocumentHelper.createElement("xml") ;
        Document document = DocumentHelper.createDocument(rootElement);
        Iterator<Map.Entry<String ,String>> iterator = params.entrySet().iterator();
        Element element ;
        Map.Entry<String,String> entry ;
        while (iterator.hasNext()) {
            entry = iterator.next();
            element = rootElement.addElement(entry.getKey());
            element.addCDATA(entry.getValue());
        }
        return document.asXML();
    }

    /**
     * 将XML字符串解析成Map
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static Map<String , String> parseToMap(String xmlStr) throws DocumentException {
        Map<String, String> parameters = new HashMap<String ,String>();
        return parseToMap(xmlStr , parameters);
    }

    /**
     * 将XML字符串解析成Map
     * @param xmlStr
     * @param targetMap
     * @return
     * @throws DocumentException
     */
    public static Map<String , String> parseToMap(String xmlStr ,Map<String , String> targetMap) throws DocumentException {
        if (xmlStr == null || (xmlStr = xmlStr.trim()).isEmpty() || targetMap == null) {
            return targetMap ;
        }
        Document document = DocumentHelper.parseText(xmlStr);
        List<Element> elements = document.getRootElement().elements();
        for (Element element : elements) {
            targetMap.put(element.getName(), element.getTextTrim());
        }
        return targetMap ;
    }


}
