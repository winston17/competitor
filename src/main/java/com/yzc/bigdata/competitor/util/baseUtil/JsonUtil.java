package com.yzc.bigdata.competitor.util.baseUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * 
 * @author Administrator
 *
 */
public class JsonUtil
{
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * obj转换成json对象
	 * 
	 * @param obj
	 * @return String
	 * @throws JsonProcessingException
	 */
	public static String obj2json(Object obj)
	{
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO: handle exception
			return "序列号出现异常："+e.getMessage();
		}
	}

	/**
	 * json转换成obj对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return 泛型
	 * @throws Exception
	 */
	public static <T> T json2pojo(String jsonStr, Class<T> clazz)
	{
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
			return objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
