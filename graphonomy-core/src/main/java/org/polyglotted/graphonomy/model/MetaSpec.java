package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.google.common.collect.Lists;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
@Setter
public class MetaSpec {

    private List<NoteClass> noteClasses = Lists.newArrayList();
    private List<RelationClass> relationClasses = Lists.newArrayList();
    private List<TermClass> termClasses = Lists.newArrayList();

    public MetaSpec addNoteClass(NoteClass noteClass) {
        noteClasses.add(checkNotNull(noteClass));
        return this;
    }

    public MetaSpec addRelationClass(RelationClass relationClass) {
        relationClasses.add(relationClass);
        return this;
    }

    public MetaSpec addTermClass(TermClass termClass) {
        termClasses.add(checkNotNull(termClass));
        return this;
    }
}