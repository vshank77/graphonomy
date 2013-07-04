package org.polyglotted.graphonomy.util;

import java.security.SecureRandom;

public class HexUtils {

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static final SecureRandom RANDOM = new SecureRandom();

    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    public static String encodeHex(byte[] data) {
        return new String(encodeHex(data, DIGITS_LOWER));
    }

    /**
     * Converts an array of characters representing hexadecimal values into an array of bytes of those same values. The
     * returned array will be half the length of the passed array, as it takes two characters to represent any given
     * byte. An exception is thrown if the passed char array has an odd number of elements.
     * 
     * @param seq
     *            A string representation of characters containing hexadecimal digits
     * @return A byte array containing binary data decoded from the supplied char array.
     * @throws DecoderException
     *             Thrown if an odd number or illegal of characters is supplied
     */
    public static byte[] decodeHex(CharSequence seq) {
        int len = seq.length();
        if ((len & 0x01) != 0) {
            throw new DecoderException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(seq.charAt(j), j) << 4;
            j++;
            f = f | toDigit(seq.charAt(j), j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toDigits
     *            the output alphabet
     * @return A char[] containing hexadecimal characters
     * @since 1.4
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * Converts a hexadecimal character to an integer.
     * 
     * @param ch
     *            A character to convert to an integer digit
     * @param index
     *            The index of the character in the source
     * @return An integer
     * @throws DecoderException
     *             Thrown if ch is an illegal hex character
     */
    public static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index);
        }
        return digit;
    }

    public static final class DecoderException extends RuntimeException {
        private static final long serialVersionUID = 2046495095878254314L;

        public DecoderException() {
            super();
        }

        public DecoderException(String arg0, Throwable arg1) {
            super(arg0, arg1);
        }

        public DecoderException(String arg0) {
            super(arg0);
        }
    }

    public static String generateUniqueId(int numChars) {
        byte[] result = new byte[numChars];
        RANDOM.nextBytes(result);
        return encodeHex(result);
    }
}
