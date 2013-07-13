package org.polyglotted.graphonomy.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

public class JsonUtilsTest extends JsonUtils {

    @Test
    public void testMapperCreate() {
        assertNotNull(JsonUtils.mapper());
    }

    @Test
    public void testPrettyJson() {
        final long time = System.currentTimeMillis();
        assertEquals(String.valueOf(time), JsonUtils.asPrettyJson(new Date(time)));
    }

    
}
