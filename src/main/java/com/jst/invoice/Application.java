package com.jst.invoice;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;



/**
 * 
 * 
 * @Package: com.jst.message  
 * @ClassName: Application 
 * @Description: SpringBoot启动类
 *
 * @author: lixin 
 * @date: 2016年12月15日 下午2:56:58 
 * @version V1.0
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.jst")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ImportResource(locations={"classpath:spring-beans.xml"})
public class Application extends SpringBootServletInitializer {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50MB");
		factory.setMaxRequestSize("50MB");
		factory.setLocation("tmp");
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class); 
	}

}
