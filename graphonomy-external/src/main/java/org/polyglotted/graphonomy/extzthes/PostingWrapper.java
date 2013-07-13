package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
