package org.polyglotted.graphonomy.terms;

import static com.google.common.collect.Lists.transform;
import static org.neo4j.helpers.collection.MapUtil.stringMap;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.DatabaseConstants;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.domain.PageResult;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.Relation;
import org.polyglotted.graphonomy.model.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;

@Service
public class TermDaoImpl extends AbstractDao<Term> implements TermDao {

    private static final String TERM_CATEGORY = "termCategory";

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
        for (Category cat : gnode.getCategories()) {
            database.saveRelations(cat);
        }
        for (Note note : gnode.getNotes()) {
            database.saveRelations(note);
        }

        for (String className : transform(gnode.getCategories(), new Function<Category, String>() {
            @Override
            public String apply(Category cat) {
                return cat.getClassName();
            }
        })) {
            database.globalIndex().add(node, TERM_CATEGORY, className);
        }
        return node;
    }

    @Override
    public void saveRelations(Term gnode) {
        for (Relation rel : gnode.getRelations()) {
            database.saveRelations(rel);
        }
    }

    @Override
    public PageResult search(String prefix, String className, int maxResults) {
        IndexHits<Node> source = database.findNodes(prefix,
                stringMap(DatabaseConstants.NodeType, getNodeType().name(), TERM_CATEGORY, className));
        return new PageResult(source, maxResults, 0);
    }
}
