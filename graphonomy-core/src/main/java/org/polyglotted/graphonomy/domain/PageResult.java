package org.polyglotted.graphonomy.domain;

import java.util.Iterator;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.helpers.collection.PagingIterator;

public class PageResult implements Iterable<Node> {

    private IndexHits<Node> indexHits;
    private PagingIterator<Node> iterator;

    public PageResult(IndexHits<Node> indexHits, int pageSize, int pageStart) {
        this.indexHits = indexHits;
        this.iterator = new PagingIterator<Node>(indexHits, pageSize);
        if (pageStart > 0)
            this.iterator.page(pageStart);
    }

    public int size() {
        return indexHits.size();
    }

    public float currentScore() {
        return indexHits.currentScore();
    }

    public void close() {
        indexHits.close();
    }

    @Override
    public Iterator<Node> iterator() {
        return iterator;
    }
}
