package org.polyglotted.graphonomy.schemarepo;

import java.util.Map;

import org.polyglotted.graphonomy.model.Defaults;
import org.polyglotted.graphonomy.model.Entity;
import org.polyglotted.graphonomy.model.NoteClass;

import com.google.common.collect.Maps;

public class SchemaWrapper {

    private Map<String, NoteClass> fields = Maps.newLinkedHashMap();
    private Map<String, Entity> entities = Maps.newLinkedHashMap();

    public void addField(NoteClass field) {
        fields.put(field.getNoteId(), field);
    }

    public boolean containsField(String fieldId) {
        return Defaults.containsNote(fieldId) || fields.containsKey(fieldId);
    }

    public NoteClass getField(String fieldId) {
        return fields.containsKey(fieldId) ? fields.get(fieldId) : Defaults.getNote(fieldId);
    }

    public Iterable<NoteClass> getFields() {
        return fields.values();
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getTermId(), entity);
    }

    public boolean containsEntity(String entityId) {
        return entities.containsKey(entityId);
    }

    public Entity getEntity(String entityId) {
        return entities.get(entityId);
    }

    public Iterable<Entity> getEntities() {
        return entities.values();
    }
}
