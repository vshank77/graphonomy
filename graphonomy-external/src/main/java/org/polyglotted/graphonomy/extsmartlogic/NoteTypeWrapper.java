package org.polyglotted.graphonomy.extsmartlogic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "noteType")
public class NoteTypeWrapper {

    @XmlAttribute
    private String id;
    @XmlElement
    private String name;
    @XmlAttribute
    private byte unique;
    @XmlElement
    private String defaultValue;
    @XmlElement
    private RegExpr regexpType;

    @Getter
    @Setter
    @XmlType
    public static class RegExpr {

        @XmlAttribute
        private int minLength;
        @XmlAttribute
        private int maxLength;
        @XmlAttribute
        private String expr;
    }
}
