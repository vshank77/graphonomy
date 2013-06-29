package org.polyglotted.graphonomy.extzthes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.Note;
import org.polyglotted.graphonomy.model.Posting;
import org.polyglotted.graphonomy.model.Relation;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.model.TermTypeFactory;
import org.polyglotted.graphonomy.util.JsonUtils;
import org.polyglotted.xpathstax.XPathStaxParser;
import org.polyglotted.xpathstax.bind.NodeConverter;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class ZthesOntologyParser {

    public static void main(String ar[]) {
        try {
            FileInputStream input = new FileInputStream("src/do_not_git_version/simple-ontology.xml");
            ZthesOntologyParser reader = new ZthesOntologyParser();
            List<Term> list = reader.parse(input);

            Files.write(JsonUtils.asPrettyJson(list), new File("src/do_not_git_version/ontology-sample.txt"),
                    Charsets.UTF_8);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Term> parse(InputStream input) throws IOException {
        XPathStaxParser parser = new XPathStaxParser();
        final List<Term> terms = Lists.newArrayList();
        parser.addHandler(new NodeConverter<TermWrapper>("/Zthes/term/*") {
            @Override
            public void process(TermWrapper wrapper) {
                terms.add(convertWrapperToTerm(wrapper));
            }
        });
        parser.parse(input);
        return terms;
    }

    private Term convertWrapperToTerm(TermWrapper wrapper) {
        final String termId = wrapper.getTermId();
        Term term = new Term();
        term.setTermId(termId);
        term.setTermName(wrapper.getTermName());
        term.setQualifier(wrapper.getTermQualifier());
        term.setLanguage(wrapper.getTermLanguage());
        term.setVocabulary(wrapper.getTermVocabulary());
        term.setSortkey(wrapper.getTermSortkey());
        term.setTermType(TermTypeFactory.with(wrapper.getTermType().name()));
        term.setUpdate(wrapper.getTermUpdate().convert());
        term.setStatus(wrapper.getTermStatus().convert());
        term.setApproval(wrapper.getTermApproval().convert());
        term.setCreatedBy(wrapper.getTermCreatedBy());
        term.setCreatedDate(wrapper.getTermCreatedDate());
        term.setModifiedBy(wrapper.getTermModifiedBy());
        term.setModifiedDate(wrapper.getTermModifiedDate());
        setPostings(wrapper, term);
        term.setCategories(Arrays.asList(new Category(termId, wrapper.getTermCategory())));
        setNotes(wrapper, termId, term);
        setRelations(wrapper, termId, term);
        return term;
    }

    private void setNotes(TermWrapper wrapper, final String termId, Term term) {
        List<Note> notes = Lists.transform(wrapper.getTermNote(), new Function<NoteWrapper, Note>() {
            @Override
            public Note apply(NoteWrapper note) {
                return new Note(termId, note.getLabel(), note.getValue());
            }
        });
        if (notes.size() > 0) {
            term.setNotes(Lists.newArrayList(notes));
        }
    }

    private void setPostings(TermWrapper wrapper, Term term) {
        List<Posting> postings = Lists.transform(wrapper.getPostings(), new Function<PostingWrapper, Posting>() {
            @Override
            public Posting apply(PostingWrapper post) {
                return new Posting(post.getSourceDb(), post.getFieldName(), post.getHitCount());
            }
        });
        if (postings.size() > 0) {
            term.setPostings(Lists.newArrayList(postings));
        }
    }

    private void setRelations(TermWrapper wrapper, final String termId, Term term) {
        List<Relation> relations = Lists.transform(wrapper.getRelation(), new Function<RelationWrapper, Relation>() {
            @Override
            public Relation apply(RelationWrapper rel) {
                String relType = rel.getRelationType();
                relType = relType.startsWith("X-") ? relType.substring(2) : relType; 
                return new Relation(termId, relType, rel.getTermId());
            }
        });
        if (relations.size() > 0) {
            term.setRelations(Lists.newArrayList(relations));
        }
    }
}