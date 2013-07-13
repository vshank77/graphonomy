package org.polyglotted.graphonomy.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString(includeFieldNames = false)
public class Range {
    private long min; // TODO add exclusive
    private long max;

    public boolean isInRange(double value) {
        return value >= min && value <= max;
    }
}