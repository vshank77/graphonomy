package org.polyglotted.graphonomy.domain;

import org.junit.Test;

public class DatabaseConstantsTest extends DatabaseConstants {

    @Test(expected = NullPointerException.class)
    public void testTypeFor() {
        DatabaseConstants.typeFor("unknownType");
    }
}
