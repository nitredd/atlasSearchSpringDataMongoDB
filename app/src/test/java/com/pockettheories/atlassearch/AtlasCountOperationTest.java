package com.pockettheories.atlassearch;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import junit.framework.TestCase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.print.Doc;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

// Before running this test case, create a dynamic-mapping index on sample_airbnb.listingsAndReviews

public class AtlasCountOperationTest extends TestCase {
    MongoClient client;
    String mongoUri = System.getProperty("MONGOURI");

    @Override
    public void setUp() {
        client = MongoClients.create(mongoUri);
    }

    public void testCount3IntegerBedrooms() {
        // db.getSiblingDB("sample_airbnb").listingsAndReviews.count({bedrooms: 3})  //returns 427
        // db.getSiblingDB("sample_airbnb").listingsAndReviews.aggregate([{ "$search" : { "compound" : { "filter" : [{ "range" : { "path" : "bedrooms", "gte" : 3, "lte" : 3}}]}, "index" : "index1"}}])  //returns 427 documents
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new RangeSearchOperator<Integer>("bedrooms").gte(3).lte(3)
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasCountOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("lowerBound"))))
                >= 427);
    }

    public void testCount3StringBedrooms() {
        //Searching with the wrong data type
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new TextSearchOperator("3", "bedrooms")
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasCountOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("lowerBound"))))
                        == 0);
    }

    public void testCount2StringMinimumNights() {
        //{minimum_nights: "2"}
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new TextSearchOperator("2", "minimum_nights")
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasCountOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("lowerBound"))))
                        >= 1001);
    }

    public void testCount2IntegerMinimumNights() {
        //Wrong data type
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new RangeSearchOperator<Integer>("minimum_nights").gte(2).lte(2)
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasCountOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("lowerBound"))))
                        == 0);
    }

    public void testCountTotal3IntegerBedrooms() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        AtlasCountOperation aco = new AtlasCountOperation(
            new RangeSearchOperator<Integer>("bedrooms").gte(3).lte(3)
            , "index1"
        );
        aco.setCountType(CountType.TOTAL);
        Aggregation agg = Aggregation.newAggregation(
                aco
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("total"))))
                        >= 427);
    }

    public void testCountTotalSummaryPhraseVeryClose() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        AtlasCountOperation aco = new AtlasCountOperation(
                new PhraseSearchOperator("Very close", "summary")
                , "index1"
        );
        aco.setCountType(CountType.TOTAL);
        Aggregation agg = Aggregation.newAggregation(
                aco
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("total"))))
                        == 117);
    }

    public void testCountTotalSummaryTextVeryClose() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        AtlasCountOperation aco = new AtlasCountOperation(
                new TextSearchOperator("Very close", "summary")
                , "index1"
        );
        aco.setCountType(CountType.TOTAL);
        Aggregation agg = Aggregation.newAggregation(
                aco
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("total"))))
                        == 1220);
    }

    public void testFirstReviewBefore2015() {
        Instant i1 = Instant.parse("2015-01-01T00:00:00.000Z");
        ZoneId utc = ZoneId.of("UTC");
//        ZonedDateTime zdatetime = ZonedDateTime.ofInstant(i1, utc);
        LocalDateTime ldt1 = LocalDateTime.ofInstant(i1, utc);

//        TimeZone utc = TimeZone.getTimeZone("UTC");
//        Date dt = new Date(115, 1, 1);
//        Calendar cal1 = Calendar.getInstance(utc);
//        cal1.setTime(dt);

        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        AtlasCountOperation aco = new AtlasCountOperation(
                new RangeSearchOperator<LocalDateTime>("first_review").lt(ldt1)
                , "index1"
        );
        aco.setCountType(CountType.TOTAL);
        Aggregation agg = Aggregation.newAggregation(
                aco
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(
                ((Long)((((Document)(aggResult.getMappedResults().get(0).get("count"))).get("total"))))
                        == 578);

    }

    @Override
    public void tearDown() {
        client.close();
    }
}
