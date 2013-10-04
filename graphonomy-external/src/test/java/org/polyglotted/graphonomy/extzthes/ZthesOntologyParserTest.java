package org.polyglotted.graphonomy.extzthes;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.util.JsonUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class ZthesOntologyParserTest {

    @Test
    public void testParse() throws IOException {
        ZthesOntologyParser reader = new ZthesOntologyParser();
        List<Term> terms = reader.parse(asResource("files/ontology.xml").openStream());

        String expected = Resources.toString(asResource("files/ontology-simple.txt"), Charsets.UTF_8);
        assertEquals(expected, JsonUtils.asPrettyJson(terms));
    }

    @Test
    public void testXmlCreate() throws IOException {
        ZthesOntologyParser reader = new ZthesOntologyParser();
        List<TermWrapper> terms = reader.parseRaw(asResource("files/ontology.xml").openStream());
        StringBuilder builder = new StringBuilder();
        builder.append("<Zthes>\n");
        for(TermWrapper term : terms) {
            term.toXml(builder);
        }
        builder.append("</Zthes>");
        
        String expected = Resources.toString(asResource("files/ontology.xml"), Charsets.UTF_8);
        assertEquals(sanitizeNl(expected), sanitizeNl(builder.toString()));
    }
    
    URL asResource(String file) {
        return getClass().getClassLoader().getResource(file);
    }
    
    static String sanitizeNl(String in) {
        return in.replace("\r\n", "\n");
    }
}
