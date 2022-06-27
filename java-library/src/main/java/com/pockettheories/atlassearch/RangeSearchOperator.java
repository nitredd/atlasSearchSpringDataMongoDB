package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;

/**
 * Range search operator
 * @param <T> Data type for the range comparison
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
     * @return Field path
     */
    public String getPath() {
        return path;
    }

    /**
     * Mutator for field path
     * @param path Field path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Accessor for greater than
     * @return Greater than value
     */
    public T getGt() {
        return gt;
    }

    /**
     * Mutator for greater than
     * @param gt Greater than value
     */
    public void setGt(T gt) {
        this.gt = gt;
    }

    /**
     * Accessor for greater than or equals
     * @return Greater than or equals value
     */
    public T getGte() {
        return gte;
    }

    /**
     * Mutator for greater than or equals
     * @param gte Greater than or equals value
     */
    public void setGte(T gte) {
        this.gte = gte;
    }

    /**
     * Accessor for less than
     * @return Less than value
     */
    public T getLt() {
        return lt;
    }

    /**
     * Mutator for less than
     * @param lt Less than value
     */
    public void setLt(T lt) {
        this.lt = lt;
    }

    /**
     * Accessor for less than or equals
     * @return Less than or equals value
     */
    public T getLte() {
        return lte;
    }

    /**
     * Mutator for less than or equals
     * @param lte Less than or equals value
     */
    public void setLte(T lte) {
        this.lte = lte;
    }

    /**
     * Constructor with field path
     * @param path Field path
     */
    public RangeSearchOperator(String path) { this.path = path; }

    /**
     * Chain operator for greater than
     * @param value Greather than value
     * @return Instance of the range search operator with the greater than value set
     */
    public RangeSearchOperator gt(T value) { this.gt = value; return this; }

    /**
     * Chain operator for greater than or equals
     * @param value Greater than or equals value
     * @return Instance of the range search operator with the greater than or equals value set
     */
    public RangeSearchOperator gte(T value) { this.gte = value; return this; }

    /**
     * Chain operator for less than
     * @param value Less than value
     * @return Instance of the range search operator with the lesser than value set
     */
    public RangeSearchOperator lt(T value) { this.lt = value; return this; }

    /**
     * Chain operator for less than or equals
     * @param value Lesser than or equals value
     * @return Instance of the range search operator with the lesser than or equals value set
     */
    public RangeSearchOperator lte(T value) { this.lte = value; return this; }

    /**
     * Builds the BSON document for the operator
     * @return BSON document
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
