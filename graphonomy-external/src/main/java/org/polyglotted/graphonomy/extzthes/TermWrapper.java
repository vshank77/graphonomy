package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement(name = "term")
public class TermWrapper {

    @XmlElement
    private String termId;

    @XmlElement
    private UpdateWrapper termUpdate = UpdateWrapper.add;

    @XmlElement
    private String termName;

    @XmlElement
    private String termQualifier;

    @XmlElement
    private TypeWrapper termType = TypeWrapper.PT;

    @XmlElement
    private String termLanguage;

    @XmlElement
    private String termVocabulary;

    @XmlElement
    private String termCategory;

    @XmlElement
    private StatusWrapper termStatus = StatusWrapper.active;

    @XmlElement
    private ApprovalWrapper termApproval = ApprovalWrapper.approved;

    @XmlElement
    private String termSortkey;

    @XmlElement
    private List<NoteWrapper> termNote = Lists.newArrayList();

    @XmlElement
    private String termCreatedBy;

    @XmlElement
    private String termCreatedDate;

    @XmlElement
    private String termModifiedBy;

    @XmlElement
    private String termModifiedDate;

    @XmlElement
    private List<PostingWrapper> postings = Lists.newArrayList();

    @XmlElement
    private List<RelationWrapper> relation = Lists.newArrayList();

    public Appendable toXml(Appendable sb) throws IOException {
        sb.append("  <term>\n");
        appendItem(sb, "termId", termId, "    ");
        appendItem(sb, "termUpdate", termUpdate.name(), "    ");
        appendItem(sb, "termName", termName, "    ");
        appendItem(sb, "termType", termType.name(), "    ");
        appendItem(sb, "termCategory", termCategory, "    ");
        appendItem(sb, "termStatus", termStatus.name(), "    ");
        appendItem(sb, "termApproval", termApproval.name(), "    ");
        appendItem(sb, "termCreatedDate", termCreatedDate, "    ");
        appendItem(sb, "termCreatedBy", termCreatedBy, "    ");
        appendItem(sb, "termModifiedDate", termModifiedDate, "    ");
        appendItem(sb, "termModifiedBy", termModifiedBy, "    ");
        if (termNote.size() > 0) {
            for (NoteWrapper note : termNote) {
                note.toXml(sb);
            }
        }
        if (postings.size() > 0) {
            for (PostingWrapper post : postings) {
                post.toXml(sb);
            }
        }
        if (relation.size() > 0) {
            for (RelationWrapper rel : relation) {
                rel.toXml(sb);
            }
        }
        sb.append("  </term>\n");
        return sb;
    }

    static Appendable appendItem(Appendable sb, String label, String field, String buf) throws IOException {
        if (field != null) {
            sb.append(buf);
            sb.append("<" + label + ">");
            sb.append(field);
            sb.append("</" + label + ">\n");
        }
        return sb;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public UpdateWrapper getTermUpdate() {
        return termUpdate;
    }

    public void setTermUpdate(UpdateWrapper termUpdate) {
        this.termUpdate = termUpdate;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermQualifier() {
        return termQualifier;
    }

    public void setTermQualifier(String termQualifier) {
        this.termQualifier = termQualifier;
    }

    public TypeWrapper getTermType() {
        return termType;
    }

    public void setTermType(TypeWrapper termType) {
        this.termType = termType;
    }

    public String getTermLanguage() {
        return termLanguage;
    }

    public void setTermLanguage(String termLanguage) {
        this.termLanguage = termLanguage;
    }

    public String getTermVocabulary() {
        return termVocabulary;
    }

    public void setTermVocabulary(String termVocabulary) {
        this.termVocabulary = termVocabulary;
    }

    public String getTermCategory() {
        return termCategory;
    }

    public void setTermCategory(String termCategory) {
        this.termCategory = termCategory;
    }

    public StatusWrapper getTermStatus() {
        return termStatus;
    }

    public void setTermStatus(StatusWrapper status) {
        this.termStatus = status;
    }

    public ApprovalWrapper getTermApproval() {
        return termApproval;
    }

    public void setTermApproval(ApprovalWrapper termApproval) {
        this.termApproval = termApproval;
    }

    public String getTermSortkey() {
        return termSortkey;
    }

    public void setTermSortkey(String termSortkey) {
        this.termSortkey = termSortkey;
    }

    public List<NoteWrapper> getTermNote() {
        return termNote;
    }

    public void setTermNote(List<NoteWrapper> termNote) {
        this.termNote = termNote;
    }

    public String getTermCreatedBy() {
        return termCreatedBy;
    }

    public void setTermCreatedBy(String termCreatedBy) {
        this.termCreatedBy = termCreatedBy;
    }

    public String getTermCreatedDate() {
        return termCreatedDate;
    }

    public void setTermCreatedDate(String termCreatedDate) {
        this.termCreatedDate = termCreatedDate;
    }

    public String getTermModifiedBy() {
        return termModifiedBy;
    }

    public void setTermModifiedBy(String termModifiedBy) {
        this.termModifiedBy = termModifiedBy;
    }

    public String getTermModifiedDate() {
        return termModifiedDate;
    }

    public void setTermModifiedDate(String termModifiedDate) {
        this.termModifiedDate = termModifiedDate;
    }

    public List<PostingWrapper> getPostings() {
        return postings;
    }

    public void setPostings(List<PostingWrapper> postings) {
        this.postings = postings;
    }

    public List<RelationWrapper> getRelation() {
        return relation;
    }

    public void setRelation(List<RelationWrapper> relation) {
        this.relation = relation;
    }
}
