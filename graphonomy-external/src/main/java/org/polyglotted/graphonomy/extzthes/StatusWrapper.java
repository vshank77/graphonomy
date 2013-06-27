package org.polyglotted.graphonomy.extzthes;

import javax.xml.bind.annotation.XmlEnum;

import org.polyglotted.graphonomy.model.Status;

@XmlEnum
public enum StatusWrapper {
    active, deactivated, deleted;

    public Status convert() {
        return Status.valueOf(name());
    }
}
