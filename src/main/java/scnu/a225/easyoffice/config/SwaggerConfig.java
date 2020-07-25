package scnu.a225.easyoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @ClassName: SwaggerConfig
 * @Description: Swagger配置类。访问地址为：[项目路径]/swagger-ui.html
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 13:15
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 13:15
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .groupName("默认")
                .select()
                .apis(RequestHandlerSelectors.basePackage("scnu.a225.easyoffice.controller"))//指定要扫描的包
                .build();

    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "EasyOffice API文档",
                "EasyOffice API文档",
                "1.0",
                "https://github.com/SCNU-A225",
                new Contact("SCNU-A225","https://github.com/SCNU-A225",""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}

