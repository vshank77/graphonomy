package org.polyglotted.graphonomy.domain;

import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.FactoryBean;

@Slf4j
public class GraphonomyDatabaseFactory implements FactoryBean<GraphDatabaseService> {

    private String location;
    private URL config;

    @Override
    public GraphDatabaseService getObject() throws Exception {
        log.debug("creating graphDb from " + location);
        GraphDatabaseBuilder builder = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(location);
        builder.loadPropertiesFromURL(config);
        return builder.newGraphDatabase();
    }

    @Override
    public Class<?> getObjectType() {
        return GraphDatabaseService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setConfig(URL config) {
        this.config = config;
    }
}
