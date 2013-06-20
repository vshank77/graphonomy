package org.polyglotted.graphonomy.domain;

import static org.polyglotted.graphonomy.domain.DatabaseConstants.NodeId;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.neo4j.graphdb.PropertyContainer;
import org.polyglotted.graphonomy.domain.PropertySetters.PropertySetter;
import org.polyglotted.graphonomy.model.GraphNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PropertyUpdater {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUpdater.class);

    public void reflectUpdate(PropertyContainer node, GraphNode gnode) {
        final Class<?> gClass = gnode.getClass();
        for (Field field : gClass.getDeclaredFields()) {
            final String fieldName = field.getName();
            if (field.isSynthetic() || Modifier.isTransient(field.getModifiers()) || NodeId.equals(fieldName)
                    || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object value = getValueFrom(gnode, field);
            if (value == null)
                continue;

            PropertySetter setter = null;
            if (PropertyTypes.isDefaultClass(value)) {
                node.setProperty(fieldName, value);
            }
            else if ((setter = PropertyTypes.getExtended(value)) != null) {
                setter.setProperty(node, field, value);
            }
            else {
                logger.error("unable to set property for field " + fieldName);
                throw new DomainFailureException();
            }
        }
    }

    private Object getValueFrom(GraphNode gnode, Field field) {
        try {
            field.setAccessible(true);
            return field.get(gnode);
        }
        catch (Exception e) {
            logger.error("unable to reflectively get value from field " + field.getName(), e);
            throw new DomainFailureException();
        }
    }

}
