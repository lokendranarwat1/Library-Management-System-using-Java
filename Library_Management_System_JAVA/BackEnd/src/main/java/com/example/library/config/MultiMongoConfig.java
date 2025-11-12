package com.example.library.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

@Configuration
public class MultiMongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MultiMongoConfig.class);

    private final Environment env;

    public MultiMongoConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void checkProps() {
        log.info("mongo.north.uri = {}", env.getProperty("mongo.north.uri"));
        log.info("mongo.north.database = {}", env.getProperty("mongo.north.database"));
        log.info("mongo.south.uri = {}", env.getProperty("mongo.south.uri"));
        log.info("mongo.south.database = {}", env.getProperty("mongo.south.database"));
        log.info("mongo.central.uri = {}", env.getProperty("mongo.central.uri"));
        log.info("mongo.central.database = {}", env.getProperty("mongo.central.database"));
    }

    private String require(String key) {
        String v = env.getProperty(key);
        if (!StringUtils.hasText(v)) {
            String msg = "Required property missing: " + key;
            log.error(msg);
            throw new IllegalStateException(msg);
        }
        return v;
    }

    @Bean(name = "northMongoTemplate")
    public MongoTemplate northMongoTemplate() {
        String uri = require("mongo.north.uri");
        String db = require("mongo.north.database");
        log.info("Creating northMongoTemplate -> uri: {}, db: {}", uri, db);
        MongoClient client = MongoClients.create(uri);
        return new MongoTemplate(client, db);
    }

    @Bean(name = "southMongoTemplate")
    public MongoTemplate southMongoTemplate() {
        String uri = require("mongo.south.uri");
        String db = require("mongo.south.database");
        log.info("Creating southMongoTemplate -> uri: {}, db: {}", uri, db);
        MongoClient client = MongoClients.create(uri);
        return new MongoTemplate(client, db);
    }

    @Primary
    @Bean(name = "centralMongoTemplate")
    public MongoTemplate centralMongoTemplate() {
        String uri = require("mongo.central.uri");
        String db = require("mongo.central.database");
        log.info("Creating centralMongoTemplate -> uri: {}, db: {}", uri, db);
        MongoClient client = MongoClients.create(uri);
        return new MongoTemplate(client, db);
    }

    /**
     * Expose a default bean named "mongoTemplate" (and primary) so Spring auto-configured
     * components that expect a single MongoTemplate will use the central DB.
     */
        @Bean(name = "mongoTemplate")
        public MongoTemplate defaultMongoTemplate(@Qualifier("centralMongoTemplate") MongoTemplate central) {
            return central;
        }
    }
