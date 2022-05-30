package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class NearSearchOperator<T> implements SearchOperator {
    T origin;
    String path;
    T pivot;


    public NearSearchOperator(String path, T origin, T pivot) { this.origin = origin; this.path = path; this.pivot = pivot; }
    public NearSearchOperator() { this(null, null, null); }


    @Override
    public Document toDocument() {
        Document opDoc = new Document("origin", origin);

        if (path == "*") {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

        if (pivot != null) {
            opDoc.append("pivot", pivot);
        }

        return new Document("near",
                opDoc
        );
    }
}
