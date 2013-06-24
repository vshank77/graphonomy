package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class PostingWrapper {

    @XmlElement
    private String sourceDb;

    @XmlElement
    private String fieldName;

    @XmlElement
    private String hitCount;

    public Appendable toXml(Appendable sb) throws IOException {
        sb.append("    <postings>\n");
        TermWrapper.appendItem(sb, "sourceDb", sourceDb, "      ");
        TermWrapper.appendItem(sb, "fieldName", fieldName, "      ");
        TermWrapper.appendItem(sb, "hitCount", hitCount, "      ");
        sb.append("    </postings>\n");
        return sb;
    }

    public String getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(String sourceDb) {
        this.sourceDb = sourceDb;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }
}
