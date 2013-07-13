package org.polyglotted.graphonomy.extsmartlogic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "attributeType")
public class AttributeTypeWrapper {

    @XmlAttribute
    private String id;
    @XmlElement
    private String name;
}
