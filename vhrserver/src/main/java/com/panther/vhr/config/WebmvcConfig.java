package com.panther.vhr.config;

import com.panther.vhr.interceptor.UserLoginVerifyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Gin 琴酒
 * @data 2023/02/26 15:48
 **/
@Configuration
public class WebmvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginVerifyInterceptor())
                .excludePathPatterns("/doLogin","/","/index.html","/css/**","/verifyCode","/js/**","/fonts/**","/img/**")
                .addPathPatterns("/**");
    }
}
