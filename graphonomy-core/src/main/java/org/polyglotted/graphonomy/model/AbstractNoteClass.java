package org.polyglotted.graphonomy.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(of = { "noteId", "noteLabel" })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public abstract class AbstractNoteClass {

    @GraphProperty
    protected String noteId;
    @GraphProperty
    protected String noteLabel;
    @GraphProperty(PropertyType.ENUM)
    protected TypeSafe type;
    @GraphProperty
    protected String defaultValue;
    @GraphProperty
    protected boolean required;
    @GraphProperty(PropertyType.LIST)
    protected List<String> enums;
    @GraphProperty
    protected String pattern;
    @Getter(AccessLevel.PROTECTED)
    @GraphProperty(PropertyType.FRAGMENT)
    protected Range range;

    public <T> T validate(String value) {
        return type.validatedValue(value, this);
    }

    public boolean hasRange() {
        return range != null;
    }

    public boolean isInRange(Number value) {
        return hasRange() && range.isInRange(value.doubleValue());
    }

    public long getRangeMin() {
        return hasRange() ? range.getMin() : -1;
    }

    public long getRangeMax() {
        return hasRange() ? range.getMax() : -1;
    }

    public AbstractNoteClass setRange(int min, int max) {
        this.range = new Range(min, max);
        return this;
    }

    public List<String> getEnums() {
        return GenericUtils.emptyOrList(enums);
    }

    public AbstractNoteClass addEnum(String enumVal) {
        enums = GenericUtils.addOrCreateList(enums, enumVal);
        return this;
    }
}
