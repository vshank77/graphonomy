package org.polyglotted.graphonomy.exports;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_CATEGORY;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_NOTE;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_RELATION;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import org.neo4j.graphdb.Node;

public class TermJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> TERM_PROPS = Arrays.asList("termId", "termName", "qualifier", "language",
            "vocabulary", "sortkey", "termType", "update", "status", "approval", "createdBy", "createdDate",
            "modifiedBy", "modifiedDate", "$postings");
    private static final List<String> CAT_PROPS = Arrays.asList("termId", "className");
    private static final List<String> NOTE_PROPS = Arrays.asList("termId", "noteLabel", "value");
    private static final List<String> RELATION_PROPS = Arrays.asList("termId", "relationName", "targetTermId");

    public TermJsonExporter(OutputStream output) {
        super(output);
    }

    public TermJsonExporter(Writer writer) {
        super(writer);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, TERM_PROPS);
        writeRelationships("categories", value.getRelationships(OUTGOING, HAS_CATEGORY), CAT_PROPS);
        writeRelationships("notes", value.getRelationships(OUTGOING, HAS_NOTE), NOTE_PROPS);
        writeRelationships("relations", value.getRelationships(OUTGOING, HAS_RELATION), RELATION_PROPS);
        json.writeEndObject();
    }
}
