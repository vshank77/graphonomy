package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class TermClass implements GraphNode {

    private long id = -1;
    @GraphProperty
    private String className;
    @GraphProperty
    private String parentClassName;
    private List<MetaNote> metaNotes = Lists.newArrayList();
    private List<MetaRelation> metaRelations = Lists.newArrayList();

    public TermClass() {}

    public TermClass(String className) {
        setClassName(className);
    }

    @Override
    public int hashCode() {
        return 47 * ((className == null) ? 0 : className.hashCode());
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
        return (className == null) ? (other.className == null) : (className.equals(other.className));
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
        return getClassName();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(className);
        return this;
    }

    public String getClassName() {
        return className;
    }

    public TermClass setClassName(String className) {
        this.className = checkNotNull(className);
        return this;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public TermClass setParentClassName(String parentClassName) {
        this.parentClassName = checkNotNull(parentClassName);
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