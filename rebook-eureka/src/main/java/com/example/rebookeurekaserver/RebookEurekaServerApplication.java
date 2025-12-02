package com.example.rebookeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RebookEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebookEurekaServerApplication.class, args);
    }

}
