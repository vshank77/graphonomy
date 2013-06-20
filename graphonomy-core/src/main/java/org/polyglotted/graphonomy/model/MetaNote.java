package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import org.polyglotted.graphonomy.domain.DatabaseConstants;
import org.polyglotted.graphonomy.util.LinkUtils;

public class MetaNote implements GraphRelation {

    private NoteClass noteClass;
    private boolean mandatory;

    public MetaNote() {}

    public MetaNote(NoteClass noteClass, boolean mandatory) {
        setNoteClass(noteClass);
        setMandatory(mandatory);
    }

    @Override
    public Iterable<String> getLinks(String fromNodeId) {
        return Arrays.asList(LinkUtils.getLink(fromNodeId, DatabaseConstants.IS_META_NOTE_OF, noteClass.getNoteId()));
    }

    @Override
    public int hashCode() {
        final int prime = 19;
        int result = 1;
        result = prime * result + (mandatory ? 1231 : 1237);
        result = prime * result + ((noteClass == null) ? 0 : noteClass.hashCode());
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
        return (noteClass == null) ? (other.noteClass == null) : noteClass.equals(other.noteClass);
    }

    public NoteClass getNoteClass() {
        return noteClass;
    }

    public MetaNote setNoteClass(NoteClass noteClass) {
        this.noteClass = checkNotNull(noteClass);
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
