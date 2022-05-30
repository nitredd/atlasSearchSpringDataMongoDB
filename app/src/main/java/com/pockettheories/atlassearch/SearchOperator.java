package com.pockettheories.atlassearch;

import org.bson.Document;

public interface SearchOperator {
    Document toDocument();
}
