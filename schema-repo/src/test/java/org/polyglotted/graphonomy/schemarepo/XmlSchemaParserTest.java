package org.polyglotted.graphonomy.schemarepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.xs.XSAttributeDecl;
import org.apache.xerces.impl.xs.XSAttributeUseImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.xs.XSConstants;
import org.junit.Test;
import org.polyglotted.graphonomy.model.Entity;
import org.polyglotted.graphonomy.util.JsonUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class XmlSchemaParserTest {

    @Test
    public void testParse() throws Exception {
        XmlSchemaParser parser = new XmlSchemaParser(toUrl("files/petstore.xsd"),
                "urn:org.polyglotted.schemarepo/petstore");
        parser.setImportDate("2013-07-09T06:04:35Z");
        SchemaWrapper schema = parser.parse();
        assertNotNull(schema.getEntities());
        assertNotNull(schema.getEntity("urn:org.polyglotted.schemarepo/petstore/Inventory"));
        assertNotNull(schema.getFields());
        assertNotNull(schema.getField("urn:org.polyglotted.schemarepo/petstore/version"));
        String expected = Resources.toString(toUrl("files/petstore-schema.txt"), Charsets.UTF_8);
        assertEquals(expected, JsonUtils.asPrettyJson(schema));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidContentType() throws Exception {
        XmlSchemaParser parser = new XmlSchemaParser(toUrl("files/petstore.xsd"),
                "urn:org.polyglotted.schemarepo/petstore");
        parser.parseComplexType(complexType(), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCompositor() throws Exception {
        XmlSchemaParser parser = new XmlSchemaParser(toUrl("files/petstore.xsd"),
                "urn:org.polyglotted.schemarepo/petstore");
        XSModelGroupImpl group = new XSModelGroupImpl();
        group.fCompositor = XSModelGroupImpl.MODELGROUP_CHOICE;
        XSParticleDecl particle = new XSParticleDecl();
        particle.fValue = group;
        parser.parseParticleInto(particle, new Entity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExistingEntity() throws Exception {
        XmlSchemaParser parser = new XmlSchemaParser(toUrl("files/petstore.xsd"),
                "urn:org.polyglotted.schemarepo/petstore");
        Entity used = new Entity("urn:test/test", "test");
        parser.getSchema().addEntity(used);
        parser.parseComplexType(complexType(), null, null);
    }

    @Test
    public void testAttrUseNull() {
        assertNull(XmlSchemaParser.defaultValueFrom(new XSAttributeUseImpl()));
    }

    @Test
    public void testAttrNull() {
        assertNull(XmlSchemaParser.defaultValueFrom(new XSAttributeDecl()));
    }

    @Test
    public void testAttrUseFixed() {
        XSAttributeUseImpl attrUse = new XSAttributeUseImpl();
        attrUse.fConstraintType = XSConstants.VC_FIXED;
        attrUse.fDefault = valInfo();
        assertEquals("test", XmlSchemaParser.defaultValueFrom(attrUse));
    }

    @Test
    public void testAttrFixed() {
        XSAttributeDecl attr = new XSAttributeDecl();
        attr.setValues(null, null, null, XSConstants.VC_FIXED, XSConstants.SCOPE_ABSENT, valInfo(), null, null);
        assertEquals("test", XmlSchemaParser.defaultValueFrom(attr));
    }

    @Test
    public void testAttrUseDefault() {
        XSAttributeUseImpl attrUse = new XSAttributeUseImpl();
        attrUse.fConstraintType = XSConstants.VC_DEFAULT;
        attrUse.fDefault = valInfo();
        assertEquals("test", XmlSchemaParser.defaultValueFrom(attrUse));
    }

    @Test
    public void testAttrDefault() {
        XSAttributeDecl attr = new XSAttributeDecl();
        attr.setValues(null, null, null, XSConstants.VC_DEFAULT, XSConstants.SCOPE_ABSENT, valInfo(), null, null);
        assertEquals("test", XmlSchemaParser.defaultValueFrom(attr));
    }

    static ValidatedInfo valInfo() {
        ValidatedInfo val = new ValidatedInfo();
        val.normalizedValue = "test";
        return val;
    }

    private XSComplexTypeDecl complexType() {
        short def = 0;
        XSComplexTypeDecl complexType = new XSComplexTypeDecl();
        complexType.setName("test");
        complexType.setValues("test", "urn:test", null, def, def, def, XSComplexTypeDecl.CONTENTTYPE_MIXED, false,
                null, null, null, null);
        return complexType;
    }

    static URL toUrl(String res) {
        return XmlSchemaParserTest.class.getClassLoader().getResource(res);
    }
}
