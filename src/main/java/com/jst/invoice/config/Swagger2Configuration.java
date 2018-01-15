package com.jst.invoice.config;

import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    /**
    *
    * @return
    */
    @Bean
    public Docket accessToken() {

        return new Docket(DocumentationType.SWAGGER_2).groupName("demo")// 定义组
                .select() // 选择那些路径和 api 会生成 document
                .apis(RequestHandlerSelectors.basePackage("com.jst.demo.controller")) // 拦截的包路径
                .paths(regex("/.*"))// 拦截的接口路径
                .build() // 创建
                .apiInfo(apiInfo()); // 配置说明
    }
    
    

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
                .title("捷顺金科")// 标题
                .description("智能停车")// 描述
                .termsOfServiceUrl("www.jsfintech.cn")//
                .contact(new Contact("lixin", "www.jsfintech.cn", "xin.li@jieshunpay.cn"))// 联系
                // .license("Apache License Version 2.0")// 开源协议
                // .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")//
                // 地址
                .version("1.0")// 版本
                .build();
    }
}
