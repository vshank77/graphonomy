package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(of = { "noteId", "noteLabel" })
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoteClass implements GraphNode {

    private long id = -1;
    @GraphProperty
    private String noteId;
    @GraphProperty
    private String noteLabel;
    @GraphProperty(PropertyType.ENUM)
    private TypeSafe type;
    @GraphProperty
    private String defaultValue;
    @GraphProperty
    private boolean required;
    @GraphProperty(PropertyType.LIST)
    private List<String> enums;
    @GraphProperty
    private String pattern;
    @Getter(AccessLevel.PROTECTED)
    @GraphProperty(PropertyType.FRAGMENT)
    private Range range;

    public NoteClass(String noteLabel) {
        this(noteLabel, noteLabel, TypeSafe.str);
    }

    public NoteClass(String noteId, String noteLabel, TypeSafe type) {
        setNoteId(noteId);
        setNoteLabel(noteLabel);
        setType(type);
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return getNoteId();
    }

    @Override
    public String getNodeName() {
        return getNoteLabel();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NOTE_CLASS;
    }

    @Override
    public GraphNode validate() {
        checkNotNull(noteId);
        checkNotNull(noteLabel);
        checkNotNull(type);
        return this;
    }

    public <T> T validate(String value) {
        return type.validatedValue(value, this);
    }

    public boolean hasRange() {
        return range != null;
    }

    public boolean isInRange(Number value) {
        return hasRange() && range.isInRange(value.doubleValue());
    }

    public long getRangeMin() {
        return hasRange() ? range.getMin() : -1;
    }

    public long getRangeMax() {
        return hasRange() ? range.getMax() : -1;
    }

    public NoteClass setRange(int min, int max) {
        this.range = new Range(min, max);
        return this;
    }

    public List<String> getEnums() {
        return GenericUtils.emptyOrList(enums);
    }

    public NoteClass addEnum(String enumVal) {
        enums = GenericUtils.addOrCreateList(enums, enumVal);
        return this;
    }
}