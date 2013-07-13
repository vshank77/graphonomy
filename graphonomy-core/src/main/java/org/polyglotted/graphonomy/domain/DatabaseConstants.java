package org.polyglotted.graphonomy.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.neo4j.graphdb.DynamicRelationshipType.withName;

import java.util.Map;

import org.neo4j.graphdb.RelationshipType;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.NoteField;
import org.polyglotted.graphonomy.model.Relation;

import com.google.common.collect.Maps;

public abstract class DatabaseConstants {

    public static final String NodeId = "nodeId";
    public static final String NodeName = "nodeName";
    public static final String NodeType = "nodeType";
    public static final String Status = "status";
    public static final String Link = "link";

    public static final String IndexIds = "index-ids";
    public static final String IndexLinks = "index-links";
    public static final String GlobalIndex = "global-index";

    public static RelationshipType HAS_CATEGORY = withName(Category.HAS_CATEGORY);
    public static RelationshipType HAS_META_NOTE = withName(MetaNote.HAS_META_NOTE);
    public static RelationshipType HAS_META_RELATION = withName(MetaRelation.HAS_META_RELATION);
    public static RelationshipType IS_META_RELATED_TO = withName(MetaRelation.IS_META_RELATED_TO);
    public static RelationshipType HAS_NOTE = withName(Note.HAS_NOTE);
    public static RelationshipType HAS_FIELD = withName(NoteField.HAS_FIELD);
    public static RelationshipType IS_FIELD_OF = withName(NoteField.IS_FIELD_OF);
    public static RelationshipType HAS_RELATION = withName(Relation.HAS_RELATION);
    public static RelationshipType IS_RELATED_TO = withName(Relation.IS_RELATED_TO);

    private static Map<String, RelationshipType> TYPES_MAP = Maps.newHashMap();
    static {
        TYPES_MAP.put(Category.HAS_CATEGORY, HAS_CATEGORY);
        TYPES_MAP.put(MetaNote.HAS_META_NOTE, HAS_META_NOTE);
        TYPES_MAP.put(MetaRelation.HAS_META_RELATION, HAS_META_RELATION);
        TYPES_MAP.put(MetaRelation.IS_META_RELATED_TO, IS_META_RELATED_TO);
        TYPES_MAP.put(Note.HAS_NOTE, HAS_NOTE);
        TYPES_MAP.put(NoteField.HAS_FIELD, HAS_FIELD);
        TYPES_MAP.put(NoteField.IS_FIELD_OF, IS_FIELD_OF);
        TYPES_MAP.put(Relation.HAS_RELATION, HAS_RELATION);
        TYPES_MAP.put(Relation.IS_RELATED_TO, IS_RELATED_TO);
    }

    public static RelationshipType typeFor(String typeName) {
        return checkNotNull(TYPES_MAP.get(typeName));
    }
}
