package org.polyglotted.graphonomy.domain;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

public final class DatabaseConstants {

    public static final String NodeId = "nodeId";
    public static final String Status = "status";
    public static final String Link = "link";

    public static final String IndexIds = "index-ids";
    public static final String IndexLinks = "index-links";

    public static RelationshipType HAS_META_NOTE = DynamicRelationshipType.withName("has_meta_note");
    public static RelationshipType HAS_META_RELATION = DynamicRelationshipType.withName("has_meta_relation");
    public static RelationshipType IS_META_RELATED_TO = DynamicRelationshipType.withName("is_meta_related_to");

    public static RelationshipType HAS_NOTE = DynamicRelationshipType.withName("has_note");
    public static RelationshipType HAS_CATEGORY = DynamicRelationshipType.withName("has_category");
    public static RelationshipType IS_RELATED_TO = DynamicRelationshipType.withName("is_related_to");
    public static RelationshipType HAS_RELATION = DynamicRelationshipType.withName("has_relation");
}
