package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
@Setter
public class Term extends AbstractTerm {

    @GraphProperty
    private String qualifier;
    @GraphProperty
    private String language;
    @GraphProperty
    private String vocabulary;
    @GraphProperty
    private String sortkey;

    @GraphProperty(PropertyType.LIST)
    private List<Posting> postings;

    private List<Category> categories;
    private List<Note> notes;
    private List<Relation> relations;

    public Term(String termId, String termName) {
        super(termId, termName);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.TERM;
    }

    @Override
    public GraphNode validate() {
        super.validate();
        checkArgument(getCategories().size() > 0);
        return this;
    }

    public List<Posting> getPostings() {
        return GenericUtils.emptyOrList(postings);
    }

    public Term addPosting(Posting posting) {
        postings = GenericUtils.addOrCreateList(postings, posting.validate());
        return this;
    }

    public List<Category> getCategories() {
        return GenericUtils.emptyOrList(categories);
    }

    public Term addCategory(Category category) {
        categories = GenericUtils.addOrCreateList(categories, category);
        return this;
    }

    public List<Note> getNotes() {
        return GenericUtils.emptyOrList(notes);
    }

    public Term addNote(Note note) {
        notes = GenericUtils.addOrCreateList(notes, note);
        return this;
    }

    public List<Relation> getRelations() {
        return GenericUtils.emptyOrList(relations);
    }

    public Term addRelation(Relation relation) {
        relations = GenericUtils.addOrCreateList(relations, relation);
        return this;
    }
}
