package org.polyglotted.graphonomy.schemarepo;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractDaoTest;
import org.polyglotted.graphonomy.domain.BaseDao;
import org.polyglotted.graphonomy.model.FieldClass;
import org.polyglotted.graphonomy.model.FieldType;
import org.polyglotted.graphonomy.model.TypeSafe;
import org.polyglotted.graphonomy.util.JsonUtils;

public class FieldClassDaoTest extends AbstractDaoTest<FieldClass> {

    private FieldClassDao fieldClassDao;

    @Before
    public void initDao() {
        fieldClassDao = new FieldClassDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final FieldClass fieldClass = new FieldClass("id", "name", TypeSafe.str);
        fieldClass.setEnums(Arrays.asList("apple", "mango", "berry"));
        fieldClass.setRequired(true).setRange(5, 5).setDefaultValue("apple");
        fieldClass.setFieldType(FieldType.EntityType).setGlobalScope(true);

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                return fieldClassDao.create(fieldClass);
            }
        });
        final ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        new FieldClassJsonExporter(byteos, graphDb).safeWrite(node);
        assertEquals(JsonUtils.asJson(fieldClass), new String(byteos.toByteArray()));
    }

    @Override
    protected BaseDao<FieldClass> getAbstractDao() {
        return fieldClassDao;
    }

    @Override
    protected FieldClass loadTestSubject(String nodeId) {
        return new FieldClass(nodeId);
    }
}
