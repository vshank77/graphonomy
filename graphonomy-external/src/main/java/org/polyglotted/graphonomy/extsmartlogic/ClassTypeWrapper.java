package org.polyglotted.graphonomy.extsmartlogic;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "classType")
public class ClassTypeWrapper {

    @XmlAttribute
    private String id;
    @XmlAttribute
    private String parentClassTypeId;
    @XmlElement(name = "classTypeName")
    private String name;
    @XmlElement
    private AttrTypeList attributeTypesForClassType;
    @XmlElement
    private NoteTypeList noteTypesForClassType;
    @XmlElement
    private ChoiceTypeList choiceTypesForClassType;
    @XmlElement
    private RelationshipTypeList relationshipTypesForClassType;

    @Getter
    @Setter
    @XmlType
    public static class AttrTypeList {
        @XmlElement
        private List<AttrTypeForClass> attributeTypeForClassType;
    }

    @Getter
    @Setter
    @XmlType
    public static class AttrTypeForClass {
        @XmlAttribute
        private String typeId;
    }

    @Getter
    @Setter
    @XmlType
    public static class NoteTypeList {
        @XmlElement
        private List<OptionTypeForClass> noteTypeForClassType;
    }

    @Getter
    @Setter
    @XmlType
    public static class ChoiceTypeList {
        @XmlElement
        private List<OptionTypeForClass> choiceTypeForClassType;
    }

    @Getter
    @Setter
    @XmlType
    public static class OptionTypeForClass {
        @XmlAttribute
        private String typeId;
        @XmlAttribute
        private byte mandatory;
    }

    @Getter
    @Setter
    @XmlType
    public static class RelationshipTypeList {
        @XmlElement
        private List<RelationshipTypeForClass> relationshipTypeForClassType;
    }

    @Getter
    @Setter
    @XmlType
    public static class RelationshipTypeForClass {
        @XmlAttribute
        private String typeId;
        @XmlAttribute
        private String targetClassTypeId;
    }
}
