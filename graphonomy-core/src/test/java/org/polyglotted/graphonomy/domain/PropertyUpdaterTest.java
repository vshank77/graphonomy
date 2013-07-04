package org.polyglotted.graphonomy.domain;

import mockit.Expectations;
import mockit.NonStrict;

import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.polyglotted.graphonomy.model.Category;
import org.polyglotted.graphonomy.model.Posting;
import org.polyglotted.graphonomy.model.Term;

public class PropertyUpdaterTest {

    @NonStrict
    private Node mockNode;

    @Test
    public void testReflectUpdate() {
        new Expectations() {
            {
                mockNode.setProperty((String) withNotNull(), withNotNull());
            }
        };

        PropertyUpdater updater = new PropertyUpdater();
        Term term = new Term("id", "value");
        term.addCategory(new Category("id", "Test Class"));
        term.addPosting(Posting.from("urn:isbn:12331Abcd28", "field", "325"));
        term.validate();
        updater.reflectUpdate(mockNode, term);
    }
}
