package scnu.a225.easyoffice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CorsConfig
 * @Description: 处理跨域问题
 * @Author: jiangjian
 * @CreateDate: 2020/7/25 19:17
 * @UpdateUser: jiangjian
 * @UpdateDate: 2020/7/25 19:17
 * @UpdateRemark: TODO
 * @Version: V1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
