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

@Accessors(chain = true)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
public class Note implements GraphRelation {

    public static final String HAS_NOTE = "has_note";

    @GraphProperty
    private String termId;
    @GraphProperty
    private String noteId;
    @GraphProperty
    private String value;

    public static Note from(Term term, NoteClass note, String value) {
        return new Note(term.getTermId(), note.getNoteId(), value);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, HAS_NOTE, noteId));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termId);
        checkNotNull(noteId);
        return this;
    }
}
