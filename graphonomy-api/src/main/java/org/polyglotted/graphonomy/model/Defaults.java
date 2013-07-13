package org.polyglotted.graphonomy.model;

import java.util.Map;

import org.polyglotted.graphonomy.model.RelationClass.BaseType;

import com.google.common.collect.Maps;

public class Defaults {

    public static final String XSD = "http://www.w3.org/2001/XMLSchema/";
    
    private static final Map<String, NoteClass> notesMap = Maps.newLinkedHashMap();
    private static final Map<String, RelationClass> relationsMap = Maps.newLinkedHashMap();

    static {
        add(notesMap,
                new NoteClass("urn:org.polyglotted.graphonomy/scope", "Scope Note", TypeSafe.str).setPattern(".*"));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/string", "string", TypeSafe.str));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/normalizedString", "normalizedString",
                TypeSafe.str));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/boolean", "boolean", TypeSafe.bool));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/byte", "byte", TypeSafe.number));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/short", "short", TypeSafe.number));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/int", "int", TypeSafe.number));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/integer", "integer", TypeSafe.number));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/long", "long", TypeSafe.number));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/decimal", "decimal", TypeSafe.decimal));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/float", "float", TypeSafe.decimal));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/double", "double", TypeSafe.decimal));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/date", "date", TypeSafe.date));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/dateTime", "dateTime", TypeSafe.date));
        add(notesMap, new NoteClass("http://www.w3.org/2001/XMLSchema/time", "time", TypeSafe.time));

        add(relationsMap, new RelationClass("BT", BaseType.hierarchy, "Broader Term").setExtended(false));
        add(relationsMap, new RelationClass("NT", BaseType.hierarchy, "Narrower Term").setExtended(false));
        add(relationsMap, new RelationClass("RT", BaseType.related, "Related To").setExtended(false));
        add(relationsMap, new RelationClass("USE", BaseType.usefor, "Use").setExtended(false));
        add(relationsMap, new RelationClass("UF", BaseType.usefor, "Use For").setExtended(false));
    }

    public static final TermClass ROOTS = new TermClass("Root Class");
    public static final TermClass ENTITIES = new TermClass("Entity Class");

    public static boolean containsNote(String noteId) {
        return notesMap.containsKey(noteId);
    }

    public static boolean containsXsdField(String fieldName) {
        return notesMap.containsKey(XSD + fieldName);
    }

    public static NoteClass getNote(String noteId) {
        return notesMap.get(noteId);
    }

    public static NoteClass getXsdField(String fieldName) {
        return notesMap.get(XSD + fieldName);
    }

    public static Iterable<NoteClass> getAllNotes() {
        return notesMap.values();
    }

    public static boolean containsRelation(String relationId) {
        return relationsMap.containsKey(relationId);
    }
    
    public static RelationClass getRelation(String relationId) {
        return relationsMap.get(relationId);
    }

    public static Iterable<RelationClass> getAllRelations() {
        return relationsMap.values();
    }
    
    private static <T extends GraphNode> void add(Map<String, T> map, T item) {
        map.put(item.getNodeId(), item);
    }
}
