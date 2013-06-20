package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.model.RelationClass;

public class RelationClassDaoTest extends AbstractDaoTest<RelationClass> {

    private RelationClassDao relationClassDao;

    @Before
    public void initDao() {
        relationClassDao = new RelationClassDaoImpl(database);
    }

    @Test
    public void testCreate() {
        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                return relationClassDao.create(RelationClass.HIERARCHY);
            }
        });
        assertEquals(outputs.get("RelationClassDaoTest.testCreate"), inspectNode(node));
    }

    @Override
    protected RelationClass loadTestSubject(String nodeId) {
        return new RelationClass(nodeId, "fwdName", "fwdDesc", "revName", "revDesc");
    }

    @Override
    protected BaseDao<RelationClass> getAbstractDao() {
        return relationClassDao;
    }
}
