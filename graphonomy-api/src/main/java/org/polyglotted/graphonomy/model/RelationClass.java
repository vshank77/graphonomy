package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

@Accessors(chain = true)
@EqualsAndHashCode(of = "relationName")
@Getter
@NoArgsConstructor
@Setter
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

    public RelationClass(String relationName) {
        this(relationName, BaseType.related, relationName);
    }

    public RelationClass(String relationName, BaseType type, String description) {
        setRelationName(relationName);
        setType(type);
        setDescription(description);
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
    public String getNodeName() {
        return getRelationName();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.RELATION_CLASS;
    }

    @Override
    public GraphNode validate() {
        checkNotNull(relationName);
        checkNotNull(type);
        checkNotNull(description);
        return this;
    }
}
