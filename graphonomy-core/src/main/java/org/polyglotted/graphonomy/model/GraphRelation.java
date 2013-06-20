package org.polyglotted.graphonomy.model;

//marker relation
public interface GraphRelation {

    Iterable<String> getLinks(String fromNodeId);
}
