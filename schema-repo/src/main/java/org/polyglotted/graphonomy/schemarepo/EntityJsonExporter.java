package org.polyglotted.graphonomy.schemarepo;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_FIELD;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.domain.AbstractJsonExporter;

public class EntityJsonExporter extends AbstractJsonExporter<Node> {

    private static final List<String> ENTITY_PROPS = Arrays.asList("termId", "termName", "termType", "update",
            "status", "approval", "createdBy", "createdDate", "modifiedBy", "modifiedDate");
    private static final List<String> FIELD_PROPS = Arrays.asList("entityId", "fieldId", "fieldName", "fieldType",
            "mandatory", "multiple");

    public EntityJsonExporter(OutputStream output) {
        super(output);
    }

    @Override
    protected void write(Node value) throws IOException {
        json.writeStartObject();
        writeId(value);
        writeProperties(value, ENTITY_PROPS);
        writeRelationships("fields", value.getRelationships(OUTGOING, HAS_FIELD), FIELD_PROPS);
        json.writeEndObject();
    }
}
