package com.turato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //@EnableSwagger2 开启 swagger 自动配置

    @Bean
    public Docket docket(Environment environment) {
    /*
    设置要显示swagger的环境
    Profiles of = Profiles.of("dev", "test");
    判断当前是否处于该环境
    通过 enable() 接收此参数判断是否要显示
    boolean b = environment.acceptsProfiles(of);
    */

   return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
           //配置是否启用Swagger，如果是false，在浏览器将无法访问
//      .enable(b)
           // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.turato.controller"))
            // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
            .paths(PathSelectors.ant("/**"))
            .build();
}

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("turato", "https://hustuhao.github.io/", "tuhao1996@qq.com");
        return new ApiInfo(
                "vueblog",
                "vueblog 后台接口",
                "v1.0",
                "https://hustuhao.github.io/",
                contact,
                "Apach 2.0 许可",
                "许可链接",
                new ArrayList<>()
        );
    }
}
