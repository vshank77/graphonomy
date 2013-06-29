package org.polyglotted.graphonomy.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IndexIds;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IndexLinks;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IndexTypes;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.Link;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeId;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeType;

import java.util.List;
import java.util.Map;

import org.apache.lucene.queryParser.QueryParser.Operator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.index.lucene.QueryContext;
import org.polyglotted.graphonomy.model.GraphNode;
import org.polyglotted.graphonomy.model.GraphRelation;
import org.polyglotted.graphonomy.model.Link;
import org.polyglotted.graphonomy.model.NodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@Component
public class GraphonomyDatabase {

    private static final Logger logger = LoggerFactory.getLogger(GraphonomyDatabase.class);
    private static final Joiner SPACE_JOINER = Joiner.on(" ");

    protected final GraphDatabaseService graphDb;
    protected final UniqNodeFactory nodeFactory;
    protected final UniqRelFactory relFactory;
    protected final PropertyUpdater updater = new PropertyUpdater();

    @Autowired
    public GraphonomyDatabase(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
        this.nodeFactory = new UniqNodeFactory(graphDb);
        this.relFactory = new UniqRelFactory(graphDb);
        DatabaseDefaults.saveAll(this);
    }

    public Node saveNode(GraphNode gnode) {
        try {
            Node node = nodeFactory.getOrCreate(NodeId, gnode.validate().getNodeId());
            checkNotNull(node, "error creating or accessing node");
            node.setProperty(NodeType, gnode.getNodeType().name());
            indexFor(IndexTypes).add(node, NodeType, gnode.getNodeType());
            gnode.setId(node.getId());
            updater.reflectUpdate(node, gnode);
            return node;
        }
        catch (Exception ex) {
            logger.error("error creating node \n", ex);
            throw new DomainFailureException();
        }
    }

    public List<Relationship> saveRelations(GraphRelation relation) {
        List<Relationship> result = Lists.newArrayList();
        for (Link link : relation.getLinks()) {
            try {
                Relationship relationship = relFactory.getOrCreate(Link, link);
                checkNotNull(relationship, "error creating or accessing relationship for " + link);
                updater.reflectUpdate(relationship, relation);
                result.add(relationship);
            }
            catch (Exception ex) {
                logger.error("error in " + link.toString());
                logger.error("error creating relationship \n", ex);
                throw new DomainFailureException();
            }
        }
        return result;
    }

    public Node findNodeByCode(String code) {
        return indexFor(IndexIds).get(NodeId, code).getSingle();
    }

    public PageResult findNodesByType(NodeType type, int pageSize, int pageStart) {
        IndexHits<Node> source = indexFor(IndexTypes).get(NodeType, type);
        return new PageResult(source, pageSize, pageStart);
    }

    public IndexHits<Node> findNodesByCode(Iterable<String> codes) {
        String query = SPACE_JOINER.join(codes).toString();
        return indexFor(IndexIds).query(NodeId, new QueryContext(query).defaultOperator(Operator.OR));
    }

    protected Index<Node> indexFor(String indexType) {
        return graphDb.index().forNodes(indexType);
    }

    protected class UniqNodeFactory extends UniqueFactory.UniqueNodeFactory {
        public UniqNodeFactory(GraphDatabaseService database) {
            super(database, IndexIds);
        }

        @Override
        protected void initialize(Node entityNode, Map<String, Object> properties) {
            entityNode.setProperty(NodeId, properties.get(NodeId));
        }
    }

    protected class UniqRelFactory extends UniqueFactory.UniqueRelationshipFactory {
        public UniqRelFactory(GraphDatabaseService database) {
            super(database, IndexLinks);
        }

        @Override
        protected Relationship create(Map<String, Object> properties) {
            Link link = (Link) properties.get(Link);
            try {
                Node fromNode = findNodeByCode(link.getFrom());
                Node toNode = findNodeByCode(link.getTo());
                return fromNode.createRelationshipTo(toNode, link.getRel());
            }
            catch (RuntimeException re) {
                System.err.println("failed to create rel on " + link);
                throw re;
            }
        }
    }
}
