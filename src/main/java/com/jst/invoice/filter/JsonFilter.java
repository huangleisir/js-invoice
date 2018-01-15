/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(JsonFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

//        log.info("JsonFilter拦截器, 开始  ---------------- request:" + request);
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest)
        {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String contentType = httpServletRequest.getContentType() == null ? "" : httpServletRequest.getContentType();
            String method = httpServletRequest.getMethod() == null ? "" : httpServletRequest.getMethod();

            if ("POST".equals(method.toUpperCase()) && contentType.toLowerCase().indexOf("application/json") != -1)
            {
                requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }else{
                // 获得用户请求的URI
                String path = httpServletRequest.getRequestURI();
                log.info("JsonFilter拦截器,contentType:" + contentType + " indexOf:" + contentType.toLowerCase().indexOf(
                        "application/json"));
                log.info("JsonFilter拦截器： 验证一下访问,path:" + path + ",url:" + httpServletRequest.getRequestURL().toString());
            }
        }

        if (requestWrapper == null)
        {
            log.info("JsonFilter拦截器,request:" + request);
            chain.doFilter(request, response);
        }
        else
        {
//            log.info("JsonFilter拦截器,requestWrapper:" + requestWrapper);
            chain.doFilter(requestWrapper, response);
        }

//        log.info("JsonFilter拦截器,结束===================== ");
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
