package com.jst.invoice.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 类名: DruidStatViewServlet</br>
 * 作者: lin
 * 描述: TODO 
 * 创建时间: 2017年5月27日上午9:01:54
 * 版权及版本: Copyright(C)2017 jst版权所有
 */
@WebServlet(urlPatterns = { "/druid/*" }, 
initParams = { 
		// IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name="allow",value="10.101.90.47,127.0.0.1,10.101.130.8,10.101.130.9,10.101.130.32,10.101.130.36"),
        @WebInitParam(name="deny",value="10.101.130.5"),// IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name = "loginUsername", value = "root"), //用户名
		@WebInitParam(name = "loginPassword", value = "root"), //登录密码
        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
		})
public class DruidStatViewServlet extends StatViewServlet {

	private static final long serialVersionUID = 1L;

}

