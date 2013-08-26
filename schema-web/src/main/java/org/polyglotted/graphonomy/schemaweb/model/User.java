package org.polyglotted.graphonomy.schemaweb.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String displayName;
    private String department;
    private String country;
    private String city;
    private boolean admin;
}
