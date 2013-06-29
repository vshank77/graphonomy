package org.polyglotted.graphonomy.dao;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.Relation;
import org.polyglotted.graphonomy.model.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TermDaoImpl extends AbstractDao<Term> implements TermDao {

    @Autowired
    public TermDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.TERM;
    }

    @Override
    protected Node createHook(Node node, Term gnode) {
        for(Category cat : gnode.getCategories()) {
            database.saveRelations(cat);
        }
        for(Note note : gnode.getNotes()) {
            database.saveRelations(note);
        }
        return node;
    }

    @Override
    public void saveRelations(Term gnode) {
        for(Relation rel : gnode.getRelations()) {
            database.saveRelations(rel);
        }
    }
}
