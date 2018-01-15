/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * 
 * @Package: com.tomtop.framework.common.utils  
 * @ClassName: SystemPropUtil 
 * @Description:  读取系统属性配置文件类 
 *
 * @author: lixin 
 * @date: 2016年8月24日 下午7:00:17 
 * @version V1.0
 */
public class SystemPropUtil {

	
	public static Map<Object, Object> systemPropMap = new HashMap<Object,Object>();
	
	static {
		if(systemPropMap.isEmpty()){
			try {
				
				systemPropMap = PropertiesLoaderUtils.loadAllProperties("application.properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 获取 数字型 值
	 * @param key
	 * @return
	 */
	public static Integer getIntValue(String key){

		String  value = getStringValue(key);
			if (!"" .equals(value)){
				return  Integer.parseInt(value);
			
			
		}

		return 0;
	}
	
	/**
	 * 获取 字符串型值
	 * @param key
	 * @return
	 */
	public static String getStringValue(String key){
		
		if(systemPropMap.containsKey(key)){
			Object value = systemPropMap.get(key);
			if (null != value){
				return (String)systemPropMap.get(key);
			}
			
		}
		return "";
	}
	
	

}