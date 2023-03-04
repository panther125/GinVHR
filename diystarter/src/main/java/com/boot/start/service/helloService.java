package com.boot.start.service;

import com.boot.start.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  抽取公共方法封装
 *
 * @author Gin 琴酒
 * @data 2023/03/03 21:48
 **/
public class helloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String name){
        return helloProperties.getPrefix() + ":" + name + helloProperties.getSuffix();
    }

}
