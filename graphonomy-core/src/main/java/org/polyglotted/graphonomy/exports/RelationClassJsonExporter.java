package org.polyglotted.graphonomy.exports;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.neo4j.graphdb.Node;

public class RelationClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> RELCLASS_PROPS = asList("relationName", "type", "description", "extended");

    public RelationClassJsonExporter(OutputStream output) {
        super(output);
    }

    public RelationClassJsonExporter(Writer writer) {
        super(writer);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, RELCLASS_PROPS);
        json.writeEndObject();
    }
}
