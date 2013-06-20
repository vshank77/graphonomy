package org.polyglotted.graphonomy.util;

import static com.google.gson.FieldNamingPolicy.IDENTITY;
import static com.google.gson.stream.JsonToken.BEGIN_ARRAY;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class GsonUtils {
    private static final Gson GSON = createGson(false, true);

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

    public static final Gson createGson(boolean prettyPrinting, boolean nonExecJson) {
        final GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(IDENTITY);
        builder.setPrettyPrinting();
        builder.registerTypeAdapterFactory(new GsonAdapterFactory());
        if (prettyPrinting)
            builder.setPrettyPrinting();
        if (nonExecJson)
            builder.generateNonExecutableJson();
        return builder.create();
    }
}
