package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_META_RELATION;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IS_META_RELATED_TO;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
public class MetaRelation implements GraphRelation {

    @GraphProperty
    private String termClassName;
    @GraphProperty
    private String relationName;
    @GraphProperty
    private String targetClassName;

    public static MetaRelation from(TermClass termClass, RelationClass relationClass, TermClass targetClass) {
        return new MetaRelation(termClass.getClassName(), relationClass.getRelationName(), targetClass.getClassName());
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termClassName, IS_META_RELATED_TO, targetClassName, relationName), new Link(
                termClassName, HAS_META_RELATION, relationName, targetClassName));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termClassName);
        checkNotNull(relationName);
        checkNotNull(targetClassName);
        return this;
    }
}
