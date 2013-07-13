package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
