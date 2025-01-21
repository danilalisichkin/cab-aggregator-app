package com.cabaggregator.ratingservice.config;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@ActiveProfiles("test")
@TestPropertySource(properties = {"mongock.enabled=false"})
public abstract class AbstractMongoIntegrationTest {
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
