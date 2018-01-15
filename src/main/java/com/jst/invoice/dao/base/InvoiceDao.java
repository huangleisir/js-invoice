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

package com.jst.invoice.dao.base;

import org.springframework.stereotype.Repository;

import com.jst.invoice.bean.Invoice;
import com.jst.invoice.bean.InvoiceAuth;
import com.jst.prodution.util.ILogger;

/** 
 * 
 * @Package: com.jst.invoice.dao.base  
 * @ClassName: InvoiceDao 
 * @Description: 发票dao
 *
 * @author: wen 
 * @date: 2017年10月31日 上午10:15:57 
 * @version V1.0 
 */
@Repository
@SuppressWarnings("unchecked")
public class InvoiceDao extends AbstractDao {

	 private static ILogger log = new ILogger("invoice", InvoiceDao.class);
	   
	    private final String MAPPER_NAMESPACE = "Invoice." ;

	    public InvoiceAuth selectKey(String appId){
	    	return baseDao.getById(MAPPER_NAMESPACE.concat("selectKey"), appId) ;
	    }
	    public int save(Invoice invoice){
	    	return baseDao.insert(MAPPER_NAMESPACE.concat("insert"), invoice) ;
	    }
	    public int updateInvoice(Invoice invoice){
	    	return baseDao.insert(MAPPER_NAMESPACE.concat("updateInvoice"), invoice) ;
	    }
	    public Invoice selectInvoiceById(String flowNo){
	    	return baseDao.getById(MAPPER_NAMESPACE.concat("selectInvoiceById"), flowNo) ;
	    }
	    public int selectDetailByddh(String ddh){
	    	return baseDao.getById(MAPPER_NAMESPACE.concat("selectDetailByddh"), ddh) ;
	    }
	    public int selectDetailByFlowno(String flowNo){
	    	return baseDao.getById(MAPPER_NAMESPACE.concat("selectDetailByFlowno"), flowNo) ;
	    }
	    
}
