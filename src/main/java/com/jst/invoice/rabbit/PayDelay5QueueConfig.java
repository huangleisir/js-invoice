/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.rabbit;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jst.invoice.config.ConfigSetting;



/**
 * 
 * 
 * @Package: com.jst.framework.rabbit  
 * @ClassName: PayDelay5Queue 
 * @Description:  延时队列，支付时间超过五分钟后支付订单失败
 *
 * @author: Administrator 
 * @date: 2017年7月25日 下午3:40:41 
 * @version V1.0
 */
//@Configuration
public class PayDelay5QueueConfig {
	
	
	private static Logger logger = LoggerFactory.getLogger(PayDelay5QueueConfig.class);	
	
	 @Autowired
	private AmqpTemplate amqpTemplate;
	 
	 private static AmqpTemplate localAmqpTemplate;
	 
	 @PostConstruct
	    public void init() {
		 localAmqpTemplate = this.amqpTemplate;
		 //设置默认
		/* RabbitTemplate template = (RabbitTemplate) this.amqpTemplate ;
		 template.setExchange(ConfigUtil.getProperty("mq.exchange.payDelay5DirectExchange"));
		 template.setRoutingKey(ConfigUtil.getProperty("mq.key.payDelay5Key"));
		 localAmqpTemplate = template ;*/
	    }
	 
	   //延时队列定义
	    @Bean
		public Queue payDelay5Queue() {
				Map<String, Object> arguments = new HashMap<String, Object>() ;
				arguments.put("x-message-ttl", Long.valueOf("30000000")) ;
				//定义死信路由
				arguments.put("x-dead-letter-exchange", ConfigSetting.getProperty("mq.exchange.payDelay5DirectExchange")) ;
				arguments.put("x-dead-letter-routing-key", ConfigSetting.getProperty("mq.key.payDelay5Key")+"_target") ;
				return new Queue(ConfigSetting.getProperty("mq.queue.payDelay5Queue"), true, false, false, arguments) ;
		} 
	    
	    //定义延时队列通过死信路由转发的目标队列
	    @Bean
		public Queue payDelay5QueueTarget() {
				return new Queue(ConfigSetting.getProperty("mq.queue.payDelay5Queue")+"_target", true, false, false) ;
		} 
	    
	    @Bean
	    DirectExchange payDelay5DirectExchange() {
	        return new DirectExchange(ConfigSetting.getProperty("mq.exchange.payDelay5DirectExchange"), true, false);
	    }
	    
	    //绑定延时队列的key
	    @Bean
	    Binding payDelay5Key(@Qualifier("payDelay5Queue") Queue payDelay5Queue,@Qualifier("payDelay5DirectExchange") DirectExchange payDelay5DirectExchange) {
	        return BindingBuilder.bind(payDelay5Queue).to(payDelay5DirectExchange).with(ConfigSetting.getProperty("mq.key.payDelay5Key"));
	    }
	    
	   //绑定延时队列的死信队列的key
	    @Bean
	    Binding payDelay5KeyTarget(@Qualifier("payDelay5QueueTarget") Queue payDelay5QueueTarget,@Qualifier("payDelay5DirectExchange") DirectExchange payDelay5DirectExchange) {
	        return BindingBuilder.bind(payDelay5QueueTarget).to(payDelay5DirectExchange).with(ConfigSetting.getProperty("mq.key.payDelay5Key")+"_target");
	    }
	 
	 //监听延时队列的死信队列达到延时的效果
	@RabbitListener(queues="${mq.queue.payDelay5Queue}"+"_target")
    public void processMessage(Message message) {
		        logger.info("=======================收到mq信息："+new String(message.getBody()));
    }

	
	public static void send(String msg) {
		logger.info("---mqsend---300000--------"+msg);
		MessageProperties messageProperties = new MessageProperties() ;
		messageProperties.setExpiration("30000");
		Message message = new Message(msg.getBytes(), messageProperties) ;
		localAmqpTemplate.convertAndSend(ConfigSetting.getProperty("mq.exchange.payDelay5DirectExchange"),ConfigSetting.getProperty("mq.key.payDelay5Key"),message);
	}

}
