package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class NearSearchOperator<T> implements SearchOperator {
    T origin;
    String path;
    float pivot;


    public NearSearchOperator(String path, T origin, float pivot) { this.origin = origin; this.path = path; this.pivot = pivot; }
    public NearSearchOperator() { this(null, null, 1.0f); }


    @Override
    public Document toDocument() {
        Document opDoc = new Document("origin", origin);

        if (path.equals("*")) {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

//        if (pivot != null) {
            opDoc.append("pivot", pivot);
//        }

        return new Document("near",
                opDoc
        );
    }
}
