package org.polyglotted.graphonomy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DateUtils {

    private static final Pattern DateRegx = Pattern.compile("^(\\d\\d\\d\\d)[-]?(\\d\\d)[-]?(\\d\\d)(?:(?:[Tt]|"
            + "[ \t]+)(\\d\\d):(\\d\\d):(\\d\\d))?(?:\\.(\\d*))?(?:Z|[ \t]*([-+]\\d\\d:\\d\\d))?$");

    private static final Pattern TimeRegx = Pattern
            .compile("^(\\d\\d):(\\d\\d):(\\d\\d)(?:\\.(\\d*))?(?:Z|[ \t]*([-+]\\d\\d:\\d\\d))?$");

    private static final ThreadLocal<DateFormat> IsoFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return isoFormat;
        }
    };

    public static boolean isDate(String target) {
        return DateRegx.matcher(target).find();
    }

    public static boolean isTime(String target) {
        return TimeRegx.matcher(target).find();
    }

    public static Date parseDate(String target) {
        Matcher m = DateRegx.matcher(target);
        if (!m.find()) {
            throw new IllegalArgumentException("Illegal Date Time format " + target);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, matcherGroupInt(m, 1));
        cal.set(Calendar.MONTH, matcherGroupInt(m, 2) - 1);
        cal.set(Calendar.DATE, matcherGroupInt(m, 3));
        cal.set(Calendar.HOUR_OF_DAY, matcherGroupInt(m, 4));
        cal.set(Calendar.MINUTE, matcherGroupInt(m, 5));
        cal.set(Calendar.SECOND, matcherGroupInt(m, 6));
        cal.set(Calendar.MILLISECOND, matcherGroupInt(m, 7));
        cal.setTimeZone(TimeZone.getTimeZone("GMT" + safeMatcherGroup(m, 8)));
        return cal.getTime();
    }

    public static long parseTime(String target) {
        return parseDate(target).getTime();
    }

    public static String formatAsDate(long value) {
        return IsoFormat.get().format(new Date(value));
    }

    public static String format(Date date) {
        return IsoFormat.get().format(date);
    }

    private static String safeMatcherGroup(Matcher m, int pos) {
        return m.group(pos) == null ? "" : m.group(pos);
    }

    private static int matcherGroupInt(Matcher m, int pos) {
        return m.group(pos) == null ? 0 : Integer.parseInt(m.group(pos));
    }
}
