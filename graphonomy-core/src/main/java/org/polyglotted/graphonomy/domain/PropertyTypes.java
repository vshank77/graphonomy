package org.polyglotted.graphonomy.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.polyglotted.graphonomy.domain.PropertySetters.DatePropertySetter;
import org.polyglotted.graphonomy.domain.PropertySetters.EnumPropertySetter;
import org.polyglotted.graphonomy.domain.PropertySetters.ListPropertySetter;
import org.polyglotted.graphonomy.domain.PropertySetters.PropertySetter;

import com.google.common.collect.Maps;

class PropertyTypes {

    private static final Map<Class<?>, Object> defaultClasses = Maps.newHashMap();
    static {
        defaultClasses.put(Boolean.class, new Boolean[0]);
        defaultClasses.put(Boolean[].class, new Boolean[0]);
        defaultClasses.put(boolean.class, new Boolean[0]);
        defaultClasses.put(boolean[].class, new boolean[0]);
        defaultClasses.put(Byte.class, new Byte[0]);
        defaultClasses.put(Byte[].class, new Byte[0]);
        defaultClasses.put(byte.class, new Byte[0]);
        defaultClasses.put(byte[].class, new byte[0]);
        defaultClasses.put(Short.class, new Short[0]);
        defaultClasses.put(Short[].class, new Short[0]);
        defaultClasses.put(short.class, new Short[0]);
        defaultClasses.put(short[].class, new short[0]);
        defaultClasses.put(Integer.class, new Integer[0]);
        defaultClasses.put(Integer[].class, new Integer[0]);
        defaultClasses.put(int.class, new Integer[0]);
        defaultClasses.put(int[].class, new int[0]);
        defaultClasses.put(Long.class, new Long[0]);
        defaultClasses.put(Long[].class, new Long[0]);
        defaultClasses.put(long.class, new Long[0]);
        defaultClasses.put(long[].class, new long[0]);
        defaultClasses.put(Float.class, new Float[0]);
        defaultClasses.put(Float[].class, new Float[0]);
        defaultClasses.put(float.class, new Float[0]);
        defaultClasses.put(float[].class, new float[0]);
        defaultClasses.put(Double.class, new Double[0]);
        defaultClasses.put(Double[].class, new Double[0]);
        defaultClasses.put(double.class, new Double[0]);
        defaultClasses.put(double[].class, new double[0]);
        defaultClasses.put(Character.class, new Character[0]);
        defaultClasses.put(Character[].class, new Character[0]);
        defaultClasses.put(char.class, new Character[0]);
        defaultClasses.put(char[].class, new char[0]);
        defaultClasses.put(String.class, new String[0]);
        defaultClasses.put(String[].class, new String[0]);
    }

    public static boolean isDefaultClass(Object obj) {
        return defaultClasses.containsKey(obj.getClass());
    }

    public static boolean isDefaultClass(Class<?> clazz) {
        return defaultClasses.containsKey(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getArrayFor(Class<?> clazz) {
        return (T[]) defaultClasses.get(clazz);
    }

    public static PropertySetter getExtended(Object obj) {
        Class<?> objClass = obj.getClass();
        if (objClass.isEnum() || Enum.class.isAssignableFrom(objClass)) {
            return new EnumPropertySetter();
        }
        else if (List.class.isAssignableFrom(objClass)) {
            return new ListPropertySetter();
        }
        else if (Date.class.isAssignableFrom(objClass)) {
            return new DatePropertySetter();
        }
        return null;
    }
}
