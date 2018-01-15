/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2016-2020 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.jst.invoice.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RESPONSE_FPKJ")
public class ReturnInterface {
  private String RETURNCODE;
  private String FP_DM; // 发票代码
  private String FP_HM;  //发票号码 
  private String PDF_URL; //pdfUrl
public String getRETURNCODE() {
	return RETURNCODE;
}
public void setRETURNCODE(String rETURNCODE) {
	RETURNCODE = rETURNCODE;
}
public String getFP_DM() {
	return FP_DM;
}
public void setFP_DM(String fP_DM) {
	FP_DM = fP_DM;
}
public String getFP_HM() {
	return FP_HM;
}
public void setFP_HM(String fP_HM) {
	FP_HM = fP_HM;
}
public String getPDF_URL() {
	return PDF_URL;
}
public void setPDF_URL(String pDF_URL) {
	PDF_URL = pDF_URL;
}
  
}
