package com.cabaggregator.rideservice.migrations;

import com.cabaggregator.rideservice.entity.Ride;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@ChangeUnit(id = "init-collections", order = "001", author = "danilalisichkin")
public class DatabaseChangelogV1 {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        mongoTemplate.indexOps(Ride.class).ensureIndex(
                new Index().on("driverId", Sort.Direction.ASC)
        );
        mongoTemplate.indexOps(Ride.class).ensureIndex(
                new Index().on("passengerId", Sort.Direction.ASC)
        );

        if (!mongoTemplate.collectionExists(Ride.class)) {
            mongoTemplate.createCollection(Ride.class);
        }
        if (!mongoTemplate.collectionExists(Ride.class)) {
            mongoTemplate.createCollection(Ride.class);
        }
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(final MongoTemplate mongoTemplate) {
        if (mongoTemplate.collectionExists(Ride.class)) {
            mongoTemplate.dropCollection(Ride.class);
        }
        if (mongoTemplate.collectionExists(Ride.class)) {
            mongoTemplate.dropCollection(Ride.class);
        }
    }

    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
    }

    @RollbackExecution
    public void rollback(final MongoTemplate mongoTemplate) {
    }
}
