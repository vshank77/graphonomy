package org.polyglotted.graphonomy.domain;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.polyglotted.graphonomy.model.GraphNode;
import org.polyglotted.graphonomy.model.NodeType;

public interface BaseDao<T extends GraphNode> {

    NodeType getNodeType();

    Node create(T gnode);

    Node loadNode(String nodeId);
    
    IndexHits<Node> loadAll(Iterable<String> nodeIds);

    IndexHits<Node> findAll();

    PageResult findAll(int pageSize, int pageStart);

    PageResult search(String prefix, int maxResults);
    
    void delete(String nodeId);

    void forceDelete(String nodeId);
}
