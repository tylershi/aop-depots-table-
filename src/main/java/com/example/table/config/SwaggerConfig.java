package com.example.table.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:24
 * @Version 1.0
 * @Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${swagger.enable}")
  private Boolean enableSwagger;

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        //页面标题
        .title("分库分表演示接口")
        //版本号
        .version("1.0")
        //描述
        .description("基于AOP实现分库分表")
        .build();
  }

  /**
   * swagger2的配置文件，这里可以配置swagger2的一些基本的内容， 比如扫描的包等等
   *
   * @return docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        // 含有Api的注解
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        // 包扫描
        // .apis(RequestHandlerSelectors.basePackage("com.example.service.web.controller"))
        // 含有 RestController 注解
        // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .build()
        //.globalOperationParameters(globalOperationParameters())
        .apiInfo(apiInfo())
        .enable(enableSwagger);
  }

}
