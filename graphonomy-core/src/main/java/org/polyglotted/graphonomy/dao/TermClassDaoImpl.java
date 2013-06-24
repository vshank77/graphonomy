package org.polyglotted.graphonomy.dao;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.TermClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TermClassDaoImpl extends AbstractDao<TermClass> implements TermClassDao {

    @Autowired
    public TermClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    protected Node createHook(Node node, TermClass gnode) {
        for (MetaNote note : gnode.getMetaNotes()) {
            database.saveRelations(note);
        }
        for (MetaRelation rel : gnode.getMetaRelations()) {
            database.saveRelations(rel);
        }
        return node;
    }
}
