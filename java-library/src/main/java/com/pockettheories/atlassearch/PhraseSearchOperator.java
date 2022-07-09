package com.pockettheories.atlassearch;

import org.bson.Document;

import java.util.List;

/**
 * Phrase search operator
 */
public class PhraseSearchOperator implements SearchOperator {
    /**
     * Query
     */
    protected Object query = null;

    /**
     * Field path
     */
    protected String path = null;

    /**
     * Distance between words of the phrase
     */
    protected Integer slop = null;

    /**
     * Accessor for query
     * @return Query
     */
    public Object getQuery() {
        return query;
    }

    /**
     * Mutator for query
     * @param query Query
     */
    public void setQuery(Object query) {
        this.query = query;
    }

    /**
     * Accessor for Field path
     * @return Field path
     */
    public String getPath() {
        return path;
    }

    /**
     * Mutator for path
     * @param path Field path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Accessor for slop
     * @return Distance between words of the phrase
     */
    public Integer getSlop() {
        return slop;
    }

    /**
     * Mutator for slop
     * @param slop Distance between words of the phrase
     */
    public void setSlop(Integer slop) {
        this.slop = slop;
    }

    /**
     * Constructor to set the query, field path, and slop
     * @param query Query
     * @param path Field path
     * @param slop Distance between words of the phrase
     */
    public PhraseSearchOperator(String query, String path, Integer slop) { this.query = query; this.path = path; this.slop = slop; }

    /**
     * Constructor to set the query and field path
     * @param query Query
     * @param path Field path
     */
    public PhraseSearchOperator(String query, String path) { this(query, path, null); }

    /**
     * Constructor to set multi-query, field path, and Distance between words of the phrase
     * @param query Query
     * @param path Field path
     * @param slop Distance between words of the phrase
     */
    public PhraseSearchOperator(List<String> query, String path, Integer slop) { this.query = query; this.path = path; this.slop = slop; }

    /**
     * Constructor to set multi-query and field path
     * @param query Query
     * @param path Field path
     */
    public PhraseSearchOperator(List<String> query, String path) { this(query, path, null); }

    /**
     * Parameter-less constructor
     */
    public PhraseSearchOperator() { /* this(null, null, null); */ }

    /**
     * Builds the BSON document
     * @return BSON document
     */
    @Override
    public Document toDocument() {
        Document opDoc = new Document("query", query);

        if (path.equals("*")) {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

        if (slop != null) {
            opDoc.append("slop", slop.intValue());
        }

        return new Document("phrase",
                opDoc
        );
    }
}
