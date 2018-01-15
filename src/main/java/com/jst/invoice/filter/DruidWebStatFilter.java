package com.jst.invoice.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 类名: DruidWebStatFilter</br>
 * 作者: lin
 * 描述: druid过滤器 
 * 创建时间: 2017年7月25日下午2:51:01
 * 版权及版本: Copyright(C)2017 jst版权所有
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = { @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") })
public class DruidWebStatFilter extends WebStatFilter {

}
