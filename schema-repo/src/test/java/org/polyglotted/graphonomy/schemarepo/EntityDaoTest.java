package org.polyglotted.graphonomy.schemarepo;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDaoTest;
import org.polyglotted.graphonomy.domain.BaseDao;
import org.polyglotted.graphonomy.model.Defaults;
import org.polyglotted.graphonomy.model.Entity;
import org.polyglotted.graphonomy.model.NoteField;
import org.polyglotted.graphonomy.util.JsonUtils;

public class EntityDaoTest extends AbstractDaoTest<Entity> {

    private EntityDao entityDao;

    @Before
    public void initDao() {
        entityDao = new EntityDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final Entity entity = new Entity("id", "name");
        entity.addField(NoteField.from(entity, Defaults.getXsdField("string"), "field"));

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                return entityDao.create(entity);
            }
        });
        final ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        new EntityJsonExporter(byteos, graphDb).safeWrite(node);
        assertEquals(JsonUtils.asJson(entity), new String(byteos.toByteArray()));
    }

    @Override
    protected BaseDao<Entity> getAbstractDao() {
        return entityDao;
    }

    @Override
    protected Entity loadTestSubject(String nodeId) {
        return new Entity(nodeId, nodeId);
    }
}
