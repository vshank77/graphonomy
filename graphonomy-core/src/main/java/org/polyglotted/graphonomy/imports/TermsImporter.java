package org.polyglotted.graphonomy.imports;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.polyglotted.graphonomy.dao.TermDao;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
@Transactional
public class TermsImporter {

    private TermDao termDao;

    public void importTerms(InputStream is) throws IOException {
        List<Term> terms = JsonUtils.mapper().readValue(is, new TypeReference<List<Term>>(){});
        for (Term term : terms) {
            termDao.create(term);
        }
        for (Term term : terms) {
            termDao.saveRelations(term);
        }
    }

    
    @Autowired
    public void setTermDao(TermDao termDao) {
        this.termDao = checkNotNull(termDao);
    }
}
