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
public class Category implements GraphRelation {

    public static final String HAS_CATEGORY = "has_category";

    @GraphProperty
    private String termId;
    @GraphProperty
    private String className;

    public static Category from(Term term, TermClass termClass) {
        return new Category(term.getTermId(), termClass.getClassName());
    }

    public static Category from(String termId, TermClass termClass) {
        return new Category(termId, termClass.getClassName());
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, HAS_CATEGORY, className));
    }

    @Override
    public GraphRelation validate() {
        checkNotNull(termId);
        checkNotNull(className);
        return this;
    }
}
