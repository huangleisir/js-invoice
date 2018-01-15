/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.common.enums;

public enum ReturnCodeEnum {
    /**  系统公共返回码定义  */
	
	SUCCESS("200", "成功"),
	FAIL("201", "系统繁忙"),
	SYSTEM_UNKNOWN_ERROR("0000", "系统未知错误"), 
	SYSTEM_DATA_NOTFUND("16001", "请求数据错误"), 
    SYSTEM_TIMEOUT("16002", "处理超时"), 
    SYSTEM_CONFIG_ERROR ("16003", "未成功加载配置文件"), 
    DATABASE_CONNECT_TIMEOUT("16004","连接数据库超时"),
    SQL_EXCEPTION("16005","Sql异常"),
    
    PARAM_NULL("160100","接口输入参数为空串或者null"),
    PARAM_INVALID("160101","接口输入参数不合法"),
    SING_ERROR("160102","报文校验失败"),
    APPKEY_ERROR("160103","appkey错误"),
    MSG_PARSE_ERROR("160104","报文解析错误"),
   
    ;
    
    /* 返回码  */
    private String code;
    
    /* 返回码描述 */
    private String desc;
    
    private ReturnCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
