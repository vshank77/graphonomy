package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class NoteClass implements GraphNode {

    private long id = -1;
    private String noteId;
    private String noteName;
    private TypeSafe type;
    private String defaultValue;
    private boolean required;
    private List<String> enums = Lists.newArrayList();
    private String pattern;
    private Range range;

    public NoteClass() {}

    public NoteClass(String noteId, String noteName, TypeSafe type) {
        setNoteId(checkNotNull(noteId));
        setNoteName(checkNotNull(noteName));
        setType(checkNotNull(type));
    }

    public <T> T validate(String value) {
        return type.validatedValue(value, this);
    }

    @Override
    public int hashCode() {
        return 47 * ((noteId == null) ? 0 : noteId.hashCode());
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
        return (noteId == null) ? (other.noteId == null) : (noteId.equals(other.noteId));
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
        return getNoteId();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(noteId);
        checkNotNull(noteName);
        checkNotNull(type);
        return this;
    }

    public String getNoteId() {
        return noteId;
    }

    public NoteClass setNoteId(String noteId) {
        this.noteId = checkNotNull(noteId);
        return this;
    }

    public String getNoteName() {
        return noteName;
    }

    public NoteClass setNoteName(String noteName) {
        this.noteName = checkNotNull(noteName);
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
        return enums;
    }

    public NoteClass setEnums(List<String> enums) {
        this.enums = enums;
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

    private static class Range {
        private final int max;
        private final int min;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public boolean isInRange(double value) {
            return value >= min && value <= max;
        }
    }
}