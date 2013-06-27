package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

public class RelationClass implements GraphNode {

    public enum BaseType {
        hierarchy, related, usefor;
    }
    
    private long id = -1;
    @GraphProperty
    private String relationName;
    @GraphProperty(PropertyType.ENUM)
    private BaseType type = BaseType.related;
    @GraphProperty
    private String description;
    @GraphProperty
    private boolean extended = true;

    public RelationClass() {}

    public RelationClass(String relationName) {
        this(relationName, BaseType.related, relationName);
    }

    public RelationClass(String relationName, BaseType type, String description) {
        setRelationName(relationName);
        setType(type);
        setDescription(description);
    }

    @Override
    public int hashCode() {
        return 19 * ((relationName == null) ? 0 : relationName.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RelationClass other = (RelationClass) obj;
        return (relationName == null) ? (other.relationName == null) : (relationName.equals(other.relationName));
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
        return getRelationName();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(relationName);
        checkNotNull(type);
        checkNotNull(description);
        return this;
    }

    public RelationshipType toRelationshipType() {
        return DynamicRelationshipType.withName(checkNotNull(relationName));
    }
    
    public String getRelationName() {
        return relationName;
    }

    public RelationClass setRelationName(String relationName) {
        this.relationName = checkNotNull(relationName);
        return this;
    }

    public BaseType getType() {
        return type;
    }

    public RelationClass setType(BaseType type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RelationClass setDescription(String description) {
        this.description = checkNotNull(description);
        return this;
    }

    public boolean isExtended() {
        return extended;
    }

    public RelationClass setExtended(boolean extended) {
        this.extended = extended;
        return this;
    }
}
