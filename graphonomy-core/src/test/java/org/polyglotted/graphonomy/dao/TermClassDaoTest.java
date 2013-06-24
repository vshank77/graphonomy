package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;

import com.google.common.collect.Lists;

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
        final TermClass testCls = new TermClass("testCls").setParentClassName("parCls");
        testCls.addMetaNote(new MetaNote(testCls, noteClass, true));
        testCls.addMetaRelation(new MetaRelation(testCls, relClass, cls2));

        List<Node> nodes = execute(new TxCallback<List<Node>>() {
            @Override
            public List<Node> doInTransaction() {
                List<Node> result = Lists.newArrayList();
                result.add(noteClassDao.create(noteClass));
                result.add(relationClassDao.create(relClass));
                result.add(termClassDao.create(cls1));
                result.add(termClassDao.create(cls2));
                result.add(termClassDao.create(testCls));
                return result;
            }
        });
        Node termNode = nodes.get(nodes.size() - 1);
        assertEquals(outputs.get("TermClassDaoTest.testCreateNodes"), inspectNodes(nodes));
        assertEquals(outputs.get("TermClassDaoTest.testCreateRels"), inspectRelationships(termNode.getRelationships()));
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
