package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class TermClass implements GraphNode {

    private long id = -1;
    private String classId;
    private String className;
    private String parentClassId;
    private List<MetaNote> metaNotes = Lists.newArrayList();
    private List<MetaRelation> metaRelations = Lists.newArrayList();

    public TermClass() {}

    public TermClass(String classId) {
        setClassId(classId);
    }

    public TermClass(String classId, String className) {
        setClassId(classId);
        setClassName(className);
    }

    @Override
    public int hashCode() {
        return 47 * ((classId == null) ? 0 : classId.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TermClass other = (TermClass) obj;
        return (classId == null) ? (other.classId == null) : (classId.equals(other.classId));
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
        return getClassId();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(classId);
        checkNotNull(className);
        return this;
    }

    public String getClassId() {
        return classId;
    }

    public TermClass setClassId(String classId) {
        this.classId = checkNotNull(classId);
        return this;
    }

    public String getClassName() {
        return className;
    }

    public TermClass setClassName(String className) {
        this.className = checkNotNull(className);
        return this;
    }

    public String getParentClassId() {
        return parentClassId;
    }

    public TermClass setParentClassId(String parentClassId) {
        this.parentClassId = checkNotNull(parentClassId);
        return this;
    }

    public List<MetaNote> getMetaNotes() {
        return metaNotes;
    }

    public TermClass addMetaNote(MetaNote metaNote) {
        metaNotes.add(metaNote);
        return this;
    }

    public TermClass setMetaNotes(List<MetaNote> metaNotes) {
        this.metaNotes = Lists.newArrayList(metaNotes);
        return this;
    }

    public List<MetaRelation> getMetaRelations() {
        return metaRelations;
    }

    public TermClass addMetaRelation(MetaRelation metaRelation) {
        metaRelations.add(metaRelation);
        return this;
    }

    public TermClass setMetaRelations(List<MetaRelation> metaRelations) {
        this.metaRelations = Lists.newArrayList(metaRelations);
        return this;
    }
}