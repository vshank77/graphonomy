package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class MetaNote {

    private NoteClass noteClass;
    private boolean mandatory;

    public MetaNote() {}

    public MetaNote(NoteClass noteClass, boolean mandatory) {
        setNoteClass(noteClass);
        setMandatory(mandatory);
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
