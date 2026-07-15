package com.co.istad.piseth.spring_web_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableConfigurationProperties
public class SpringWebMvcApplication {

    static void main(String[] args) {
        SpringApplication.run(SpringWebMvcApplication.class, args);
    }

}