package org.polyglotted.graphonomy.schemarepo;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.polyglotted.graphonomy.model.Range;
import org.polyglotted.graphonomy.model.TypeSafe;
import org.polyglotted.graphonomy.util.DateUtils;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
class Restriction {
    private static final int DUMMY = Integer.MIN_VALUE;
    private static final long Y19X = -2208988800000L;
    private static final long Y3K = 32503680000000L;

    private boolean dateType;
    private String pattern;
    private List<String> enumeration = null;
    private long fractionDigits = DUMMY;
    private long totalDigits = DUMMY;
    private long length = DUMMY;
    private long maxExclusive = DUMMY;
    private long maxInclusive = DUMMY;
    private long minExclusive = DUMMY;
    private long minInclusive = DUMMY;
    private long maxLength = DUMMY;
    private long minLength = DUMMY;

    public static Restriction parseFrom(XSSimpleTypeDefinition simpleType, TypeSafe typeSafe) {
        Restriction result = new Restriction();
        result.dateType = (typeSafe == TypeSafe.date);
        result.fractionDigits = longValueOf(simpleType, XSSimpleTypeDefinition.FACET_FRACTIONDIGITS);
        result.totalDigits = longValueOf(simpleType, XSSimpleTypeDefinition.FACET_TOTALDIGITS);
        result.length = longValueOf(simpleType, XSSimpleTypeDefinition.FACET_LENGTH);
        result.maxLength = longValueOf(simpleType, XSSimpleTypeDefinition.FACET_MAXLENGTH);
        result.minLength = longValueOf(simpleType, XSSimpleTypeDefinition.FACET_MINLENGTH);
        result.maxExclusive = dateOrLongValueOf(simpleType, result.dateType, XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
        result.maxInclusive = dateOrLongValueOf(simpleType, result.dateType, XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
        result.minExclusive = dateOrLongValueOf(simpleType, result.dateType, XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
        result.minInclusive = dateOrLongValueOf(simpleType, result.dateType, XSSimpleTypeDefinition.FACET_MININCLUSIVE);
        for (Object obj : simpleType.getLexicalEnumeration()) {
            result.addEnumeration((String) obj);
        }
        if (simpleType.getLexicalPattern().size() > 0) {
            result.pattern = simpleType.getLexicalPattern().item(0);
        }
        return result;
    }

    public String getPattern() {
        if (isBothAvailable(fractionDigits, totalDigits))
            return "[\\-+]?[0-9]{" + (totalDigits - fractionDigits) + "}.[0-9]{" + fractionDigits + "}";
        return pattern;
    }

    public List<String> getEnumeration() {
        return GenericUtils.emptyOrList(enumeration);
    }

    public void addEnumeration(String enumVal) {
        enumeration = GenericUtils.addOrCreateList(enumeration, enumVal);
    }

    public Range getRange() {
        if (isAvailable(length)) {
            return new Range(length, length);
        }
        else if (isOneAvailable(minLength, maxLength)) {
            return rangeFromLength();
        }

        if (isBothAvailable(minExclusive, maxExclusive)) {
            return new Range(minExclusive + 1, maxExclusive - 1);
        }
        else if (isBothAvailable(minExclusive, maxInclusive)) {
            return new Range(minExclusive + 1, maxInclusive);
        }
        else if (isBothAvailable(minInclusive, maxExclusive)) {
            return new Range(minInclusive, maxExclusive - 1);
        }
        else if (isBothAvailable(minInclusive, maxInclusive)) {
            return new Range(minInclusive, maxInclusive);
        }

        if (dateType) {
            if (isAvailable(minExclusive)) {
                return new Range(minExclusive + 1, Y3K);
            }
            else if (isAvailable(minInclusive)) {
                return new Range(minInclusive, Y3K);
            }
            else if (isAvailable(maxExclusive)) {
                return new Range(Y19X, maxExclusive - 1);
            }
            else if (isAvailable(maxInclusive)) {
                return new Range(Y19X, maxInclusive);
            }
        }

        return null;
    }

    private Range rangeFromLength() {
        long min = isAvailable(minLength) ? minLength : 0;
        long max = isAvailable(maxLength) ? maxLength : Integer.MAX_VALUE;
        return new Range(min, max);
    }

    static boolean isOneAvailable(long val1, long val2) {
        return isAvailable(val1) || isAvailable(val2);
    }

    static boolean isBothAvailable(long val1, long val2) {
        return isAvailable(val1) && isAvailable(val2);
    }

    static boolean isAvailable(long value) {
        return value != DUMMY;
    }

    static long dateOrLongValueOf(XSSimpleTypeDefinition simpleType, boolean dateType, short facetName) {
        if (dateType) {
            String value = simpleType.getLexicalFacetValue(facetName);
            return (value != null) ? DateUtils.parseTime(value) : DUMMY;
        }
        return longValueOf(simpleType, facetName);
    }

    static long longValueOf(XSSimpleTypeDefinition simpleType, short facetName) {
        String value = simpleType.getLexicalFacetValue(facetName);
        return (value != null) ? Double.valueOf(value).longValue() : DUMMY;
    }
}