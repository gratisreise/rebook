package com.example.rebookgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RebookGatewayApplication {

    public static void main(String[] args) {
        System.out.println("JWT_SECRET: " + System.getenv("JWT_SECRET"));
        System.out.println("SENTRY_DSN: " + System.getenv("SENTRY_DSN"));
        SpringApplication.run(RebookGatewayApplication.class, args);
    }

}
