package org.polyglotted.graphonomy.dao;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.model.TypeSafe;

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
        final NoteClass noteClass = new NoteClass("note1", "note1", TypeSafe.str);
        final TermClass cls1 = new TermClass("parCls", "parCls");
        final TermClass cls2 = new TermClass("relCls", "relCls");
        final TermClass testCls = new TermClass("testCls", "testCls").setParentClassId("parCls");
        testCls.addMetaNote(new MetaNote(noteClass, true));
        testCls.addMetaRelation(new MetaRelation(RelationClass.HIERARCHY, cls2));

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                noteClassDao.create(noteClass);
                relationClassDao.create(RelationClass.HIERARCHY);
                termClassDao.create(cls1);
                termClassDao.create(cls2);
                return termClassDao.create(testCls);
            }
        });
        System.out.println(inspectNode(node));
        System.out.println(inspectRelationships(node.getRelationships()));
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
