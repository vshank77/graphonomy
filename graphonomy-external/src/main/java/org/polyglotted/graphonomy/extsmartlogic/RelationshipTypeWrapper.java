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

    @XmlAttribute
    private int baseTypeCode;

    @XmlAttribute
    private byte changeable;
    
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

    public int getBaseTypeCode() {
        return baseTypeCode;
    }

    public void setBaseTypeCode(int baseTypeCode) {
        this.baseTypeCode = baseTypeCode;
    }

    public byte getChangeable() {
        return changeable;
    }

    public void setChangeable(byte changeable) {
        this.changeable = changeable;
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
