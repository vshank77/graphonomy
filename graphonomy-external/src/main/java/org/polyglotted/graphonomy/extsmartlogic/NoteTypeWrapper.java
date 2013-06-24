package org.polyglotted.graphonomy.extsmartlogic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getUnique() {
        return unique;
    }

    public void setUnique(byte unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public RegExpr getRegexpType() {
        return regexpType;
    }

    public void setRegexpType(RegExpr regexpType) {
        this.regexpType = regexpType;
    }

    @XmlType
    public static class RegExpr {
        @XmlAttribute
        private int minLength;
        @XmlAttribute
        private int maxLength;
        @XmlAttribute
        private String expr;

        public int getMinLength() {
            return minLength;
        }

        public void setMinLength(int minLength) {
            this.minLength = minLength;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public String getExpr() {
            return expr;
        }

        public void setExpr(String expr) {
            this.expr = expr;
        }

    }
}
