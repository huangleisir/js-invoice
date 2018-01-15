package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: HTTPSendException 
 * @Description: 消息发送异常处理类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:15:29 
 * @version V1.0
 */
public class HTTPSendException extends SystemException {
	private static final long serialVersionUID = 4712034900235857634L;

	public HTTPSendException(String msg) {
        super(msg);
    }
    
    public HTTPSendException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public HTTPSendException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public HTTPSendException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
