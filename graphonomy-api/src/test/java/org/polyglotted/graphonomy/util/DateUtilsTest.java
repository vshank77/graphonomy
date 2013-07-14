package org.polyglotted.graphonomy.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateUtilsTest extends DateUtils {

    @Test
    public void testFormat() {
        String value = "2013-06-05T08:23:45Z";
        assertEquals(value, format(parseDate(value)));
    }

    @Test
    public void testIsDate0() {
        assertTrue(isDate("2013-06-05"));
    }

    @Test
    public void testIsDate1() {
        String value = "2013-06-05T08:23:45";
        assertTrue(isDate(value));
        assertEquals(1370420625000L, getDateValue(value));
    }

    @Test
    public void testIsDate2() {
        String value = "2013-06-05\t08:23:45";
        assertTrue(isDate(value));
        assertEquals(1370420625000L, getDateValue(value));
    }

    @Test
    public void testIsDate3() {
        String value = "2013-06-05 08:23:45.678";
        assertTrue(isDate(value));
        assertEquals(1370420625678L, getDateValue(value));
    }

    @Test
    public void testIsDate4() {
        String value = "2013-06-05 08:23:45Z";
        assertTrue(isDate(value));
        assertEquals(1370420625000L, getDateValue(value));
    }

    @Test
    public void testIsDate5() {
        String value = "2013-06-05 08:23:45.678Z";
        assertTrue(isDate(value));
        assertEquals(1370420625678L, getDateValue(value));
    }

    @Test
    public void testIsDate6() {
        String value = "2013-06-05 08:23:45-01:00";
        assertTrue(isDate(value));
        assertEquals(1370424225000L, getDateValue(value));
    }

    @Test
    public void testIsDate7() {
        String value = "2013-06-05 08:23:45+05:30";
        assertTrue(isDate(value));
        assertEquals(1370400825000L, getDateValue(value));
    }

    @Test
    public void testIsDate8() {
        String value = "2013-06-05 08:23:45 +01:00";
        assertTrue(isDate(value));
        assertEquals(1370417025000L, getDateValue(value));
    }

    @Test
    public void testIsDate9() {
        String value = "2013-06-05 08:23:45\t+01:00";
        assertTrue(isDate(value));
        assertEquals(1370417025000L, getDateValue(value));
    }

    @Test
    public void testIsDate10() {
        String value = "20130605T08:23:45.250-01:00";
        assertTrue(isDate(value));
        assertEquals(1370424225250L, getDateValue(value));
    }

    @Test
    public void testIsDate11() {
        String value = "20130605T08:23:45";
        assertTrue(isDate(value));
        assertEquals(1370420625000L, getDateValue(value));
    }

    @Test
    public void testIsNotDate1() {
        assertFalse(isDate("2013-06-05 "));
    }

    @Test
    public void testIsNotDate2() {
        assertFalse(isDate("2013-06-05T"));
    }

    @Test
    public void testIsNotDate3() {
        assertFalse(isDate("2013-06-05 11"));
    }

    @Test
    public void testIsNotDate4() {
        assertFalse(isDate("2013-06-05 08:23"));
    }

    @Test
    public void testIsNotDate5() {
        assertFalse(isDate("2013-06-05 8:23:45"));
    }

    @Test
    public void testIsNotDate6() {
        assertFalse(isDate("2013-06-05 08:23:45 "));
    }

    @Test
    public void testIsNotDate7() {
        assertFalse(isDate("2013-06-05T08:23:45Z "));
    }

    @Test
    public void testIsNotDate8() {
        assertFalse(isDate("2013-06-05 08:23:45 +01"));
    }

    @Test
    public void testIsNotDate9() {
        assertFalse(isDate("2013-06-05 08:23:45 -01:2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsNotDateFail() {
        getDateValue("2013-06-05 08:23:45 -01:2");
    }

    private long getDateValue(String value) {
        return DateUtils.parseDate(value).getTime();
    }

    @Test
    public void testIsTime0() {
        String value = "08:23:45";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime1() {
        String value = "08:23:45.678";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime2() {
        String value = "08:23:45Z";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime3() {
        String value = "08:23:45.678Z";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime4() {
        String value = "08:23:45-01:00";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime5() {
        String value = "08:23:45 +01:00";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime6() {
        String value = "08:23:45\t+01:00";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsTime7() {
        String value = "08:23:45.250-01:00";
        assertTrue(isTime(value));
    }

    @Test
    public void testIsNotTime0() {
        assertFalse(isTime("08:2:3"));
    }

    @Test
    public void testIsNotTime1() {
        assertFalse(isTime("08:23:45Z "));
    }

    @Test
    public void testIsNotTime2() {
        assertFalse(isTime("08:23:45 +01"));
    }

    @Test
    public void testIsNotTime3() {
        assertFalse(isTime("08:23:45 -01:2"));
    }
}
