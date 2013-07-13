package org.polyglotted.graphonomy.meta;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.TermClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TermClassDaoImpl extends AbstractDao<TermClass> implements TermClassDao {

    @Autowired
    public TermClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.TERM_CLASS;
    }

    @Override
    protected Node createHook(Node node, TermClass gnode) {
        for (MetaNote note : gnode.getMetaNotes()) {
            database.saveRelations(note);
        }
        return node;
    }

    @Override
    public void saveMetaRelations(TermClass gnode) {
        for (MetaRelation rel : gnode.getMetaRelations()) {
            database.saveRelations(rel);
        }
    }
}
