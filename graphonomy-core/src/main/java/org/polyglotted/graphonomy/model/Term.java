package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;

import org.polyglotted.graphonomy.model.TermTypeFactory.StandardTermType;

import com.google.common.collect.Lists;

public class Term implements GraphNode {

    private long id = -1;
    private String termId;
    private TermUpdate termUpdate = TermUpdate.add;
    private String termName;
    private String termQualifier;
    private TermType termType = StandardTermType.PT;
    private String termLanguage;
    private String termVocabulary;
    private transient List<TermClass> termCategory = Lists.newArrayList();
    private Status status = Status.active;
    private TermApproval termApproval = TermApproval.approved;
    private String termSortkey;
    private transient List<Note> termNote = Lists.newArrayList();
    private String termCreatedBy = "admin";
    private Date termCreatedDate = new Date();
    private String termModifiedBy = "admin";
    private Date termModifiedDate = new Date();
    private List<Posting> postings = Lists.newArrayList();
    private transient List<Relation> relation = Lists.newArrayList();

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
        checkArgument(termCategory.size() > 0);
        return this;
    }

    public String getTermId() {
        return termId;
    }

    public Term setTermId(String termId) {
        this.termId = checkNotNull(termId);
        return this;
    }

    public TermUpdate getTermUpdate() {
        return termUpdate;
    }

    public Term setTermUpdate(TermUpdate termUpdate) {
        this.termUpdate = checkNotNull(termUpdate);
        return this;
    }

    public String getTermName() {
        return termName;
    }

    public Term setTermName(String termName) {
        this.termName = checkNotNull(termName);
        return this;
    }

    public String getTermQualifier() {
        return termQualifier;
    }

    public Term setTermQualifier(String termQualifier) {
        this.termQualifier = termQualifier;
        return this;
    }

    public TermType getTermType() {
        return termType;
    }

    public Term setTermType(TermType termType) {
        this.termType = checkNotNull(termType);
        return this;
    }

    public String getTermLanguage() {
        return termLanguage;
    }

    public Term setTermLanguage(String termLanguage) {
        this.termLanguage = termLanguage;
        return this;
    }

    public String getTermVocabulary() {
        return termVocabulary;
    }

    public Term setTermVocabulary(String termVocabulary) {
        this.termVocabulary = termVocabulary;
        return this;
    }

    public List<TermClass> getTermCategory() {
        return termCategory;
    }

    public Term addTermCategory(TermClass category) {
        termCategory.add(category);
        return this;
    }

    public Term setTermCategory(List<TermClass> termCategory) {
        this.termCategory = checkNotNull(termCategory);
        return this;
    }

    public Status getTermStatus() {
        return status;
    }

    public Term setTermStatus(Status status) {
        this.status = checkNotNull(status);
        return this;
    }

    public TermApproval getTermApproval() {
        return termApproval;
    }

    public Term setTermApproval(TermApproval termApproval) {
        this.termApproval = checkNotNull(termApproval);
        return this;
    }

    public String getTermSortkey() {
        return termSortkey;
    }

    public Term setTermSortkey(String termSortkey) {
        this.termSortkey = termSortkey;
        return this;
    }

    public List<Note> getTermNote() {
        return termNote;
    }

    public Term addTermNote(Note note) {
        termNote.add(note);
        return this;
    }

    public Term setTermNote(List<Note> termNote) {
        this.termNote = checkNotNull(termNote);
        return this;
    }

    public String getTermCreatedBy() {
        return termCreatedBy;
    }

    public Term setTermCreatedBy(String termCreatedBy) {
        this.termCreatedBy = checkNotNull(termCreatedBy);
        return this;
    }

    public Date getTermCreatedDate() {
        return termCreatedDate;
    }

    public Term setTermCreatedDate(Date termCreatedDate) {
        this.termCreatedDate = checkNotNull(termCreatedDate);
        return this;
    }

    public String getTermModifiedBy() {
        return termModifiedBy;
    }

    public Term setTermModifiedBy(String termModifiedBy) {
        this.termModifiedBy = checkNotNull(termModifiedBy);
        return this;
    }

    public Date getTermModifiedDate() {
        return termModifiedDate;
    }

    public Term setTermModifiedDate(Date termModifiedDate) {
        this.termModifiedDate = checkNotNull(termModifiedDate);
        return this;
    }

    public List<Posting> getPostings() {
        return postings;
    }

    public Term addPosting(Posting posting) {
        postings.add(posting);
        return this;
    }

    public Term setPostings(List<Posting> postings) {
        this.postings = checkNotNull(postings);
        return this;
    }

    public List<Relation> getRelation() {
        return relation;
    }

    public Term addRelation(Relation rel) {
        relation.add(rel);
        return this;
    }

    public Term setRelation(List<Relation> relation) {
        this.relation = checkNotNull(relation);
        return this;
    }
}
