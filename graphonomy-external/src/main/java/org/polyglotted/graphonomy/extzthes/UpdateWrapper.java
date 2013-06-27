package org.polyglotted.graphonomy.extzthes;

import javax.xml.bind.annotation.XmlEnum;

import org.polyglotted.graphonomy.model.TermUpdate;

@XmlEnum
public enum UpdateWrapper {
    add, delete;
    
    TermUpdate convert() {
        return TermUpdate.valueOf(name());
    }
}
