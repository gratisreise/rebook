package com.example.rebookconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class RebookConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebookConfigApplication.class, args);
    }

}
