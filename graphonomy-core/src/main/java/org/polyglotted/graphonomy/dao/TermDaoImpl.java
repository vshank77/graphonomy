package org.polyglotted.graphonomy.dao;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.Relation;
import org.polyglotted.graphonomy.model.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TermDaoImpl extends AbstractDao<Term> implements TermDao {

    @Autowired
    public TermDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    protected Node createHook(Node node, Term gnode) {
        for(Category cat : gnode.getCategories()) {
            database.saveRelations(cat);
        }
        for(Note note : gnode.getNotes()) {
            database.saveRelations(note);
        }
        for(Relation rel : gnode.getRelations()) {
            database.saveRelations(rel);
        }
        return node;
    }
}
