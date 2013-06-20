package org.polyglotted.graphonomy.dao;

import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.RelationClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RelationClassDaoImpl extends AbstractDao<RelationClass> implements RelationClassDao {

    @Autowired
    public RelationClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }
}
