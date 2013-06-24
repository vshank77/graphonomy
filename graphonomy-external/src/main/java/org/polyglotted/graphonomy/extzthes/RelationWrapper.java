package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class RelationWrapper {

    @XmlElement
    private String relationType;

    @XmlElement
    private String sourceDb;

    @XmlElement
    private String termId;

    @XmlElement
    private String termName;

    public Appendable toXml(Appendable sb) throws IOException {
        sb.append("    <relation>\n");
        TermWrapper.appendItem(sb, "relationType", relationType, "      ");
        TermWrapper.appendItem(sb, "sourceDb", sourceDb, "      ");
        TermWrapper.appendItem(sb, "termId", termId, "      ");
        TermWrapper.appendItem(sb, "termName", termName, "      ");
        sb.append("    </relation>\n");
        return sb;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(String sourceDb) {
        this.sourceDb = sourceDb;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
