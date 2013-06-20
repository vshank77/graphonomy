package org.polyglotted.graphonomy.dao;

import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.NoteClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class NoteClassDaoImpl extends AbstractDao<NoteClass> implements NoteClassDao {

    @Autowired
    public NoteClassDaoImpl(GraphonomyDatabase database) {
        super(database);
    }
}
