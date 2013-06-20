package org.polyglotted.graphonomy.util;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

public class LinkUtils {
    private static final char DASH = '-';

    public static String getFrom(String link) {
        return link.substring(0, link.indexOf(DASH));
    }

    public static RelationshipType getRel(String link) {
        return DynamicRelationshipType.withName(link.substring(link.indexOf(DASH) + 1, link.lastIndexOf(DASH)));
    }

    public static String getTo(String link) {
        return link.substring(link.lastIndexOf(DASH) + 1);
    }

    public static String getLink(String from, RelationshipType rel, String to) {
        return from + DASH + rel.name() + DASH + to;
    }
}
