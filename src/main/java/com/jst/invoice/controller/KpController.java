package com.jst.invoice.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jst.invoice.bean.DownloadPdfUrl;
import com.jst.invoice.bean.Invoice;
import com.jst.invoice.bean.QueryInvoiceStatus;
import com.jst.invoice.bean.RedInvoice;
import com.jst.invoice.bean.RepeatSendEmailBean;
import com.jst.invoice.bean.ResultBean;
import com.jst.invoice.common.constant.SysConstant;
import com.jst.invoice.common.util.CommonUtils;
import com.jst.invoice.sdk.signsdk.SignUtil;
import com.jst.invoice.service.InvoiceService;

@Controller
public class KpController {
	private final Logger log = LoggerFactory.getLogger(KpController.class);

	@Autowired
	private InvoiceService invoiceService;

	@RequestMapping(value = "/createBlueInvoice", method = RequestMethod.POST)
	@ResponseBody
	private Object createBlueInvoice(@RequestBody @Validated Invoice invoice, BindingResult bindingResult)
			throws Exception {
		log.info("开具蓝字发票入参  " + JSON.toJSONString(invoice));
		ResultBean vilidate = null;
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage() + ";");
			}
			vilidate = new ResultBean("201", sb.toString(), "");
			return vilidate;
		}
		//含税标志为为含税 判断项目单价和项目数量是否为空 
		if(invoice.getHsbz().equals("1")){ // 含税
			
		   Double xmdj=	invoice.getXmdj();
		   if(xmdj==null){
			   ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "含税时，项目单价不能为空", "");
				return result;
		   }
		   Double xmsl =invoice.getXmsl();
		   if(xmsl==null){
			   ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "含税时，项目数量不能为空", "");
				return result;
		   }
		  
		}
		//根据订单号判断是否重复  
		int num =invoiceService.selectDetailByDdh(invoice.getDdh());
		if(num>0){
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "订单号已经存在", "");
			return result;
		}
		// 验证邮箱格式是否正确
		boolean flag = CommonUtils.checkEmail(invoice.getGhfemail());
		if (!flag) {
			ResultBean result = new ResultBean("201", "邮箱格式有误", "");
			return result;
		}
		
		if(!CommonUtils.checkMobile(invoice.getGhfsj())){
			ResultBean result = new ResultBean("201", "手机号码有误", "");
			return result;
		}
		String appId = invoice.getAppId();
		String appSign = invoice.getAppSign();
		String appKey = invoiceService.getAppkey(appId);
		if (null == appKey) {
			ResultBean result = new ResultBean("201", "appId有误", "");
			return result;
		}
		// 通过appId 获取加密key
		String sign = SignUtil.getSignature(invoice, appKey);
		log.info("发票平台加密key:" + sign);
		if (!appSign.equals(sign)) {
			ResultBean result = new ResultBean("201", "数据验签失败", "");
			return result;
		}
		// 处理开票
		ResultBean result = (ResultBean) invoiceService.createBlueInvoice(invoice);
		log.info("开具蓝票接口返回结果"+JSON.toJSONString(result));
		return result;
	}
	
	@RequestMapping(value = "/createRedInvoice", method = RequestMethod.POST)
	@ResponseBody
	private Object createRedInvoice(@RequestBody @Validated RedInvoice redInvoice, BindingResult bindingResult)
			throws Exception {
		log.info("开具红字发票入参 ： " + JSON.toJSONString(redInvoice));
		ResultBean vilidate = null;
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage() + ";");
			}
			vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, sb.toString(), "");
			return vilidate;
		}
		String appId = redInvoice.getAppId();
		String appSign = redInvoice.getAppSign();
		String appKey = invoiceService.getAppkey(appId);
		if (null == appKey) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "appId有误", "");
			return result;
		}
		// 通过appId 获取加密key
		String sign = SignUtil.getSignature(redInvoice, appKey);
		log.info("发票平台加密key:" + sign);
		if (!appSign.equals(sign)) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "数据验签失败", "");
			return result;
		}
		//根据订单号判断是否重复  
		int num =invoiceService.selectDetailByDdh(redInvoice.getDdh());
		if(num>0){
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "订单号已经存在", "");
			return result;
		}
		
		//根据订单号判断是否已经开过票  
	   	int haveNum =invoiceService.selectDetailByFlowno(redInvoice.getFlowNo());
			if(!(haveNum>0)){
					ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "该发票流水号没有开票记录，不能发起红冲", "");
					return result;
			}
				
		// 处理开票
		ResultBean result = (ResultBean) invoiceService.createRedInvoice(redInvoice);
        log.info("红冲接口返回结果"+JSON.toJSONString(result));
		return result;
	}
	
	@RequestMapping(value = "/downloadPdfUrl", method = RequestMethod.POST)
	@ResponseBody
	private Object downloadPdfUrl(@RequestBody @Validated DownloadPdfUrl downloadPdfUrl, BindingResult bindingResult)
			throws Exception {
		log.info("下载发票入参 ： " + JSON.toJSONString(downloadPdfUrl));
		ResultBean vilidate = null;
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage() + ";");
			}
			vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, sb.toString(), "");
			return vilidate;
		}
		String appId = downloadPdfUrl.getAppId();
		String appSign = downloadPdfUrl.getAppSign();
		String appKey = invoiceService.getAppkey(appId);
		if (null == appKey) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "appId有误", "");
			return result;
		}
		// 通过appId 获取加密key
		String sign = SignUtil.getSignature(downloadPdfUrl, appKey);
		log.info("发票平台加密key:" + sign);
		if (!appSign.equals(sign)) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "数据验签失败", "");
			return result;
		}
		//根据订单号判断是否已经开过票  
	   	int haveNum =invoiceService.selectDetailByFlowno(downloadPdfUrl.getFlowNo());
			if(!(haveNum>0)){
					ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "该发票流水号没有开票记录。", "");
					return result;
			}
		// 获取发票对象 
		Invoice pdfInvoice =invoiceService.getInvoice(downloadPdfUrl.getFlowNo());
		ResultBean result = new ResultBean(SysConstant.INVOICE_S_CODE, "处理成功", "");
		 Map resultMap =new HashMap();
		resultMap.put("pdfUrl", pdfInvoice.getPdfurl());
		result.setData(resultMap);
        log.info("下载接口返回结果"+JSON.toJSONString(result));
		return result;
	}
	
	@RequestMapping(value = "/queryInvoiceStatus", method = RequestMethod.POST)
	@ResponseBody
	private Object queryInvoiceStatus(@RequestBody @Validated QueryInvoiceStatus queryInvoiceStatus, BindingResult bindingResult)
			throws Exception {
		log.info("发票状态查询入参 ： " + JSON.toJSONString(queryInvoiceStatus));
		ResultBean vilidate = null;
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage() + ";");
			}
			vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, sb.toString(), "");
			return vilidate;
		}
		String appId = queryInvoiceStatus.getAppId();
		String appSign = queryInvoiceStatus.getAppSign();
		String appKey = invoiceService.getAppkey(appId);
		if (null == appKey) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "appId有误", "");
			return result;
		}
		// 通过appId 获取加密key
		String sign = SignUtil.getSignature(queryInvoiceStatus, appKey);
		log.info("发票平台加密key:" + sign);
		if (!appSign.equals(sign)) {
			ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "数据验签失败", "");
			return result;
		}
		//根据订单号判断是否已经开过票  
	   	int haveNum =invoiceService.selectDetailByFlowno(queryInvoiceStatus.getFlowNo());
			if(!(haveNum>0)){
					ResultBean result = new ResultBean(SysConstant.INVOICE_F_CODE, "该发票流水号没有开票记录。", "");
					return result;
			}
		// 获取发票对象 
		Invoice queryInvoice =invoiceService.getInvoice(queryInvoiceStatus.getFlowNo());
		if(SysConstant.INVOICE_HAND.equals(queryInvoice.getStatus())&&queryInvoice.getKplx().equals(SysConstant.BLUE_TYPE)){
			//处理中,组装请求报文 
			ResultBean result =(ResultBean) invoiceService.queryCreateInvoice(queryInvoice);
			return result; 
		}else{
			ResultBean result = new ResultBean(SysConstant.INVOICE_S_CODE, "处理成功", "");
		    Map resultMap =new HashMap();
		    resultMap.put("status", queryInvoice.getStatus());
			resultMap.put("pdfUrl", queryInvoice.getPdfurl());
			result.setData(resultMap);
	        log.info("发票状态查询结果"+JSON.toJSONString(result));
			return result;
		}
	
	}
	
	 /**
	  * 重发邮件 
	  * @return
	  */
	 @RequestMapping(value = "/repeatSendEmail", method = RequestMethod.POST)
	 @ResponseBody
	 public Object repeatSendEmail(@RequestBody @Validated RepeatSendEmailBean repeatSendEmailBean, BindingResult bindingResult){
		 log.info("重发邮件入参 ： " + JSON.toJSONString(repeatSendEmailBean));
			ResultBean vilidate = null;
			if (bindingResult.hasErrors()) {
				StringBuilder sb = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError err : errors) {
					sb.append(err.getDefaultMessage() + ";");
				}
				vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, sb.toString(), "");
				return vilidate;
			}
			// 验证邮箱格式是否正确
			boolean flag = CommonUtils.checkEmail(repeatSendEmailBean.getEmail());
			if (!flag) {
				ResultBean result = new ResultBean("201", "邮箱格式有误", "");
				return result;
			}
			String appId = repeatSendEmailBean.getAppId();
			String appSign = repeatSendEmailBean.getAppSign();
			String appKey = invoiceService.getAppkey(appId);
			if (null == appKey) {
				vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, "appId有误", "");
				return vilidate;
			}
			// 通过appId 获取加密key
			String sign = SignUtil.getSignature(repeatSendEmailBean, appKey);
			log.info("发票平台加密key:" + sign);
			if (!appSign.equals(sign)) {
				vilidate= new ResultBean(SysConstant.INVOICE_F_CODE, "数据验签失败", "");
				return vilidate;
			}
			Invoice  invoice =invoiceService.getInvoice(repeatSendEmailBean.getFlowNo());
			if(null == invoice){
				vilidate = new ResultBean(SysConstant.INVOICE_F_CODE, "该发票流水号没有数据", "");
				return vilidate;
			}
			invoice.setGhfemail(repeatSendEmailBean.getEmail());
			vilidate = invoiceService.repeatSendEmail(invoice);
			log.info("重发邮件返回 ："+JSON.toJSONString(vilidate));
			return vilidate ;
	 }
}
