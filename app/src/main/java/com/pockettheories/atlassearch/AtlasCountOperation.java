package com.pockettheories.atlassearch;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class AtlasCountOperation implements AggregationOperation {
    protected SearchOperator searchOperation;
    protected String indexName;
    protected CountType countType;
    protected long countThreshold = -1;


    public AtlasCountOperation(SearchOperator searchOperation, String indexName) { this.searchOperation = searchOperation; this.indexName = indexName != null ? indexName : "default"; }
    public AtlasCountOperation(SearchOperator searchOperation) { this(searchOperation, null); }

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
        Document searchOperand = searchOperation.toDocument();
        if (this.indexName != null) {
            searchOperand = searchOperand.append("index", this.indexName != null ? this.indexName : "default");
        }
        if (this.countType != CountType.NONE) {
            Document countDoc = new Document("type", this.countType.toString());
            if (this.countThreshold != -1) {
                countDoc.append("threshold", this.countThreshold);
            }
            searchOperand = searchOperand.append("count", countDoc);
        }
        Document retVal =new Document(getOperator(),searchOperand);
        return retVal;
    }

    @Override
    public List<Document> toPipelineStages(AggregationOperationContext context) {
        List<Document> retVal = new ArrayList<Document>();
        retVal.add(toDocument(context));
        return retVal;
    }

    @Override
    public String getOperator() {
        return "$searchMeta";
    }

    public CountType getCountType() {
        return countType;
    }

    public void setCountType(CountType countType) {
        this.countType = countType;
    }

    public long getCountThreshold() {
        return countThreshold;
    }

    public void setCountThreshold(long countThreshold) {
        this.countThreshold = countThreshold;
    }
}