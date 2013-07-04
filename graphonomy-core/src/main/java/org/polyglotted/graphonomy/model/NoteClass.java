package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import lombok.NoArgsConstructor;

import org.polyglotted.graphonomy.util.HexUtils;

@NoArgsConstructor
public class NoteClass extends AbstractNoteClass implements GraphNode {

    private long id = -1;

    /* for unit-testing only - not safe for use */
    public NoteClass(String noteLabel) {
        this(noteLabel, noteLabel, TypeSafe.str);
    }

    public NoteClass(String noteLabel, TypeSafe type) {
        this(HexUtils.generateUniqueId(8), noteLabel, type);
    }
    
    public NoteClass(String noteId, String noteLabel, TypeSafe type) {
        setNoteId(noteId);
        setNoteLabel(noteLabel);
        setType(type);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return getNoteId();
    }

    @Override
    public String getNodeName() {
        return getNoteLabel();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NOTE_CLASS;
    }

    @Override
    public GraphNode validate() {
        checkNotNull(noteId);
        checkNotNull(noteLabel);
        checkNotNull(type);
        return this;
    }
}