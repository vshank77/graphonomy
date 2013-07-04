package org.polyglotted.graphonomy.model;

public interface GraphNode extends Validated<GraphNode> {

    long getId();

    void setId(long id);

    String getNodeId();

    String getNodeName();

    NodeType getNodeType();
}
