package org.polyglotted.graphonomy.schemarepo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.xerces.xs.XSTypeDefinition.COMPLEX_TYPE;

import java.net.URI;
import java.net.URL;
import java.util.Date;

import lombok.SneakyThrows;

import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.polyglotted.graphonomy.model.Entity;
import org.polyglotted.graphonomy.model.FieldClass;
import org.polyglotted.graphonomy.model.FieldType;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.NoteField;
import org.polyglotted.graphonomy.model.TypeSafe;
import org.polyglotted.graphonomy.util.DateUtils;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import com.google.common.annotations.VisibleForTesting;

public class XmlSchemaParser {

    private final XSModel model;
    private final String targetNamespace;
    private SchemaWrapper schemaWrapper;
    private String importDate;

    public XmlSchemaParser(URL url, String namespace) {
        this(toUri(url), namespace);
    }

    public XmlSchemaParser(URI uri, String namespace) {
        this(uri.toASCIIString(), namespace);
    }

    public XmlSchemaParser(String uri, String namespace) {
        XSLoader schemaLoader = createLoader();
        model = schemaLoader.loadURI(uri);
        targetNamespace = namespace;
        importDate = DateUtils.format(new Date());
        schemaWrapper = new SchemaWrapper();
    }

    public SchemaWrapper parse() {
        parseGlobalAttributes();
        parseGlobalSimpleTypeDefinitions();
        parseGlobalComplexTypeDefinitions();
        return schemaWrapper;
    }

    void parseGlobalAttributes() {
        XSNamedMap map = model.getComponentsByNamespace(XSConstants.ATTRIBUTE_DECLARATION, targetNamespace);
        for (int i = 0; i < map.getLength(); i++) {
            XSAttributeDeclaration attr = (XSAttributeDeclaration) map.item(i);
            FieldClass field = parseAttribute(idFrom(attr), attr, false, defaultValueFrom(attr));
            schemaWrapper.addField(field);
        }
    }

    void parseGlobalSimpleTypeDefinitions() {
        XSNamedMap map = model.getComponentsByNamespace(XSConstants.TYPE_DEFINITION, targetNamespace);
        for (int i = 0; i < map.getLength(); i++) {
            XSTypeDefinition typeDef = (XSTypeDefinition) map.item(i);
            if (typeDef.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE) {
                FieldClass field = parseSimpleType((XSSimpleTypeDefinition) typeDef, false);
                schemaWrapper.addField(field);
            }
        }
    }

    void parseGlobalComplexTypeDefinitions() {
        XSNamedMap map = model.getComponentsByNamespace(XSConstants.TYPE_DEFINITION, targetNamespace);
        for (int i = 0; i < map.getLength(); i++) {
            XSTypeDefinition typeDef = (XSTypeDefinition) map.item(i);
            if (typeDef.getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE) {
                Entity entity = parseComplexType((XSComplexTypeDefinition) typeDef, null, null);
                schemaWrapper.addEntity(entity);
            }
        }
    }

    protected FieldClass parseSimpleType(XSSimpleTypeDefinition simpleType, boolean anonymous) {
        return fieldFrom(idFrom(simpleType), simpleType.getName(), false, null, simpleType, !anonymous);
    }

    protected Entity parseComplexType(XSComplexTypeDefinition complexType, Entity parent, String fieldName) {
        Entity entity = (parent == null) ? new Entity(idFrom(complexType), complexType.getName()) : new Entity(
                buildAnonymousId(parent, fieldName), fieldName);
        checkArgument(!schemaWrapper.containsEntity(entity.getTermId()));
        entity.setCreatedDate(importDate);
        entity.setModifiedDate(importDate);
        switch (complexType.getContentType()) {
            case XSComplexTypeDefinition.CONTENTTYPE_ELEMENT:
                parseAttributeListInto(complexType.getAttributeUses(), entity);
                parseParticleInto(complexType.getParticle(), entity);
                break;
            case XSComplexTypeDefinition.CONTENTTYPE_EMPTY:
                parseAttributeListInto(complexType.getAttributeUses(), entity);
                break;
            default:
                throw new RuntimeException(complexType.getName() + " not of element or empty content type");
        }
        return entity;
    }

    protected void parseParticleInto(XSParticle particle, Entity parent) {
        checkArgument(particle.getTerm() instanceof XSModelGroup, "particle should be a model group");
        checkNotNull(parent, "parent cannot be null is parseParticle");
        XSModelGroup modelGroup = (XSModelGroup) particle.getTerm();
        checkArgument(modelGroup.getCompositor() == XSModelGroup.COMPOSITOR_ALL
                || modelGroup.getCompositor() == XSModelGroup.COMPOSITOR_SEQUENCE,
                "only all or sequence supported on compositors");

        for (Object obj : modelGroup.getParticles()) {
            XSParticle subParticle = (XSParticle) obj;
            checkArgument(subParticle.getTerm() instanceof XSElementDeclaration, "sub-particle should be an element");
            XSElementDeclaration elementDecl = (XSElementDeclaration) subParticle.getTerm();
            XSTypeDefinition typeDef = elementDecl.getTypeDefinition();
            String fieldName = elementDecl.getName();

            NoteField note;
            if (typeDef.getAnonymous()) {
                if (typeDef.getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE) {
                    Entity child = parseComplexType((XSComplexTypeDefinition) typeDef, parent, fieldName);
                    note = NoteField.from(parent, child, fieldName);
                    schemaWrapper.addEntity(child);
                }
                else {
                    FieldClass fieldClass = parseSimpleType((XSSimpleTypeDefinition) typeDef, true);
                    fieldClass.setNoteId(buildAnonymousId(parent, fieldName));
                    fieldClass.setNoteLabel(fieldName);
                    fieldClass.setRequired(subParticle.getMinOccurs() > 0);
                    note = NoteField.from(parent, fieldClass, fieldName);
                    schemaWrapper.addField(fieldClass);
                }
            }// don't do not anonymous
            else {
                note = new NoteField(parent.getTermId(), idFrom(typeDef), fieldName,
                        fieldTypeFrom(typeDef.getTypeCategory()));
            }
            note.setMandatory(subParticle.getMinOccurs() > 0);
            note.setMultiple(subParticle.getMaxOccursUnbounded() || subParticle.getMaxOccurs() > 1);
            parent.addField(note);
        }
    }

    protected void parseAttributeListInto(XSObjectList attributeUses, Entity entity) {
        for (Object obj : attributeUses) {
            XSAttributeUse attrUse = (XSAttributeUse) obj;
            XSAttributeDeclaration attr = attrUse.getAttrDeclaration();
            String attrId = idFrom(attr.getTypeDefinition());

            NoteClass fieldClass;
            if (!schemaWrapper.containsField(attrId)) {
                fieldClass = parseAttribute(buildAnonymousId(entity, attr.getName()), attr, attrUse.getRequired(),
                        defaultValueFrom(attrUse));
                schemaWrapper.addField(fieldClass);
            }
            else {
                fieldClass = schemaWrapper.getField(attrId);
            }
            checkNotNull(fieldClass, "fieldClass is empty for " + attrId);
            entity.addField(NoteField.from(entity, fieldClass, attr.getName()).setMandatory(fieldClass.isRequired()));
        }
    }

    protected FieldClass parseAttribute(String attrId, XSAttributeDeclaration attr, boolean required,
            String defaultValue) {
        FieldClass field = fieldFrom(attrId, attr.getName(), required, defaultValue, attr.getTypeDefinition(),
                (attr.getScope() == XSConstants.SCOPE_GLOBAL));
        return field;
    }

    protected FieldClass fieldFrom(String id, String name, boolean required, String defaultValue,
            XSSimpleTypeDefinition simpleType, boolean globalScope) {
        FieldClass field = new FieldClass();
        field.setNoteId(id);
        field.setNoteLabel(name);
        field.setType(typeFrom(simpleType));
        field.setRequired(required);
        field.setDefaultValue(defaultValue);
        Restriction restriction = Restriction.parseFrom(simpleType);
        field.setEnums(restriction.getEnumeration());
        field.setPattern(restriction.getPattern());
        field.setRange(restriction.getRange());
        field.setFieldType(FieldType.ValueType);
        field.setGlobalScope(globalScope);
        return field;
    }

    @VisibleForTesting
    void setImportDate(String date) {
        this.importDate = date;
    }

    @VisibleForTesting
    SchemaWrapper getSchema() {
        return schemaWrapper;
    }

    static FieldType fieldTypeFrom(short category) {
        return (category == COMPLEX_TYPE) ? FieldType.EntityType : FieldType.ValueType;
    }

    static TypeSafe typeFrom(XSSimpleTypeDefinition simpleType) {
        checkNotNull(simpleType.getPrimitiveType(), "simpleType not a primitive");
        String baseName = simpleType.getPrimitiveType().getName();
        TypeSafe typeSafe = TypeSafe.from(baseName);
        checkNotNull(typeSafe, baseName + " not supported as a type in schema-repo");
        return typeSafe;
    }

    static String defaultValueFrom(XSAttributeUse attr) {
        short constraintType = attr.getConstraintType();
        if (constraintType == XSConstants.VC_DEFAULT || constraintType == XSConstants.VC_FIXED) {
            return attr.getValueConstraintValue().getNormalizedValue();
        }
        return null;
    }

    static String defaultValueFrom(XSAttributeDeclaration attr) {
        short constraintType = attr.getConstraintType();
        if (constraintType == XSConstants.VC_DEFAULT || constraintType == XSConstants.VC_FIXED) {
            return attr.getValueConstraintValue().getNormalizedValue();
        }
        return null;
    }

    static String idFrom(XSObject item) {
        return item.getNamespace() + "/" + item.getName();
    }

    static String buildAnonymousId(Entity parent, String fieldName) {
        return parent.getTermId() + "#" + fieldName;
    }

    @SneakyThrows
    private static XSLoader createLoader() {
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        XSImplementation impl = (XSImplementation) registry.getDOMImplementation("XS-Loader");
        XSLoader schemaLoader = impl.createXSLoader(null);
        schemaLoader.getConfig().setParameter("validate", Boolean.TRUE);
        return schemaLoader;
    }

    @SneakyThrows
    private static URI toUri(URL url) {
        return checkNotNull(url).toURI();
    }
}
