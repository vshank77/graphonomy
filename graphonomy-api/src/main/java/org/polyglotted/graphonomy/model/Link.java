package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import com.google.common.base.Strings;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Link implements Validated<Link> {
    private static final char DASH = '-';

    private final String from;
    private final String rel;
    private final String to;
    private final String unique;

    public Link(String from, String rel, String to) {
        this(from, rel, to, "");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(from);
        sb.append(DASH);
        sb.append(rel);
        sb.append(DASH);
        sb.append(to);
        if (!Strings.isNullOrEmpty(unique)) {
            sb.append(DASH);
            sb.append(unique);
        }
        return sb.toString();
    }

    @Override
    public Link validate() {
        checkNotNull(from);
        checkNotNull(rel);
        checkNotNull(to);
        return this;
    }
}
