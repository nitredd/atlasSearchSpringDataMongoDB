package com.pockettheories.atlassearch;

import org.bson.Document;

public class StringSearchFacet implements SearchFacet{

    private final String name;
    private final String path;
    private String type = "string";
    private Integer numBuckets = null;

    public StringSearchFacet(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public StringSearchFacet(String name, String path, Integer numBuckets) {
        this(name, path);
        this.numBuckets = numBuckets;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Document toDocument() {
        Document facetDoc = new Document();

        facetDoc.append("type", type);
        facetDoc.append("path", path);

        if (numBuckets != null){
            facetDoc.append("numBuckets", numBuckets);
        }

        return facetDoc;
    }

}
