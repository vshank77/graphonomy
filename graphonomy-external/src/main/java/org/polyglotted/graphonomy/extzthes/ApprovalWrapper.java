package org.polyglotted.graphonomy.extzthes;
 
import javax.xml.bind.annotation.XmlEnum;

import org.polyglotted.graphonomy.model.TermApproval;
 
@XmlEnum
public enum ApprovalWrapper {
    approved, candidate, rejected;

    public TermApproval convert() {
        return TermApproval.valueOf(name());
    }
}
