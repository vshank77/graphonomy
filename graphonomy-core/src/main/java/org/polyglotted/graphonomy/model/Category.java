package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.HAS_CATEGORY;

import java.util.Arrays;
import java.util.List;

public class Category implements GraphRelation {

    @GraphProperty
    private String termId;
    @GraphProperty
    private String className;

    public Category() {}

    public Category(Term term, TermClass termClass) {
        this(term.getTermId(), termClass.getClassName());
    }

    public Category(String termId, TermClass termClass) {
        this(termId, termClass.getClassName());
    }

    public Category(String termId, String className) {
        setTermId(termId);
        setClassName(className);
    }

    @Override
    public List<Link> getLinks() {
        return Arrays.asList(new Link(termId, HAS_CATEGORY, className));
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + ((termId == null) ? 0 : termId.hashCode());
        result = prime * result + ((className == null) ? 0 : className.hashCode());
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
        Category other = (Category) obj;
        if ((termId == null) ? (other.termId != null) : !termId.equals(other.termId))
            return false;
        if (className == null ? (other.className != null) : !className.equals(other.className))
            return false;
        return true;
    }

    public String getTermId() {
        return termId;
    }

    public Category setTermId(String termId) {
        this.termId = checkNotNull(termId);
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Category setClassName(String className) {
        this.className = checkNotNull(className);
        return this;
    }
}
