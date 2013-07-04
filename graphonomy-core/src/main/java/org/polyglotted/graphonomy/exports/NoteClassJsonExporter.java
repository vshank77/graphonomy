package org.polyglotted.graphonomy.exports;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.neo4j.graphdb.Node;

public class NoteClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> NOTECLASS_PROPS = asList("noteId", "noteLabel", "type", "defaultValue", "required",
            "enums", "pattern", "$range");

    public NoteClassJsonExporter(OutputStream output) {
        super(output);
    }

    public NoteClassJsonExporter(Writer output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeProperties(value, NOTECLASS_PROPS);
        writeId(value);
        json.writeEndObject();
    }
}
