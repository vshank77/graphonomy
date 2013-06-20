package org.polyglotted.graphonomy.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.polyglotted.graphonomy.model.MetaNote;
import org.polyglotted.graphonomy.model.MetaRelation;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GsonAdapterFactory implements TypeAdapterFactory {

    private Map<Class<?>, TypeAdapter<?>> typeAdapterMap = Maps.newConcurrentMap();

    public GsonAdapterFactory() {
        typeAdapterMap.put(Date.class, new DateAdapter().nullSafe());
        typeAdapterMap.put(MetaNote.class, new MetaNoteAdapter());
        typeAdapterMap.put(MetaRelation.class, new MetaRelationAdapter());
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
            String noteId = null;
            boolean mandatory = false;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if ("noteId".equals(name)) {
                    noteId = reader.nextString();
                }
                else if ("mandatory".equals(name)) {
                    mandatory = reader.nextBoolean();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new MetaNote(new NoteClass(noteId), mandatory);
        }

        @Override
        public void write(JsonWriter writer, MetaNote metaNote) throws IOException {
            writer.beginObject();
            writer.name("noteId").value(metaNote.getNoteClass().getNoteId());
            writer.name("mandatory").value(metaNote.isMandatory());
            writer.endObject();
        }
    }

    private static class MetaRelationAdapter extends TypeAdapter<MetaRelation> {
        @Override
        public MetaRelation read(JsonReader reader) throws IOException {
            String relationId = null;
            String targetId = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if ("relation".equals(name)) {
                    relationId = reader.nextString();
                }
                else if ("target".equals(name)) {
                    targetId = reader.nextString();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new MetaRelation(new RelationClass(relationId), new TermClass(targetId));
        }

        @Override
        public void write(JsonWriter writer, MetaRelation metaRel) throws IOException {
            writer.beginObject();
            writer.name("relation").value(metaRel.getRelationClass().getRelationId());
            writer.name("target").value(metaRel.getTargetClass().getClassId());
            writer.endObject();
        }
    }
}
