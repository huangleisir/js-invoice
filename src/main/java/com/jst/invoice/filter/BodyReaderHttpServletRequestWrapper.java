/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.filter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
	 private Map<String, String[]> paramsMap;

	    @Override
	    public Map getParameterMap() {
	        return paramsMap;
	    }

	    @Override
	    public String getParameter(String name) {// 重写getParameter，代表参数从当前类中的map获取
	        String[] values = paramsMap.get(name);
	        if (values == null || values.length == 0) {
	            return null;
	        }
	        return values[0];
	    }

	    @Override
	    public String[] getParameterValues(String name) {// 同上
	        return paramsMap.get(name);
	    }

	    @Override
	    public Enumeration getParameterNames() {
	        return Collections.enumeration(paramsMap.keySet());
	    }

	    private String getRequestBody(InputStream stream) {
	        String line = "";
	        StringBuilder body = new StringBuilder();
	        int counter = 0;

	        // 读取POST提交的数据内容
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        try {
	            while ((line = reader.readLine()) != null) {
	                if (counter > 0) {
	                    body.append("rn");
	                }
	                body.append(line);
	                counter++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return body.toString();
	    }

	    private HashMap<String, String[]> getParamMapFromPost(HttpServletRequest request) {

	        String body = "";
	        try {
	            body = getRequestBody(request.getInputStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        HashMap<String, String[]> result = new HashMap<String, String[]>();

	        if (null == body || 0 == body.length()) {
	            return result;
	        }

	        return parseQueryString(body);
	    }

	    // 自定义解码函数
	    private String decodeValue(String value) {
	            try {
	                return URLDecoder.decode(value, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                return "";// 非UTF-8编码
	            }
	    }

	    public HashMap<String, String[]> parseQueryString(String s) {
	        String valArray[] = null;
	        if (s == null) {
	            throw new IllegalArgumentException();
	        }
	        HashMap<String, String[]> ht = new HashMap<String, String[]>();
	        StringTokenizer st = new StringTokenizer(s, "&");
	        while (st.hasMoreTokens()) {
	            String pair = (String) st.nextToken();
	            int pos = pair.indexOf('=');
	            if (pos == -1) {
	                continue;
	            }
	            String key = pair.substring(0, pos);
	            String val = pair.substring(pos + 1, pair.length());
	            if (ht.containsKey(key)) {
	                String oldVals[] = (String[]) ht.get(key);
	                valArray = new String[oldVals.length + 1];
	                for (int i = 0; i < oldVals.length; i++) {
	                    valArray[i] = oldVals[i];
	                }
	                valArray[oldVals.length] = decodeValue(val);
	            } else {
	                valArray = new String[1];
	                valArray[0] = decodeValue(val);
	            }
	            ht.put(key, valArray);
	        }
	        return ht;
	    }

	    private Map<String, String[]> getParamMapFromGet(HttpServletRequest request) {
	        return parseQueryString(request.getQueryString());
	    }

	    private final byte[] body; // 报文

	    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
	        super(request);
	        body = readBytes(request.getInputStream());

	        // 首先从POST中获取数据
	        if ("POST".equals(request.getMethod().toUpperCase())) {
	            paramsMap = getParamMapFromPost(this);
	        } else {
	            paramsMap = getParamMapFromGet(this);
	        }

	    }

	    @Override
	    public BufferedReader getReader() throws IOException {
	        return new BufferedReader(new InputStreamReader(getInputStream()));
	    }

	    @Override
	    public ServletInputStream getInputStream() throws IOException {
	        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
	        return new ServletInputStream() {

	            @Override
	            public int read() throws IOException {
	                return bais.read();
	            }

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener arg0) {

				}
	        };
	    }

	    private static byte[] readBytes(InputStream in) throws IOException {
	    	byte[] content = null  ;
	    	try {
	        BufferedInputStream bufin = new BufferedInputStream(in);
	        int buffSize = 1024;
	        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

	        byte[] temp = new byte[buffSize];
	        int size = 0;
	        while ((size = bufin.read(temp)) != -1) {
	            out.write(temp, 0, size);
	        }
	        bufin.close();

	        content = out.toByteArray();
	    	} catch (Exception e) {
	    		content = "{}".getBytes();
			}
	        return content;
	    }

}