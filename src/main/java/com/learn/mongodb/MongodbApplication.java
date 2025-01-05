package com.learn.mongodb;

import com.learn.mongodb.config.ChangeStreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MongodbApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MongodbApplication.class);
		ctx.getBean("changeStreamConfig", ChangeStreamConfig.class).runChangeStreamService();
	}

}
