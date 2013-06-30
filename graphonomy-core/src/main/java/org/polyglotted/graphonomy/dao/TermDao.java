package org.polyglotted.graphonomy.dao;

import org.polyglotted.graphonomy.domain.PageResult;
import org.polyglotted.graphonomy.model.Term;

public interface TermDao extends BaseDao<Term> {

    /**
     * when creating a term in database, the relations are not saved automatically (while notes and categories are
     * saved). This is to allow to forward referencing the relationships while batch loading. So callers have to
     * explicitly invoke this method to save the relationships.
     * 
     * @param term
     *            the Term origin for saving the relationships
     */
    void saveRelations(Term term);

    PageResult search(String prefix, String className, int maxResults);
}
