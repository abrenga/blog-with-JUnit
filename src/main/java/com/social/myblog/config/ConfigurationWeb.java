package com.social.myblog.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ConfigurationWeb implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")//qui ci mappiamo la richiesta che iniziano con Uploads
                .addResourceLocations("file:/uploads/");//qui mappa il percorso fisico della risorsa
    }
}
