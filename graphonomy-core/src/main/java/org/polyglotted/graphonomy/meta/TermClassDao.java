package org.polyglotted.graphonomy.meta;

import org.polyglotted.graphonomy.domain.BaseDao;
import org.polyglotted.graphonomy.model.TermClass;

public interface TermClassDao extends BaseDao<TermClass> {

    /**
     * when creating a termClass in database, the meta relationships are not saved automatically (while notes are
     * saved). This is to allow to forward referencing the relationships while batch loading. So callers have to
     * explicitly invoke this method to save the meta relationships.
     * 
     * @param term
     *            the Term origin for saving the relationships
     */
    void saveMetaRelations(TermClass term);
}
