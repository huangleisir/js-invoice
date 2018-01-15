package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: MsgCheckClassCaseException 
 * @Description: 报文格式校验字段类造型异常处理类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:16:15 
 * @version V1.0
 */
public class MsgCheckClassCaseException extends MsgCheckException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgCheckClassCaseException(String msg) {
        super(msg);
    }
    
    public MsgCheckClassCaseException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgCheckClassCaseException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgCheckClassCaseException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
