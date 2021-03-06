package org.polyglotted.graphonomy.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.neo4j.helpers.collection.MapUtil.stringMap;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeType;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.Status;
import static org.polyglotted.graphonomy.model.Status.active;
import static org.polyglotted.graphonomy.model.Status.deleted;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.IndexHits;
import org.polyglotted.graphonomy.model.GraphNode;

public abstract class AbstractDao<T extends GraphNode> implements BaseDao<T> {

    protected final GraphonomyDatabase database;

    protected AbstractDao(GraphonomyDatabase database) {
        this.database = database;
    }

    @Override
    public final Node create(T gnode) {
        Node node = database.saveNode(gnode);
        return createHook(node, gnode);
    }

    /* Sub-classes should override to do additional index loads and relationships with the created node */
    protected Node createHook(Node node, T gnode) {
        return node;
    }

    @Override
    public Node loadNode(String nodeId) {
        Node node = checkNotNull(database.findNodeByCode(nodeId), "unable to find node with Id " + nodeId);
        if (node.hasProperty(Status) && !active.name().equals(node.getProperty(Status)))
            throw new DomainFailureException("loading inactive or deleted node");
        return node;
    }

    @Override
    public IndexHits<Node> loadAll(Iterable<String> nodeIds) {
        return database.findNodesByCode(nodeIds);
    }

    @Override
    public IndexHits<Node> findAll() {
        return database.findNodesByType(getNodeType());
    }

    @Override
    public PageResult findAll(int pageSize, int pageStart) {
        IndexHits<Node> source = database.findNodesByType(getNodeType());
        return new PageResult(source, pageSize, pageStart);
    }

    @Override
    public PageResult search(String prefix, int maxResults) {
        IndexHits<Node> source = database.findNodes(prefix, stringMap(NodeType, getNodeType().name()));
        return new PageResult(source, maxResults, 0);
    }

    @Override
    public void delete(String nodeId) {
        Node node = loadNode(nodeId);
        node.setProperty(Status, deleted.name());
    }

    @Override
    public void forceDelete(String nodeId) {
        Node node = loadNode(nodeId);
        for (Relationship rel : node.getRelationships()) {
            rel.delete();
        }
        node.delete();
    }
}
