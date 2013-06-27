package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_RELATION;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IS_RELATED_TO;

import java.util.Arrays;
import java.util.List;

import org.neo4j.graphdb.RelationshipType;

public class Relation implements GraphRelation {

    @GraphProperty
    private String termId;
    @GraphProperty
    private String relationName;
    @GraphProperty
    private String targetTermId;

    public Relation() {}

    public Relation(Term term, RelationshipType rel, Term target) {
        this(term.getTermId(), rel.name(), target.getTermId());
    }

    public Relation(String termId, RelationshipType rel, Term target) {
        this(termId, rel.name(), target.getTermId());
    }

    public Relation(String termId, String relationName, String targetTermId) {
        setTermId(termId);
        setRelationName(relationName);
        setTargetTermId(targetTermId);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, IS_RELATED_TO, targetTermId),
                new Link(termId, HAS_RELATION, relationName));
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + ((termId == null) ? 0 : termId.hashCode());
        result = prime * result + ((relationName == null) ? 0 : relationName.hashCode());
        result = prime * result + ((targetTermId == null) ? 0 : targetTermId.hashCode());
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
        Relation other = (Relation) obj;
        if ((termId == null) ? (other.termId != null) : !termId.equals(other.termId))
            return false;
        if ((relationName == null) ? (other.relationName != null) : !relationName.equals(other.relationName))
            return false;
        if (targetTermId == null ? (other.targetTermId != null) : !targetTermId.equals(other.targetTermId))
            return false;
        return true;
    }

    public String getTermId() {
        return termId;
    }

    public Relation setTermId(String termId) {
        this.termId = checkNotNull(termId);
        return this;
    }

    public String getRelationName() {
        return relationName;
    }

    public Relation setRelationName(String relationName) {
        this.relationName = checkNotNull(relationName);
        return this;
    }

    public String getTargetTermId() {
        return targetTermId;
    }

    public Relation setTargetTermId(String targetTermId) {
        this.targetTermId = checkNotNull(targetTermId);
        return this;
    }
}
