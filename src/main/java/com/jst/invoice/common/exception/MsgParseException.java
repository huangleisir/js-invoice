package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: MsgParseException 
 * @Description: 报文解析异常处理
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:17:29 
 * @version V1.0
 */
public class MsgParseException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgParseException(String msg) {
        super(msg);
    }
    
    public MsgParseException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgParseException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgParseException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
