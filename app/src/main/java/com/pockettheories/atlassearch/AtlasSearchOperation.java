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
    protected CountType countType;
    protected long countThreshold = -1;
    protected String highlightPath = null;
    protected Integer highlightMaxCharsToExamine = null;
    protected Integer highlightMaxNumPassages = null;
    protected Boolean returnStoredSource = null;

    public Boolean getReturnStoredSource() {
        return returnStoredSource;
    }

    public void setReturnStoredSource(Boolean returnStoredSource) {
        this.returnStoredSource = returnStoredSource;
    }

    public String getHighlightPath() {
        return highlightPath;
    }

    public void setHighlightPath(String highlightPath) {
        this.highlightPath = highlightPath;
    }

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

    @Override
    public Document toDocument(AggregationOperationContext context) {
        Document searchOperand = searchOperation.toDocument();
        if (this.indexName != null) {
            searchOperand = searchOperand.append("index", this.indexName != null ? this.indexName : "default");
        }
        if (this.countType != null && this.countType != CountType.NONE) {
            Document countDoc = new Document("type", this.countType.toString());
            if (this.countThreshold != -1) {
                countDoc.append("threshold", this.countThreshold);
            }
            searchOperand = searchOperand.append("count", countDoc);
        }
        if (highlightPath != null) {
            Document highlightDoc = new Document("path", highlightPath);
            if (highlightMaxCharsToExamine != null) {
                highlightDoc.append("maxCharsToExamine", highlightMaxCharsToExamine.intValue());
            }
            if (highlightMaxNumPassages != null) {
                highlightDoc.append("maxNumPassages", highlightMaxNumPassages.intValue());
            }
            searchOperand.append("highlight", highlightDoc);
        }
        if (returnStoredSource != null) {
            searchOperand.append("returnStoredSource", returnStoredSource.booleanValue());
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
        return "$search";
    }
}
