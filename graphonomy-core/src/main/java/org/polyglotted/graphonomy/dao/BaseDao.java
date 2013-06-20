package org.polyglotted.graphonomy.dao;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.model.GraphNode;

public interface BaseDao<T extends GraphNode> {
    
    Node create(T gnode);

    Node loadNode(String nodeId);
    
    void delete(String nodeId);
    
    void forceDelete(String nodeId);
}
