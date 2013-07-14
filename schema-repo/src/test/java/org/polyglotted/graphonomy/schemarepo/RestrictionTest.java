package org.polyglotted.graphonomy.schemarepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import mockit.Expectations;
import mockit.NonStrict;

import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.junit.Test;
import org.polyglotted.graphonomy.model.Range;
import org.polyglotted.graphonomy.model.TypeSafe;

public class RestrictionTest {

    @NonStrict
    private XSSimpleTypeDefinition simpleType;

    @Test
    public void testNoRange() {
        basicExpectations();
        assertNull(Restriction.parseFrom(simpleType, TypeSafe.str).getRange());
    }

    @Test
    public void testLength() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_LENGTH);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.str).getRange();
        assertEquals(25, res.getMin());
        assertEquals(25, res.getMax());
    }

    @Test
    public void testMinLength() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINLENGTH);
                returns("0");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.str).getRange();
        assertEquals(0, res.getMin());
        assertEquals(Integer.MAX_VALUE, res.getMax());
    }

    @Test
    public void testMaxLength() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXLENGTH);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.str).getRange();
        assertEquals(0, res.getMin());
        assertEquals(25, res.getMax());
    }

    @Test
    public void testMinExMaxEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
                returns("0");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.decimal).getRange();
        assertEquals(1, res.getMin());
        assertEquals(24, res.getMax());
    }

    @Test
    public void testMinExMaxIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
                returns("0");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.decimal).getRange();
        assertEquals(1, res.getMin());
        assertEquals(25, res.getMax());
    }

    @Test
    public void testMinInMaxEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MININCLUSIVE);
                returns("0");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.decimal).getRange();
        assertEquals(0, res.getMin());
        assertEquals(24, res.getMax());
    }

    @Test
    public void testMinInMaxIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MININCLUSIVE);
                returns("0");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
                returns("25");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.decimal).getRange();
        assertEquals(0, res.getMin());
        assertEquals(25, res.getMax());
    }

    @Test
    public void testDateMinExMaxEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
                returns("2013-06-01");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
                returns("2013-12-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800001L, res.getMin());
        assertEquals(1385855999999L, res.getMax());
    }

    @Test
    public void testDateMinInMaxEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MININCLUSIVE);
                returns("2013-06-01");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
                returns("2013-12-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800000L, res.getMin());
        assertEquals(1385855999999L, res.getMax());
    }

    @Test
    public void testDateMinExMaxIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
                returns("2013-06-01");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
                returns("2013-12-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800001L, res.getMin());
        assertEquals(1385856000000L, res.getMax());
    }

    @Test
    public void testDateMinInMaxIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MININCLUSIVE);
                returns("2013-06-01");
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
                returns("2013-12-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800000L, res.getMin());
        assertEquals(1385856000000L, res.getMax());
    }

    @Test
    public void testDateMinEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MINEXCLUSIVE);
                returns("2013-06-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800001L, res.getMin());
        assertEquals(32503680000000L, res.getMax());
    }

    @Test
    public void testDateMinIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MININCLUSIVE);
                returns("2013-06-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(1370044800000L, res.getMin());
        assertEquals(32503680000000L, res.getMax());
    }

    @Test
    public void testDateMaxEx() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE);
                returns("2013-06-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(-2208988800000L, res.getMin());
        assertEquals(1370044799999L, res.getMax());
    }

    @Test
    public void testDateMaxIn() {
        basicExpectations();
        new Expectations() {
            {
                simpleType.getLexicalFacetValue(XSSimpleTypeDefinition.FACET_MAXINCLUSIVE);
                returns("2013-06-01");
            }
        };
        Range res = Restriction.parseFrom(simpleType, TypeSafe.date).getRange();
        assertEquals(-2208988800000L, res.getMin());
        assertEquals(1370044800000L, res.getMax());
    }

    private void basicExpectations() {
        new Expectations() {
            {
                simpleType.getLexicalEnumeration();
                returns(new StringListImpl(null));
                simpleType.getLexicalPattern();
                returns(new StringListImpl(null));
            }
        };
    }
}
