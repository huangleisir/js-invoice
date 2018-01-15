/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jst.invoice.filter.JsonFilter;


@Configuration
public class FilterConfig {

	
	  
	  
	@Bean  
	public FilterRegistrationBean  filterRegistrationBean() {  
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();  

        JsonFilter arrearageFilter = new JsonFilter() ;
	    registrationBean.setFilter(arrearageFilter);  
	    List<String> urlPatterns = new ArrayList<String>(); 
	    urlPatterns.add("/*");  
	    registrationBean.setUrlPatterns(urlPatterns);  
	    return registrationBean;  
	}  
	
	
	
}
