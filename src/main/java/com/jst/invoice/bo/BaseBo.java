/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class BaseBo implements Serializable,Cloneable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "App版本号", required = true)
	private int appVersion ;//App版本号
	@ApiModelProperty(value = "设备类型, 1-安卓，2-ios", required = true)
	private int deviceType ;//设备类型, 1-安卓，2-ios
	@ApiModelProperty(value = "个推clientId  设备id", required = true)
	private String clientId ;//个推clientId  设备id
	
	private String userId ;
	private int pageNo = 1 ;
	private int pageSize = 10 ;
	
    public int getAppVersion() {
        return appVersion;
    }
    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }
    public int getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
