package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

public class Link {
    private static final char DASH = '-';

    private final String from;
    private final RelationshipType rel;
    private final String to;
    private boolean unique = false;

    public Link(String from, String rel, String to) {
        this(from, DynamicRelationshipType.withName(rel), to);
    }

    public Link(String from, RelationshipType rel, String to) {
        this.from = checkNotNull(from);
        this.rel = checkNotNull(rel);
        this.to = checkNotNull(to);
    }

    @Override
    public int hashCode() {
        return 17 * from.hashCode() + 19 * rel.name().hashCode() + 31 * to.hashCode();
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
        return from.equals(other.from) && rel.name().equals(other.rel.name()) && to.equals(other.to);
    }

    @Override
    public String toString() {
        return from + DASH + rel.name() + DASH + to;
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

    public boolean isUnique() {
        return unique;
    }

    public Link setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }
}
