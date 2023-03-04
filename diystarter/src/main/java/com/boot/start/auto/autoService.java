package com.boot.start.auto;

import com.boot.start.bean.HelloProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gin 琴酒
 * @data 2023/03/03 21:56
 **/
@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class autoService {

    @Bean
    @ConditionalOnMissingBean(HelloProperties.class)
    public HelloProperties getproperties(){
        HelloProperties helloProperties = new HelloProperties();
        helloProperties.setPrefix("dear");
        helloProperties.setSuffix("bybe");
        return helloProperties;
    }
}
