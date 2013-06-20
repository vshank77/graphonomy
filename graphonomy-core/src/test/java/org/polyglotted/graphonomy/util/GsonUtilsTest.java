package org.polyglotted.graphonomy.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.google.gson.Gson;

public class GsonUtilsTest {

    private static final String dateStr = "\"2013-06-20T20:32:56Z\"";
    private Gson gson = GsonUtils.createGson(false, false);

    @Test
    public void testToDate() {
        assertEquals(dateStr, gson.toJson(getDate()));
    }

    @Test
    public void testFromDate() {
        assertEquals(getDate(), gson.fromJson(dateStr, Date.class));
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Zulu"));
        calendar.set(2013, 5, 20, 20, 32, 56);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }
}
