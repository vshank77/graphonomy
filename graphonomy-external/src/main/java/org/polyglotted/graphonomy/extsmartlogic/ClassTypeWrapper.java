package org.polyglotted.graphonomy.extsmartlogic;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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

    public AttrTypeList getAttributeTypesForClassType() {
        return attributeTypesForClassType;
    }

    public void setAttributeTypesForClassType(AttrTypeList attributeTypesForClassType) {
        this.attributeTypesForClassType = attributeTypesForClassType;
    }

    public void setParentClassTypeId(String parentClassTypeId) {
        this.parentClassTypeId = parentClassTypeId;
    }

    public String getParentClassTypeId() {
        return parentClassTypeId;
    }

    public NoteTypeList getNoteTypesForClassType() {
        return noteTypesForClassType;
    }

    public void setNoteTypesForClassType(NoteTypeList noteTypesForClassType) {
        this.noteTypesForClassType = noteTypesForClassType;
    }

    public ChoiceTypeList getChoiceTypesForClassType() {
        return choiceTypesForClassType;
    }

    public void setChoiceTypesForClassType(ChoiceTypeList choiceTypesForClassType) {
        this.choiceTypesForClassType = choiceTypesForClassType;
    }

    public RelationshipTypeList getRelationshipTypesForClassType() {
        return relationshipTypesForClassType;
    }

    public void setRelationshipTypesForClassType(RelationshipTypeList relationshipTypesForClassType) {
        this.relationshipTypesForClassType = relationshipTypesForClassType;
    }

    @XmlType
    public static class AttrTypeList {
        @XmlElement
        private List<AttrTypeForClass> attributeTypeForClassType;

        public void setAttributeTypeForClassType(List<AttrTypeForClass> attributeTypeForClassType) {
            this.attributeTypeForClassType = attributeTypeForClassType;
        }

        public List<AttrTypeForClass> getAttributeTypeForClassType() {
            return attributeTypeForClassType;
        }
    }

    @XmlType
    public static class AttrTypeForClass {
        @XmlAttribute
        private String typeId;

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeId() {
            return typeId;
        }
    }

    @XmlType
    public static class NoteTypeList {
        @XmlElement
        private List<OptionTypeForClass> noteTypeForClassType;

        public void setNoteTypeForClassType(List<OptionTypeForClass> noteTypeForClassType) {
            this.noteTypeForClassType = noteTypeForClassType;
        }

        public List<OptionTypeForClass> getNoteTypeForClassType() {
            return noteTypeForClassType;
        }
    }

    @XmlType
    public static class ChoiceTypeList {
        @XmlElement
        private List<OptionTypeForClass> choiceTypeForClassType;

        public void setChoiceTypeForClassType(List<OptionTypeForClass> choiceTypeForClassType) {
            this.choiceTypeForClassType = choiceTypeForClassType;
        }

        public List<OptionTypeForClass> getChoiceTypeForClassType() {
            return choiceTypeForClassType;
        }
    }

    @XmlType
    public static class OptionTypeForClass {
        @XmlAttribute
        private String typeId;

        @XmlAttribute
        private byte mandatory;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public byte isMandatory() {
            return mandatory;
        }

        public void setMandatory(byte mandatory) {
            this.mandatory = mandatory;
        }
    }

    @XmlType
    public static class RelationshipTypeList {
        @XmlElement
        private List<RelationshipTypeForClass> relationshipTypeForClassType;

        public void setRelationshipTypeForClassType(List<RelationshipTypeForClass> relationshipTypeForClassType) {
            this.relationshipTypeForClassType = relationshipTypeForClassType;
        }

        public List<RelationshipTypeForClass> getRelationshipTypeForClassType() {
            return relationshipTypeForClassType;
        }
    }

    @XmlType
    public static class RelationshipTypeForClass {
        @XmlAttribute
        private String typeId;

        @XmlAttribute
        private String targetClassTypeId;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTargetClassTypeId() {
            return targetClassTypeId;
        }

        public void setTargetClassTypeId(String targetClassTypeId) {
            this.targetClassTypeId = targetClassTypeId;
        }
    }
}
