
package com.jst.invoice.common.util;

/**
 * 
 * 
 * @Package: com.tomtop.framework.common.constant  
 * @ClassName: SystemConstants 
 * @Description: 系统参数常量类
 *
 * @author: lixin 
 * @date: 2016年9月8日 下午6:25:07 
 * @version V1.0
 */
public interface SystemConstants {
	
	/**
	 * 服务名
	 */
	public static String SYSTEM_NAME="system.name";
	
	/**
	 * 该服务部署在哪个域，最大值不可超过7 ，从0开始，最多扩到8个机房
	 */
	public static String SYSTEM_REGION_INDEX="system.region.index";
	
	/**
	 * 第几个服务,最大值不可以超过1023，最多一个机房可以扩1024个服务，算上机器复用
	 */
	public static String SYSTEM_WORKER_INDEX="system.worker.index";
	
	/**
	 * rabbitmq主机地址
	 */
	
    public static final String RABBITMQ_HOSTADDR = "rabbitmq.hostaddr";
    
    /**
     * rabbitmq端口
     */
    public static final String RABBITMQ_PORT = "rabbitmq.port";
    
    /**
     * rabbitmq用户名
     */
    public static final String RABBITMQ_USERNAME = "rabbitmq.username";
    
    /**
     * rabbitmq密码
     */
    public static final String RABBITMQ_PASSWD = "rabbitmq.password";
    
    /**
     * rabbitmq队列名
     */
    public static final String RABBITMQ_SYSLOG_QUEUENAME = "rabbitmq.queuename";
    
    /**
     * rabbitmq exchange名
     */
    public static final String RABBITMQ_SYSLOG_EXCHANGE = "rabbitmq.exchange";
    
    /**
     * rabbitmq routerKey名
     */
    public static final String RABBITMQ_SYSLOG_ROUTERKEY = "rabbitmq.routerKey";
    
    /**
     * rabbitmq channel 个数
     */
    public static final String RABBITMQ_CHANNEL_COUNT = "rabbitmq.channel.count";
    
}


