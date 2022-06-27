package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;
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
     * Fuzzy maximum edit
     */
    protected Integer fuzzyMaxEdits = null;

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
     * Accessor for fuzzy maximum edits
     * @return Fuzzy maximum edits
     */
    public Integer getFuzzyMaxEdits() {
        return fuzzyMaxEdits;
    }

    /**
     * Mutator for fuzzy maximum edits
     * @param fuzzyMaxEdits Fuzzy maximum edits
     */
    public void setFuzzyMaxEdits(Integer fuzzyMaxEdits) {
        this.fuzzyMaxEdits = fuzzyMaxEdits;
    }

    /**
     * Constructor to set the query, field path, and fuzzy maximum edits
     * @param query Query
     * @param path Field path
     * @param fuzzyMaxEdits Fuzzy maximum edits
     */
    public PhraseSearchOperator(String query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }

    /**
     * Constructor to set the query and field path
     * @param query Query
     * @param path Field path
     */
    public PhraseSearchOperator(String query, String path) { this(query, path, null); }

    /**
     * Constructor to set multi-query, field path, and fuzzy maximum edits
     * @param query Query
     * @param path Field path
     * @param fuzzyMaxEdits Fuzzy maximum edits
     */
    public PhraseSearchOperator(List<String> query, String path, Integer fuzzyMaxEdits) { this.query = query; this.path = path; this.fuzzyMaxEdits = fuzzyMaxEdits; }

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

        if (fuzzyMaxEdits != null) {
            opDoc.append("fuzzy", new Document("maxEdits", fuzzyMaxEdits.intValue()));
        }

        return new Document("phrase",
                opDoc
        );
    }
}
