package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

public class ExistsSearchOperator implements SearchOperator {
    String path;


    public ExistsSearchOperator(String path) { this.path = path; }


    @Override
    public Document toDocument() {
        Document opDoc = null;

        if (path.equals("*")) {
            opDoc = new Document("path", new Document("wildcard", "*"));
        } else {
            opDoc = new Document("path", path);
        }

        return new Document("exists",
                opDoc
        );
    }
}
