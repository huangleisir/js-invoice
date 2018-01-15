package com.jst.invoice.common.exception;

import com.jst.invoice.common.enums.ReturnCodeEnum;

/**
 * 
 * 
 * @Package: com.tomtop.framework.common.exception  
 * @ClassName: TOMTOPException 
 * @Description: TOMTOP项目电商平台异常基类
 *
 * @author: lixin 
 * @date: 2016年8月16日 下午8:21:03 
 * @version V1.0
 */
public class JstException extends Exception {
	private static final long serialVersionUID = 4712034900235857634L;
	/** 错误码 */
	protected String code;
	
	public JstException(String msg) {
        super(msg);
        this.code = ReturnCodeEnum.SYSTEM_UNKNOWN_ERROR.getCode();
    }
    
    public JstException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    
    public JstException(String msg, Throwable t) {
        super(msg, t);
        this.code = ReturnCodeEnum.SYSTEM_UNKNOWN_ERROR.getCode();
        this.setStackTrace(t.getStackTrace());
    }

    public JstException(String code, String msg, Throwable t) {
    	super(msg, t);
    	this.code = code;
    	this.setStackTrace(t.getStackTrace());
    }

	public String getCode() {
		return code;
	}
    
}
