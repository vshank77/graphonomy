package org.polyglotted.graphonomy.domain;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class SearchHelper {

    public static String stem(String input, String... fields) {
        StringBuilder builder = new StringBuilder();

        List<String> words = Lists.newArrayList();
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                builder.append(c);
            }
            else if (Character.isSpaceChar(c)) {
                words.add(builder.toString());
                builder.delete(0, builder.length());
            }
        }
        if (builder.length() > 0) {
            words.add(builder.toString());
            builder.delete(0, builder.length());
        }

        for (String field : fields) {
            builder.append("(");
            for (int i = 0; i < words.size(); i++) {
                builder.append(field);
                builder.append(":");
                builder.append(words.get(i));
                builder.append("*");
                if (i != words.size() - 1)
                    builder.append(" ");
            }
            builder.append(")");
        }
        return builder.toString();
    }
}
