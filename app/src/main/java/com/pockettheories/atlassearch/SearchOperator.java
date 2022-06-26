package com.pockettheories.atlassearch;

import org.bson.Document;

/**
 * Interface for all operators used in Atlas Search
 */
public interface SearchOperator {
    /**
     * Builds the BSON document for the operator
     * @return
     */
    Document toDocument();
}
