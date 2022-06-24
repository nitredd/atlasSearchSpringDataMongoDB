package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;
import java.util.List;

public class PhraseSearchOperator implements SearchOperator {
    Object query = null;
    String path = null;
    Integer fuzzyMaxEdits = null;


    public PhraseSearchOperator(String query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }
    public PhraseSearchOperator(String query, String path) { this(query, path, null); }
    public PhraseSearchOperator(List<String> query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }
    public PhraseSearchOperator(List<String> query, String path) { this(query, path, null); }
    public PhraseSearchOperator() { /* this(null, null, null); */ }


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

        return new Document("phrase",
                opDoc
        );
    }
}
