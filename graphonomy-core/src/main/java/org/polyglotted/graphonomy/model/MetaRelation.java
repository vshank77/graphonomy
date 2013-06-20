package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class MetaRelation {

    private RelationClass relationClass;
    private TermClass targetClass;

    public MetaRelation() {}

    public MetaRelation(RelationClass relationClass, TermClass targetClass) {
        setRelationClass(relationClass);
        setTargetClass(targetClass);
    }

    @Override
    public int hashCode() {
        final int prime = 47;
        int result = 1;
        result = prime * result + ((relationClass == null) ? 0 : relationClass.hashCode());
        result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
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
        MetaRelation other = (MetaRelation) obj;
        if ((relationClass == null) ? (other.relationClass != null) : !relationClass.equals(other.relationClass))
            return false;
        if ((targetClass == null) ? (other.targetClass != null) : !targetClass.equals(other.targetClass))
            return false;
        return true;
    }

    public RelationClass getRelationClass() {
        return relationClass;
    }

    public MetaRelation setRelationClass(RelationClass relationClass) {
        this.relationClass = checkNotNull(relationClass);
        return this;
    }

    public TermClass getTargetClass() {
        return targetClass;
    }

    public MetaRelation setTargetClass(TermClass targetClass) {
        this.targetClass = checkNotNull(targetClass);
        return this;
    }
}
