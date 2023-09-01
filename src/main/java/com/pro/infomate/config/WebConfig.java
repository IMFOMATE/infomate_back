package com.pro.infomate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${image.add-resource-locations}")
  private String ADD_IMG_RESOURCE_LOCATION;

  @Value("${image.add-resource-handler}")
  private String ADD_IMG_RESOURCE_HANDLER;

  @Value("${files.add-resource-locations}")
  private String ADD_DOCUMENT_RESOURCE_LOCATION;

  @Value("${files.add-resource-handler}")
  private String ADD_DOCUMENT_RESOURCE_HANDLER;

  @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler(ADD_DOCUMENT_RESOURCE_HANDLER)
            .addResourceLocations(ADD_DOCUMENT_RESOURCE_LOCATION);

    registry.addResourceHandler(ADD_IMG_RESOURCE_HANDLER)
            .addResourceLocations(ADD_IMG_RESOURCE_LOCATION);
  }

}
