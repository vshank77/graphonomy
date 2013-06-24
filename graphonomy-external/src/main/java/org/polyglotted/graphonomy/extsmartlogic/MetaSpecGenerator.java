package org.polyglotted.graphonomy.extsmartlogic;

import java.util.List;
import java.util.Map;

import org.polyglotted.graphonomy.extsmartlogic.ClassTypeWrapper.AttrTypeForClass;
import org.polyglotted.graphonomy.extsmartlogic.ClassTypeWrapper.OptionTypeForClass;
import org.polyglotted.graphonomy.extsmartlogic.ClassTypeWrapper.RelationshipTypeForClass;
import org.polyglotted.graphonomy.extsmartlogic.NoteTypeWrapper.RegExpr;
import org.polyglotted.graphonomy.extsmartlogic.RelationshipTypeWrapper.InnerType;
import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.MetaSpec;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.RelationClass.BaseType;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.model.TypeSafe;

import com.google.common.collect.Maps;

public class MetaSpecGenerator {

    private final HandlerFactory handlerFactory;
    private final Map<String, NoteClass> notesMap = Maps.newLinkedHashMap();

    final MetaSpec spec = new MetaSpec();

    public MetaSpecGenerator(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public void generateSpec() {
        generateNotes();
        generateRelationships();
        generateClasses();
    }

    private void generateClasses() {
        for (Map.Entry<String, ClassTypeWrapper> entry : handlerFactory.classes.entrySet()) {
            ClassTypeWrapper classType = entry.getValue();
            TermClass termCls = new TermClass(classType.getName());
            if (classType.getParentClassTypeId() != null) {
                ClassTypeWrapper parType = handlerFactory.classes.get(classType.getParentClassTypeId());
                termCls.setParentClassName(parType.getName());
            }
            if (classType.getAttributeTypesForClassType() != null) {
                for (AttrTypeForClass attr : classType.getAttributeTypesForClassType().getAttributeTypeForClassType()) {
                    NoteClass noteCls = notesMap.get("attr" + attr.getTypeId());
                    termCls.addMetaNote(new MetaNote(termCls, noteCls, false));
                }
            }
            if (classType.getChoiceTypesForClassType() != null) {
                for (OptionTypeForClass choice : classType.getChoiceTypesForClassType().getChoiceTypeForClassType()) {
                    NoteClass noteCls = notesMap.get("choice" + choice.getTypeId());
                    termCls.addMetaNote(new MetaNote(termCls, noteCls, choice.isMandatory() == (byte) 1));
                }
            }
            if (classType.getNoteTypesForClassType() != null) {
                for (OptionTypeForClass note : classType.getNoteTypesForClassType().getNoteTypeForClassType()) {
                    NoteClass noteCls = notesMap.get("note" + note.getTypeId());
                    termCls.addMetaNote(new MetaNote(termCls, noteCls, note.isMandatory() == (byte) 1));
                }
            }
            if (classType.getRelationshipTypesForClassType() != null) {
                for (RelationshipTypeForClass rel : classType.getRelationshipTypesForClassType()
                        .getRelationshipTypeForClassType()) {
                    String termClassName = termCls.getClassName();
                    String targetClassName = handlerFactory.classes.get(rel.getTargetClassTypeId()).getName();
                    RelationshipTypeWrapper relWrapper = handlerFactory.relationships.get(rel.getTypeId());
                    termCls.addMetaRelation(new MetaRelation(termClassName, relWrapper.getForwardName()
                            .getAbbreviation(), targetClassName));
                }
            }
            spec.addTermClass(termCls);
        }
    }

    private void generateRelationships() {
        for (Map.Entry<String, RelationshipTypeWrapper> entry : handlerFactory.relationships.entrySet()) {
            RelationshipTypeWrapper relationship = entry.getValue();
            InnerType fwdName = relationship.getForwardName();
            InnerType revName = relationship.getReverseName();
            BaseType type = BaseType.values()[relationship.getBaseTypeCode()];
            boolean extended = (relationship.getChangeable() == (byte) 1);
            spec.addRelationClass(new RelationClass(fwdName.getAbbreviation(), type, fwdName.getDesc())
                    .setExtended(extended));
            if (!revName.getAbbreviation().equals(fwdName.getAbbreviation())) {
                spec.addRelationClass(new RelationClass(revName.getAbbreviation(), type, revName.getDesc())
                        .setExtended(extended));
            }
        }
    }

    private void generateNotes() {
        generateNotesFromAttributes(handlerFactory.attributes);
        generateNotesFromChoices(handlerFactory.choices);
        generateNotesFromNotes(handlerFactory.notes);
    }

    private void generateNotesFromNotes(List<NoteTypeWrapper> notes) {
        for (NoteTypeWrapper note : notes) {
            RegExpr regEx = note.getRegexpType();
            NoteClass cls = new NoteClass(note.getName(), TypeSafe.str);
            cls.setDefaultValue(note.getDefaultValue());
            cls.setRange(regEx.getMinLength(), regEx.getMaxLength());
            cls.setPattern(regEx.getExpr());
            spec.addNoteClass(cls);
            notesMap.put("note" + note.getId(), cls);
        }
    }

    private void generateNotesFromChoices(List<ChoiceTypeWrapper> choices) {
        for (ChoiceTypeWrapper choice : choices) {
            NoteClass cls = new NoteClass(choice.getName(), TypeSafe.str);
            for (ChoiceTypeWrapper.Value val : choice.getValues().getValue()) {
                cls.addEnum(val.getValue());
                if (val.getId().equals(choice.getDefaultValueId())) {
                    cls.setDefaultValue(val.getValue());
                }
            }
            spec.addNoteClass(cls);
            notesMap.put("choice" + choice.getId(), cls);
        }
    }

    private void generateNotesFromAttributes(List<AttributeTypeWrapper> attributes) {
        for (AttributeTypeWrapper attr : attributes) {
            NoteClass cls = new NoteClass(attr.getName(), TypeSafe.bool).setDefaultValue("1");
            spec.addNoteClass(cls);
            notesMap.put("attr" + attr.getId(), cls);
        }
    }
}
