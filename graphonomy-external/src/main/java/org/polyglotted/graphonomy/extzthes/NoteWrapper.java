package org.polyglotted.graphonomy.extzthes;

import java.io.IOException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
public class NoteWrapper {

    @XmlAttribute
    private String label;

    @XmlAttribute
    private String vocab;

    @XmlValue
    private String value;

    public Appendable toXml(Appendable sb) throws IOException {
        sb.append("    <termNote label=\"");
        sb.append(label);
        if (vocab != null) {
            sb.append("\" vocab=\"");
            sb.append(vocab);
        }
        sb.append("\"");
        if (value != null) {
            sb.append(">");
            sb.append(value);
            sb.append("</termNote>\n");
        }
        else {
            sb.append("/>\n");
        }
        return sb;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
