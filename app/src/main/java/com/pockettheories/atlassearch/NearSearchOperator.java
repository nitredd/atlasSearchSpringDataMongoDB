package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Near search operator
 * @param <T> Data type of the field
 */
public class NearSearchOperator<T> implements SearchOperator {
    /**
     * The point from which distance will be calculated
     */
    protected T origin;

    /**
     * Field path
     */
    protected String path;

    /**
     * Pivot
     */
    protected float pivot;

    /**
     * Accessor for origin
     * @return
     */
    public T getOrigin() {
        return origin;
    }

    /**
     * Mutator for origin
     * @param origin
     */
    public void setOrigin(T origin) {
        this.origin = origin;
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
     * Accessor for pivot
     * @return
     */
    public float getPivot() {
        return pivot;
    }

    /**
     * Mutator for pivot
     * @param pivot
     */
    public void setPivot(float pivot) {
        this.pivot = pivot;
    }

    /**
     * Constructor to set field path, origin, and pivot
     * @param path
     * @param origin
     * @param pivot
     */
    public NearSearchOperator(String path, T origin, float pivot) { this.origin = origin; this.path = path; this.pivot = pivot; }

    /**
     * Parameter-less Constructor
     */
    public NearSearchOperator() { this(null, null, 1.0f); }

    /**
     * Builds a BSON document
     * @return
     */
    @Override
    public Document toDocument() {
        Document opDoc = new Document("origin", origin);

        if (path.equals("*")) {
            opDoc.append("path", new Document("wildcard", "*"));
        } else {
            opDoc.append("path", path);
        }

//        if (pivot != null) {
            opDoc.append("pivot", pivot);
//        }

        return new Document("near",
                opDoc
        );
    }
}
