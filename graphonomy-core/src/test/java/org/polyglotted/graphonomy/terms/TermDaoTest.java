package org.polyglotted.graphonomy.terms;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.graphonomy.model.Defaults.ROOTS;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDaoTest;
import org.polyglotted.graphonomy.domain.BaseDao;
import org.polyglotted.graphonomy.meta.NoteClassDao;
import org.polyglotted.graphonomy.meta.NoteClassDaoImpl;
import org.polyglotted.graphonomy.meta.RelationClassDao;
import org.polyglotted.graphonomy.meta.RelationClassDaoImpl;
import org.polyglotted.graphonomy.meta.TermClassDao;
import org.polyglotted.graphonomy.meta.TermClassDaoImpl;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.Posting;
import org.polyglotted.graphonomy.model.Relation;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.util.JsonUtils;

public class TermDaoTest extends AbstractDaoTest<Term> {
    private NoteClassDao noteClassDao;
    private RelationClassDao relationClassDao;
    private TermClassDao termClassDao;
    private TermDao termDao;

    @Before
    public void initDao() {
        noteClassDao = new NoteClassDaoImpl(database);
        relationClassDao = new RelationClassDaoImpl(database);
        termClassDao = new TermClassDaoImpl(database);
        termDao = new TermDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final NoteClass noteClass = new NoteClass("note1");
        final RelationClass relClass = new RelationClass("relation");
        final TermClass termClass = new TermClass("testCls");
        final Term relTerm = new Term("relid", "relname");
        relTerm.addCategory(Category.from(relTerm, termClass));
        final Term term = new Term("id", "name").setLanguage("english");
        term.setQualifier("qual1").setVocabulary("vocab").setSortkey("sorter");
        term.addPosting(Posting.from("urn:isbn123", "field1", "25"));
        term.addCategory(Category.from(term, termClass));
        term.addNote(Note.from(term, noteClass, "note value"));
        term.addRelation(Relation.from(term, relClass.getRelationName(), relTerm));

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                noteClassDao.create(noteClass);
                relationClassDao.create(relClass);
                termClassDao.create(termClass);
                termDao.create(relTerm);
                Node result = termDao.create(term);
                termDao.saveRelations(term);
                return result;
            }
        });
        final ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        new TermJsonExporter(byteos, graphDb).safeWrite(node);
        assertEquals(JsonUtils.asJson(term), new String(byteos.toByteArray()));
    }

    @Override
    protected BaseDao<Term> getAbstractDao() {
        return termDao;
    }

    @Override
    protected Term loadTestSubject(String nodeId) {
        return new Term(nodeId, nodeId).addCategory(Category.from(nodeId, ROOTS));
    }
}
