package org.polyglotted.graphonomy.domain;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexManager;
import org.polyglotted.graphonomy.model.MetaNote;

public class DatabaseDefaultsTest extends DatabaseDefaults {

    @Test (expected = DbException.class)
    public void testSaveAll(@Mocked final GraphDatabaseService graphDb) {
        new Expectations() {
            IndexManager indexMgr;
            Transaction tx;
            {
                graphDb.index(); maxTimes = 2;
                returns(indexMgr);
                graphDb.beginTx();
                returns(tx);
                tx.failure();
                tx.finish();
            }
        };
        saveAll(new GraphonomyDatabase(graphDb));
    }

    @Test (expected = DomainFailureException.class)
    public void testSaveRels(@Mocked final GraphDatabaseService graphDb) {
        new Expectations() {
            IndexManager indexMgr;
            {
                graphDb.index(); maxTimes = 2;
                returns(indexMgr);
            }
        };
        new GraphonomyDatabase(graphDb).saveRelations(new MetaNote("test", "test", false));
    }
}
