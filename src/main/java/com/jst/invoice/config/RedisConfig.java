package com.jst.invoice.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.jst.invoice.redis.BaseRedisTemplate;
import com.jst.prodution.member.serviceBean.LoginBean;


/**
 * 
 * 
 * @Package: com.jst.message.config  
 * @ClassName: RedisConfig 
 * @Description: Redis配置
 *
 * @author: Administrator 
 * @date: 2016年12月27日 上午11:40:37 
 * @version V1.0
 */
@Configuration  
@EnableCaching
public class RedisConfig   {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);  

	
    @Bean  
    public KeyGenerator keyGenerator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append("_"+obj.toString());  
                }  
                return sb.toString();  
            }  
        };  
  
    }  
  
	@Bean(name = "baseRedisTemplate")
	public BaseRedisTemplate getBaseRedisTemplate(
			@Autowired JedisConnectionFactory jedisConnectionFactory) {
		BaseRedisTemplate  baseRedisTemplate = new BaseRedisTemplate();
		baseRedisTemplate.setConnectionFactory(jedisConnectionFactory);
		return baseRedisTemplate;
	}
	
	@Bean(name = "lstringRedisTemplate")
	public StringRedisTemplate getStringRedisTemplate(
			@Autowired JedisConnectionFactory jedisConnectionFactory) {
		StringRedisTemplate  stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
		return stringRedisTemplate;
	}
	
	 @Bean(name = "loginBeanTemplate")
	    public RedisTemplate<String, LoginBean> redisMsgTemplate(@Autowired JedisConnectionFactory factory) {
	        RedisTemplate<String, LoginBean> template = new RedisTemplate<String, LoginBean>();
	        template.setConnectionFactory(factory);
	    /*    template.setKeySerializer(new StringRedisSerializer());
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);  
	        ObjectMapper om = new ObjectMapper();  
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
	        jackson2JsonRedisSerializer.setObjectMapper(om);  
	        template.setValueSerializer(jackson2JsonRedisSerializer);  
	        template.afterPropertiesSet(); */
	        return template;
	    }
	
	@Bean(name = "redisTemplate")
	public RedisTemplate<Object, Object> getRedisTemplate(
			@Autowired JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
    
    
   
	
}
