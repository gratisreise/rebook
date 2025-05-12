package com.example.rebookgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RebookGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebookGatewayApplication.class, args);
    }

}
