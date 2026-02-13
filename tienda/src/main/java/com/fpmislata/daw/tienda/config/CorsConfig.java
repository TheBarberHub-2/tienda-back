package com.fpmislata.daw.tienda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:4199",
                                "http://localhost:4200",
                                "http://greatbank.producciondaw.cip.fpmislata.com",
                                "http://thebarberhub.producciondaw.cip.fpmislata.com",
                                "http://thebarberhub-client.producciondaw.cip.fpmislata.com",
                                "http://greatbank.preproducciondaw.cip.fpmislata.com",
                                "http://thebarberhub.preproducciondaw.cip.fpmislata.com",
                                "http://thebarberhub-client.preproducciondaw.cip.fpmislata.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}