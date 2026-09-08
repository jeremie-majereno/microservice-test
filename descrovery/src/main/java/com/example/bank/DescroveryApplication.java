package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DescroveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DescroveryApplication.class, args);
    }

}
