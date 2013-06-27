package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.exports.NoteClassJsonExporter;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.TypeSafe;
import org.polyglotted.graphonomy.util.JsonUtils;

public class NoteClassDaoTest extends AbstractDaoTest<NoteClass> {

    private NoteClassDao noteClassDao;

    @Before
    public void initDao() {
        noteClassDao = new NoteClassDaoImpl(database);
    }

    @Test
    public void testCreate() {
        final NoteClass noteClass = new NoteClass("name", TypeSafe.str);
        noteClass.setEnums(Arrays.asList("apple", "mango", "berry"));
        noteClass.setRequired(true).setRange(5, 5).setDefaultValue("apple");

        Node node = execute(new TxCallback<Node>() {
            @Override
            public Node doInTransaction() {
                return noteClassDao.create(noteClass);
            }
        });
        final StringWriter writer = new StringWriter();
        new NoteClassJsonExporter(writer).safeWrite(node);
        assertEquals(JsonUtils.asJson(noteClass), writer.toString());
    }

    @Override
    protected NoteClass loadTestSubject(String nodeId) {
        return new NoteClass(nodeId);
    }

    @Override
    protected BaseDao<NoteClass> getAbstractDao() {
        return noteClassDao;
    }
}
