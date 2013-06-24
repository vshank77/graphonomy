package org.polyglotted.graphonomy.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.TypeSafe;
import org.polyglotted.graphonomy.model.TypeSafe.TypeValidationException;
 
public class NoteClassTest {
 
    @Test
    public void testNullValue() {
        assertNull(new NoteClass("name", TypeSafe.str).validate(null));
    }
 
    @Test
    public void testDefaultValue() {
        assertNotNull(new NoteClass("name", TypeSafe.str).setDefaultValue("hello").validate(null));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testRequiredValue() {
        new NoteClass("name", TypeSafe.str).setRequired(true).validate(null);
    }
 
    @Test
    public void testValidEnum() {
        List<String> enums = Arrays.asList("apple", "mango", "berry");
        assertEquals("berry", new NoteClass("name", TypeSafe.str).setEnums(enums).validate("berry"));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidEnum() {
        List<String> enums = Arrays.asList("apple", "mango", "berry");
        new NoteClass("name", TypeSafe.str).setEnums(enums).validate("banana");
    }
 
    @Test
    public void testValidPattern() {
        assertEquals("abc", new NoteClass("name", TypeSafe.str).setPattern("[a-z]{3}").validate("abc"));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidPattern() {
        new NoteClass("name", TypeSafe.str).setPattern("[a-z]{3}").validate("abcd");
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidPatternString() {
        new NoteClass("name", TypeSafe.str).setPattern("***").validate("abc");
    }
 
    @Test
    public void testValidRange() {
        assertEquals("abc", new NoteClass("name", TypeSafe.str).setRange(3, 5).validate("abc"));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidRangeLow() {
        new NoteClass("name", TypeSafe.str).setRange(3, 5).validate("ab");
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidRangeHigh() {
        new NoteClass("name", TypeSafe.str).setRange(3, 5).validate("abcdef");
    }
 
    @Test
    public void testNullBool() {
        assertEquals(Boolean.FALSE, new NoteClass("name", TypeSafe.bool).validate(null));
    }
 
    @Test
    public void testDefaultBoolean() {
        assertEquals(Boolean.TRUE, new NoteClass("name", TypeSafe.bool).setDefaultValue("true").validate(null));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testRequiredBoolean() {
        new NoteClass("name", TypeSafe.bool).setRequired(true).validate(null);
    }
 
    @Test
    public void testBool1() {
        assertEquals(Boolean.TRUE, new NoteClass("name", TypeSafe.bool).validate("yes"));
    }
 
    @Test
    public void testBool2() {
        assertEquals(Boolean.TRUE, new NoteClass("name", TypeSafe.bool).validate("true"));
    }
 
    @Test
    public void testBool3() {
        assertEquals(Boolean.TRUE, new NoteClass("name", TypeSafe.bool).validate("on"));
    }
 
    @Test
    public void testBool4() {
        assertEquals(Boolean.TRUE, new NoteClass("name", TypeSafe.bool).validate("1"));
    }
 
    @Test
    public void testBool5() {
        assertEquals(Boolean.FALSE, new NoteClass("name", TypeSafe.bool).validate("no"));
    }
 
    @Test
    public void testNullNumber() {
        assertEquals(0, new NoteClass("name", TypeSafe.number).validate(null));
    }
 
    @Test
    public void testDefaultInteger() {
        assertEquals(25, new NoteClass("name", TypeSafe.number).setDefaultValue("25").validate(null));
    }
 
    @Test
    public void testDefaultLong() {
        long time = System.currentTimeMillis();
        assertEquals(time,
                new NoteClass("name", TypeSafe.number).setDefaultValue(String.valueOf(time)).validate(null));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testDefaultNumberError() {
        new NoteClass("name", TypeSafe.number).setDefaultValue("ab").validate(null);
    }
 
    @Test(expected = TypeValidationException.class)
    public void testRequiredNumber() {
        new NoteClass("name", TypeSafe.number).setRequired(true).validate(null);
    }
 
    @Test
    public void testIntegerValue() {
        assertEquals(25, new NoteClass("name", TypeSafe.number).validate("25"));
    }
 
    @Test
    public void testLongValue() {
        long time = System.currentTimeMillis();
        assertEquals(time, new NoteClass("name", TypeSafe.number).setRequired(true)
                .validate(String.valueOf(time)));
    }
 
    @Test
    public void testValidIntRange() {
        assertEquals(3, new NoteClass("name", TypeSafe.number).setRange(3, 5).validate("3"));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidIntRangeLow() {
        new NoteClass("name", TypeSafe.number).setRange(3, 5).validate("2");
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidIntRangeHigh() {
        new NoteClass("name", TypeSafe.number).setRange(3, 5).validate("6");
    }
 
    @Test
    public void testNullDecimal() {
        assertEquals(0.0, new NoteClass("name", TypeSafe.decimal).validate(null));
    }
 
    @Test
    public void testDefaultDecimal() {
        assertEquals(25.0, new NoteClass("name", TypeSafe.decimal).setDefaultValue("25").validate(null));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testDefaultDecimalError() {
        new NoteClass("name", TypeSafe.decimal).setDefaultValue("ab").validate(null);
    }
 
    @Test(expected = TypeValidationException.class)
    public void testRequiredDecimal() {
        new NoteClass("name", TypeSafe.decimal).setRequired(true).validate(null);
    }
 
    @Test
    public void testDecimalValue() {
        assertEquals(25.0, new NoteClass("name", TypeSafe.decimal).validate("25"));
    }
 
    @Test
    public void testValidDecimalRange() {
        assertEquals(3.0, new NoteClass("name", TypeSafe.decimal).setRange(3, 5).validate("3"));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidDecimalRangeLow() {
        new NoteClass("name", TypeSafe.decimal).setRange(3, 5).validate("2");
    }
 
    @Test(expected = TypeValidationException.class)
    public void testInvalidDecimalRangeHigh() {
        new NoteClass("name", TypeSafe.decimal).setRange(3, 5).validate("6");
    }
 
 
    @Test
    public void testNullDate() {
        assertNull(new NoteClass("name", TypeSafe.date).validate(null));
    }
 
    @Test
    public void testDefaultDate() {
        assertEquals(getDate(), new NoteClass("name", TypeSafe.date).setDefaultValue("20130618T15:05:47").validate(null));
    }
 
    @Test(expected = TypeValidationException.class)
    public void testDefaultDateError() {
        new NoteClass("name", TypeSafe.date).setDefaultValue("2013/06/18").validate(null);
    }
 
    @Test(expected = TypeValidationException.class)
    public void testRequiredDate() {
        new NoteClass("name", TypeSafe.date).setRequired(true).validate(null);
    }
 
    @Test
    public void testDateValue() {
        assertEquals(getDate(), new NoteClass("name", TypeSafe.date).validate("20130618T15:05:47"));
    }
 
    private Date getDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Zulu"));
        calendar.set(2013, 5, 18, 15, 05, 47);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }
}