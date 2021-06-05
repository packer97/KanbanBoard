package com.group_3.kanbanboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;


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
        registry.addResourceHandler("wikipedia/downloaded/**")
                .addResourceLocations(Paths.get( "downloaded").toUri().toString());
        System.out.println(Paths.get( "downloaded").toUri().toString());


    }
}