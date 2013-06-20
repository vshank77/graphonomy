package org.polyglotted.graphonomy.util;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static com.google.gson.stream.JsonToken.BEGIN_ARRAY;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

public class JsonUtils {
    public static final Gson GSON = createGson(false);

    public static <V> V parseEntities(String json, Type type, Type listType) {
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        try {
            if (listType != null && jsonReader.peek() == BEGIN_ARRAY)
                return GSON.fromJson(jsonReader, listType);

            else if (type == null)
                return null;

            return GSON.fromJson(jsonReader, type);
        }
        catch (IOException io) {
            throw new RuntimeException(io);
        }
        finally {
            try {
                jsonReader.close();
            }
            catch (IOException ignore) {}
        }
    }

    public static final Gson createGson(final boolean serializeNulls) {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateFormatter());
        builder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);
        if (serializeNulls)
            builder.serializeNulls();
        return builder.create();
    }

    private static class DateFormatter implements JsonDeserializer<Date>, JsonSerializer<Date> {
        public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        private final DateFormat format;

        public DateFormatter() {
            format = new SimpleDateFormat(DATE_FORMAT);
            final TimeZone timeZone = TimeZone.getTimeZone("Zulu");
            format.setTimeZone(timeZone);
        }

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            final String value = json.getAsString();
            try {
                synchronized (format) {
                    return format.parse(value);
                }
            }
            catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }

        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            String formatted;
            synchronized (format) {
                formatted = format.format(date);
            }
            return new JsonPrimitive(formatted);
        }
    }
}
