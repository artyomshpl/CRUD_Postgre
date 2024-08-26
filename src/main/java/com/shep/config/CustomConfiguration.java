package com.shep.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    @Bean
    @ConditionalOnProperty(name = "custom.property.enabled", havingValue = "true")
    public String thisIsMyFirstConditionalBean() {
        return "This is my first conditional bean";
    }
}