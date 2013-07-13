package org.polyglotted.graphonomy.meta;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class NoteClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> NOTECLASS_PROPS = asList("noteId", "noteLabel", "type", "defaultValue", "required",
            "enums", "pattern", "$range");

    public NoteClassJsonExporter(OutputStream output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, NOTECLASS_PROPS);
        json.writeEndObject();
    }
}
