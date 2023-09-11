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


    @Value("${image.add-resource-locations}")
    private String ADD_RESOURCE_LOCATION;

    @Value("${image.add-resource-handler}")
    private String ADD_RESOURCE_HANDLER;

    @Value("${image.image-dir}")
    private String ADD_RESOURCE_IMG_DIR;

    @Value("${image.image-url}")
    private String ADD_RESOURCE_IMG_URL;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ADD_RESOURCE_HANDLER)
                .addResourceLocations(ADD_RESOURCE_LOCATION);

        registry.addResourceHandler("/imgs/**")
                .addResourceLocations("file:///" + ADD_RESOURCE_IMG_DIR);
    }

}
