package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: BusinessException 
 * @Description: 业务异常基类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:09:30 
 * @version V1.0
 */
public class BusinessException extends JstException {
	private static final long serialVersionUID = 4712034900235857634L;

	public BusinessException(String msg) {
        super(msg);
    }
    
    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public BusinessException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public BusinessException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

	public String getCode() {
		return code;
	}
}