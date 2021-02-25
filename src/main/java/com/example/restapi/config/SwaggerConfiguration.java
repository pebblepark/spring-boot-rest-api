package com.example.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.restapi.controller"))
                // basePackage 에 해당하는 파일을 읽어 mapping 된 resource 들을 문서화 시킴
                .paths(PathSelectors.any())
                // swagger 로 문서화 할 mapping path 설정
                // PathSelectors.ant(“/ v1/**”) 로 쓰면 v1으로 시작하는 resource 만 문서화
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
    }

    private ApiInfo swaggerInfo() { // swaggerInfo() 세팅하면 문서에 대한 설명과 작성자 정보를 노출시킴
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("서버 API에 대한 연동 문서입니다")
                .license("pebblepark").licenseUrl("https://github.com/pebblepark").version("1").build();
    }
}