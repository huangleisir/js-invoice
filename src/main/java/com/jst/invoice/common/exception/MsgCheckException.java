package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: MsgCheckException 
 * @Description: 报文格式校验异常处理类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:16:34 
 * @version V1.0
 */
public class MsgCheckException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgCheckException(String msg) {
        super(msg);
    }
    
    public MsgCheckException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgCheckException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgCheckException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
