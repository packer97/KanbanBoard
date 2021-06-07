package com.group_3.kanbanboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.util.List;


@Configuration
@Import(BeanConfig.class)
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("profile/login");
        registry.addViewController("/").setViewName("homepage");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {



        registry.addResourceHandler("wikipedia/downloaded/html/**")
                .addResourceLocations(Paths.get("downloaded/html").toUri().toString());
        registry.addResourceHandler("wikipedia/downloaded/pdf/**")
                .addResourceLocations(Paths.get("downloaded/pdf").toUri().toString());
        registry.addResourceHandler("wikipedia/downloaded/wiki/**")
                .addResourceLocations(Paths.get("downloaded/wiki").toUri().toString());

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}