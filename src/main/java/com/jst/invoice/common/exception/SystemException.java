package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: SystemException 
 * @Description: 系统异常处理基类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:20:14 
 * @version V1.0
 */
public class SystemException extends JstException {
	private static final long serialVersionUID = 4712034900235857634L;

	public SystemException(String msg) {
        super(msg);
    }
    
    public SystemException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public SystemException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public SystemException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
