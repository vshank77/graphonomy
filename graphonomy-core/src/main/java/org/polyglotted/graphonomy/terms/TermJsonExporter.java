package org.polyglotted.graphonomy.terms;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_CATEGORY;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_NOTE;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_RELATION;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class TermJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> TERM_PROPS = Arrays.asList("termId", "termName", "termType", "update", "status",
            "approval", "createdBy", "createdDate", "modifiedBy", "modifiedDate", "qualifier", "language",
            "vocabulary", "sortkey", "$postings");
    private static final List<String> CAT_PROPS = Arrays.asList("termId", "className");
    private static final List<String> NOTE_PROPS = Arrays.asList("termId", "noteId", "value");
    private static final List<String> RELATION_PROPS = Arrays.asList("termId", "relationName", "targetTermId");

    public TermJsonExporter(OutputStream output) {
        super(output);
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
