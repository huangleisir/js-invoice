package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: MsgParseNullException 
 * @Description: 报文解析消息为空异常处理
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:18:11 
 * @version V1.0
 */
public class MsgParseNullException extends MsgParseException {
	private static final long serialVersionUID = 4712034900235857634L;

	public MsgParseNullException(String msg) {
        super(msg);
    }
    
    public MsgParseNullException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public MsgParseNullException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public MsgParseNullException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
