package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Auto-complete operator
 */
public class AutocompleteSearchOperator implements SearchOperator {
    /**
     * Query
     */
    String query;

    /**
     * Field path
     */
    String path;

    /**
     * Fuzzy maximum edits
     */
    Integer fuzzyMaxEdits = null;

    /**
     * Token order
     */
    String tokenOrder = null;

    /**
     * Get the query
     * @return Query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Set the query
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Get the field path
     * @return Field path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the field path
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get the Fuzzy Maximum Edits
     * @return Fuzzy Maximum Edits
     */
    public Integer getFuzzyMaxEdits() {
        return fuzzyMaxEdits;
    }

    /**
     * Set the Fuzzy Maximum Edits
     * @param fuzzyMaxEdits
     */
    public void setFuzzyMaxEdits(Integer fuzzyMaxEdits) {
        this.fuzzyMaxEdits = fuzzyMaxEdits;
    }

    /**
     * Get the Token Order
     * @return Token Order
     */
    public String getTokenOrder() {
        return tokenOrder;
    }

    /**
     * Set the Token Order
     * @param tokenOrder
     */
    public void setTokenOrder(String tokenOrder) {
        this.tokenOrder = tokenOrder;
    }

    /**
     * Constructor for the search operator that sets the query, field path, and fuzzy maximum edits
     * @param query Query
     * @param path Field path
     * @param fuzzyMaxEdits Fuzzy maximum edits
     */
    public AutocompleteSearchOperator(String query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }

    /**
     * Constructor for the search operator that sets the query, and field path
     * @param query Query
     * @param path Field path
     */
    public AutocompleteSearchOperator(String query, String path) { this(query, path, null); }

    /**
     * Constructor for the search operator
     */
    public AutocompleteSearchOperator() { this(null, null, null); }

    /**
     * Build the BSON document for the auto complete operator
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

        if (tokenOrder != null) {
            opDoc.append("tokenOrder", tokenOrder); //either "any" or "sequential"
        }

        return new Document("autocomplete",
                opDoc
        );
    }
}
