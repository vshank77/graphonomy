package org.polyglotted.graphonomy.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HexUtilsTest extends HexUtils {

    private final static byte[] bytes = { (byte) 113, (byte) -55, (byte) 122, (byte) -64, (byte) 108, (byte) 97 };
    private final static String hex = "71c97ac06c61";

    @Test
    public void testEncodeHexByteArray() {
        assertEquals(hex, HexUtils.encodeHex(bytes));
    }

    @Test
    public void testDecodeHex() {
        assertArrayEquals(bytes, HexUtils.decodeHex(hex));
    }

    @Test
    public void testUniqueId() {
        assertEquals(8, HexUtils.generateUniqueId(4).length());
    }

    @Test(expected = DecoderException.class)
    public void testFailedDigit() {
        HexUtils.toDigit('g', 0);
    }

    @Test(expected = DecoderException.class)
    public void testWrongDecode() {
        HexUtils.decodeHex("abc");
    }
}
