package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

import com.google.common.collect.Lists;

public class NoteClass implements GraphNode {

    private long id = -1;
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
    @GraphProperty(PropertyType.FRAGMENT)
    private Range range;

    public NoteClass() {}

    public NoteClass(String noteLabel) {
        this(noteLabel, TypeSafe.str);
    }

    public NoteClass(String noteLabel, TypeSafe type) {
        setNoteLabel(noteLabel);
        setType(type);
    }

    public <T> T validate(String value) {
        return type.validatedValue(value, this);
    }

    @Override
    public int hashCode() {
        return 47 * ((noteLabel == null) ? 0 : noteLabel.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NoteClass other = (NoteClass) obj;
        return (noteLabel == null) ? (other.noteLabel == null) : (noteLabel.equals(other.noteLabel));
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return getNoteLabel();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(noteLabel);
        checkNotNull(type);
        return this;
    }

    public String getNoteLabel() {
        return noteLabel;
    }

    public NoteClass setNoteLabel(String noteLabel) {
        this.noteLabel = checkNotNull(noteLabel);
        return this;
    }

    public TypeSafe getType() {
        return type;
    }

    public NoteClass setType(TypeSafe type) {
        this.type = checkNotNull(type);
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public NoteClass setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public List<String> getEnums() {
        if (enums == null) {
            return Collections.emptyList();
        }
        return enums;
    }

    public NoteClass addEnum(String enumVal) {
        if (enums == null) {
            enums = Lists.newArrayList();
        }
        enums.add(enumVal);
        return this;
    }

    public NoteClass setEnums(List<String> enums) {
        this.enums = Lists.newArrayList(enums);
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public NoteClass setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public NoteClass setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public boolean hasRange() {
        return range != null;
    }

    public boolean isInRange(Number value) {
        return hasRange() && range.isInRange(value.doubleValue());
    }

    public int getRangeMin() {
        return hasRange() ? range.min : -1;
    }

    public int getRangeMax() {
        return hasRange() ? range.max : -1;
    }

    public NoteClass setRange(int min, int max) {
        this.range = new Range(min, max);
        return this;
    }

    protected static class Range {
        private int max;
        private int min;

        public Range() {}

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public boolean isInRange(double value) {
            return value >= min && value <= max;
        }
    }
}