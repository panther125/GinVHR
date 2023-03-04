package com.boot.start.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Gin 琴酒
 * @data 2023/03/03 21:50
 **/
@ConfigurationProperties("panther.hello")
public class HelloProperties  {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
