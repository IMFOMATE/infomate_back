package com.pro.infomate.config;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.service.visitor.DocumentToDTOVisitor;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
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

    @Bean
    DocumentVisitor<DocumentDetailResponse> documentVisitor(){
        return new DocumentToDTOVisitor();
    }
}
