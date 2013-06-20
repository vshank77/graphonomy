package org.polyglotted.graphonomy.domain;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.neo4j.graphdb.PropertyContainer;
import org.polyglotted.graphonomy.model.Fragment;
import org.polyglotted.graphonomy.util.GsonUtils;

import com.google.gson.Gson;

class PropertySetters {

    public interface PropertySetter {
        void setProperty(PropertyContainer node, Field field, Object value);
    }

    public static class DatePropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DatabaseConstants.DateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
            node.setProperty("D$" + field.getName(), dateFormat.format(value));
        }
    }

    public static class EnumPropertySetter implements PropertySetter {
        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            Enum<?> enumValue = (Enum<?>) value;
            node.setProperty("E$" + field.getName(), enumValue.name());
        }
    }

    public static class ListPropertySetter implements PropertySetter {
        private static Gson gson = GsonUtils.createGson(false, false);

        @Override
        public void setProperty(PropertyContainer node, Field field, Object value) {
            List<?> listValue = (List<?>) value;
            if (listValue.isEmpty())
                return;

            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class<?> genType = (Class<?>) listType.getActualTypeArguments()[0];
            if (PropertyTypes.isDefaultClass(genType)) {
                node.setProperty("L$" + field.getName(), listValue.toArray(PropertyTypes.getArrayFor(genType)));
            }
            else if (Fragment.class.isAssignableFrom(genType)) {
                node.setProperty("F$" + field.getName(), gson.toJson(listValue));
            }
            else
                throw new DomainFailureException();
        }
    }
}
