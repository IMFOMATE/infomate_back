package com.pro.infomate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelmapper(){

        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate(){return new RestTemplate(); }
}
