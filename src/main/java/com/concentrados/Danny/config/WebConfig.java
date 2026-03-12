package com.concentrados.Danny.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto crea un "puente" entre la URL /fichas/ y tu carpeta física
        registry.addResourceHandler("/fichas/**")
                .addResourceLocations("file:src/main/resources/static/fichas/");
    }
}