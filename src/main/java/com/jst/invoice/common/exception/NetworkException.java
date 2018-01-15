package com.jst.invoice.common.exception;


/**
 * 
 * @ClassName: NetworkException 
 * @Description: 数据入库网络连接异常处理基类 
 * @author Lee 
 * @date 2015-6-30 下午12:27:32
 */
public class NetworkException extends SystemException {
	private static final long serialVersionUID = 4712034900235857634L;

	public NetworkException(String msg) {
        super(msg);
    }
    
    public NetworkException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public NetworkException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public NetworkException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
