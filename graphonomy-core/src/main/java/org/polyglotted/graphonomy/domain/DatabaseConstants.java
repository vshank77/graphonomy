package org.polyglotted.graphonomy.domain;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

public final class DatabaseConstants {

    public static final String DateFormat = "yyyyMMdd'T'hh:mm:ss";

    public static final String NodeId = "nodeId";
    public static final String Status = "status";
    public static final String Link = "link";

    public static final String IndexIds = "index-ids";
    public static final String IndexLinks = "index-links";

    public static RelationshipType IS_META_NOTE_OF = DynamicRelationshipType.withName("is_meta_note_of");
    public static RelationshipType IS_META_RELATION_OF = DynamicRelationshipType.withName("is_meta_relation_of");
    public static RelationshipType IS_META_RELATION_TO = DynamicRelationshipType.withName("is_meta_relation_to");
}
