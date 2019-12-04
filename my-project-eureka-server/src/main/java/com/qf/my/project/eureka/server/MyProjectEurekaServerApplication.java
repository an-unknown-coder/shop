package com.qf.my.project.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MyProjectEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyProjectEurekaServerApplication.class, args);
    }

}
