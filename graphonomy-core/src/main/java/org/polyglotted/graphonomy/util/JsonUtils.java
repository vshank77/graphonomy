package org.polyglotted.graphonomy.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.polyglotted.graphonomy.exports.JsonException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    protected static final ObjectMapper Mapper = new ObjectMapper();
    protected static final JsonFactory JsonFactory = new JsonFactory(Mapper);
    static {
        Mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        Mapper.setSerializationInclusion(Include.NON_EMPTY);
        Mapper.setVisibilityChecker(Mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        JsonFactory.enable(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM);
    }

    public static JsonGenerator createGenerator(OutputStream output) {
        return createGenerator(new OutputStreamWriter(output));
    }

    public static JsonGenerator createGenerator(Writer output) {
        try {
            return JsonFactory.createGenerator(output);
        }
        catch (IOException ioe) {
            throw new JsonException("unable to create node writer", ioe);
        }
    }

    public static String asPrettyJson(Object value) {
        try {
            return Mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        }
        catch (IOException ioe) {
            throw new JsonException("asPrettyJson failed", ioe);
        }
    }

    public static String asJson(Object value) {
        try {
            return Mapper.writeValueAsString(value);
        }
        catch (IOException ioe) {
            throw new JsonException("asJson failed", ioe);
        }
    }

    public static <T> T parse(String content, Class<T> type) {
        try {
            return Mapper.readValue(content, type);
        }
        catch (IOException ioe) {
            throw new JsonException("parse failed", ioe);
        }
    }
}
