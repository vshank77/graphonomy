package org.polyglotted.graphonomy.schemarepo;

import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.FieldClass;
import org.polyglotted.graphonomy.model.NodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldClassDaoImpl extends AbstractDao<FieldClass> implements FieldClassDao {

    @Autowired
    public FieldClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FIELD_CLASS;
    }
}
