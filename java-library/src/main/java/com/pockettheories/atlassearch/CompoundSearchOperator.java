package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

// TODO - Refactor and replace the public members with protected members and add accessors/mutators

/**
 * Compound search operator
 */
public class CompoundSearchOperator implements SearchOperator {
    /**
     * List of operators to include in Must
     */
    public List<SearchOperator> mustList = new ArrayList<>();

    /**
     * List of operators to include in Must-Not
     */
    public List<SearchOperator> mustNotList = new ArrayList<>();

    /**
     * List of operators to include in Should
     */
    public List<SearchOperator> shouldList = new ArrayList<>();

    /**
     * List of operators to include in Filter
     */
    public List<SearchOperator> filterList = new ArrayList<>();

    /**
     * Minimum should match
     */
    public Integer minimumShouldMatch = null;

    /**
     * Parameterless constructor
     */
    public CompoundSearchOperator() { }

    /**
     * Build the BSON document for the compound operator
     * @return BSON document
     */
    @Override
    public Document toDocument() {
        Document opDoc = new Document();

        if (mustList.size()>0) {
            opDoc.append("must", mustList.stream().map( e -> e.toDocument() ).toList());
        }
        if (mustNotList.size()>0) {
            opDoc.append("mustNot", mustNotList.stream().map( e -> e.toDocument() ).toList());
        }
        if (shouldList.size()>0) {
            opDoc.append("should", shouldList.stream().map( e -> e.toDocument() ).toList());
        }
        if (filterList.size()>0) {
            opDoc.append("filter", filterList.stream().map( e -> e.toDocument() ).toList());
        }

        if (minimumShouldMatch != null) {
            opDoc.append("minimumShouldMatch", minimumShouldMatch.intValue());
        }

        return new Document("compound", opDoc);
    }
}
