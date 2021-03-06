package com.pockettheories.atlassearch;

import com.mongodb.client.model.geojson.Geometry;
import org.bson.Document;

/**
 * Geo Shape operator
 */
public class GeoShapeOperator implements SearchOperator {
    /**
     * Field path
     */
    protected String path;

    /**
     * Relation between the geometry and the GeoJSON in the resulting documents
     */
    protected GeoRelation relation;

    /**
     * The geometry used in querying
     */
    protected com.mongodb.client.model.geojson.Geometry geometry;

    /**
     * Scoring modifier
     */
    protected Document score;

    /**
     * Accessor for the field path
     * @return Field path
     */
    public String getPath() {
        return path;
    }

    /**
     * Mutator for the field path
     * @param path Field path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Accessor for the geo relation
     * @return Geo relation
     */
    public GeoRelation getRelation() {
        return relation;
    }

    /**
     * Mutator for the geo relation
     * @param relation Geo relation
     */
    public void setRelation(GeoRelation relation) {
        this.relation = relation;
    }

    /**
     * Accessor for the querying geometry
     * @return Geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Mutator for the querying geometry
     * @param geometry Geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Accessor for the scoring modifier
     * @return Score modifier
     */
    public Document getScore() {
        return score;
    }

    /**
     * Mutator for the scoring modifier
     * @param score Score modifier
     */
    public void setScore(Document score) {
        this.score = score;
    }

    /**
     * Constructor to set field path, geo relation, geometry, score
     * @param path Field path
     * @param relation Geo relation
     * @param geometry Geometry
     * @param score Score modifier
     */
    public GeoShapeOperator(String path, GeoRelation relation, com.mongodb.client.model.geojson.Geometry geometry, Document score) {
        this.path = path;
        this.relation = relation;
        this.geometry  = geometry;
        this.score = score;
    }

    /**
     * Constructor to set field path, geo relation, geometry
     * @param path Field path
     * @param relation Geo relation
     * @param geometry Geometry
     */
    public GeoShapeOperator(String path, GeoRelation relation, com.mongodb.client.model.geojson.Geometry geometry) { this(path, relation, geometry, null); }

    /**
     * Parameter-less Constructor
     */
    public GeoShapeOperator() { this(null, null, null, null); }

    /**
     * Builds a BSON document
     * @return BSON document
     */
    @Override
    public Document toDocument() {
        Document opDoc = new Document("geometry", getGeometry())
                .append("path", getPath())
                .append("relation", getRelation())
                ;
        if (getScore()!=null)
            opDoc.append("score", getScore());
        return new Document("geoShape",
                opDoc
        );
    }
}
