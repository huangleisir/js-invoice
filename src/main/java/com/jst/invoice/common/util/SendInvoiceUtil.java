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

package com.jst.invoice.common.util;

import java.util.HashMap;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.jst.invoice.common.constant.SysConstant;
import com.jst.invoice.common.util.AesUtil;
import com.jst.invoice.common.util.Base64Util;
import com.jst.invoice.common.util.HttpRequestor;
import com.jst.invoice.common.util.TemplateHelper;
import com.jst.invoice.common.util.XmlHelper1;
import com.jst.invoice.bo.KpInterface;
import com.jst.invoice.bo.ReturnInterface;
import com.jst.invoice.config.ConfigSetting;
import com.jst.prodution.util.ILogger;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @Package: com.jst.invoice.common.util
 * @ClassName: SendInvoiceUtil
 * @Description: 发票交互工具类 （航信）
 *
 * @date: 2017年10月30日 上午8:46:52
 * @version V1.0
 */
public class SendInvoiceUtil {
	 private static ILogger log = new ILogger("Invoice", SendInvoiceUtil.class);
	/**
	 * 
	 * @param kpMap
	 *            开票map
	 * @param requestMap
	 *            请求头map
	 * @return
	 * @throws Exception
	 */
	public static Map haseKp(Map kpMap, Map requestMap) {
		String flowNo = (String)kpMap.get("fpqqlsh"); //平台请求流水返回业务系统的订单号 
		Map resultMap = new HashMap();
		log.info("开票Map------>"+JSON.toJSONString(kpMap));
		String content = TemplateHelper.generate(kpMap, "kp.xml");
		try {
			String baseContent = AesUtil.encrypt(content);
			requestMap.put("content", baseContent);
			log.info("content------>"+baseContent);
			// 生成完整请求信息
			// 本地：request_t.xml
			String xml = TemplateHelper.generate(requestMap, "request_t.xml");
			// 发送请求
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("xml", xml);
			String url = ConfigSetting.getProperty("haseKpUrl");// 440300349724458
			try{
				
				log.info("发送报文 :"+JSON.toJSONString(map));
				String info = new HttpRequestor().doPost(url, map);
				log.info("航信返回数据:"+info);
				// 解析返回信息
				XStream xStream = new XStream();
				xStream.alias("interface", KpInterface.class);
				KpInterface kpInterface = XmlHelper1.toBean(info, KpInterface.class);
				String returnCode = kpInterface.getReturnStateInfo().getReturnCode();
				log.info("returnCode:" + returnCode);
				String msg = Base64Util.getFromBase64(kpInterface.getReturnStateInfo().getReturnMessage());
				log.info("returnMessage:" + msg);
				if (returnCode.equals(SysConstant.SUCCESS_CODE)) {
					log.info("content:" + kpInterface.getData().getContent());
					String  aesInfo= AesUtil.decrypt(kpInterface.getData().getContent());
					XStream reStream = new XStream();
					reStream.alias("RESPONSE_FPKJ", ReturnInterface.class);
					ReturnInterface returnInterface = XmlHelper1.toBean(aesInfo, ReturnInterface.class);
					log.info("解密后content:" + aesInfo);
					//resultMap.put("content", content);
					resultMap.put("returnCode", returnCode);
					resultMap.put("pdfUrl",returnInterface.getPDF_URL());
					resultMap.put("fpdm",returnInterface.getFP_DM()); //发票代码
					resultMap.put("fphm",returnInterface.getFP_HM());
					resultMap.put("flowNo", flowNo);
				}else{
					resultMap.put("returnCode", returnCode);
					resultMap.put("content", msg);
					resultMap.put("flowNo", flowNo);
				}
			}catch(Exception e){
				//异常情况 
				log.info("调用航信开票连接出现异常 ："+e.getMessage());
				resultMap.put("returnCode", SysConstant.INVOICE_EXC);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回数据|resultMap:" + JSON.toJSONString(resultMap));
		return resultMap;
	}

}
