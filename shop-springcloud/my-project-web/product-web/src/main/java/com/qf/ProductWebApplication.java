package com.qf;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.qf")
@EnableDiscoveryClient
public class ProductWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductWebApplication.class, args);
    }

}
