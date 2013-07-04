package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.model.TermTypeFactory.StandardTermType;
import org.polyglotted.graphonomy.util.DateUtils;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(of = { "termId", "termName" })
@Getter
@NoArgsConstructor
@Setter
public class Term implements GraphNode {

    private long id = -1;

    @GraphProperty
    private String termId;
    @GraphProperty
    private String termName;
    @GraphProperty
    private String qualifier;
    @GraphProperty
    private String language;
    @GraphProperty
    private String vocabulary;
    @GraphProperty
    private String sortkey;
    @GraphProperty(PropertyType.STRING)
    private TermType termType = StandardTermType.PT;
    @GraphProperty(PropertyType.ENUM)
    private TermUpdate update = TermUpdate.add;
    @GraphProperty(PropertyType.ENUM)
    private Status status = Status.active;
    @GraphProperty(PropertyType.ENUM)
    private TermApproval approval = TermApproval.approved;
    @GraphProperty
    private String createdBy = "admin";
    @GraphProperty
    private String createdDate = DateUtils.format(new Date());
    @GraphProperty
    private String modifiedBy = "admin";
    @GraphProperty
    private String modifiedDate = DateUtils.format(new Date());

    @GraphProperty(PropertyType.LIST)
    private List<Posting> postings;

    private List<Category> categories;
    private List<Note> notes;
    private List<Relation> relations;

    public Term(String termId, String termName) {
        setTermId(termId);
        setTermName(termName);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return getTermId();
    }

    @Override
    public String getNodeName() {
        return getTermName();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.TERM;
    }

    @Override
    public GraphNode validate() {
        checkNotNull(termId);
        checkNotNull(termName);
        checkNotNull(termType);
        checkNotNull(update);
        checkNotNull(status);
        checkNotNull(approval);
        checkNotNull(createdBy);
        checkNotNull(createdDate);
        checkNotNull(modifiedBy);
        checkNotNull(modifiedDate);
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
