package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Range search operator
 * @param <T>
 */
public class RangeSearchOperator<T> implements SearchOperator {
    /**
     * Field path
     */
    protected String path;

    /**
     * Greater than
     */
    protected T gt = null;

    /**
     * Greater than or equals
     */
    protected T gte = null;

    /**
     * Less than
     */
    protected T lt = null;

    /**
     * Less than or equals
     */
    protected T lte = null;

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
     * Accessor for greater than
     * @return
     */
    public T getGt() {
        return gt;
    }

    /**
     * Mutator for greater than
     * @param gt
     */
    public void setGt(T gt) {
        this.gt = gt;
    }

    /**
     * Accessor for greater than or equals
     * @return
     */
    public T getGte() {
        return gte;
    }

    /**
     * Mutator for greater than or equals
     * @param gte
     */
    public void setGte(T gte) {
        this.gte = gte;
    }

    /**
     * Accessor for less than
     * @return
     */
    public T getLt() {
        return lt;
    }

    /**
     * Mutator for less than
     * @param lt
     */
    public void setLt(T lt) {
        this.lt = lt;
    }

    /**
     * Accessor for less than or equals
     * @return
     */
    public T getLte() {
        return lte;
    }

    /**
     * Mutator for less than or equals
     * @param lte
     */
    public void setLte(T lte) {
        this.lte = lte;
    }

    /**
     * Constructor with field path
     * @param path
     */
    public RangeSearchOperator(String path) { this.path = path; }

    /**
     * Chain operator for greater than
     * @param value
     * @return
     */
    public RangeSearchOperator gt(T value) { this.gt = value; return this; }

    /**
     * Chain operator for greater than or equals
     * @param value
     * @return
     */
    public RangeSearchOperator gte(T value) { this.gte = value; return this; }

    /**
     * Chain operator for less than
     * @param value
     * @return
     */
    public RangeSearchOperator lt(T value) { this.lt = value; return this; }

    /**
     * Chain operator for less than or equals
     * @param value
     * @return
     */
    public RangeSearchOperator lte(T value) { this.lte = value; return this; }

    /**
     * Builds the BSON document for the operator
     * @return
     */
    @Override
    public Document toDocument() {
        Document opDoc;

        if (path.equals("*")) {
            opDoc = new Document("path", new Document("wildcard", "*"));
        } else {
            opDoc = new Document("path", path);
        }

        if (gt != null) {
            opDoc.append("gt", gt);
        }
        if (gte != null) {
            opDoc.append("gte", gte);
        }
        if (lt != null) {
            opDoc.append("lt", lt);
        }
        if (lte != null) {
            opDoc.append("lte", lte);
        }

        return new Document("range",
                opDoc
        );
    }
}
