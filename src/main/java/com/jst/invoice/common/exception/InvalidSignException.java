package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: InvalidSignException 
 * @Description: 签名异常处理
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:14:20 
 * @version V1.0
 */
public class InvalidSignException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public InvalidSignException(String msg) {
        super(msg);
    }
    
    public InvalidSignException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public InvalidSignException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public InvalidSignException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
