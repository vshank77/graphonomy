package org.polyglotted.graphonomy.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.KeywordTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.junit.Test;

public class SimpleSchemaAnalyzerTest {

    private SimpleSchemaAnalyzer analyzer = new SimpleSchemaAnalyzer();

    @Test
    public void testTokenStream() {
        assertTrue(analyzer.tokenStream("nodeName", new StringReader("test")) instanceof StopFilter);
        assertTrue(analyzer.tokenStream("test", new StringReader("test")) instanceof KeywordTokenizer);
    }

    @Test
    public void testReusableTokenStreamDefault() throws IOException {
        assertNotNull(analyzer.reusableTokenStream("test", new StringReader("test")));
    }

    @Test
    public void testGetPositionIncrementGap() {
        assertEquals(0, analyzer.getPositionIncrementGap("nodeName"));
        assertEquals(0, analyzer.getPositionIncrementGap("test"));
    }

    @Test
    public void testToString() {
        assertEquals("SimpleSchemaAnalyzer([nodeType, nodeName, termCategory])", analyzer.toString());
    }
}
