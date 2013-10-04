package org.polyglotted.graphonomy.domain;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lombok.SneakyThrows;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.polyglotted.graphonomy.util.JsonUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public abstract class AbstractJsonExporter<T> {

    protected final JsonGenerator json;
    protected final GraphDatabaseService graphDb;

    public AbstractJsonExporter(OutputStream output, GraphDatabaseService graphDb) {
        this.json = JsonUtils.createGenerator(output);
        this.graphDb = graphDb;
    }

    @SneakyThrows
    public void close() {
        json.flush();
        json.close();
    }

    @SneakyThrows
    public void safeWrite(Iterable<T> array) {
        try {
            json.writeStartArray();
            for (T t : array) {
                write(t);
            }
            json.writeEndArray();
        }
        finally {
            close();
        }
    }

    @SneakyThrows
    public void safeWrite(T value) {
        try {
            write(value);
        }
        finally {
            close();
        }
    }

    protected abstract void write(T value) throws IOException;

    protected void writeId(Node node) throws IOException {
        json.writeNumberField("id", node.getId());
    }

    protected void writeProperties(PropertyContainer node, Collection<String> filters) throws IOException {
        final List<String> filteredKeys = Lists.newArrayList(filters);
        Iterables.retainAll(filteredKeys, (Collection<?>) node.getPropertyKeys());
        writeProperties(node, filteredKeys.iterator());
    }

    protected void writeProperties(PropertyContainer node, Iterator<String> propertyKeys) throws IOException {
        if (!propertyKeys.hasNext())
            return;
        while (propertyKeys.hasNext()) {
            String prop = propertyKeys.next();
            if (prop.startsWith("$")) {
                json.writeFieldName(prop.substring(1));
                json.writeRawValue((String) node.getProperty(prop));
            }
            else {
                json.writeObjectField(prop, node.getProperty(prop));
            }
        }
    }

    protected void writeRelationships(String fieldName, Iterable<Relationship> rels, Collection<String> filters)
            throws IOException {
        Iterator<Relationship> iter = rels.iterator();
        if (!iter.hasNext()) {
            return;
        }
        json.writeFieldName(fieldName);
        json.writeStartArray();
        while (iter.hasNext()) {
            json.writeStartObject();
            writeProperties(iter.next(), filters);
            json.writeEndObject();
        }
        json.writeEndArray();
    }
}
