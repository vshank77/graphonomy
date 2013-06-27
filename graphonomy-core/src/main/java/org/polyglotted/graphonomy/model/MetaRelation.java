package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_RELATION;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IS_META_RELATED_TO;

import java.util.Arrays;
import java.util.List;

public class MetaRelation implements GraphRelation {

    @GraphProperty
    private String termClassName;
    @GraphProperty
    private String relationName;
    @GraphProperty
    private String targetClassName;

    public MetaRelation() {}

    public MetaRelation(TermClass termClass, RelationClass relationClass, TermClass targetClass) {
        this(termClass.getClassName(), relationClass.getRelationName(), targetClass.getClassName());
    }

    public MetaRelation(String termClassName, String relationName, String targetClassName) {
        setTermClassName(termClassName);
        setRelationName(relationName);
        setTargetClassName(targetClassName);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termClassName, IS_META_RELATED_TO, targetClassName, relationName), new Link(termClassName,
                HAS_META_RELATION, relationName, targetClassName));
    }

    @Override
    public int hashCode() {
        final int prime = 47;
        int result = 1;
        result = prime * result + ((termClassName == null) ? 0 : termClassName.hashCode());
        result = prime * result + ((relationName == null) ? 0 : relationName.hashCode());
        result = prime * result + ((targetClassName == null) ? 0 : targetClassName.hashCode());
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
        if (termClassName == null ? (other.termClassName != null) : !termClassName.equals(other.termClassName))
            return false;
        if ((relationName == null) ? (other.relationName != null) : !relationName.equals(other.relationName))
            return false;
        if ((targetClassName == null) ? (other.targetClassName != null) : !targetClassName
                .equals(other.targetClassName))
            return false;
        return true;
    }

    public String getTermClassName() {
        return termClassName;
    }

    public MetaRelation setTermClassName(String termClassName) {
        this.termClassName = checkNotNull(termClassName);
        return this;
    }

    public String getRelationName() {
        return relationName;
    }

    public MetaRelation setRelationName(String relationName) {
        this.relationName = checkNotNull(relationName);
        return this;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public MetaRelation setTargetClassName(String targetClassName) {
        this.targetClassName = checkNotNull(targetClassName);
        return this;
    }
}
