package com.jst.invoice.bean;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.jst.invoice.common.util.SignField;

/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

/** 
 * 
 * @Package: com.aisino.model  
 * @ClassName: Invoice 
 * @Description: 开票bean
 *
 * @author: Administrator 
 * @date: 2017年10月27日 上午11:33:32 
 * @version V1.0 
 */
public class Invoice implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@SignField
	@NotEmpty(message="appId不能为空")
	private String appId ;   // appId
	@NotEmpty(message="签名sign不能为空")
	private String appSign;  // 签名sign  
	private String fpqqlsh;//请求唯一流水号 
	@SignField
	@NotEmpty(message="请选择开发票渠道")
	private String kpqd ;  // 1 航信  2 百望  
	private String dsptbm;   //平台编码 
	@SignField
	private String nsrsbh;    //开票方识别号
	@SignField
	private String nsrmc;   //开票方名称
	@SignField
	private String nsrdzdah;    // 开票号电子档案号
	@SignField
	private String swjcdm;    //税务机构代码
	@SignField
	private String dkbz;     // 代开标记
	@SignField
	@NotEmpty(message="开票项目不能为空")
	private String kpxm; // 主要开票项目 
	
	@SignField
	@NotEmpty(message="销货方识别号不能为空")
	private String xhfnsrsbh;  // 销货方识别号
	@SignField
	@NotEmpty(message="销货方名称不能为空")
	private String xhfmc;  // 销货方名称
	@SignField
	private String xhfdz;   // 销货方地址
	@SignField
	private String xhfdh; // 销货方电话
	@SignField
	private String  xhfyhzh;// 销货方银行账号
	@SignField
	@NotEmpty(message="购货方名称不能为空")
	private String ghfmc;  // 购货方名称
	@SignField
	private String ghfnsrsbh;  //购货方识别号
	@SignField
	private String ghfyhzh ;//购货方银行账号
	@SignField
	private String ghfdz ; //购货方地址
	@SignField
	@NotEmpty(message="购货方手机不能为空")
	private String ghfsj;  //购货方手机 
	@SignField
	@NotEmpty(message="购货方邮箱不能为空")
	private String ghfemail;   // 购货方邮箱
	@SignField
	private String ghfqylx;  //购货方类型 (01 企业 02 机关事业单位 03 个人 04 其他) 
	@SignField
	@NotEmpty(message="开票人不能为空")
	private String kpy;   // 开票人
	@SignField
	private String sky ;//收款员
	@SignField
	private String fhr; // 复核人
	@SignField
	private String kplx;   // 开票类型 1正  2红
	@SignField
	private String yfpdm;   // 原发票代码 
	@SignField
	private String yfphm;   // 原发票号码
	@SignField
	@NotEmpty(message="特殊红冲标志不能为空")
	private String tschbz;   // 特殊红冲标志   0 正常 1特殊红冲 
	@SignField
	@NotEmpty(message="操作代码不能为空")
	private String czdm;   // 操作代码  10 正常开 11 正票错票重开  20 退货折让红票  21 错票重开红票  22 换票冲红 
	@SignField
	private String qdbz;   // 清单标志 
	private String chyy;   // 红冲原因 
	@SignField
	@NotNull(message="价税合计金额不能为空")
	private Double kphjje;   // 价税合计金额
	private Double hjbhsje;   // 合计不含税金额 
	private Double hjse;   // 合计税额 
	
	/**
	 * 增值税发票红字发票开具备注-- 对应正数发票代码为:xx  号码 为:yy
	 */
	private String zd; //备注 
    /**
     * 项目信息 XMXX
     */
	@SignField
	@NotEmpty(message="项目名称不能为空")
	private String xmmc; // 项目名称
	private String xmdw;  // 项目单位  
	@SignField
	private Double xmsl;  // 项目数目 
	@SignField
	@NotEmpty(message="含税标志不能为空")
	private String hsbz;  //含税标志 0 不含税  1含税  
	@SignField
	private Double xmdj;  //项目单价 
	@SignField
	private String fphxz;  // 发票行性质  0 正常行 1 折扣行 2被折扣行  
	@SignField
	@NotEmpty(message="商品编码不能为空")
	private String spbm;  //商品编码  
	private String yhzcbz;   // 优惠正常标识 0 不使用 1使用 
	private Double xmje;   // 项目金额
	@SignField
	@NotEmpty(message="税率不能为空")
	private String sl;   //税率 
	private Double se;  //税额
	/**
	 * 订单信息 DDXX	
	 */
	@SignField
	@NotEmpty(message="订单号不能为空")
	private String ddh;  //订单号 
	private String thdh;  //退货单号  开具红字发票退货、折让的时候必填 
	private String fpdm ; //发票代码 
	private String fphm; //发票号码
	private String createTime ;
	private String updateTime ;
	private String status; //发票状态 
	
	private String  pdfurl ;//pdurl
	@SignField
	private String bz ; //备注
	public String getFpqqlsh() {
		return fpqqlsh;
	}
	public void setFpqqlsh(String fpqqlsh) {
		this.fpqqlsh = fpqqlsh;
	}
	public String getDsptbm() {
		return dsptbm;
	}
	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}
	public String getNsrsbh() {
		return nsrsbh;
	}
	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}
	public String getNsrmc() {
		return nsrmc;
	}
	public void setNsrmc(String nsrmc) {
		this.nsrmc = nsrmc;
	}
	public String getNsrdzdah() {
		return nsrdzdah;
	}
	public void setNsrdzdah(String nsrdzdah) {
		this.nsrdzdah = nsrdzdah;
	}
	public String getSwjcdm() {
		return swjcdm;
	}
	public void setSwjcdm(String swjcdm) {
		this.swjcdm = swjcdm;
	}
	public String getDkbz() {
		return dkbz;
	}
	public void setDkbz(String dkbz) {
		this.dkbz = dkbz;
	}
	public String getKpxm() {
		return kpxm;
	}
	public void setKpxm(String kpxm) {
		this.kpxm = kpxm;
	}
	public String getXhfnsrsbh() {
		return xhfnsrsbh;
	}
	public void setXhfnsrsbh(String xhfnsrsbh) {
		this.xhfnsrsbh = xhfnsrsbh;
	}
	public String getXhfmc() {
		return xhfmc;
	}
	public void setXhfmc(String xhfmc) {
		this.xhfmc = xhfmc;
	}
	public String getXhfdz() {
		return xhfdz;
	}
	public void setXhfdz(String xhfdz) {
		this.xhfdz = xhfdz;
	}
	public String getXhfdh() {
		return xhfdh;
	}
	public void setXhfdh(String xhfdh) {
		this.xhfdh = xhfdh;
	}
	
	public String getXhfyhzh() {
		return xhfyhzh;
	}
	public void setXhfyhzh(String xhfyhzh) {
		this.xhfyhzh = xhfyhzh;
	}
	public String getGhfmc() {
		return ghfmc;
	}
	public void setGhfmc(String ghfmc) {
		this.ghfmc = ghfmc;
	}
	
	public String getGhfnsrsbh() {
		return ghfnsrsbh;
	}
	public void setGhfnsrsbh(String ghfnsrsbh) {
		this.ghfnsrsbh = ghfnsrsbh;
	}
	public String getGhfsj() {
		return ghfsj;
	}
	public void setGhfsj(String ghfsj) {
		this.ghfsj = ghfsj;
	}
	public String getGhfemail() {
		return ghfemail;
	}
	public void setGhfemail(String ghfemail) {
		this.ghfemail = ghfemail;
	}
	public String getGhfqylx() {
		return ghfqylx;
	}
	public void setGhfqylx(String ghfqylx) {
		this.ghfqylx = ghfqylx;
	}
	public String getKpy() {
		return kpy;
	}
	public void setKpy(String kpy) {
		this.kpy = kpy;
	}
	public String getSky() {
		return sky;
	}
	public void setSky(String sky) {
		this.sky = sky;
	}
	public String getFhr() {
		return fhr;
	}
	public void setFhr(String fhr) {
		this.fhr = fhr;
	}
	public String getKplx() {
		return kplx;
	}
	public void setKplx(String kplx) {
		this.kplx = kplx;
	}
	public String getYfpdm() {
		return yfpdm;
	}
	public void setYfpdm(String yfpdm) {
		this.yfpdm = yfpdm;
	}
	public String getYfphm() {
		return yfphm;
	}
	public void setYfphm(String yfphm) {
		this.yfphm = yfphm;
	}
	public String getTschbz() {
		return tschbz;
	}
	public void setTschbz(String tschbz) {
		this.tschbz = tschbz;
	}
	public String getCzdm() {
		return czdm;
	}
	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}
	public String getQdbz() {
		return qdbz;
	}
	public void setQdbz(String qdbz) {
		this.qdbz = qdbz;
	}
	public String getChyy() {
		return chyy;
	}
	public void setChyy(String chyy) {
		this.chyy = chyy;
	}
	
	
	public Double getKphjje() {
		return kphjje;
	}
	public void setKphjje(Double kphjje) {
		this.kphjje = kphjje;
	}
	public Double getHjbhsje() {
		return hjbhsje;
	}
	public void setHjbhsje(Double hjbhsje) {
		this.hjbhsje = hjbhsje;
	}
	public Double getHjse() {
		return hjse;
	}
	public void setHjse(Double hjse) {
		this.hjse = hjse;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public String getXmdw() {
		return xmdw;
	}
	public void setXmdw(String xmdw) {
		this.xmdw = xmdw;
	}
	
	public Double getXmsl() {
		return xmsl;
	}
	public void setXmsl(Double xmsl) {
		this.xmsl = xmsl;
	}
	public String getHsbz() {
		return hsbz;
	}
	public void setHsbz(String hsbz) {
		this.hsbz = hsbz;
	}

	public Double getXmdj() {
		return xmdj;
	}
	public void setXmdj(Double xmdj) {
		this.xmdj = xmdj;
	}
	public String getFphxz() {
		return fphxz;
	}
	public void setFphxz(String fphxz) {
		this.fphxz = fphxz;
	}
	public String getSpbm() {
		return spbm;
	}
	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}
	public String getYhzcbz() {
		return yhzcbz;
	}
	public void setYhzcbz(String yhzcbz) {
		this.yhzcbz = yhzcbz;
	}
	
	public Double getXmje() {
		return xmje;
	}
	public void setXmje(Double xmje) {
		this.xmje = xmje;
	}
	
	public String getGhfyhzh() {
		return ghfyhzh;
	}
	public void setGhfyhzh(String ghfyhzh) {
		this.ghfyhzh = ghfyhzh;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public Double getSe() {
		return se;
	}
	public void setSe(Double se) {
		this.se = se;
	}
	public String getDdh() {
		return ddh;
	}
	public void setDdh(String ddh) {
		this.ddh = ddh;
	}
	public String getThdh() {
		return thdh;
	}
	public void setThdh(String thdh) {
		this.thdh = thdh;
	}
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getKpqd() {
		return kpqd;
	}
	public void setKpqd(String kpqd) {
		this.kpqd = kpqd;
	}
	public String getFpdm() {
		return fpdm;
	}
	public void setFpdm(String fpdm) {
		this.fpdm = fpdm;
	}
	public String getFphm() {
		return fphm;
	}
	public void setFphm(String fphm) {
		this.fphm = fphm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPdfurl() {
		return pdfurl;
	}
	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}
	
	public String getGhfdz() {
		return ghfdz;
	}
	public void setGhfdz(String ghfdz) {
		this.ghfdz = ghfdz;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}
