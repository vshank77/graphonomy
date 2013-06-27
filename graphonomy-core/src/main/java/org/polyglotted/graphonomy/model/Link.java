package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.neo4j.graphdb.RelationshipType;

import com.google.common.base.Strings;

public class Link {
    private static final char DASH = '-';

    private final String from;
    private final RelationshipType rel;
    private final String to;
    private final String unique;

    public Link(String from, RelationshipType rel, String to) {
        this(from, rel, to, "");
    }

    public Link(String from, RelationshipType rel, String to, String unique) {
        this.from = checkNotNull(from);
        this.rel = checkNotNull(rel);
        this.to = checkNotNull(to);
        this.unique = checkNotNull(unique);
    }

    @Override
    public int hashCode() {
        return 17 * from.hashCode() + 19 * rel.name().hashCode() + 31 * to.hashCode() + 29 * unique.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Link other = (Link) obj;
        return from.equals(other.from) && rel.name().equals(other.rel.name()) && to.equals(other.to)
                && unique.equals(other.unique);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(from);
        sb.append(DASH);
        sb.append(rel.name());
        sb.append(DASH);
        sb.append(to);
        if (!Strings.isNullOrEmpty(unique)) {
            sb.append(DASH);
            sb.append(unique);
        }
        return sb.toString();
    }

    public String getFrom() {
        return from;
    }

    public RelationshipType getRel() {
        return rel;
    }

    public String getTo() {
        return to;
    }

    public String getUnique() {
        return unique;
    }
}
