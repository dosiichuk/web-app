package com.crud.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfigurtion {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
