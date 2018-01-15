package com.jst.invoice.common.exception;

/**
 * 
 * @ClassName: SendRefuseException 
 * @Description: 发送消息到队列异常
 * @author Lee 
 * @date 2015-7-3 下午2:41:39
 */
@SuppressWarnings("serial")
public class SendRefuseException extends SystemException {

    
    public SendRefuseException(String msg) {
        super(msg);
    }
    
    public SendRefuseException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public SendRefuseException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public SendRefuseException(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
        this.setStackTrace(t.getStackTrace());
    }
    
    
}
