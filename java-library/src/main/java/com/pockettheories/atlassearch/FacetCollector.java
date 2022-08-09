package com.pockettheories.atlassearch;

import org.bson.Document;
import java.util.List;

public class FacetCollector implements SearchCollector{
    private final SearchOperator operator;
    private final List<SearchFacet> facets;

    public FacetCollector(SearchOperator operator, List<SearchFacet> facets) {
        this.operator = operator;
        this.facets = facets;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public List<SearchFacet> getFacets() {
        return facets;
    }

    @Override
    public Document toDocument() {
        Document facetCollectorDoc = new Document();

        facetCollectorDoc.append("operator", operator.toDocument());

        if (facets != null && !facets.isEmpty()) {
            Document facetsDoc = new Document();
            facets.stream().forEach(e -> {
                facetsDoc.append(e.getName(), e.toDocument());
            });
            facetCollectorDoc.append("facets", facetsDoc);
        }

        Document facet = new Document("facet", facetCollectorDoc);
        return facet;
    }

}
