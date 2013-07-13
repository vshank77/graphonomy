package org.polyglotted.graphonomy.meta;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_NOTE;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_RELATION;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class TermClassJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> TERMCLS_PROPS = Arrays.asList("className", "parentClassName");
    private static final List<String> METANOTE_PROPS = Arrays.asList("termClassName", "noteId", "mandatory");
    private static final List<String> METAREL_PROPS = Arrays.asList("termClassName", "relationName", "targetClassName");

    public TermClassJsonExporter(OutputStream output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, TERMCLS_PROPS);
        writeRelationships("metaNotes", value.getRelationships(OUTGOING, HAS_META_NOTE), METANOTE_PROPS);
        writeRelationships("metaRelations", value.getRelationships(OUTGOING, HAS_META_RELATION), METAREL_PROPS);
        json.writeEndObject();
    }
}
