package org.polyglotted.graphonomy.imports;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.polyglotted.graphonomy.dao.NoteClassDao;
import org.polyglotted.graphonomy.dao.RelationClassDao;
import org.polyglotted.graphonomy.dao.TermClassDao;
import org.polyglotted.graphonomy.model.MetaSpec;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MetaSpecImporter {

    private NoteClassDao noteClassDao;
    private RelationClassDao relationClassDao;
    private TermClassDao termClassDao;

    public void importSpec(InputStream is) throws IOException {
        MetaSpec metaSpec = JsonUtils.mapper().readValue(is, MetaSpec.class);
        for (NoteClass noteCls : metaSpec.getNoteClasses()) {
            noteClassDao.create(noteCls);
        }
        for (RelationClass relCls : metaSpec.getRelationClasses()) {
            relationClassDao.create(relCls);
        }
        for (TermClass termCls : metaSpec.getTermClasses()) {
            termClassDao.create(termCls);
        }
        for (TermClass termCls : metaSpec.getTermClasses()) {
            termClassDao.saveMetaRelations(termCls);
        }
    }

    @Autowired
    public void setNoteClassDao(NoteClassDao noteClassDao) {
        this.noteClassDao = checkNotNull(noteClassDao);
    }

    @Autowired
    public void setRelationClassDao(RelationClassDao relationClassDao) {
        this.relationClassDao = checkNotNull(relationClassDao);
    }

    @Autowired
    public void setTermClassDao(TermClassDao termClassDao) {
        this.termClassDao = checkNotNull(termClassDao);
    }
}
