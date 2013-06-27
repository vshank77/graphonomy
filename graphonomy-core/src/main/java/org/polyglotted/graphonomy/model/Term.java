package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.model.TermTypeFactory.StandardTermType;
import org.polyglotted.graphonomy.util.DateUtils;

import com.google.common.collect.Lists;

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

    public Term() {}

    public Term(String termId, String termName) {
        setTermId(termId);
        setTermName(termName);
    }

    @Override
    public int hashCode() {
        return 31 * ((termId == null) ? 0 : termId.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Term other = (Term) obj;
        return (termId == null) ? (other.termId == null) : (termId.equals(other.termId));
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
    public GraphNode validate() {
        checkNotNull(termId);
        checkNotNull(termName);
        checkArgument(getCategories().size() > 0);
        return this;
    }

    public String getTermId() {
        return termId;
    }

    public Term setTermId(String termId) {
        this.termId = checkNotNull(termId);
        return this;
    }

    public String getTermName() {
        return termName;
    }

    public Term setTermName(String termName) {
        this.termName = checkNotNull(termName);
        return this;
    }

    public TermType getTermType() {
        return termType;
    }

    public Term setTermType(TermType termType) {
        this.termType = checkNotNull(termType);
        return this;
    }

    public String getQualifier() {
        return qualifier;
    }

    public Term setQualifier(String qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Term setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public Term setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
        return this;
    }

    public String getSortkey() {
        return sortkey;
    }

    public Term setSortkey(String sortkey) {
        this.sortkey = sortkey;
        return this;
    }

    public TermUpdate getUpdate() {
        return update;
    }

    public Term setUpdate(TermUpdate update) {
        this.update = checkNotNull(update);
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Term setStatus(Status status) {
        this.status = checkNotNull(status);
        return this;
    }

    public TermApproval getApproval() {
        return approval;
    }

    public Term setApproval(TermApproval approval) {
        this.approval = checkNotNull(approval);
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Term setCreatedBy(String createdBy) {
        this.createdBy = checkNotNull(createdBy);
        return this;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Term setCreatedDate(String createdDate) {
        this.createdDate = checkNotNull(createdDate);
        return this;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Term setModifiedBy(String modifiedBy) {
        this.modifiedBy = checkNotNull(modifiedBy);
        return this;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public Term setModifiedDate(String modifiedDate) {
        this.modifiedDate = checkNotNull(modifiedDate);
        return this;
    }

    public List<Posting> getPostings() {
        if (postings == null)
            return Collections.emptyList();
        return postings;
    }

    public Term addPosting(Posting posting) {
        if (postings == null)
            postings = Lists.newArrayList();
        postings.add(posting);
        return this;
    }

    public Term setPostings(List<Posting> postings) {
        this.postings = Lists.newArrayList(postings);
        return this;
    }

    public List<Category> getCategories() {
        if (categories == null)
            return Collections.emptyList();
        return categories;
    }

    public Term addCategory(Category category) {
        if (categories == null)
            categories = Lists.newArrayList();
        categories.add(category);
        return this;
    }

    public Term setCategories(List<Category> categories) {
        this.categories = Lists.newArrayList(categories);
        return this;
    }

    public List<Note> getNotes() {
        if (notes == null)
            return Collections.emptyList();
        return notes;
    }

    public Term addNote(Note note) {
        if (notes == null)
            notes = Lists.newArrayList();
        notes.add(note);
        return this;
    }

    public Term setNotes(List<Note> notes) {
        this.notes = Lists.newArrayList(notes);
        return this;
    }

    public List<Relation> getRelations() {
        if (relations == null)
            return Collections.emptyList();
        return relations;
    }

    public Term addRelation(Relation relation) {
        if (relations == null)
            relations = Lists.newArrayList();
        relations.add(relation);
        return this;
    }

    public Term setRelations(List<Relation> relations) {
        this.relations = Lists.newArrayList(relations);
        return this;
    }
}
