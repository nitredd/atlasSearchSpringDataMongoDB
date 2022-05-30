package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class WildcardSearchOperator implements SearchOperator {
    String query;
    String path;
    boolean allowAnalyzedField = false;


    public WildcardSearchOperator(String query, String path, boolean allowAnalyzedField) { this.query = query; this.path = path; this.allowAnalyzedField = allowAnalyzedField; }
    public WildcardSearchOperator(String query, String path) { this(query, path, false); }
    public WildcardSearchOperator() { this(null, null, false); }


    @Override
    public Document toDocument() {
        Document opDoc = new Document("query", query);

        if (path == "*") {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

        if (allowAnalyzedField != false) {
            opDoc.append("allowAnalyzedField", allowAnalyzedField);
        }

        return new Document("wildcard",
                opDoc
        );
    }
}
