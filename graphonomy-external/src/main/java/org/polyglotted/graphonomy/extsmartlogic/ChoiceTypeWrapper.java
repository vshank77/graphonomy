package org.polyglotted.graphonomy.extsmartlogic;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Getter
    @Setter
    @XmlType
    public static class ValueList {
        @XmlElement
        private List<Value> value;
    }

    @Getter
    @Setter
    @XmlType
    public static class Value {
        @XmlAttribute
        private String id;
        @XmlValue
        private String value;
    }
}
