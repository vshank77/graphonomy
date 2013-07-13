package org.polyglotted.graphonomy.extsmartlogic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "relationshipType")
public class RelationshipTypeWrapper {

    @XmlAttribute
    private String id;
    @XmlAttribute
    private int baseTypeCode;
    @XmlAttribute
    private byte changeable;
    @XmlElement
    private InnerType forwardName;
    @XmlElement
    private InnerType reverseName;

    @Getter
    @Setter
    @XmlType
    public static class InnerType {

        @XmlAttribute
        private String abbreviation;
        @XmlValue
        private String desc;
    }
}
