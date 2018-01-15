/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.rabbit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.jst.invoice.config.ConfigSetting;



/**
 * 
 * 
 * @Package: com.jst.framework.rabbit  
 * @ClassName: DemoQueue 
 * @Description: TODO
 *
 * @author: Administrator 
 * @date: 2017年7月25日 下午3:40:41 
 * @version V1.0
 */
//@Configuration
public class DemoQueue {
	
	
	private static Logger logger = LoggerFactory.getLogger(DemoQueue.class);	
	
	 @Autowired
	private AmqpTemplate amqpTemplate;
	 
	 private static AmqpTemplate localAmqpTemplate;
	 
	 @PostConstruct
	    public void init() {
		 localAmqpTemplate = this.amqpTemplate;
	    }

	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(value = "${mq.queue.demo}", durable = "true"),
	        exchange = @Exchange(value = "${mq.exchange.demo}", type = ExchangeTypes.TOPIC),
	        key = "${mq.key.demo}"))
    public void processMessage(Message message) {
		        logger.info("=======================收到mq信息："+new String(message.getBody()));
    }

	
	public static void send(String msg) {
		logger.info("---mqsend-----------"+msg);
		MessageProperties messageProperties = new MessageProperties() ;
		Message message = new Message(msg.getBytes(), messageProperties) ;
		localAmqpTemplate.convertAndSend(ConfigSetting.getProperty("mq.exchange.demo"),ConfigSetting.getProperty("mq.key.demo"),message);
	}

}
