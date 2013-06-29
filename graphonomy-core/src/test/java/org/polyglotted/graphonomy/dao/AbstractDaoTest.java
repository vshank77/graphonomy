package org.polyglotted.graphonomy.dao;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.polyglotted.graphonomy.domain.DomainFailureException;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.GraphNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public abstract class AbstractDaoTest<T extends GraphNode> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoTest.class);
    protected static final Joiner COMMA_JOINER = Joiner.on(",");

    protected interface TxCallback<V> {
        V doInTransaction();
    }

    protected interface TxVoidCallback {
        void doInTransaction();
    }

    protected GraphDatabaseService graphDb;
    protected GraphonomyDatabase database;

    @Before
    public void initDb() {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
        database = new GraphonomyDatabase(graphDb);
    }

    @After
    public void shutdownDb() {
        graphDb.shutdown();
    }

    protected <V> V execute(TxCallback<V> callback) {
        Transaction tx = graphDb.beginTx();
        try {
            V result = callback.doInTransaction();
            tx.success();
            return result;
        }
        catch (Exception ex) {
            tx.failure();
            throw new RuntimeException(ex);
        }
        finally {
            tx.finish();
        }
    }

    protected void execute(TxVoidCallback callback) {
        Transaction tx = graphDb.beginTx();
        try {
            callback.doInTransaction();
            tx.success();
        }
        catch (Exception ex) {
            tx.failure();
            throw new RuntimeException(ex);
        }
        finally {
            tx.finish();
        }
    }

    @Test(expected = DomainFailureException.class)
    public void testDelete() {
        final String nodeId = "id";
        execute(new TxVoidCallback() {
            @Override
            public void doInTransaction() {
                getAbstractDao().create(loadTestSubject(nodeId));
                getAbstractDao().delete(nodeId);
            }
        });
        getAbstractDao().loadNode(nodeId);
    }

    @Test(expected = NullPointerException.class)
    public void testForceDelete() {
        final String nodeId = "id";
        execute(new TxVoidCallback() {
            @Override
            public void doInTransaction() {
                getAbstractDao().create(loadTestSubject(nodeId));
                getAbstractDao().forceDelete(nodeId);
            }
        });
        getAbstractDao().loadNode(nodeId);
    }

    protected abstract BaseDao<T> getAbstractDao();

    protected abstract T loadTestSubject(String nodeId);

    public static InputStream asStream(String path) {
        return AbstractDaoTest.class.getClassLoader().getResourceAsStream(path);
    }
}
