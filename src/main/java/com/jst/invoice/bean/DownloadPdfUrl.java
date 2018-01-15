/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.jst.invoice.bean;


import org.hibernate.validator.constraints.NotEmpty;

import com.jst.invoice.common.util.SignField;

/** 
 * 
 * @Package: com.jst.invoice.bean  
 * @ClassName: DownloadPdfUrl 
 * @Description: 下载pdfurl bean
 *
 * @author: wen 
 * @date: 2017年10月31日 下午5:09:54 
 * @version V1.0 
 */
public class DownloadPdfUrl {
	@SignField
	@NotEmpty(message="appId不能为空")
	private String appId ;   // appId
	@NotEmpty(message="签名sign不能为空")
	private String appSign;  // 签名sign  
	@SignField
	@NotEmpty(message="流水号不能为空")
	private String flowNo;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSign() {
		return appSign;
	}
	public void setAppSign(String appSign) {
		this.appSign = appSign;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
}
