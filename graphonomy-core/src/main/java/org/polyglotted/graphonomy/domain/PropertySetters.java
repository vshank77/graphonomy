package org.polyglotted.graphonomy.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.neo4j.graphdb.PropertyContainer;
import org.polyglotted.graphonomy.model.Fragment;
import org.polyglotted.graphonomy.model.GraphProperty;
import org.polyglotted.graphonomy.util.DateUtils;
import org.polyglotted.graphonomy.util.JsonUtils;

class PropertySetters {
    public interface PropertySetter {
        void setProperty(PropertyContainer node, Field field, Object value);
    }

    public static PropertySetter getSetter(GraphProperty property) {
        switch (property.value()) {
            case DATE:
                return new DatePropertySetter();
            case ENUM:
                return new EnumPropertySetter();
            case LIST:
                return new ListPropertySetter();
            case FRAGMENT:
                return new FragmentPropertySetter();
            case STRING:
                return new ToStringPropertySetter();
            default:
                return new DefaultPropertySetter();
        }
    }

    public static class DefaultPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            node.setProperty(field.getName(), value);
        }
    }

    public static class ToStringPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            node.setProperty(field.getName(), String.valueOf(checkNotNull(value)));
        }
    }

    public static class DatePropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            node.setProperty(field.getName(), DateUtils.format((Date) value));
        }
    }

    public static class EnumPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            Enum<?> enumValue = (Enum<?>) value;
            node.setProperty(field.getName(), enumValue.name());
        }
    }

    public static class ListPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            List<?> listValue = (List<?>) value;
            if (listValue.isEmpty())
                return;

            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class<?> genType = (Class<?>) listType.getActualTypeArguments()[0];
            if (Neo4jSupportedTypes.isDefaultClass(genType)) {
                node.setProperty(field.getName(), listValue.toArray(Neo4jSupportedTypes.getArrayFor(genType)));
            }
            else if (Fragment.class.isAssignableFrom(genType)) {
                node.setProperty("$" + field.getName(), JsonUtils.asJson(listValue));
            }
            else
                throw new DomainFailureException("failed to create node for " + field.getName());
        }
    }

    public static class FragmentPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            node.setProperty("$" + field.getName(), JsonUtils.asJson(value));
        }
    }
}
