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
public class MetaNote implements GraphRelation {

    public static final String HAS_META_NOTE = "has_meta_note";

    @GraphProperty
    private String termClassName;
    @GraphProperty
    private String noteId;
    @GraphProperty
    private boolean mandatory;

    public static MetaNote from(TermClass termClass, NoteClass noteClass, boolean mandatory) {
        return new MetaNote(termClass.getClassName(), noteClass.getNoteId(), mandatory);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termClassName, HAS_META_NOTE, noteId));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termClassName);
        checkNotNull(noteId);
        return this;
    }
}
