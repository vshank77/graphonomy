package org.polyglotted.graphonomy.model;

import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.polyglotted.graphonomy.util.DateUtils;

import com.google.common.collect.Maps;

public enum TypeSafe {
    str("string", "normalizedString") {
        @Override
        @SuppressWarnings("unchecked")
        public String validatedValue(String value, AbstractNoteClass clazz) {
            if (value == null) {
                if (clazz.getDefaultValue() != null)
                    return clazz.getDefaultValue();
                else if (clazz.isRequired())
                    throw new TypeValidationException("value is required for this note type");
                return null;
            }
            if (clazz.getEnums().size() > 0) {
                for (String enumVal : clazz.getEnums()) {
                    if (enumVal.equals(value))
                        return value; // dont need to do further checks
                }
                throw new TypeValidationException("Not a valid enum value " + value);
            }
            if (clazz.getPattern() != null) {
                try {
                    if (!Pattern.matches(clazz.getPattern(), value))
                        throw new TypeValidationException("Pattern " + clazz.getPattern() + " does not match value "
                                + value);
                }
                catch (PatternSyntaxException pex) {
                    throw new TypeValidationException("Pattern " + clazz.getPattern() + " not valid; "
                            + pex.getMessage());
                }
            }
            if (clazz.hasRange() && !(clazz.isInRange(value.length()))) {
                throw new TypeValidationException("length of value " + value + " is not in range ["
                        + clazz.getRangeMin() + "," + clazz.getRangeMax() + "]");
            }
            return value;
        }
    },
    bool("boolean") {
        @Override
        @SuppressWarnings("unchecked")
        public Boolean validatedValue(String value, AbstractNoteClass clazz) {
            if (value == null) {
                if (clazz.getDefaultValue() != null) {
                    return Boolean.parseBoolean(clazz.getDefaultValue());
                }
                else if (clazz.isRequired())
                    throw new TypeValidationException("value is required for this note type");
            }
            return "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)
                    || "1".equalsIgnoreCase(value);
        }
    },
    number("byte", "short", "int", "integer", "long", "positiveIntger", "negativeInteger", "nonPositiveInteger",
            "nonNegativeInteger", "unsignedByte", "unsignedShort", "unsignedInt", "unsignedLong") {
        @Override
        @SuppressWarnings("unchecked")
        public Number validatedValue(String value, AbstractNoteClass clazz) {
            if (value == null) {
                if (clazz.getDefaultValue() != null) {
                    return asNumber(clazz.getDefaultValue(), "unable to parse defaultValue as a number ");
                }
                else if (clazz.isRequired())
                    throw new TypeValidationException("value is required for this note type");
                return 0;
            }
            Number number = asNumber(value, "unable to parse value as number ");
            if (clazz.hasRange() && !(clazz.isInRange(number))) {
                throw new TypeValidationException("value " + value + " is not in range [" + clazz.getRangeMin() + ","
                        + clazz.getRangeMax() + "]");
            }
            return number;
        }
    },
    decimal("decimal", "float", "double") {
        @Override
        @SuppressWarnings("unchecked")
        public Double validatedValue(String value, AbstractNoteClass clazz) {
            if (value == null) {
                if (clazz.getDefaultValue() != null) {
                    return asDecimal(clazz.getDefaultValue(), "unable to parse defaultValue as a decimal ");
                }
                else if (clazz.isRequired())
                    throw new TypeValidationException("value is required for this note type");
                return 0.0;
            }
            Double number = asDecimal(value, "unable to parse value as decimal ");
            if (clazz.hasRange() && !(clazz.isInRange(number))) {
                throw new TypeValidationException("value " + value + " is not in range [" + clazz.getRangeMin() + ","
                        + clazz.getRangeMax() + "]");
            }
            return number;
        }
    },
    date("date", "dateTime") {
        @Override
        @SuppressWarnings("unchecked")
        public Date validatedValue(String value, AbstractNoteClass clazz) {
            if (value == null) {
                if (clazz.getDefaultValue() != null) {
                    return asDate(clazz.getDefaultValue(), "unable to parse defaultValue as a date ");
                }
                else if (clazz.isRequired())
                    throw new TypeValidationException("value is required for this note type");
                return null;
            }
            // TODO should we support range for dates?
            return asDate(value, "unable to parse value as date ");
        }
    };

    private static final Map<String, TypeSafe> namesMap = Maps.newHashMap();
    static {
        for (TypeSafe type : TypeSafe.values()) {
            for (String alt : type.altNames) {
                namesMap.put(alt, type);
            }
        }
    }

    private final String[] altNames;

    private TypeSafe(String... altNames) {
        this.altNames = altNames;
    }

    public abstract <T> T validatedValue(String value, AbstractNoteClass clazz);

    public static TypeSafe from(String name) {
        return namesMap.get(name);
    }

    private static Number asNumber(String value, String message) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ignore) {}
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException willBeCaught) {
            throw new TypeValidationException(message + value);
        }
    }

    private static Double asDecimal(String value, String message) {
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException willBeCaught) {
            throw new TypeValidationException(message + value);
        }
    }

    private static Date asDate(String value, String message) {
        try {
            return DateUtils.parseSilent(value);
        }
        catch (RuntimeException e) {
            throw new TypeValidationException(message + value);
        }
    }

    public static final class TypeValidationException extends RuntimeException {
        private static final long serialVersionUID = 345166052435211623L;

        public TypeValidationException(String message) {
            super(message);
        }
    }
}