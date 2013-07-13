package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static lombok.AccessLevel.PROTECTED;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;
import org.polyglotted.graphonomy.model.TermTypeFactory.StandardTermType;
import org.polyglotted.graphonomy.util.DateUtils;

@Accessors(chain = true)
@EqualsAndHashCode(of = { "termId", "termName" })
@Getter
@NoArgsConstructor(access = PROTECTED)
@Setter
abstract class AbstractTerm implements GraphNode {

    private long id = -1;

    @GraphProperty
    private String termId;
    @GraphProperty
    private String termName;
    @GraphProperty(PropertyType.STRING)
    private TermType termType = StandardTermType.PT;
    @GraphProperty(PropertyType.ENUM)
    private TermUpdate update = TermUpdate.add;
    @GraphProperty(PropertyType.ENUM)
    private Status status = Status.active;
    @GraphProperty(PropertyType.ENUM)
    private TermApproval approval = TermApproval.approved;
    @GraphProperty
    private String createdBy = "admin";
    @GraphProperty
    private String createdDate = DateUtils.format(new Date());
    @GraphProperty
    private String modifiedBy = "admin";
    @GraphProperty
    private String modifiedDate = DateUtils.format(new Date());

    protected AbstractTerm(String termId, String termName) {
        setTermId(termId);
        setTermName(termName);
    }

    @Override
    public GraphNode validate() {
        checkNotNull(termId);
        checkNotNull(termName);
        checkNotNull(termType);
        checkNotNull(update);
        checkNotNull(status);
        checkNotNull(approval);
        checkNotNull(createdBy);
        checkNotNull(createdDate);
        checkNotNull(modifiedBy);
        checkNotNull(modifiedDate);
        return this;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNodeId() {
        return termId;
    }

    @Override
    public String getNodeName() {
        return termName;
    }
}
