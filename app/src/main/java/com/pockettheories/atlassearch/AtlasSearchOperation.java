package com.pockettheories.atlassearch;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class AtlasSearchOperation implements AggregationOperation {
    protected SearchOperator searchOperation;
    protected String indexName;


    //TODO Add highlight


    public AtlasSearchOperation(SearchOperator searchOperation, String indexName) { this.searchOperation = searchOperation; this.indexName = indexName != null ? indexName : "default"; }
    public AtlasSearchOperation(SearchOperator searchOperation) { this(searchOperation, null); }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public SearchOperator getSearchOperation() {
        return searchOperation;
    }

    public void setSearchOperation(SearchOperator searchOperation) {
        this.searchOperation = searchOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return new Document("$search",
                searchOperation.toDocument()
        );
    }

    @Override
    public List<Document> toPipelineStages(AggregationOperationContext context) {
        List<Document> retVal = new ArrayList<Document>();
        retVal.add(toDocument(context));
        return retVal;
    }

    @Override
    public String getOperator() {
        return "$search";
    }
}
