package org.polyglotted.graphonomy.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import lombok.SneakyThrows;

import org.polyglotted.graphonomy.model.TermType;
import org.polyglotted.graphonomy.model.TermTypeFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public abstract class JsonUtils {
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
        SimpleModule jsonModule = new SimpleModule("polyglotted module").addDeserializer(TermType.class,
                new TermTypeDeserializer());
        Mapper.registerModule(jsonModule);
        JsonFactory.enable(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM);
    }

    public static JsonGenerator createGenerator(OutputStream output) {
        return createGenerator(new OutputStreamWriter(output));
    }

    @SneakyThrows
    public static JsonGenerator createGenerator(Writer output) {
        return JsonFactory.createGenerator(output);
    }

    @SneakyThrows
    public static String asPrettyJson(Object value) {
        return Mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    @SneakyThrows
    public static String asJson(Object value) {
        return Mapper.writeValueAsString(value);
    }

    public static ObjectMapper mapper() {
        return Mapper;
    }

    static class TermTypeDeserializer extends StdDeserializer<TermType> {
        private static final long serialVersionUID = -3640287104633732032L;

        public TermTypeDeserializer() {
            super(TermType.class);
        }

        @Override
        public TermType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            String text = jp.getText();
            return TermTypeFactory.with(text);
        }
    }
}
