/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

@Configuration  
public class DubboConfig {

	//定义dubbo扫描的包
	@Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.jst.invoice.dubbo.service,com.jst.prodution.invoice,com.jst.invoice.service,com.jst.invoice.controller");
        return annotationBean;
    }
	
	
	@Bean
    public ApplicationConfig applicationConfig() {
        // 当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(ConfigSetting.getProperty("dubbo.applicationName"));
        return applicationConfig;
    }
	
	
	@Bean
    public RegistryConfig registryConfig() {
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(ConfigSetting.getProperty("dubbo.protocol"));
        registry.setAddress(ConfigSetting.getProperty("dubbo.registryAddress"));
        registry.setCheck(false);
        return registry;
    }
	
	
	@Bean
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(ConfigSetting.getProperty("dubbo.protocolName"));
        protocolConfig.setPort(ConfigSetting.getIntProperty("dubbo.protocolPort"));
        protocolConfig.setThreads(ConfigSetting.getIntProperty("dubbo.pootThreads"));
        protocolConfig.setThreadpool(ConfigSetting.getProperty("dubbo.poolType"));
        protocolConfig.setAccepts(ConfigSetting.getIntProperty("dubbo.protocolAccepts"));
        //protocolConfig.setServer(dubboConfigSettings.getProtocolServer());
        return protocolConfig;
    }
	
	//定义提供者配置
	@Bean(name="defaultProvider")
    public ProviderConfig providerConfig(@Autowired ApplicationConfig applicationConfig,@Autowired RegistryConfig registryConfig,
    		@Autowired ProtocolConfig protocolConfig) {
        ProviderConfig providerConfig = new ProviderConfig();
        //providerConfig.setTimeout(dubboConfigSettings.getTimeout());
        //providerConfig.setRetries(dubboConfigSettings.getRetries());
        providerConfig.setDelay(ConfigSetting.getIntProperty("dubbo.delay"));
        providerConfig.setConnections(ConfigSetting.getIntProperty("dubbo.referenceConnections"));
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        return providerConfig;
    }
	
	//定义消费者配置
	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig() ;
		consumerConfig.setTimeout(ConfigSetting.getIntProperty("dubbo.timeout"));
		consumerConfig.setRetries(ConfigSetting.getIntProperty("dubbo.retries"));
		return consumerConfig ;
	} 


	
}
