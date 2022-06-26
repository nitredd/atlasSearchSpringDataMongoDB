package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;
import java.util.List;

/**
 * Text search operator
 */
public class TextSearchOperator implements SearchOperator {
    /**
     * Query
     */
    protected Object query = null;

    /**
     * Field path
     */
    protected String path = null;

    /**
     * Fuzzy maximum edits
     */
    protected Integer fuzzyMaxEdits = null;

    /**
     * Accessor for query
     * @return
     */
    public Object getQuery() {
        return query;
    }

    /**
     * Mutator for query
     * @param query
     */
    public void setQuery(Object query) {
        this.query = query;
    }

    /**
     * Accessor for field path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Mutator for field path
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Accessor for fuzzy maximum edits
     * @return
     */
    public Integer getFuzzyMaxEdits() {
        return fuzzyMaxEdits;
    }

    /**
     * Mutator for fuzzy maximum edits
     * @param fuzzyMaxEdits
     */
    public void setFuzzyMaxEdits(Integer fuzzyMaxEdits) {
        this.fuzzyMaxEdits = fuzzyMaxEdits;
    }

    /**
     * Constructor for the text operator with query, field path, and fuzzy maximum edits
     * @param query
     * @param path
     * @param fuzzyMaxEdits
     */
    public TextSearchOperator(String query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }

    /**
     * Constructor for the text operator with query and field path
     * @param query
     * @param path
     */
    public TextSearchOperator(String query, String path) { this(query, path, null); }

    /**
     * Constructor for the text operator with multi-query, field path, and fuzzy maximum edits
     * @param query
     * @param path
     * @param fuzzyMaxEdits
     */
    public TextSearchOperator(List<String> query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }

    /**
     * Constructor for the text operator with multi-query and field path
     * @param query
     * @param path
     */
    public TextSearchOperator(List<String> query, String path) { this(query, path, null); }

    /**
     * Parameter-less constructor
     */
    public TextSearchOperator() { /* this(null, null, null); */ }

    /**
     * Builds the BSON document for the operator
     * @return
     */
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

        return new Document("text",
            opDoc
        );
    }
}
