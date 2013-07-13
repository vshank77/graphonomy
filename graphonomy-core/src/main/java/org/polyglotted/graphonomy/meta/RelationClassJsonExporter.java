package org.polyglotted.graphonomy.meta;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class RelationClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> RELCLASS_PROPS = asList("relationName", "type", "description", "extended");

    public RelationClassJsonExporter(OutputStream output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, RELCLASS_PROPS);
        json.writeEndObject();
    }
}
