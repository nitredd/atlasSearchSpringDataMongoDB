package com.pockettheories.atlassearch;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import junit.framework.TestCase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

// Before running this test case, create a dynamic-mapping index on sample_airbnb.listingsAndReviews

public class AtlasSearchOperationTest extends TestCase {
    MongoClient client;
    String mongoUri = System.getProperty("MONGOURI");

    @Override
    public void setUp() {
        client = MongoClients.create(mongoUri);
    }

    public void testSearch3IntegerBedrooms() {
        // db.getSiblingDB("sample_airbnb").listingsAndReviews.count({bedrooms: 3})  //returns 427
        // db.getSiblingDB("sample_airbnb").listingsAndReviews.aggregate([{ "$search" : { "compound" : { "filter" : [{ "range" : { "path" : "bedrooms", "gte" : 3, "lte" : 3}}]}, "index" : "index1"}}])  //returns 427 documents
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new RangeSearchOperator<Integer>("bedrooms").gte(3).lte(3)
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasSearchOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(aggResult.getMappedResults().size() == 427);
    }

    public void testSearch3StringBedrooms() {
        //Searching with the wrong data type
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new TextSearchOperator("3", "bedrooms")
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasSearchOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(aggResult.getMappedResults().size() == 0);
    }

    public void testSearch2StringMinimumNights() {
        //{minimum_nights: "2"}
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new TextSearchOperator("2", "minimum_nights")
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasSearchOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(aggResult.getMappedResults().size() == 1505);
    }

    public void testSearch2IntegerMinimumNights() {
        //Wrong data type
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new RangeSearchOperator<Integer>("minimum_nights").gte(2).lte(2)
        ));
        Aggregation agg = Aggregation.newAggregation(
            new AtlasSearchOperation(cso, "index1")
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(aggResult.getMappedResults().size() == 0);
    }

    public void testSearchTotal3IntegerBedrooms() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.filterList.addAll(List.of(
            new RangeSearchOperator<Integer>("bedrooms").gte(3).lte(3)
        ));
        AtlasSearchOperation aso = new AtlasSearchOperation(cso, "index1");
        aso.setCountType(CountType.TOTAL);  // This has to be $set/$project into the results
        Aggregation agg = Aggregation.newAggregation(
            aso
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue(aggResult.getMappedResults().size() == 427);
    }

    public void testSearchSortAscendingBedrooms() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.mustList.addAll(List.of(  // Ensure we aren't using filterList when sorting - filtering doesn't score docs
            new NearSearchOperator<Integer>("bedrooms", 0, 1.0f)
        ));
        AtlasSearchOperation aso = new AtlasSearchOperation(cso, "index1");
        Aggregation agg = Aggregation.newAggregation(
            aso
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue((Integer)(((Document)(aggResult.getMappedResults().get(0))).get("bedrooms")) == 0);
    }

    public void testSearchSortDescendingBedrooms() {
        MongoOperations mongoOps = new MongoTemplate(client, "sample_airbnb");
        CompoundSearchOperator cso = new CompoundSearchOperator();
        cso.mustList.addAll(List.of(  // Ensure we aren't using filterList when sorting - filtering doesn't score docs
                new NearSearchOperator<Integer>("bedrooms", 9999, 1.0f)
        ));
        AtlasSearchOperation aso = new AtlasSearchOperation(cso, "index1");
        Aggregation agg = Aggregation.newAggregation(
                aso
        );
        AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "listingsAndReviews", Document.class);
        assertTrue((Integer)(((Document)(aggResult.getMappedResults().get(0))).get("bedrooms")) == 20);
    }

    @Override
    public void tearDown() {
        client.close();
    }
}
