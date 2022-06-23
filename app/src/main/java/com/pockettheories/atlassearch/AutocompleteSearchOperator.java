package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class AutocompleteSearchOperator implements SearchOperator {
    String query;
    String path;
    Integer fuzzyMaxEdits = null;
    String tokenOrder = null;


    public AutocompleteSearchOperator(String query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }
    public AutocompleteSearchOperator(String query, String path) { this(query, path, null); }
    public AutocompleteSearchOperator() { this(null, null, null); }


    @Override
    public Document toDocument() {
        Document opDoc = new Document("query", query);

        if (path.equals("*")) {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

        if (fuzzyMaxEdits != null) {
            opDoc.append("fuzzy", new Document("maxEdits", fuzzyMaxEdits.intValue()));
        }

        if (tokenOrder != null) {
            opDoc.append("tokenOrder", tokenOrder); //either "any" or "sequential"
        }

        return new Document("autocomplete",
                opDoc
        );
    }
}
