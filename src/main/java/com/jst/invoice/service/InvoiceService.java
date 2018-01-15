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

package com.jst.invoice.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.jst.invoice.bean.Invoice;
import com.jst.invoice.bean.InvoiceAuth;
import com.jst.invoice.bean.RedInvoice;
import com.jst.invoice.bean.ResultBean;
import com.jst.invoice.bo.GlobalInfo;
import com.jst.invoice.common.constant.SysConstant;
import com.jst.invoice.common.util.CalculateUtil;
import com.jst.invoice.common.util.DateUtil;
import com.jst.invoice.common.util.SendInvoiceUtil;
import com.jst.invoice.common.util.SnowflakeIdUtil;
import com.jst.invoice.config.ConfigSetting;
import com.jst.invoice.controller.KpController;
import com.jst.invoice.dao.base.InvoiceDao;
import com.jst.prodution.base.bean.BaseBean;
import com.jst.prodution.email.dubbo.service.SendEmailDuService;
import com.jst.prodution.email.serviceBean.MailBean;
import com.jst.prodution.util.BeanUtils;


/**
 * 
 * @Package: com.jst.invoice.service
 * @ClassName: InvoiceService
 * @Description: 开票服务类
 *
 * @author: hw
 * @date: 2017年10月30日 上午11:03:23
 * @version V1.0
 */
@Service
public class InvoiceService {
	@Autowired
	InvoiceDao invoiceDao;
	private final Logger log = LoggerFactory.getLogger(KpController.class);

	@Reference
	SendEmailDuService  sendEmailDuService  ;// 邮件发送dubbo
	/**
	 * 开正票 （蓝字票）
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Object createBlueInvoice(Invoice invoice) throws Exception {
		GlobalInfo	ginfo =buildGlobalInfo();
		log.info("开票入参 ：" + JSON.toJSONString(invoice));
		String flowNo = "JSFP" + SnowflakeIdUtil.generate(16);
		invoice.setKplx(SysConstant.BLUE_TYPE);
		invoice.setFpqqlsh(flowNo);
		invoice.setDsptbm(ginfo.getUserName());
		invoice.setNsrsbh(ConfigSetting.getProperty("taxpayerId"));
		invoice.setNsrmc(SysConstant.COMPANY_NAME);
		// 不含税金额(四舍五入,保留两位小数)= 含税金额/(1+税率)
		// 税额= 含税金额-不含税金额
		// 不含税总金额=各行不含税金额相加
		// 总税额 = 价税合计-不含税总金额
		Double hsje = invoice.getKphjje(); // 含税金额
		String sl = invoice.getSl(); // 税率
		Double hjb = CalculateUtil.div(hsje, CalculateUtil.add(Double.valueOf(1), Double.valueOf(sl)), 4); // 不含税金额
		invoice.setHjbhsje(hjb);
		invoice.setHjse(CalculateUtil.subtract(hsje, hjb)); // 合计税额
		if(invoice.getHsbz().equals("1")){ // 含税
			Double xmdj = invoice.getXmdj(); // 项目单价
			Double xmsl = invoice.getXmsl();// 项目数量
			invoice.setXmje(CalculateUtil.mul(xmdj, xmsl));
		}
		invoice.setSe(CalculateUtil.subtract(hsje, hjb));
		// 保存发票流水
		saveInvoiceDetail(invoice);
		Map kpMap = BeanUtils.converToMap(invoice);
		Map requestMap = BeanUtils.converToMap(ginfo);

		Map resultMap = SendInvoiceUtil.haseKp(kpMap, requestMap);
		ResultBean result = null;
		Invoice updateInvoice = new Invoice();
		updateInvoice.setFpqqlsh(flowNo);
		if(!resultMap.get("returnCode").equals(SysConstant.INVOICE_EXC)){
			if (resultMap.get("returnCode").equals(SysConstant.SUCCESS_CODE)) {
				String pdfurl = (String) resultMap.get("pdfUrl");
				updateInvoice.setFpdm((String) resultMap.get("fpdm"));
				updateInvoice.setFphm((String) resultMap.get("fphm"));
				updateInvoice.setPdfurl(pdfurl);
				updateInvoice.setStatus(SysConstant.INVOICE_SUCCESS);
				result = new ResultBean(SysConstant.INVOICE_S_CODE, "开票成功", "");
				result.setData(resultMap);
				// 发邮件
				MailBean  input  =  new MailBean();
				input.setPdfUrl(pdfurl);
				input.setFlowNo(flowNo);
				input.setTo(invoice.getGhfemail());
				input.setSubject("【电子发票】您收到一张新的电子发票[发票号码："+(String) resultMap.get("fphm")+"]");
				input.setContent(buildMailContext(resultMap,invoice));
				log.info("createBlueInvoice|发送邮件入参 ---"+JSON.toJSONString(input));
				MailBean  resBean = (MailBean) sendEmailDuService.action(input);
				log.info("createBlueInvoice|发送邮件出参 ---"+JSON.toJSONString(resBean));
			} else {
				updateInvoice.setStatus(SysConstant.INVOICE_FAIL);
				result = new ResultBean(SysConstant.INVOICE_F_CODE, "开票失败", "");
				result.setData(resultMap);
			}
			// 请求完更新状态
			updateInvoiceDetail(updateInvoice);
		}else{
			//发送异常情况  
			result = new ResultBean(SysConstant.INVOICE_EXC, "开票请求航信超时", "");
		}
		
		return result;
	}

	/**
	 * 发票红冲 
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Object createRedInvoice(RedInvoice invoice) throws Exception {

		String flowNo = invoice.getFlowNo();// 流水号
		String ddh = invoice.getDdh();
		Invoice invObj = invoiceDao.selectInvoiceById(flowNo); // 正票对象
		invObj.setKplx(SysConstant.RED_TYPE);// 红票
		GlobalInfo	ginfo =buildGlobalInfo();
		log.info("开票入参 ：" + JSON.toJSONString(invoice));
		String redFlowNo = SysConstant.SYS_MARK+ SnowflakeIdUtil.generate(16);
		invObj.setFpqqlsh(redFlowNo);
		invObj.setDsptbm(ginfo.getUserName());
		invObj.setDdh(ddh);// 订单号
		// 红票转负数
		invObj.setSe(-invObj.getKphjje());
		invObj.setKphjje(-invObj.getKphjje());
		invObj.setHjbhsje(-invObj.getHjbhsje());
		invObj.setHjse(-invObj.getHjse());
		invObj.setXmsl(-invObj.getXmsl());
		invObj.setNsrsbh(ConfigSetting.getProperty("taxpayerId"));
		invObj.setNsrmc(SysConstant.COMPANY_NAME);
		saveInvoiceDetail(invObj);
		Map kpMap = BeanUtils.converToMap(invObj);
		Map requestMap = BeanUtils.converToMap(ginfo);
		Map resultMap = SendInvoiceUtil.haseKp(kpMap, requestMap);
		ResultBean result = null;
		Invoice updateInvoice = new Invoice();
		updateInvoice.setFpqqlsh(redFlowNo);
		if(!resultMap.get("returnCode").equals(SysConstant.INVOICE_EXC)){
			if (resultMap.get("returnCode").equals(SysConstant.SUCCESS_CODE)) {
				String pdfurl = (String) resultMap.get("pdfUrl");
				updateInvoice.setFpdm((String) resultMap.get("fpdm"));
				updateInvoice.setFphm((String) resultMap.get("fphm"));
				updateInvoice.setPdfurl(pdfurl);
				updateInvoice.setStatus(SysConstant.INVOICE_SUCCESS);
				result = new ResultBean(SysConstant.INVOICE_S_CODE, "开票成功", "");
				result.setData(resultMap);
				// 发邮件
				MailBean  input  =  new MailBean();
				input.setPdfUrl(pdfurl);
				input.setFlowNo(redFlowNo);
				input.setTo(invObj.getGhfemail());
				// 发邮件
				input.setSubject("【电子发票】您的电子发票[发票号码:"+(String) resultMap.get("fphm")+"]已冲销");
				input.setContent(buildRedContext(resultMap,invObj));
				log.info("createRedInvoice|发送邮件入参 ---"+JSON.toJSONString(input));
				MailBean  resBean = (MailBean) sendEmailDuService.action(input);
				log.info("createRedInvoice|发送邮件出参 ---"+JSON.toJSONString(resBean));
			} else {
				updateInvoice.setStatus(SysConstant.INVOICE_FAIL);
				result = new ResultBean(SysConstant.INVOICE_F_CODE, "开票失败", "");
				result.setData(resultMap);
			}
			// 请求完更新状态
			updateInvoiceDetail(updateInvoice);
		}else{
			//发送异常情况  
			result = new ResultBean(SysConstant.INVOICE_EXC, "开票请求航信超时", "");
		}
		
		return result;
	}

	/**
	 * 查询发起开票（原开票异常情况下出现）
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Object queryCreateInvoice(Invoice invoice) throws Exception {
		GlobalInfo	ginfo =buildGlobalInfo();
		log.info("查询发起开票 ：" + JSON.toJSONString(invoice));
		String flowNo = SysConstant.SYS_MARK + SnowflakeIdUtil.generate(16);
		invoice.setFpqqlsh(flowNo);
		invoice.setDsptbm(ginfo.getUserName());
		invoice.setNsrsbh(ConfigSetting.getProperty("taxpayerId"));
		invoice.setNsrmc(SysConstant.COMPANY_NAME);
		// 不含税金额(四舍五入,保留两位小数)= 含税金额/(1+税率)
		// 税额= 含税金额-不含税金额
		// 不含税总金额=各行不含税金额相加
		// 总税额 = 价税合计-不含税总金额
		Double hsje = invoice.getKphjje(); // 含税金额
		String sl = invoice.getSl(); // 税率
		Double hjb = CalculateUtil.div(hsje, CalculateUtil.add(Double.valueOf(1), Double.valueOf(sl)), 4); // 不含税金额
		invoice.setHjbhsje(hjb);
		invoice.setHjse(CalculateUtil.subtract(hsje, hjb)); // 合计税额
		Double xmdj = invoice.getXmdj(); // 项目单价
		Double xmsl = invoice.getXmsl();// 项目数量
		invoice.setXmje(CalculateUtil.mul(xmdj, xmsl));
		invoice.setSe(CalculateUtil.subtract(hsje, hjb));
		Map kpMap = BeanUtils.converToMap(invoice);
		Map requestMap = BeanUtils.converToMap(ginfo);

		Map resultMap = SendInvoiceUtil.haseKp(kpMap, requestMap);
		ResultBean result = null;
		Invoice updateInvoice = new Invoice();
		updateInvoice.setFpqqlsh(flowNo);
		if(!resultMap.get("returnCode").equals(SysConstant.INVOICE_EXC)){
			if (resultMap.get("returnCode").equals(SysConstant.SUCCESS_CODE)) {
				String pdfurl = (String) resultMap.get("pdfUrl");
				updateInvoice.setFpdm((String) resultMap.get("fpdm"));
				updateInvoice.setFphm((String) resultMap.get("fphm"));
				updateInvoice.setPdfurl(pdfurl);
				updateInvoice.setStatus(SysConstant.INVOICE_SUCCESS);
				result = new ResultBean(SysConstant.INVOICE_S_CODE, "开票成功", "");
				result.setData(resultMap);
				MailBean  input  =  new MailBean();
				input.setPdfUrl(pdfurl);
				input.setFlowNo(flowNo);
				input.setTo(invoice.getGhfemail());
				input.setSubject("【电子发票】您收到一张新的电子发票[发票号码："+(String) resultMap.get("fphm")+"]");
				input.setContent(buildMailContext(resultMap,invoice));
				log.info("queryCreateInvoice|发送邮件入参 ---"+JSON.toJSONString(input));
				sendEmailDuService.action(input);
			} else {
				updateInvoice.setStatus(SysConstant.INVOICE_FAIL);
				result = new ResultBean(SysConstant.INVOICE_F_CODE, "开票失败", "");
				result.setData(resultMap);
			}
			// 请求完更新状态
			updateInvoiceDetail(updateInvoice);
		}else{
			//发送异常情况  
			result = new ResultBean(SysConstant.INVOICE_EXC, "开票请求航信超时", "");
		}
		
		return result;
	}
	
	/**
	 * 邮件重发  
	 * @param invoice
	 * @return
	 */
	public ResultBean repeatSendEmail(Invoice invoice){
		ResultBean result = null ;
		MailBean  input  =  new MailBean();
		input.setPdfUrl(invoice.getPdfurl());
		input.setFlowNo(invoice.getFpqqlsh());
		input.setTo(invoice.getGhfemail());
		input.setSubject("【电子发票】您收到一张新的电子发票[发票号码："+invoice.getFphm()+"]");
		Map resultMap = new HashMap();
		resultMap.put("fphm", invoice.getFphm());
		resultMap.put("fpdm", invoice.getFpdm());
		input.setContent(buildMailContext(resultMap,invoice));
		log.info("repeatSendEmail|发送邮件入参 ---"+JSON.toJSONString(input));
		MailBean bean  = (MailBean) sendEmailDuService.action(input);
		if(bean.getResCode().equals(BaseBean.RES_CODE_SUCCESS)){
			result = new ResultBean(SysConstant.INVOICE_S_CODE, "发送成功", "");
		}else{
			result = new ResultBean(SysConstant.INVOICE_F_CODE, "发送失败", "");
		}
		return result;
		
	}
	
	/**
	 * 正票模版
	 * @param map
	 * @param invoice
	 * @return
	 */
	public String buildMailContext(Map map,Invoice invoice){
	    StringBuffer bf = new StringBuffer();
	     bf.append("尊敬的客户您好：\r\n");
	     bf.append("\r"+DateUtil.getSysDate()+"您开具的电子发票，我们将电子发票发送给您，以便作为您的报销凭证，您可以打印附件发票信息进行报销。\r\n");
	     bf.append("  \r发票信息如下:\r\n");
	     bf.append(" \r开票日期 ："+DateUtil.getSysDate()+"\r\n");
	     bf.append("\r发票代码 ："+(String) map.get("fpdm")+"\r\n");
	     bf.append("\r发票号码 ："+(String) map.get("fphm")+"\r\n");
	     bf.append("\r销货方名称  ："+invoice.getXhfmc()+"\r\n");
	     bf.append("\r购货方名称  ："+invoice.getGhfmc()+"\r\n");
	     bf.append("\r价税合计  ：￥"+invoice.getKphjje()+"\r\n");
	     bf.append("附件是电子发票PDF文件，供下载使用。");
	     return bf.toString();
	}

	/**
	 * 反票模版 
	 * @param map
	 * @param invoice
	 * @return
	 */
	public String buildRedContext(Map map,Invoice invoice){
	    StringBuffer bf = new StringBuffer();
	     bf.append("尊敬的客户您好：\r\n");
	     bf.append("\r"+DateUtil.getSysDate()+"您开具的电子发票已冲销。\r\n");
	     bf.append("   \r发票冲销信息如下:\r\n");
	     bf.append("   \r冲销日期："+DateUtil.getSysDate()+"\r\n");
	     bf.append("   \r红字发票代码 ："+(String) map.get("fpdm")+"\r\n");
	     bf.append("   \r红字发票号码 ："+(String) map.get("fphm")+"\r\n");
	     bf.append("   \r销货方名称  ："+invoice.getXhfmc()+"\r\n");
	     bf.append("   \r购货方名称  ："+invoice.getGhfmc()+"\r\n");
	     bf.append("   \r价税合计  ：￥"+invoice.getKphjje()+"\r\n");
	     bf.append("附件是红字电子发票PDF文件，请核对。");
	     return bf.toString();
	}
	
	protected  GlobalInfo buildGlobalInfo(){
		GlobalInfo ginfo = new GlobalInfo();
		ginfo.setInterfaceCode(ConfigSetting.getProperty("interfaceCode"));
		// 10位随机数+Base64(10位随机数+注册码)
		// String regCode =ConfigSetting.getProperty("regCode");
		// String password= PasswordUtil.getPassword(regCode);
		String userName = ConfigSetting.getProperty("sysCode");
		ginfo.setUserName(userName);
		ginfo.setPassWord("8405049080NvlJec2scNyzY5YeEjUSAg==");
		ginfo.setTaxpayerId(ConfigSetting.getProperty("taxpayerId"));// 税号
		ginfo.setAuthorizationCode(ConfigSetting.getProperty("authorizationCode"));
		String ptCode = ConfigSetting.getProperty("ptCode");// 平台编码
		ginfo.setRequestCode(ptCode);
		ginfo.setRequestTime(DateUtil.getSysDateTimeMillis());
		ginfo.setResponseCode(ConfigSetting.getProperty("responseCode"));
		String exchangeId = ptCode + DateUtil.getCuSysDate() + SnowflakeIdUtil.generate(9);
		ginfo.setDataExchangeId(exchangeId); // P0000001ECXML.FPKJ.BC.E_INV000000001
		return ginfo;
	}
	/**
	 * 获取appkey
	 * 
	 * @param appId
	 * @return
	 */
	public String getAppkey(String appId) {
		String appkey = null;
		InvoiceAuth auth = invoiceDao.selectKey(appId);
		if (null != auth) {
			appkey = auth.getAppKey();
		}
		return appkey;
	}

	/**
	 * 保存流水明细
	 * 
	 * @param invoice
	 * @return
	 */
	public int saveInvoiceDetail(Invoice invoice) {
		invoice.setCreateTime(DateUtil.getSysDateTime());
		invoice.setStatus(SysConstant.INVOICE_HAND);
		int num = invoiceDao.save(invoice);
		return num;
	}

	/**
	 * 更新发票明细
	 * 
	 * @param invoice
	 * @return
	 */
	public int updateInvoiceDetail(Invoice invoice) {
		invoice.setUpdateTime(DateUtil.getSysDateTime());
		int num = invoiceDao.updateInvoice(invoice);
		return num;
	}

	/**
	 * 红冲根据原开票流水好判断是否开过票  
	 * @return
	 */
	public int selectDetailByFlowno(String flowNo){
		return  invoiceDao.selectDetailByFlowno(flowNo);
	}
	
	/**
	 * 订单是否存在
	 * @param ddh
	 * @return
	 */
	public int selectDetailByDdh(String ddh){
		return  invoiceDao.selectDetailByddh(ddh);
	}
	
	/**
	 * 获取发票对象
	 * @return
	 */
	public Invoice getInvoice(String flowNo){
		Invoice invObj = invoiceDao.selectInvoiceById(flowNo); 
		return invObj;
	}
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
}
