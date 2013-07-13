package org.polyglotted.graphonomy.domain;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

import lombok.SneakyThrows;

import org.neo4j.graphdb.PropertyContainer;
import org.polyglotted.graphonomy.domain.PropertySetters.PropertySetter;
import org.polyglotted.graphonomy.model.GraphProperty;

import com.google.common.collect.Sets;

class PropertyUpdater {

    public void reflectUpdate(PropertyContainer node, Object item) {
        final Class<?> gClass = item.getClass();
        for (Field field : collectFields(gClass)) {
            GraphProperty graphProperty = field.getAnnotation(GraphProperty.class);
            if (graphProperty == null) {
                continue;
            }

            Object value = getValueFrom(item, field);
            if (value == null)
                continue;

            PropertySetter setter = PropertySetters.getSetter(graphProperty);
            setter.setProperty(node, field, value);
        }
    }

    private Set<Field> collectFields(Class<?> clazz) {
        Set<Field> fields = Sets.newHashSet();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @SneakyThrows
    private Object getValueFrom(Object item, Field field) {
        field.setAccessible(true);
        return field.get(item);
    }
}
