package org.polyglotted.graphonomy.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GsonAdapterFactory implements TypeAdapterFactory {

    private static final String MANDATORY = "mandatory";
    private static final String NOTE_LABEL = "noteLabel";
    private static final String RELATION_NAME = "relationName";
    private static final String TERM_CLASS_NAME = "termClassName";
    private static final String TARGET_CLASS_NAME = "targetClassName";

    private Map<Class<?>, TypeAdapter<?>> typeAdapterMap = Maps.newConcurrentMap();

    public GsonAdapterFactory() {
        typeAdapterMap.put(Date.class, new DateAdapter().nullSafe());
        typeAdapterMap.put(MetaNote.class, new MetaNoteAdapter().nullSafe());
        typeAdapterMap.put(MetaRelation.class, new MetaRelationAdapter().nullSafe());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        return (TypeAdapter<T>) typeAdapterMap.get(type.getType());
    }

    private static class DateAdapter extends TypeAdapter<Date> {
        private final DateFormat slgFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss'Z'");
        private final DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        public DateAdapter() {
            slgFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
            isoFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
        }

        @Override
        public Date read(JsonReader reader) throws IOException {
            String formatted = reader.nextString();
            synchronized (isoFormat) {
                try {
                    return isoFormat.parse(formatted);
                }
                catch (Exception ex) {}
                try {
                    return slgFormat.parse(formatted);
                }
                catch (Exception ex) {}
                throw new RuntimeException("unable to parse string as date " + formatted);
            }
        }

        @Override
        public void write(JsonWriter writer, Date value) throws IOException {
            synchronized (isoFormat) {
                writer.value(isoFormat.format(value));
            }
        }
    }

    private static class MetaNoteAdapter extends TypeAdapter<MetaNote> {
        @Override
        public MetaNote read(JsonReader reader) throws IOException {
            String termClassName = null;
            String noteLabel = null;
            boolean mandatory = false;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (TERM_CLASS_NAME.equals(name)) {
                    termClassName = reader.nextString();
                }
                else if (NOTE_LABEL.equals(name)) {
                    noteLabel = reader.nextString();
                }
                else if (MANDATORY.equals(name)) {
                    mandatory = reader.nextBoolean();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new MetaNote(termClassName, noteLabel, mandatory);
        }

        @Override
        public void write(JsonWriter writer, MetaNote metaNote) throws IOException {
            writer.beginObject();
            writer.name(TERM_CLASS_NAME).value(metaNote.getTermClassName());
            writer.name(NOTE_LABEL).value(metaNote.getNoteLabel());
            writer.name(MANDATORY).value(metaNote.isMandatory());
            writer.endObject();
        }
    }

    private static class MetaRelationAdapter extends TypeAdapter<MetaRelation> {
        @Override
        public MetaRelation read(JsonReader reader) throws IOException {
            String termClassName = null;
            String relationName = null;
            String targetClassName = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (TERM_CLASS_NAME.equals(name)) {
                    termClassName = reader.nextString();
                }
                else if (RELATION_NAME.equals(name)) {
                    relationName = reader.nextString();
                }
                else if (TARGET_CLASS_NAME.equals(name)) {
                    targetClassName = reader.nextString();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new MetaRelation(termClassName, relationName, targetClassName);
        }

        @Override
        public void write(JsonWriter writer, MetaRelation metaRel) throws IOException {
            writer.beginObject();
            writer.name(TERM_CLASS_NAME).value(metaRel.getTermClassName());
            writer.name(RELATION_NAME).value(metaRel.getRelationName());
            writer.name(TARGET_CLASS_NAME).value(metaRel.getTargetClassName());
            writer.endObject();
        }
    }
}
