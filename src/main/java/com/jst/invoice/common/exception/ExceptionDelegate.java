package com.jst.invoice.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: ExceptionDelegate 
 * @Description: 异常代理接口, 主要负责区分业务异常和系统异常
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:11:52 
 * @version V1.0
 */
public class ExceptionDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionDelegate.class);  

	private ExceptionHandler eh;
	
	public ExceptionDelegate(ExceptionHandler eh) {
		this.eh = eh;
	}

	/**
	 * 处理异常
	 * <p>详细描述</p>
	 * @param e
	 */
	public void handle(Throwable e) {
		if (e instanceof Exception) {
			handleBizException((Exception) e);
		} else {
			log.error("系统捕获到一个未知异常", e);
		}
	}
	
	private void handleBizException(Exception e) {
		if (e instanceof JstException) {
			eh.handle((JstException) e);
		} else {
			log.error("系统发生异常", e);
		}
	}
}
