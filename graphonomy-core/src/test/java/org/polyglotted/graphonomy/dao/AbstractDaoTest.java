package org.polyglotted.graphonomy.dao;

import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeId;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.polyglotted.graphonomy.domain.DomainFailureException;
import org.polyglotted.graphonomy.domain.GraphonomyDatabase;
import org.polyglotted.graphonomy.model.GraphNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public abstract class AbstractDaoTest<T extends GraphNode> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoTest.class);
    protected static final Joiner COMMA_JOINER = Joiner.on(",");
    protected static Properties outputs = new Properties();

    protected interface TxCallback<V> {
        V doInTransaction();
    }

    protected interface TxVoidCallback {
        void doInTransaction();
    }

    protected GraphDatabaseService graphDb;
    protected GraphonomyDatabase database;

    @BeforeClass
    public static void initOutputs() throws IOException {
        outputs.load(asStream("outputs/AbstractDaoTest.txt"));
    }

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

    public static String inspectNode(Node node) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        final Iterator<String> propertyKeys = node.getPropertyKeys().iterator();
        if (propertyKeys.hasNext())
            inspectProperties(node, propertyKeys, builder);
        builder.append("}");
        return builder.toString();
    }

    public static String inspectRelationships(Iterable<Relationship> relationships) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Relationship rel : relationships) {
            if (i++ != 0) {
                builder.append(",");
            }
            builder.append("{");
            builder.append("\"from\":");
            builder.append(escapeString(rel.getStartNode().getProperty(NodeId)));
            builder.append(",\"rel\":");
            builder.append(escapeString(rel.getType().name()));
            builder.append(",\"to\":");
            builder.append(escapeString(rel.getEndNode().getProperty(NodeId)));
            Iterator<String> propertyKeys = rel.getPropertyKeys().iterator();
            if (propertyKeys.hasNext()) {
                builder.append(",");
                inspectProperties(rel, propertyKeys, builder);
            }
            builder.append("}");
        }
        builder.append("]");
        return builder.toString();
    }

    private static void inspectProperties(PropertyContainer node, Iterator<String> propertyKeys, StringBuilder builder) {
        int i = 0;
        while (propertyKeys.hasNext()) {
            if (i++ != 0)
                builder.append(",");
            String prop = propertyKeys.next();
            builder.append("\"" + prop + "\":");
            inspectProperty(node.getProperty(prop), builder);
        }
    }

    private static void inspectProperty(Object property, final StringBuilder builder) {
        if (property.getClass().isArray()) {
            List<?> list = Lists.transform(Arrays.asList((Object[]) property), new Function<Object, String>() {
                @Override
                public String apply(Object o) {
                    return escapeString(o);
                }
            });
            builder.append("[");
            COMMA_JOINER.appendTo(builder, list);
            builder.append("]");
        }
        else {
            builder.append(escapeString(property));
        }
    }

    private static String escapeString(Object property) {
        return "\"" + String.valueOf(property) + "\"";
    }
}
