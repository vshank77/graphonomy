package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class RelationClass implements GraphNode {

    public static final RelationClass HIERARCHY = new RelationClass("a1b2c3", "NT", "Narrow Term", "BT", "Broad Term");

    private long id = -1;
    private String relationId;
    private String forwardName;
    private String forwardDesc;
    private String reverseName;
    private String reverseDesc;

    public RelationClass() {}

    public RelationClass(String relationId) {
        setRelationId(relationId);
    }

    public RelationClass(String relationId, String forwardName, String forwardDesc, String reverseName,
            String reverseDesc) {
        setRelationId(relationId);
        setForwardName(forwardName);
        setForwardDesc(forwardDesc);
        setReverseName(reverseName);
        setReverseDesc(reverseDesc);
    }

    @Override
    public int hashCode() {
        return 19 * ((relationId == null) ? 0 : relationId.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RelationClass other = (RelationClass) obj;
        return (relationId == null) ? (other.relationId == null) : (relationId.equals(other.relationId));
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return getRelationId();
    }

    @Override
    public GraphNode validate() {
        checkNotNull(relationId);
        checkNotNull(forwardName);
        checkNotNull(reverseName);
        return this;
    }

    public String getRelationId() {
        return relationId;
    }

    public RelationClass setRelationId(String relationId) {
        this.relationId = checkNotNull(relationId);
        return this;
    }

    public String getForwardName() {
        return forwardName;
    }

    public RelationClass setForwardName(String forwardName) {
        this.forwardName = checkNotNull(forwardName);
        return this;
    }

    public String getForwardDesc() {
        return forwardDesc;
    }

    public RelationClass setForwardDesc(String forwardDesc) {
        this.forwardDesc = forwardDesc;
        return this;
    }

    public String getReverseName() {
        return reverseName;
    }

    public RelationClass setReverseName(String reverseName) {
        this.reverseName = checkNotNull(reverseName);
        return this;
    }

    public String getReverseDesc() {
        return reverseDesc;
    }

    public RelationClass setReverseDesc(String reverseDesc) {
        this.reverseDesc = reverseDesc;
        return this;
    }
}
