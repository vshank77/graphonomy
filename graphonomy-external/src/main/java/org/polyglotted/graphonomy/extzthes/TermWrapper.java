package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Lists;

@Getter
@Setter
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
}
