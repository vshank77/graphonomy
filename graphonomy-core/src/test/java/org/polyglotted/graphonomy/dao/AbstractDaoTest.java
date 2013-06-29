package org.polyglotted.graphonomy.dao;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.EnumMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.polyglotted.graphonomy.domain.DomainFailureException;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.domain.PageResult;
import org.polyglotted.graphonomy.model.GraphNode;
import org.polyglotted.graphonomy.model.NodeType;
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

    @Test
    public void testFindAllDefault() {
        final String nodeId = "id";
        execute(new TxVoidCallback() {
            @Override
            public void doInTransaction() {
                getAbstractDao().create(loadTestSubject(nodeId));
            }
        });
        EnumMap<NodeType, Integer> hitCounts = new EnumMap<NodeType, Integer>(NodeType.class);
        hitCounts.put(NodeType.NOTE_CLASS, 2); hitCounts.put(NodeType.RELATION_CLASS, 6);
        hitCounts.put(NodeType.TERM_CLASS, 2); hitCounts.put(NodeType.TERM, 1);
        PageResult result = getAbstractDao().findAll(10, 0);
        try {
            assertEquals(hitCounts.get(getAbstractDao().getNodeType()), Integer.valueOf(result.size()));
        }
        finally {
            result.close();
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
