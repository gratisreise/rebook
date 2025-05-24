//package com.example.rebookuserservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    private final String[] WHITE_LIST = {"http://localhost:3001"};
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//            .allowedOrigins("http://localhost:3001") // 프론트엔드 주소
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//            .allowCredentials(true);
//    }
//}
