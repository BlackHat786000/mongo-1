//package com.learn.mongodb.config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfig {
//
//    @Bean
//    public MongoClient mongoClient() {
//        String connectionString = "mongodb://localhost:27017/learn_mongo";
//        return MongoClients.create(connectionString);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "learn_mongo");
//    }
//}
