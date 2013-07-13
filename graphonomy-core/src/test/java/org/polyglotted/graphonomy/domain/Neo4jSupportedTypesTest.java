package org.polyglotted.graphonomy.domain;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class Neo4jSupportedTypesTest extends Neo4jSupportedTypes {

    @Test
    public void testIsDefaultClass() {
        assertFalse(isDefaultClass(Iterable.class));
    }
}
