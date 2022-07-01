package com.pockettheories.atlassearch;

import com.mongodb.client.model.geojson.Geometry;
import org.bson.Document;

/**
 * Geo Within operator
 */
public class GeoWithinOperator implements SearchOperator {
    /**
     * Field path
     */
    protected String path;

    /**
     * bottomLeft point and topRight point
     */
    protected Document box;

    /**
     * center point and radius (in meters)
     */
    protected Document circle;

    /**
     * Geometry object
     */
    protected Geometry geometry;

    /**
     * Scoring modifier
     */
    protected Document score;

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
     * Accessor for box
     * @return Box
     */
    public Document getBox() {
        return box;
    }

    /**
     * Mutator for box
     * @param box Box
     */
    public void setBox(Document box) {
        this.box = box;
    }

    /**
     * Accessor for circle
     * @return Circle
     */
    public Document getCircle() {
        return circle;
    }

    /**
     * Mutator for circle
     * @param circle Circle
     */
    public void setCircle(Document circle) {
        this.circle = circle;
    }

    /**
     * Accessor for geometry
     * @return Geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Mutator for geometry
     * @param geometry Geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Accessor for score
     * @return Score modifier
     */
    public Document getScore() {
        return score;
    }

    /**
     * Mutator for score
     * @param score Score modifier
     */
    public void setScore(Document score) {
        this.score = score;
    }

    /**
     * Constructor to set field path, box, circle, geometry, score
     * @param path Field path
     * @param box Box
     * @param circle Circle
     * @param geometry Geometry
     * @param score Score modifier
     */
    public GeoWithinOperator(String path, Document box, Document circle, Geometry geometry, Document score) {
        this.path = path;
        this.box = box;
        this.circle = circle;
        this.geometry = geometry;
        this.score = score;
    }

    /**
     * Constructor to set field path, box, circle, geometry, score
     * @param path Field path
     * @param geometry Geometry
     * @param score Score modifier
     */
    public GeoWithinOperator(String path, Geometry geometry, Document score) {
        this(path, null, null, geometry, score);
    }

    /**
     * Parameter-less Constructor
     */
    public GeoWithinOperator() {}

    /**
     * Builds a BSON document
     * @return BSON document
     */
    @Override
    public Document toDocument() {
        Document opDoc = new Document("path", getPath());
        if (getBox() != null) opDoc.append("box", getBox());
        if (getCircle() != null) opDoc.append("circle", getCircle());
        if (getGeometry() != null) opDoc.append("geometry", getGeometry());
        if (getScore() != null) opDoc.append("score", getScore());
        return new Document("geoWithin",
            opDoc
        );
    }
}
