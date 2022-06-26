package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Regular expression search operator
 */
public class RegexSearchOperator implements SearchOperator {
    /**
     * Query
     */
    String query;

    /**
     * Field path
     */
    String path;

    /**
     * Allow analyzed field
     */
    boolean allowAnalyzedField = false;

    /**
     * Accessor for query
     * @return
     */
    public String getQuery() {
        return query;
    }

    /**
     * Mutator for query
     * @param query
     */
    public void setQuery(String query) {
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
     * Accessor for allow analyzed field
     * @return
     */
    public boolean getAllowAnalyzedField() {
        return allowAnalyzedField;
    }

    /**
     * Mutator for allow analyzed field
     * @param allowAnalyzedField
     */
    public void setAllowAnalyzedField(boolean allowAnalyzedField) {
        this.allowAnalyzedField = allowAnalyzedField;
    }

    /**
     * Constructor for the Regular Expression operator with query, field path, and allowAnalyzedField
     * @param query
     * @param path
     * @param allowAnalyzedField
     */
    public RegexSearchOperator(String query, String path, boolean allowAnalyzedField) { this.query = query; this.path = path; this.allowAnalyzedField = allowAnalyzedField; }

    /**
     * Constructor for the Regular Expression operator with query and field path
     * @param query
     * @param path
     */
    public RegexSearchOperator(String query, String path) { this(query, path, false); }

    /**
     * Parameter-less constructor
     */
    public RegexSearchOperator() { this(null, null, false); }

    /**
     * Build the BSON document for the operator
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

        if (allowAnalyzedField != false) {
            opDoc.append("allowAnalyzedField", allowAnalyzedField);
        }

        return new Document("regex",
                opDoc
        );
    }
}
