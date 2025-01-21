package com.cabaggregator.ratingservice.migrations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "create-indexed", order = "002", author = "danilalisichkin")
public class DatabaseChangelogV2 {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        MongoDatabase db = mongoTemplate.getDb();
        MongoCollection<Document> collection = db.getCollection("driver_rate");

        collection.createIndex(
                Indexes.compoundIndex(
                        Indexes.ascending("driverId"),
                        Indexes.ascending("rideId")),
                new IndexOptions()
                        .name("idx_driver_ride")
                        .unique(true));

        collection = db.getCollection("passenger_rate");

        collection.createIndex(
                Indexes.compoundIndex(
                        Indexes.ascending("passengerId"),
                        Indexes.ascending("rideId")),
                new IndexOptions()
                        .name("idx_passenger_ride")
                        .unique(true));
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(final MongoTemplate mongoTemplate) {
        MongoDatabase db = mongoTemplate.getDb();
        MongoCollection<Document> collection = db.getCollection("driver_rate");

        collection.dropIndex("idx_driver_ride");

        collection = db.getCollection("passenger_rate");

        collection.dropIndex("idx_passenger_ride");
    }


    @Execution
    public void execute(final MongoTemplate mongoTemplate) {
    }

    @RollbackExecution
    public void rollback(final MongoTemplate mongoTemplate) {
    }
}
