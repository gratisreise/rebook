package com.example.rebookuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RebookUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebookUserServiceApplication.class, args);
    }

}
