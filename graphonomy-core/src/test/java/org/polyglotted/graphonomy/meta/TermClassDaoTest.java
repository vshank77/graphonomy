package org.polyglotted.graphonomy.meta;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDaoTest;
import org.polyglotted.graphonomy.domain.BaseDao;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.util.JsonUtils;

public class TermClassDaoTest extends AbstractDaoTest<TermClass> {

    private NoteClassDao noteClassDao;
    private RelationClassDao relationClassDao;
    private TermClassDao termClassDao;

    @Before
    public void initDao() {
        noteClassDao = new NoteClassDaoImpl(database);
        relationClassDao = new RelationClassDaoImpl(database);
        termClassDao = new TermClassDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final NoteClass noteClass = new NoteClass("note1");
        final RelationClass relClass = new RelationClass("relation");
        final TermClass cls1 = new TermClass("parCls");
        final TermClass cls2 = new TermClass("relCls");
        final TermClass termClass = new TermClass("testCls").setParentClassName("parCls");
        termClass.addMetaNote(MetaNote.from(termClass, noteClass, true));
        termClass.addMetaRelation(MetaRelation.from(termClass, relClass, cls2));
        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                noteClassDao.create(noteClass);
                relationClassDao.create(relClass);
                termClassDao.create(cls1);
                termClassDao.create(cls2);
                Node result = termClassDao.create(termClass);
                termClassDao.saveMetaRelations(termClass);
                return result;
            }
        });
        final ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        new TermClassJsonExporter(byteos, graphDb).safeWrite(node);
        assertEquals(JsonUtils.asJson(termClass), new String(byteos.toByteArray()));
    }

    @Override
    protected BaseDao<TermClass> getAbstractDao() {
        return termClassDao;
    }

    @Override
    protected TermClass loadTestSubject(String nodeId) {
        return new TermClass(nodeId);
    }
}
