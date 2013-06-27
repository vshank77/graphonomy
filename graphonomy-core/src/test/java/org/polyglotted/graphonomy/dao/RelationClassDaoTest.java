package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.exports.RelationClassJsonExporter;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.util.JsonUtils;

public class RelationClassDaoTest extends AbstractDaoTest<RelationClass> {

    private RelationClassDao relationClassDao;

    @Before
    public void initDao() {
        relationClassDao = new RelationClassDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final RelationClass relationClass = new RelationClass("relation");
        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                return relationClassDao.create(relationClass);
            }
        });
        final StringWriter writer = new StringWriter();
        new RelationClassJsonExporter(writer).safeWrite(node);
        assertEquals(JsonUtils.asJson(relationClass), writer.toString());
    }

    @Override
    protected RelationClass loadTestSubject(String nodeId) {
        return new RelationClass(nodeId);
    }

    @Override
    protected BaseDao<RelationClass> getAbstractDao() {
        return relationClassDao;
    }
}
