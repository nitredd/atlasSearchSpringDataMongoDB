package com.pockettheories.atlassearch;

public enum CountType {
    NONE(""),
    LOWER_BOUND("lowerBound"),
    TOTAL("total")
    ;

    private final String paramValue;
    CountType(String paramValue) { this.paramValue = paramValue; }
    @Override
    public String toString() { return this.paramValue; }
}
