package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class RangeSearchOperator<T> implements SearchOperator {
    String path;
    T gt = null;
    T gte = null;
    T lt = null;
    T lte = null;

    public RangeSearchOperator(String path) { this.path = path; }

    public RangeSearchOperator gt(T value) { this.gt = value; return this; }
    public RangeSearchOperator gte(T value) { this.gte = value; return this; }
    public RangeSearchOperator lt(T value) { this.lt = value; return this; }
    public RangeSearchOperator lte(T value) { this.lte = value; return this; }

    @Override
    public Document toDocument() {
        Document opDoc;

        if (path == "*") {
            opDoc = new Document("path", new Document("wildcard", "*"));
        } else {
            opDoc = new Document("path", path);
        }

        if (gt != null) {
            opDoc.append("gt", gt);
        }
        if (gte != null) {
            opDoc.append("gte", gte);
        }
        if (lt != null) {
            opDoc.append("lt", gt);
        }
        if (lte != null) {
            opDoc.append("lte", lte);
        }

        return new Document("range",
                opDoc
        );
    }
}
