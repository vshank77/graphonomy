package org.polyglotted.graphonomy.dao;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.Status;
import static org.polyglotted.graphonomy.model.Status.active;
import static org.polyglotted.graphonomy.model.Status.deleted;

import java.lang.reflect.ParameterizedType;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.polyglotted.graphonomy.domain.DomainFailureException;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.domain.PageResult;
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
    public PageResult findAll(int pageSize, int pageStart) {
        return database.findNodesByType(getNodeType(), pageSize, pageStart);
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

    protected Class<?> getEntityClass() {
        ParameterizedType ptype = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<?>) ptype.getActualTypeArguments()[0];
    }
}
