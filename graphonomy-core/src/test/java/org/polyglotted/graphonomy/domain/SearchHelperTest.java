package org.polyglotted.graphonomy.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchHelperTest extends SearchHelper {

    @Test
    public void testStemSingle() {
        assertEquals("(field:test*)", stem("test", "field"));
    }

    @Test
    public void testStemSingleSpace() {
        assertEquals("(field:test*)", stem("test ", "field"));
    }

    @Test
    public void testStemSplChars() {
        assertEquals("(field:test*)", stem("test !@£$%^&*:;'\"\\/?<>,.|()", "field"));
    }

    @Test
    public void testStemDouble() {
        assertEquals("(field:test* field:second*)", stem("test second", "field"));
    }

    @Test
    public void testStemDoubleSpace() {
        assertEquals("(field:test* field:second*)", stem("test second", "field"));
    }
}
