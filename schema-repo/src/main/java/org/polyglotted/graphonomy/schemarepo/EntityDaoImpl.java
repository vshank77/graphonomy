package org.polyglotted.graphonomy.schemarepo;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.Entity;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.NoteField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDaoImpl extends AbstractDao<Entity> implements EntityDao {

    @Autowired
    public EntityDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ENTITY;
    }

    @Override
    protected Node createHook(Node node, Entity gnode) {
        database.saveRelations(gnode.getCategory());
        for (NoteField field : gnode.getFields()) {
            database.saveRelations(field);
        }
        return node;
    }
}
