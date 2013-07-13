package org.polyglotted.graphonomy.model;

import static org.polyglotted.graphonomy.model.Defaults.ENTITIES;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@NoArgsConstructor
@Setter
public class Entity extends AbstractTerm {

    private List<NoteField> fields;

    public Entity(String entityId, String entityName) {
        super(entityId, entityName);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ENTITY;
    }

    public Category getCategory() {
        return Category.from(getTermId(), ENTITIES);
    }

    public List<NoteField> getFields() {
        return GenericUtils.emptyOrList(fields);
    }

    public Entity addField(NoteField field) {
        fields = GenericUtils.addOrCreateList(fields, field);
        return this;
    }
}
