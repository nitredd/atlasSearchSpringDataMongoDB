package com.pockettheories.atlassearch;

/**
 * Used to specify the geo relation with the geoShape operator
 */
public enum GeoRelation {
    /**
     * Unspecified
     */
    NONE(null),
    /**
     * The geometry contains the resulting documents
     */
    CONTAINS("contains"),
    /**
     * The geometry is disjoint with the resulting documents
     */
    DISJOINT("disjoint"),
    /**
     * The geometry intersects with the resulting documents
     */
    INTERSECTS("intersects"),
    /**
     * The geometry is within the resulting documents
     */
    WITHIN("within")
    ;

    /**
     * string value needed for the aggregation pipeline
     */
    private final String paramValue;

    /**
     * Constructor to hold the string value needed for the aggregation pipeline
     * @param paramValue string value needed for the aggregation pipeline
     */
    GeoRelation(String paramValue) { this.paramValue = paramValue; }

    /**
     * Converts to the string expected in the aggregation pipeline
     * @return Parameter value for the aggregation pipeline
     */
    @Override
    public String toString() { return this.paramValue; }
}
