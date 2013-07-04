package org.polyglotted.graphonomy.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class GenericUtils {

    public static <T> List<T> addOrCreateList(List<T> list, T value) {
        List<T> result = list;
        if (result == null)
            result = Lists.newArrayList();
        result.add(value);
        return result;
    }

    public static <T> List<T> emptyOrList(List<T> list) {
        if (list == null)
            return Collections.emptyList();
        return list;
    }

    public static <T> Set<T> addOrCreateSet(Set<T> set, T value) {
        Set<T> result = set;
        if (result == null)
            result = Sets.newLinkedHashSet();
        result.add(value);
        return result;
    }

    public static <T> Set<T> emptyOrSet(Set<T> set) {
        if (set == null)
            return Collections.emptySet();
        return set;
    }
}
