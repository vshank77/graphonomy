package org.polyglotted.graphonomy.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GraphProperty {

    PropertyType value() default PropertyType.DEFAULT;

    public enum PropertyType {
        DEFAULT, DATE, ENUM, LIST, STRING;
    }
}
