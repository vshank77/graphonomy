package org.polyglotted.graphonomy.model;


//marker interface
public interface GraphNode {

    long getId();
    
    void setId(long id);
    
    String getNodeId();
 
    NodeType getNodeType();
    
    GraphNode validate();
}
