package org.polyglotted.graphonomy.schemarepo;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class FieldClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> FIELDCLASS_PROPS = asList("noteId", "noteLabel", "type", "defaultValue", "required",
            "enums", "pattern", "$range", "fieldType", "globalScope");

    public FieldClassJsonExporter(OutputStream output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, FIELDCLASS_PROPS);
        json.writeEndObject();
    }
}
