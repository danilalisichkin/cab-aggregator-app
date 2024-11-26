package com.cabaggregator.ratingservice.migrations;

import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.entity.PassengerRate;
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
        mongoTemplate.indexOps(DriverRate.class).ensureIndex(
                new Index().on("driverId", Sort.Direction.ASC)
        );
        mongoTemplate.indexOps(PassengerRate.class).ensureIndex(
                new Index().on("passengerId", Sort.Direction.ASC)
        );

        if (!mongoTemplate.collectionExists(DriverRate.class)) {
            mongoTemplate.createCollection(DriverRate.class);
        }
        if (!mongoTemplate.collectionExists(PassengerRate.class)) {
            mongoTemplate.createCollection(PassengerRate.class);
        }
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(final MongoTemplate mongoTemplate) {
        if (mongoTemplate.collectionExists(DriverRate.class)) {
            mongoTemplate.dropCollection(DriverRate.class);
        }
        if (mongoTemplate.collectionExists(PassengerRate.class)) {
            mongoTemplate.dropCollection(PassengerRate.class);
        }
    }

    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
    }

    @RollbackExecution
    public void rollback(final MongoTemplate mongoTemplate) {
    }
}
