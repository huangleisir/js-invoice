package com.jst.invoice.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.invoice.common.bean.Result;
import com.jst.invoice.common.enums.ReturnCodeEnum;
import com.jst.prodution.utils.exception.JstException;


/**
 * 
 * 
 * @Package: 
 * @ClassName: GlobalExceptionHandler 
 * @Description: 统一异常处理类
 * see http://spring.io/guides/tutorials/bookmarks/  搜索ExceptionHandler
 *
 * @author: lixin 
 * @date: 2016年10月6日 下午3:12:53 
 * @version V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);  

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleOtherExceptions(Exception e) throws Exception {
        	
    	Result result = null;
    	
    	if(e instanceof JstException){
    		log.error(e.getMessage(),e);
    		result =  new Result( ((JstException) e).getMessageCode(), ((JstException) e).getMessageContent());
    	} else 	if ( e instanceof org.springframework.beans.TypeMismatchException ) {
    		log.error(ReturnCodeEnum.SYSTEM_DATA_NOTFUND.getCode(),ReturnCodeEnum.SYSTEM_DATA_NOTFUND.getDesc(),e);
    		result =  new Result(ReturnCodeEnum.SYSTEM_DATA_NOTFUND.getCode(),ReturnCodeEnum.SYSTEM_DATA_NOTFUND.getDesc(), 
    				"MsgParseException: " + e.getMessage());
    	} else {
    		log.error(ReturnCodeEnum.FAIL.getDesc(),e);
    		result = new Result(ReturnCodeEnum.FAIL.getCode(), ReturnCodeEnum.FAIL.getDesc());
    	}
    	
    	return result;
    }

	
}
