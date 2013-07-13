package org.polyglotted.graphonomy.schemarepo;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.polyglotted.graphonomy.model.Range;
import org.polyglotted.graphonomy.util.GenericUtils;

@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
class Restriction {
    private static final int DUMMY = Integer.MIN_VALUE;

    private String pattern;
    private List<String> enumeration = null;
    private int fractionDigits = DUMMY;
    private int totalDigits = DUMMY;
    private int length = DUMMY;
    private int maxExclusive = DUMMY;
    private int maxInclusive = DUMMY;
    private int minExclusive = DUMMY;
    private int minInclusive = DUMMY;
    private int maxLength = DUMMY;
    private int minLength = DUMMY;

    public static Restriction parseFrom(XSSimpleTypeDefinition simpleType) {
        Restriction result = new Restriction();
        result.fractionDigits = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_FRACTIONDIGITS);
        result.length = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_LENGTH);
        result.maxExclusive = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
        result.maxInclusive = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
        result.maxLength = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MAXLENGTH);
        result.minExclusive = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
        result.minInclusive = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MININCLUSIVE);
        result.minLength = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_MINLENGTH);
        result.totalDigits = intValueOf(simpleType, XSSimpleTypeDefinition.FACET_TOTALDIGITS);
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

        return null;
    }

    private Range rangeFromLength() {
        int min = isAvailable(minLength) ? minLength : 0;
        int max = isAvailable(maxLength) ? maxLength : Integer.MAX_VALUE;
        return new Range(min, max);
    }

    static boolean isOneAvailable(int val1, int val2) {
        return isAvailable(val1) || isAvailable(val2);
    }

    static boolean isBothAvailable(int val1, int val2) {
        return isAvailable(val1) && isAvailable(val2);
    }

    static boolean isAvailable(int value) {
        return value != DUMMY;
    }

    static int intValueOf(XSSimpleTypeDefinition simpleType, short facetName) {
        String value = simpleType.getLexicalFacetValue(facetName);
        if (value != null) {
            Double dbl = Double.parseDouble(value);
            return dbl.intValue();
        }
        return DUMMY;
    }
}