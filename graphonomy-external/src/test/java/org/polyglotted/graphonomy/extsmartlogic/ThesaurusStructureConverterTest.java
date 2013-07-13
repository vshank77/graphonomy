package org.polyglotted.graphonomy.extsmartlogic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.polyglotted.graphonomy.model.MetaSpec;
import org.polyglotted.graphonomy.util.JsonUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class ThesaurusStructureConverterTest {

    @Test
    public void test() throws IOException {
        MetaSpec metaSpec = new ThesaurusStructureConverter().convert(asResource("files/metaspec.xml").openStream());

        String expected = Resources.toString(asResource("files/metaspec-simple.txt"), Charsets.UTF_8);
        assertEquals(expected, JsonUtils.asPrettyJson(metaSpec));
    }

    URL asResource(String file) {
        return getClass().getClassLoader().getResource(file);
    }
}
