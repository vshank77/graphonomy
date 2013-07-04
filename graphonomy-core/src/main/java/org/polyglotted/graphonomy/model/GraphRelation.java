package org.polyglotted.graphonomy.model;

import java.util.List;

public interface GraphRelation extends Validated<GraphRelation> {

    List<Link> getLinks();
}
