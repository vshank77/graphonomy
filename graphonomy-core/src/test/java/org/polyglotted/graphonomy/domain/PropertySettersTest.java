package org.polyglotted.graphonomy.domain;

import java.util.Date;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;
import org.neo4j.graphdb.PropertyContainer;
import org.polyglotted.graphonomy.model.GraphProperty;
import org.polyglotted.graphonomy.model.GraphProperty.PropertyType;

import com.google.common.collect.Lists;

public class PropertySettersTest extends PropertySetters {

    @Test
    public void testDater(@Mocked final PropertyContainer node) {
        Dater dater = new Dater(new Date());
        new Expectations() {
            {
                node.setProperty("classDate", withNotNull());
            }
        };
        new PropertyUpdater().reflectUpdate(node, dater);
    }

    @Test
    public void testLister() {
        Lister lister = new Lister();
        new PropertyUpdater().reflectUpdate(null, lister);
    }

    @Test(expected = DomainFailureException.class)
    public void testListerFail() {
        Lister lister = new Lister().add(new Date());
        new PropertyUpdater().reflectUpdate(null, lister);
    }

    private static class Dater {
        @GraphProperty(PropertyType.DATE)
        Date classDate;

        public Dater(Date classDate) {
            this.classDate = classDate;
        }
    }

    private static class Lister {
        @GraphProperty(PropertyType.LIST)
        List<Date> classDates = Lists.newArrayList();

        public Lister add(Date classDate) {
            classDates.add(classDate);
            return this;
        }
    }
}
