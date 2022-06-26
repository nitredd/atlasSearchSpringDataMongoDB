package com.pockettheories.atlassearch;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Performs a search with Atlas Search
 */
public class AtlasSearchOperation implements AggregationOperation {
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
     * Field path for highlighting
     */
    protected String highlightPath = null;

    /**
     * Maximum characters to examine for highlighting
     */
    protected Integer highlightMaxCharsToExamine = null;

    /**
     * Maximum number of passages for highlighting
     */
    protected Integer highlightMaxNumPassages = null;

    /**
     * Return stored source documents from the search stage
     */
    protected Boolean returnStoredSource = null;

    /**
     * Get return stored source
     * @return
     */
    public Boolean getReturnStoredSource() {
        return returnStoredSource;
    }

    /**
     * Set return stored source
     * @param returnStoredSource
     */
    public void setReturnStoredSource(Boolean returnStoredSource) {
        this.returnStoredSource = returnStoredSource;
    }

    /**
     * Get the highlight field path
     * @return
     */
    public String getHighlightPath() {
        return highlightPath;
    }

    /**
     * Set the highlight field path
     * @param highlightPath
     */
    public void setHighlightPath(String highlightPath) {
        this.highlightPath = highlightPath;
    }

    /**
     * Set the search operation and use an index other than the "default"
     * @param searchOperation
     * @param indexName
     */
    public AtlasSearchOperation(SearchOperator searchOperation, String indexName) { this.searchOperation = searchOperation; this.indexName = indexName != null ? indexName : "default"; }

    /**
     * Set the search operation
     * @param searchOperation
     */
    public AtlasSearchOperation(SearchOperator searchOperation) { this(searchOperation, null); }

    /**
     * Returns the Atlas Search index name
     * @return
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
     * Get the count type (lower bound, total)
     * @return
     */
    public CountType getCountType() {
        return countType;
    }

    /**
     * Set the count type
     * @param countType Count type (lower bound, total)
     */
    public void setCountType(CountType countType) {
        this.countType = countType;
    }

    /**
     * Get the count threshold
     * @return
     */
    public long getCountThreshold() {
        return countThreshold;
    }

    /**
     * Set the count threshold
     * @return
     */
    public void setCountThreshold(long countThreshold) {
        this.countThreshold = countThreshold;
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
     * @return
     */
    @Override
    public String getOperator() {
        return "$search";
    }
}
