package com.jst.invoice.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @Package: com.tomtop.framework.core.redis  
 * @ClassName: ICacheTemplate 
 * @Description: 缓存模版接口
 *
 * @author: lixin 
 * @date: 2016年9月8日 下午6:21:26 
 * @version V1.0
 */
public interface ICacheTemplate {
	/** 
     * 设置一个key的过期时间（单位：秒） 
     * @param key key值 
     * @param timeout 多少秒后过期 
     * @param timeUnit 时间过期周期
     * @return true：设置了过期时间  false：没有设置过期时间/不能设置过期时间 
     */  
	public Boolean expire(String key, long timeout,TimeUnit timeUnit);
    
    /** 
     * 设置一个key在某个时间点过期 
     * @param key key值 
     * @param Date date  该key在某个设置的Date时间点过期
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间 
     */  
    public Boolean expireAt(String key, Date date);
    
    /**
     * 设置单个值
     * @param key
     * @param value
     * @return
     */
    public void set(String key, String value) ;
    
    
    /**
     *  设置某个值，同时设置该key的超时时间
     * @param key
     * @param value
     * @param second
     * @return
     */
    public void setex(String key, String value, long timeout) ;
    
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key);
  
    
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key, String defaultValue);
    
    /**
     * 删除单个值
     * @param key
     * @return
     */
    public void delete(String key) ;
    
   
}
