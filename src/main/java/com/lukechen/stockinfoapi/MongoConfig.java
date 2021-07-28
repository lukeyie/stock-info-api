package com.lukechen.stockinfoapi;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private ResourceBundle reader = ResourceBundle.getBundle("dbconfig");

    @Override
    public String getDatabaseName() {
        return reader.getString("db.name");
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(reader.getString("db.url"));
        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Set<String> getMappingBasePackages() {
        return Collections.singleton("com.lukechen");
    }
}
