package com.casavo.notification.configurations;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongodbConfiguration {

    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.connection}") String connection) {

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connection))
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
