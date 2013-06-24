package org.polyglotted.graphonomy.extsmartlogic;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "choiceType")
public class ChoiceTypeWrapper {

    @XmlAttribute
    private String id;

    @XmlElement
    private String name;

    @XmlAttribute
    private String defaultValueId;

    @XmlElement
    private ValueList values;

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

    public String getDefaultValueId() {
        return defaultValueId;
    }

    public void setDefaultValueId(String defaultValueId) {
        this.defaultValueId = defaultValueId;
    }

    public void setValues(ValueList values) {
        this.values = values;
    }

    public ValueList getValues() {
        return values;
    }

    @XmlType
    public static class ValueList {

        @XmlElement
        private List<Value> value;

        public void setValue(List<Value> value) {
            this.value = value;
        }

        public List<Value> getValue() {
            return value;
        }
    }

    @XmlType
    public static class Value {

        @XmlAttribute
        private String id;

        @XmlValue
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
