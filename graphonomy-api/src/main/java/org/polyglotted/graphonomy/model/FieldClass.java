package org.polyglotted.graphonomy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class FieldClass extends NoteClass {

    @GraphProperty(PropertyType.ENUM)
    private FieldType fieldType;
    @GraphProperty
    private boolean globalScope;

    public FieldClass(String noteLabel) {
        super(noteLabel);
    }

    public FieldClass(String noteId, String noteLabel, TypeSafe type) {
        super(noteId, noteLabel, type);
    }

    @Override
    public boolean canEqual(Object other) {
        return (other instanceof FieldClass);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FIELD_CLASS;
    }
}
