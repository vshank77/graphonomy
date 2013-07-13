package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(of = "className")
@Getter
@NoArgsConstructor
@Setter
public class TermClass implements GraphNode {

    private long id = -1;
    @GraphProperty
    private String className;
    @GraphProperty
    private String parentClassName;

    @Setter(AccessLevel.PROTECTED)
    private Set<MetaNote> metaNotes;
    @Setter(AccessLevel.PROTECTED)
    private Set<MetaRelation> metaRelations;

    public TermClass(String className) {
        setClassName(className);
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
    public String getNodeName() {
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

    public Set<MetaNote> getMetaNotes() {
        return GenericUtils.emptyOrSet(metaNotes);
    }

    public TermClass addMetaNote(MetaNote metaNote) {
        metaNotes = GenericUtils.addOrCreateSet(metaNotes, metaNote);
        return this;
    }

    public Set<MetaRelation> getMetaRelations() {
        return GenericUtils.emptyOrSet(metaRelations);
    }

    public TermClass addMetaRelation(MetaRelation metaRelation) {
        metaRelations = GenericUtils.addOrCreateSet(metaRelations, metaRelation);
        return this;
    }
}