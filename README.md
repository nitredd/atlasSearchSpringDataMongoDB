# Atlas Search add-on for Spring Data for MongoDB

Spring Data for MongoDB provides classes to easily construct aggregation pipeline stages, such as the MatchOperation class to define the $match stage:

    MatchOperation matchStage = Aggregation.match(
        new Criteria("firstName").is("Nitin")
        .and("lastName").is("Reddy")
        //.andOperator(new Criteria("lastName").is("Reddy"))
    );

However, because Atlas Search was (relatively) recently introduced, the Spring Data for MongoDB library does not include classes for constructing the $search aggregation pipeline stage.

The following operators of Atlas Search can be used with this library:
* Auto-Complete
* Compound Search (Must, Must-Not, Should, Filter)
* Exists
* Near
* Phrase
* Range
* Regex
* Text
* Wildcard

## Usage

To use a single Atlas Search operation, use the Operator class directly, then wrap it within the AtlasSearchOperation and pass to Aggregation.newAggregation as follows:

    //Set up the connection
    MongoClient client = MongoClients.create("mongodb+srv://myusername:mypassword@mongono5.fdu3e.mongodb.net/sample_mflix");
    //Connect to the sample_mflix database (click the Ellipsis and click the "Load Sample Dataset" menu item to get this database)
    MongoOperations mongoOps = new MongoTemplate(client, "sample_mflix");
    
    TextSearchOperator search = new TextSearchOperator("country", "fullplot");
    Aggregation agg = Aggregation.newAggregation(
        new AtlasSearchOperation(search, "default"),  //Wrap the TextSearchOperator object in an AtlasSearchOperation object
        new SetOperation("score", new Document("$meta", "searchScore"))  //Use this to fetch the text match score
    );
    AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "movies", Document.class);

To use multiple Atlas Search operations, pass an array of Operator objects into the must/mustNot/should/filter Lists of a CompoundSearchOperator object.

    //Set up the connection
    MongoClient client = MongoClients.create("mongodb+srv://myusername:mypassword@mongono5.fdu3e.mongodb.net/sample_mflix");
    //Connect to the sample_mflix database (click the Ellipsis and click the "Load Sample Dataset" menu item to get this database)
    MongoOperations mongoOps = new MongoTemplate(client, "sample_mflix");
    
    //This combines multiple Atlas Search operations
    CompoundSearchOperator cso = new CompoundSearchOperator();
    cso.filterList.addAll(List.of(  //mustList, mustNotList, shouldList, filterList
        new TextSearchOperator("country", "fullplot"),
        new RangeSearchOperator<Integer>("runtime").gte(3) //.lte(3)
        new TextSearchOperator("cultural", "plot"),
        new RangeSearchOperator<Integer>("runtime").gte(1200)
        new RangeSearchOperator<Date>("released").lte(new Date(83, 9, 22)).gte(new java.util.Date(83, 9, 20))
    ));

    Aggregation agg = Aggregation.newAggregation(
        new AtlasSearchOperation(cso, "default"),  //Wrap the CompoundSearchOperator object in an AtlasSearchOperation object
        new SetOperation("score", new Document("$meta", "searchScore"))  //Use this to fetch the text match score
    );
    AggregationResults<Document> aggResult = mongoOps.aggregate(agg, "movies", Document.class);


