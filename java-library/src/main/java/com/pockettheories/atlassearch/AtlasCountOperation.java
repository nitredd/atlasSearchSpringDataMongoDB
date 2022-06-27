package com.pockettheories.atlassearch;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Performs a count of search results with Atlas Search
 */
public class AtlasCountOperation implements AggregationOperation {
    /**
     * Search operator
     */
    protected SearchOperator searchOperation;

    /**
     * Name of the Atlas Search index
     */
    protected String indexName;

    /**
     * Count type - lower bound or total
     */
    protected CountType countType;

    /**
     * Count threshold - to use with lower bound
     */
    protected long countThreshold = -1;


    /**
     * Creates an aggregation stage for counting search results with Atlas Search
     * @param searchOperation Search operation
     * @param indexName Name of the Atlas Search index
     */
    public AtlasCountOperation(SearchOperator searchOperation, String indexName) { this.searchOperation = searchOperation; this.indexName = indexName != null ? indexName : "default"; }

    /**
     * Creates an aggregation stage for counting search results with Atlas Search, with a "default" index name
     * @param searchOperation Search operation
     */
    public AtlasCountOperation(SearchOperator searchOperation) { this(searchOperation, null); }

    /**
     * Returns the Atlas Search index name
     * @return Atlas Search index name
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * Sets the Atlas Search index name
     * @param indexName Atlas Search index name
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * Gets the Atlas Search operation
     * @return Atlas Search operation
     */
    public SearchOperator getSearchOperation() {
        return searchOperation;
    }

    /**
     * Sets the Atlas Search operation
     * @param searchOperation Atlas Search operation
     */
    public void setSearchOperation(SearchOperator searchOperation) {
        this.searchOperation = searchOperation;
    }

    /**
     * Builds the aggregation pipeline stage
     * @param context the {@link AggregationOperationContext} to operate within. Must not be {@literal null}.
     * @return BSON Document
     */
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
        Document retVal =new Document(getOperator(),searchOperand);
        return retVal;
    }

    /**
     * Builds aggregation pipeline stages
     * @param context the {@link AggregationOperationContext} to operate within. Must not be {@literal null}.
     * @return List of BSON Documents
     */
    @Override
    public List<Document> toPipelineStages(AggregationOperationContext context) {
        List<Document> retVal = new ArrayList<Document>();
        retVal.add(toDocument(context));
        return retVal;
    }

    /**
     * Returns the operator name
     * @return Always returns "$searchMeta"
     */
    @Override
    public String getOperator() {
        return "$searchMeta";
    }

    /**
     * Returns the count type (lower bound, total)
     * @return Count Type
     */
    public CountType getCountType() {
        return countType;
    }

    /**
     * Sets the count type
     * @param countType Count Type
     */
    public void setCountType(CountType countType) {
        this.countType = countType;
    }

    /**
     * Gets the count threshold
     * @return Count Threshold
     */
    public long getCountThreshold() {
        return countThreshold;
    }

    /**
     * Sets the count threshold
     * @param countThreshold Count Threshold
     */
    public void setCountThreshold(long countThreshold) {
        this.countThreshold = countThreshold;
    }
}
