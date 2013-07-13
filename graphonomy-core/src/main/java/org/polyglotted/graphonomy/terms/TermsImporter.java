package org.polyglotted.graphonomy.terms;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.polyglotted.graphonomy.api.Importer;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
@Qualifier("graphonomy.TermsImporter")
@Transactional
public class TermsImporter implements Importer<List<Term>> {

    private TermDao termDao;

    public List<Term> doImport(InputStream is) throws IOException {
        List<Term> terms = JsonUtils.mapper().readValue(is, new TypeReference<List<Term>>() {});
        for (Term term : terms) {
            termDao.create(term);
        }
        for (Term term : terms) {
            termDao.saveRelations(term);
        }
        return terms;
    }

    @Autowired
    public void setTermDao(TermDao termDao) {
        this.termDao = checkNotNull(termDao);
    }
}
