package com.pockettheories.atlassearch;

/**
 * Used to specify the type of counting with Atlas Search
 */
public enum CountType {
    /**
     * Unspecified
     */
    NONE(""),
    /**
     * Accurate upto threshold, and then provides a lower bound
     */
    LOWER_BOUND("lowerBound"),
    /**
     * Exact count
     */
    TOTAL("total")
    ;

    /**
     * string value needed for the aggregation pipeline
     */
    private final String paramValue;

    /**
     * Constructor to hold the string value needed for the aggregation pipeline
     * @param paramValue
     */
    CountType(String paramValue) { this.paramValue = paramValue; }

    /**
     * Converts to the string expected in the aggregation pipeline
     * @return
     */
    @Override
    public String toString() { return this.paramValue; }
}
