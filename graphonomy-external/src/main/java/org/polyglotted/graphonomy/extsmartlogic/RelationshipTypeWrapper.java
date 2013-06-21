package org.polyglotted.graphonomy.extsmartlogic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "relationshipType")
public class RelationshipTypeWrapper {

    @XmlAttribute
    private String id;

    private String guid;

    @XmlElement
    private InnerType forwardName;

    @XmlElement
    private InnerType reverseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InnerType getForwardName() {
        return forwardName;
    }

    public void setForwardName(InnerType forwardName) {
        this.forwardName = forwardName;
    }

    public InnerType getReverseName() {
        return reverseName;
    }

    public void setReverseName(InnerType reverseName) {
        this.reverseName = reverseName;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    @XmlType
    public static class InnerType {
        @XmlAttribute
        private String abbreviation;

        @XmlValue
        private String desc;

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
