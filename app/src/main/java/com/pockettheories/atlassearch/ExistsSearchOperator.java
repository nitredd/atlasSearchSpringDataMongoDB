package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Exists operator
 */
public class ExistsSearchOperator implements SearchOperator {
    /**
     * Field path
     */
    protected String path;

    /**
     * Accessor for the field path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Mutator for the field path
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Constructor to set the field path
     * @param path
     */
    public ExistsSearchOperator(String path) { this.path = path; }

    /**
     * Parameter-less constructor
     */
    public ExistsSearchOperator() { this(null); }

    /**
     * Builds the BSON document for the operator
     * @return
     */
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
