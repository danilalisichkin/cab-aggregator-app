package com.cabaggregator.ratingservice.integration.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class AbstractRepositoryIntegrationTest {
    static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    public static void mongoDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}
