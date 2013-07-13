package org.polyglotted.graphonomy.meta;

import org.polyglotted.graphonomy.domain.AbstractDao;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.NodeType;
import org.polyglotted.graphonomy.model.NoteClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteClassDaoImpl extends AbstractDao<NoteClass> implements NoteClassDao {

    @Autowired
    public NoteClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NOTE_CLASS;
    }
}
