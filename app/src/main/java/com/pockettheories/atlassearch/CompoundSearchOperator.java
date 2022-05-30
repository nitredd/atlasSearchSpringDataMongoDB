package com.pockettheories.atlassearch;

import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class CompoundSearchOperator implements SearchOperator {
    public List<SearchOperator> mustList = new ArrayList<>();
    public List<SearchOperator> mustNotList = new ArrayList<>();
    public List<SearchOperator> shouldList = new ArrayList<>();
    public List<SearchOperator> filterList = new ArrayList<>();

    public Integer minimumShouldMatch = null;

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
