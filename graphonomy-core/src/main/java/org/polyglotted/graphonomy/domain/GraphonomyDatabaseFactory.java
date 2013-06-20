package org.polyglotted.graphonomy.domain;

import java.net.URL;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphonomyDatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(GraphonomyDatabaseFactory.class);

    public static GraphDatabaseService createDatabase(String location, URL config) {
        logger.debug("creating graphDb from " + location);
        GraphDatabaseBuilder builder = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(location);
        builder.loadPropertiesFromURL(config);
        return builder.newGraphDatabase();
    }
}
