package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

public class TermClass implements GraphNode {

    private long id = -1;
    @GraphProperty
    private String className;
    @GraphProperty
    private String parentClassName;
    private Set<MetaNote> metaNotes;
    private Set<MetaRelation> metaRelations;

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
    public NodeType getNodeType() {
        return NodeType.TERM_CLASS;
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

    public Set<MetaNote> getMetaNotes() {
        if(metaNotes == null) {
            return Collections.emptySet();
        }
        return metaNotes;
    }

    public TermClass addMetaNote(MetaNote metaNote) {
        if(metaNotes == null) {
            metaNotes = Sets.newLinkedHashSet();
        }
        metaNotes.add(metaNote);
        return this;
    }

    public TermClass setMetaNotes(Set<MetaNote> metaNotes) {
        this.metaNotes = Sets.newLinkedHashSet(metaNotes);
        return this;
    }

    public Set<MetaRelation> getMetaRelations() {
        if(metaRelations == null) {
            return Collections.emptySet();
        }
        return metaRelations;
    }

    public TermClass addMetaRelation(MetaRelation metaRelation) {
        if(metaRelations == null) {
            metaRelations = Sets.newLinkedHashSet();
        }
        metaRelations.add(metaRelation);
        return this;
    }

    public TermClass setMetaRelations(Set<MetaRelation> metaRelations) {
        this.metaRelations = Sets.newLinkedHashSet(metaRelations);
        return this;
    }
}