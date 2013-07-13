package org.polyglotted.graphonomy.meta;

import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.RelationClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationClassDaoImpl extends AbstractDao<RelationClass> implements RelationClassDao {

    @Autowired
    public RelationClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.RELATION_CLASS;
    }
}
