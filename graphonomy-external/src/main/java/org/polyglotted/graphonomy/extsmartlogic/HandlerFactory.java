package org.polyglotted.graphonomy.extsmartlogic;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import org.polyglotted.graphonomy.util.HexUtils;
import org.polyglotted.xpathstax.bind.NodeConverter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HandlerFactory {

    private static final String ATTR_TYPE_XPATH = "/thesaurusStructure/attributeTypes/attributeType/*";
    private static final String CHOICE_TYPE_XPATH = "/thesaurusStructure/choiceTypes/choiceType/*";
    private static final String NOTE_TYPE_XPATH = "/thesaurusStructure/noteTypes/noteType/*";
    private static final String RELATIONSHIP_TYPE_XPATH = "/thesaurusStructure/relationshipTypes/relationshipType/*";
    private static final String CLASS_TYPE_XPATH = "/thesaurusStructure/classTypes/classType/*";
    private static final SecureRandom random = new SecureRandom();

    final List<AttributeTypeWrapper> attributes = Lists.newArrayList();
    final List<ChoiceTypeWrapper> choices = Lists.newArrayList();
    final List<NoteTypeWrapper> notes = Lists.newArrayList();
    final Map<String, RelationshipTypeWrapper> relationships = Maps.newLinkedHashMap();
    final Map<String, ClassTypeWrapper> classes = Maps.newLinkedHashMap();

    public NodeConverter<AttributeTypeWrapper> createAttributeConverter() {
        return new NodeConverter<AttributeTypeWrapper>(ATTR_TYPE_XPATH) {
            @Override
            public void process(AttributeTypeWrapper object) {
                object.setGuid(generateGuid());
                attributes.add(object);
            }
        };
    }

    public NodeConverter<ChoiceTypeWrapper> createChoiceConverter() {
        return new NodeConverter<ChoiceTypeWrapper>(CHOICE_TYPE_XPATH) {
            @Override
            public void process(ChoiceTypeWrapper object) {
                object.setGuid(generateGuid());
                choices.add(object);
            }
        };
    }

    public NodeConverter<NoteTypeWrapper> createNoteConverter() {
        return new NodeConverter<NoteTypeWrapper>(NOTE_TYPE_XPATH) {
            @Override
            public void process(NoteTypeWrapper object) {
                object.setGuid(generateGuid());
                notes.add(object);
            }
        };
    }

    public NodeConverter<RelationshipTypeWrapper> createRelationshipConverter() {
        return new NodeConverter<RelationshipTypeWrapper>(RELATIONSHIP_TYPE_XPATH) {
            @Override
            public void process(RelationshipTypeWrapper object) {
                object.setGuid(generateGuid());
                relationships.put(object.getId(), object);
            }
        };
    }

    public NodeConverter<ClassTypeWrapper> createClassConverter() {
        return new NodeConverter<ClassTypeWrapper>(CLASS_TYPE_XPATH) {
            @Override
            public void process(ClassTypeWrapper object) {
                object.setGuid(generateGuid());
                classes.put(object.getId(), object);
            }
        };
    }

    static String generateGuid() {
        byte[] result = new byte[6];
        random.nextBytes(result);
        return HexUtils.encodeHex(result);
    }
}
