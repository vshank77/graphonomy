package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.graphonomy.domain.DatabaseDefaults.ROOTS;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.exports.TermJsonExporter;
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
        relTerm.addCategory(new Category(relTerm, termClass));
        final Term term = new Term("id", "name").setLanguage("english");
        term.setQualifier("qual1").setVocabulary("vocab").setSortkey("sorter");
        term.addPosting(new Posting("urn:isbn123", "field1", "25"));
        term.addCategory(new Category(term, termClass));
        term.addNote(new Note(term, noteClass, "note value"));
        term.addRelation(new Relation(term, relClass.toRelationshipType(), relTerm));

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                noteClassDao.create(noteClass);
                relationClassDao.create(relClass);
                termClassDao.create(termClass);
                termDao.create(relTerm);
                return termDao.create(term);
            }
        });
        final StringWriter writer = new StringWriter();
        new TermJsonExporter(writer).safeWrite(node);
        assertEquals(JsonUtils.asJson(term), writer.toString());
    }

    @Override
    protected BaseDao<Term> getAbstractDao() {
        return termDao;
    }

    @Override
    protected Term loadTestSubject(String nodeId) {
        return new Term(nodeId, nodeId).addCategory(new Category(nodeId, ROOTS));
    }
}
