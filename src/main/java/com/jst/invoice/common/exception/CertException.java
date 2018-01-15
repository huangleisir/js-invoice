package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: CertException 
 * @Description: 证书异常处理
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:20:34 
 * @version V1.0
 */
public class CertException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public CertException(String msg) {
        super(msg);
    }
    
    /**
     * 构造.
     * @param msg 错误信息
     * @param t 前一异常
     */
    public CertException(String msg, Throwable t) {
        super(msg, t);
        
        this.setStackTrace(t.getStackTrace());
    }
}