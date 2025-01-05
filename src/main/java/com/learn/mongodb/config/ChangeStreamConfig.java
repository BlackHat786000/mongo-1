package com.learn.mongodb.config;

import com.learn.mongodb.service.ChangeStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChangeStreamConfig {

    @Autowired
    private ChangeStreamService changeStreamService;

    @Bean
    public void runChangeStreamService() {
        changeStreamService.runChangeStreamOnAddressesCollection();
        changeStreamService.runChangeStreamOnProductsCollection();
    }
}
