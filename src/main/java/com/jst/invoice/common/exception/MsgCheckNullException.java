package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: MsgCheckNullException 
 * @Description: 报文格式校验字段为空异常处理类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:17:13 
 * @version V1.0
 */
public class MsgCheckNullException extends MsgCheckException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgCheckNullException(String msg) {
        super(msg);
    }
    
    public MsgCheckNullException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgCheckNullException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgCheckNullException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
