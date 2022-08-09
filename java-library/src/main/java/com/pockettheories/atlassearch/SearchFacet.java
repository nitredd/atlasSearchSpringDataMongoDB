package com.pockettheories.atlassearch;

import org.bson.Document;

public interface SearchFacet {
    String getName();

    Document toDocument();
}
