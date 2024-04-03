package org.eventhub.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedHeaders("Content-Type")
//                        .allowedHeaders("Access-Control-Allow-Headers", "X-Requested-With, content-type",
//                                "Access-Control-Allow-Origin","Access-Control-Allow-Credentials",
//                                "Authorization", "Origin");
//
//                //        .allowedMethods("POST","GET","OPTIONS")
//            }
//        };
//    }
}
