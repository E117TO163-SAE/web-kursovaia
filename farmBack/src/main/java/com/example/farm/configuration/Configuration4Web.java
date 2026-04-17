package com.example.farm.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class Configuration4Web implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:[^.]*}")
                .setViewName("forward:/index.html");

        registry.addViewController("/**/{spring:[^.]*}")
                .setViewName("forward:/index.html");
    }
}
