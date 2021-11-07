package com.mountain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    Contact contact=new Contact("Traffic Group","none","none");
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("The RESTful API in Tranffic System")
                .description("The first edition")
                .termsOfServiceUrl("www.dlut.edu.cn")
                .contact(contact)
                .version("v1.0")
                .build();
    }
    @Bean
    public Docket defaultDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Traffic Group")
                .apiInfo(apiInfo())
                .select()
//               选择读取那一级目录下的包
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
//    如果有多个分组，可以设置多个Docket
}
