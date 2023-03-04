package com.panther.actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gin 琴酒
 * @data 2023/03/02 17:35
 **/
@EnableAdminServer
@SpringBootApplication
public class appRun {

    public static void main(String[] args) {
        SpringApplication.run(appRun.class, args);
    }
}
