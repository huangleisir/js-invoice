package com.jst.invoice.common.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 
 * 
 * @Package: com.jst.gateway.bean  
 * @ClassName: Result 
 * @Description: 返回报文bean
 *
 * @author: lixin 
 * @date: 2016年12月14日 下午9:15:29 
 * @version V1.0
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Result
  implements Serializable 
{
  private static final long serialVersionUID = -6888789042963799184L;
  private String code="200";
  private String msg="成功";

  private Object data;
  private Page page;

  public Result()
  {
  }

  public Result(String code, String msg)
  {
    this.code = code;
    this.msg = msg;
  }

  public Result(Object data) {
    this.data = data;
  }

  public Result( Object data, Page page) {
	this.data = data ;
    this.page = page;
  }

  
  
  public Result(String code, String msg, Object data) {
    this(code, msg);
    this.data = data;
  }

  public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}



public Object getData() {
	return data;
}

public void setData(Object data) {
	this.data = data;
}

public Page getPage() {
	return page;
}

public void setPage(Page page) {
	this.page = page;
}

public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}