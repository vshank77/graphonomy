package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_NOTE;

import java.util.Arrays;
import java.util.List;

public class Note implements GraphRelation {

    @GraphProperty
    private String termId;
    @GraphProperty
    private String noteLabel;
    @GraphProperty
    private String value;

    public Note() {}

    public Note(Term term, NoteClass note, String value) {
        this(term.getTermId(), note.getNoteLabel(), value);
    }

    public Note(String termId, NoteClass note, String value) {
        this(termId, note.getNoteLabel(), value);
    }

    public Note(String termId, String noteLabel, String value) {
        setTermId(termId);
        setNoteLabel(noteLabel);
        setValue(value);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, HAS_NOTE, noteLabel));
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + ((termId == null) ? 0 : termId.hashCode());
        result = prime * result + ((noteLabel == null) ? 0 : noteLabel.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if ((termId == null) ? (other.termId != null) : !termId.equals(other.termId))
            return false;
        if (noteLabel == null ? (other.noteLabel != null) : !noteLabel.equals(other.noteLabel))
            return false;
        if ((value == null) ? (other.value != null) : !value.equals(other.value))
            return false;
        return true;
    }

    public String getTermId() {
        return termId;
    }

    public Note setTermId(String termId) {
        this.termId = checkNotNull(termId);
        return this;
    }

    public String getNoteLabel() {
        return noteLabel;
    }

    public Note setNoteLabel(String noteLabel) {
        this.noteLabel = checkNotNull(noteLabel);
        return this;
    }

    public String getValue() {
        return value;
    }

    public Note setValue(String value) {
        this.value = value;
        return this;
    }
}
