package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class MetaSpec {

    private List<NoteClass> noteClasses = Lists.newArrayList();
    private List<RelationClass> relationClasses = Lists.newArrayList();
    private List<TermClass> termClasses = Lists.newArrayList();

    public List<NoteClass> getNoteClasses() {
        return noteClasses;
    }

    public MetaSpec addNoteClass(NoteClass noteClass) {
        noteClasses.add(checkNotNull(noteClass));
        return this;
    }

    public MetaSpec setNoteClasses(List<NoteClass> noteClasses) {
        this.noteClasses = Lists.newArrayList(noteClasses);
        return this;
    }

    public List<RelationClass> getRelationClasses() {
        return relationClasses;
    }

    public MetaSpec addRelationClass(RelationClass relationClass) {
        relationClasses.add(relationClass);
        return this;
    }

    public MetaSpec setRelationClasses(List<RelationClass> relationClasses) {
        this.relationClasses = Lists.newArrayList(relationClasses);
        return this;
    }

    public List<TermClass> getTermClasses() {
        return termClasses;
    }

    public MetaSpec addTermClass(TermClass termClass) {
        termClasses.add(checkNotNull(termClass));
        return this;
    }

    public MetaSpec setTermClasses(List<TermClass> termClasses) {
        this.termClasses = Lists.newArrayList(termClasses);
        return this;
    }
}