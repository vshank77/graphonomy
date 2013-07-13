package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

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
public class Relation implements GraphRelation {

    public static final String HAS_RELATION = "has_relation";
    public static final String IS_RELATED_TO = "is_related_to";

    @GraphProperty
    private String termId;
    @GraphProperty
    private String relationName;
    @GraphProperty
    private String targetTermId;

    public static Relation from(Term term, String rel, Term target) {
        return new Relation(term.getTermId(), rel, target.getTermId());
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, IS_RELATED_TO, targetTermId),
                new Link(termId, HAS_RELATION, relationName));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termId);
        checkNotNull(relationName);
        checkNotNull(targetTermId);
        return this;
    }
}
