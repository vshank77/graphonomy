package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_NOTE;

import java.util.Arrays;
import java.util.List;

public class MetaNote implements GraphRelation {

    @GraphProperty
    private String termClassName;
    @GraphProperty
    private String noteLabel;
    @GraphProperty
    private boolean mandatory;

    public MetaNote() {}

    public MetaNote(TermClass termClass, NoteClass noteClass, boolean mandatory) {
        this(termClass.getClassName(), noteClass.getNoteLabel(), mandatory);
    }

    public MetaNote(String termClassName, String noteLabel, boolean mandatory) {
        setTermClassName(termClassName);
        setNoteLabel(noteLabel);
        setMandatory(mandatory);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termClassName, HAS_META_NOTE, noteLabel));
    }

    @Override
    public int hashCode() {
        final int prime = 19;
        int result = 1;
        result = prime * result + (mandatory ? 1231 : 1237);
        result = prime * result + ((termClassName == null) ? 0 : termClassName.hashCode());
        result = prime * result + ((noteLabel == null) ? 0 : noteLabel.hashCode());
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
        MetaNote other = (MetaNote) obj;
        if (mandatory != other.mandatory)
            return false;
        if (termClassName == null ? (other.termClassName != null) : !termClassName.equals(other.termClassName))
            return false;
        if (noteLabel == null ? (other.noteLabel != null) : !noteLabel.equals(other.noteLabel))
            return false;
        return true;
    }

    public String getTermClassName() {
        return termClassName;
    }

    public MetaNote setTermClassName(String termClassName) {
        this.termClassName = checkNotNull(termClassName);
        return this;
    }

    public String getNoteLabel() {
        return noteLabel;
    }

    public MetaNote setNoteLabel(String noteLabel) {
        this.noteLabel = checkNotNull(noteLabel);
        return this;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public MetaNote setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }
}
