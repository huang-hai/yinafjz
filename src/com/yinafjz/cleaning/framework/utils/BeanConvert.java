package com.yinafjz.cleaning.framework.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanConvert {
    private static final Logger log = LoggerFactory.getLogger(BeanConvert.class);
    
    static ObjectMapper objectMapper;
    
    /**
     * 将javaBean转换成Map
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();
	
		for (Method method : methods) {
		    try {
				if (method.getName().startsWith("get")) {
				    String field = method.getName();
				    field = field.substring(field.indexOf("get") + 3);
				    field = field.toLowerCase().charAt(0) + field.substring(1);
				    Object value = method.invoke(javaBean, (Object[]) null);
				    result.put(field, null == value ? "" : value.toString());
				}
		    } catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
		    }
		}
		return result;
    }
    
    /**
     * 将javaBean转换成Map
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, Object> toMapAndObject(Object javaBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();
	
		for (Method method : methods) {
		    try {
				if (method.getName().startsWith("get")) {
				    String field = method.getName();
				    field = field.substring(field.indexOf("get") + 3);
				    field = field.toLowerCase().charAt(0) + field.substring(1);
				    Object value = method.invoke(javaBean, (Object[]) null);
				    result.put(field, null == value ? "" : value);
				}
		    } catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
		    }
		}
		return result;
    }
    
    /**
     *将Map转换成javaBean
     * @param type
     * @param data
     * @return
     */
    public static <T> T toBean(Class<T> type, Map data) {
		T bean = null;
		try {
		    bean = type.newInstance();
		} catch (Exception e) {
		    log.error(e.getMessage());
		    return null;
		}
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
		    try {
				if (method.getName().startsWith("set")) {
				    if (method.getGenericParameterTypes().length == 1) {
					Class clazz = method.getParameterTypes()[0];
					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0)
						+ field.substring(1);
					String dataValue = data.get(field) == null ? "" : data
						.get(field).toString();
					// Number.class.isAssignableFrom(clazz)
						if (!dataValue.equals("")) {
							Object object =  clazz.getConstructor(new Class[] { String.class}).newInstance(new Object[] { dataValue });
						    method.invoke(bean, new Object[] { object });
						}
				    }
				}
		    } catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
		    }
		}
		return bean;
    }
    

    public static Map<String, Object> objArrayToMap(Object[] objArray) {
		Map<String, Object> result = new HashMap<String, Object>();
		/*for (Object obj : objArray) {
		    Method[] methods = obj.getClass().getDeclaredMethods();
		    for (Method method : methods) {
			if (method.getName().startsWith("get")) {
			    String field = method.getName();
			    field = field.substring(field.indexOf("get") + 3);
			    field = field.toLowerCase().charAt(0) + field.substring(1);
			    try {
				Object value = method.invoke(obj, (Object[]) null);
				result.put(obj.getClass().getSimpleName()+"."+field, value);
			    } catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			    } 
			}
		    }
		}*/
		for (Object obj : objArray) {
		    result.put(obj.getClass().getSimpleName(), obj);
		}
		return result;
    }
    
    /**
     *      使用泛型方法，把json字符串转换为相应的JavaBean对象。
     *      (1)转换为普通JavaBean：readValue(json,Student.class)
     *      (2)转换为List:readValue(json,List.class).但是如果我们想把json转换为特定类型的List，比如List<Student>，就不能直接进行转换了。
     *         因为readValue(json,List.class)返回的其实是List<Map>类型，你不能指定readValue()的第二个参数是List<Student>.class，所以不能直接转换。
     *         我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List。
     *      (3)转换为Map：readValue(json,Map.class)
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们使用泛型，得到的也是泛型
     *
     * @param content 要转换的JavaBean类型
     * @param valueType 原始json字符串数据
     * @return JavaBean对象
     */
    public static <T> T readValue(String content, Class<T> valueType){
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
        }
        try{
            return objectMapper.readValue(content, valueType);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     *      把JavaBean转换为json字符串
     *      (1)普通对象转换：toJson(Student)
     *      (2)List转换：toJson(List)
     *      (3)Map转换:toJson(Map)
     * 我们发现不管什么类型，都可以直接传入这个方法
     *
     * @param object JavaBean对象
     * @return json字符串
     */
    public static String toJSon(Object object){
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
        }
        try{
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
