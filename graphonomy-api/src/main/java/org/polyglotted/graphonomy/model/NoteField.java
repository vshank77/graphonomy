package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "entityId", "fieldId", "fieldName" })
@Getter
@Setter
public class NoteField implements GraphRelation {

    public static final String HAS_FIELD = "has_field";
    public static final String IS_FIELD_OF = "is_field_of";

    @GraphProperty
    private String entityId;
    @GraphProperty
    private String fieldId;
    @GraphProperty
    private String fieldName;
    @GraphProperty(PropertyType.ENUM)
    private FieldType fieldType;
    @GraphProperty
    private boolean mandatory;
    @GraphProperty
    private boolean multiple;

    public static NoteField from(Entity entity, NoteClass field, String name) {
        return new NoteField(entity.getTermId(), field.getNoteId(), name, FieldType.ValueType);
    }

    public static NoteField from(Entity entity, Entity field, String name) {
        return new NoteField(entity.getTermId(), field.getTermId(), name, FieldType.EntityType);
    }

    public NoteField(String entityId, String fieldId, String name, FieldType type) {
        setEntityId(entityId).setFieldId(fieldId).setFieldName(name).setFieldType(type);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(entityId, HAS_FIELD, fieldId, fieldName), new Link(fieldId, IS_FIELD_OF,
                entityId, fieldName));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(entityId);
        checkNotNull(fieldId);
        checkNotNull(fieldName);
        checkNotNull(fieldType);
        return this;
    }
}
