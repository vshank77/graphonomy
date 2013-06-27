package org.polyglotted.graphonomy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final ThreadLocal<DateFormat> SlgFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat slgFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
            slgFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
            return slgFormat;
        }
    };

    private static final ThreadLocal<DateFormat> IsoFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            isoFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
            return isoFormat;
        }
    };

    public static String format(Date date) {
        return IsoFormat.get().format(date);
    }

    public static String formatIso(Date date) {
        return IsoFormat.get().format(date);
    }

    public static String formatSlg(Date date) {
        return SlgFormat.get().format(date);
    }

    public static Date parseThrow(String value) {
        try {
            return IsoFormat.get().parse(value);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseSlgThrow(String value) {
        try {
            return SlgFormat.get().parse(value);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseSilent(String value) {
        try {
            return IsoFormat.get().parse(value);
        }
        catch (ParseException e) {}
        try {
            return SlgFormat.get().parse(value);
        }
        catch (ParseException e) {}
        throw new RuntimeException("unable to parse value as date " + value);
    }
}
