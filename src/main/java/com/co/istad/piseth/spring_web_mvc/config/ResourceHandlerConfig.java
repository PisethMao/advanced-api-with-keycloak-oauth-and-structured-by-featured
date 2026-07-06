package com.co.istad.piseth.spring_web_mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {
    @Value("${file-upload.client-path}")
    private String clientPath;
    @Value("${file-upload.server-path}")
    private String serverPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientPath + "/**")
                .addResourceLocations("file:" + serverPath);
    }
}
