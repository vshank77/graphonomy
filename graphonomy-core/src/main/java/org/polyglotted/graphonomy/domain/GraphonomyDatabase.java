package org.polyglotted.graphonomy.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IndexIds;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.IndexLinks;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.Link;
import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeId;

import java.util.List;
import java.util.Map;

import org.apache.lucene.queryParser.QueryParser.Operator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.index.lucene.QueryContext;
import org.polyglotted.graphonomy.model.GraphNode;
import org.polyglotted.graphonomy.model.GraphRelation;
import org.polyglotted.graphonomy.util.LinkUtils;
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

    private PropertyUpdater updater = new PropertyUpdater();
    private GraphDatabaseService graphDb;

    @Autowired
    public GraphonomyDatabase(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public Node saveNode(GraphNode gnode) {
        try {
            Node node = new UniqNodeFactory().getOrCreate(NodeId, gnode.getNodeId());
            checkNotNull(node, "error creating or accessing node");
            gnode.setId(node.getId());
            updater.reflectUpdate(node, gnode);
            return node;
        }
        catch (Exception ex) {
            logger.error("error creating node \n", ex);
            throw new DomainFailureException();
        }
    }

    public List<Relationship> saveRelations(GraphNode fromNode, GraphRelation relation) {
        List<Relationship> result = Lists.newArrayList();
        try {
            for (String link : relation.getLinks(fromNode.getNodeId())) {
                Relationship relationship = new UniqRelFactory().getOrCreate(Link, link);
                checkNotNull(relationship, "error creating or accessing relationship for " + link);
                // propertyUpdater.reflectUpdate(relationship, relation);
                result.add(relationship);
            }
            return result;
        }
        catch (Exception ex) {
            logger.error("error creating relationship \n", ex);
            throw new DomainFailureException();
        }
    }

    public Node findNodeByCode(String code) {
        return indexFor(IndexIds).get(NodeId, code).getSingle();
    }

    public IndexHits<Node> findNodesByCode(Iterable<String> codes) {
        String query = SPACE_JOINER.join(codes).toString();
        return indexFor(IndexIds).query(NodeId, new QueryContext(query).defaultOperator(Operator.OR));
    }

    private Index<Node> indexFor(String nodeType) {
        return graphDb.index().forNodes(nodeType);
    }

    private class UniqNodeFactory extends UniqueFactory.UniqueNodeFactory {
        public UniqNodeFactory() {
            super(graphDb, IndexIds);
        }

        @Override
        protected void initialize(Node entityNode, Map<String, Object> properties) {
            entityNode.setProperty(NodeId, properties.get(NodeId));
        }
    }

    private class UniqRelFactory extends UniqueFactory.UniqueRelationshipFactory {
        public UniqRelFactory() {
            super(graphDb, IndexLinks);
        }

        @Override
        protected Relationship create(Map<String, Object> properties) {
            String link = (String) properties.get(Link);
            try {
                Node fromNode = findNodeByCode(LinkUtils.getFrom(link));
                Node toNode = findNodeByCode(LinkUtils.getTo(link));
                RelationshipType relation = LinkUtils.getRel(link);
                return fromNode.createRelationshipTo(toNode, relation);
            }
            catch (RuntimeException re) {
                System.err.println("failed to create rel on " + link);
                throw re;
            }
        }
    }
}
