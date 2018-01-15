package com.jst.invoice.common.exception;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: PermissionCheckException 
 * @Description: 机构交易权限校验异常处理基类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:19:15 
 * @version V1.0
 */
public class PermissionCheckException extends BusinessException {
	private static final long serialVersionUID = 4712034900235857634L;

	public PermissionCheckException(String msg) {
        super(msg);
    }
    
    public PermissionCheckException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public PermissionCheckException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }

    public PermissionCheckException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

}
