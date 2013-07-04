package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_NOTE;

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

    @GraphProperty
    private String termClassName;
    @GraphProperty
    private String noteLabel;
    @GraphProperty
    private boolean mandatory;

    public static MetaNote from(TermClass termClass, NoteClass noteClass, boolean mandatory) {
        return new MetaNote(termClass.getClassName(), noteClass.getNoteLabel(), mandatory);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termClassName, HAS_META_NOTE, noteLabel));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termClassName);
        checkNotNull(noteLabel);
        return this;
    }
}
