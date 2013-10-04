package org.polyglotted.graphonomy.domain;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.neo4j.graphdb.Relationship;
import org.polyglotted.graphonomy.util.JsonUtils;

import com.google.common.collect.Lists;

public class AbstractJsonExporterTest extends AbstractJsonExporter<String> {

    private static ByteArrayOutputStream byteos = new ByteArrayOutputStream();

    public AbstractJsonExporterTest() {
        super(byteos, null);
    }

    @Test
    public void testIterable() {
        List<String> list = Arrays.asList("test", "test2");
        safeWrite(list);
        assertEquals(JsonUtils.asJson(list), new String(byteos.toByteArray()));
        byteos.reset();
    }

    @Override
    protected void write(String value) throws IOException {
        Iterator<String> iterator = Lists.<String> newArrayList().iterator();
        super.writeProperties(null, iterator); // empty call
        List<Relationship> rels = Lists.<Relationship> newArrayList();
        super.writeRelationships("test", rels, null); // empty call
        json.writeObject(value);
    }
}
