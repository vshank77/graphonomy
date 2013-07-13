package org.polyglotted.graphonomy.extsmartlogic;

import java.io.IOException;
import java.io.InputStream;

import org.polyglotted.graphonomy.model.MetaSpec;
import org.polyglotted.xpathstax.XPathStaxParser;

public class ThesaurusStructureConverter {

    public MetaSpec convert(InputStream input) throws IOException {
        XPathStaxParser parser = new XPathStaxParser();

        HandlerFactory handlerFactory = new HandlerFactory();
        parser.addHandler(handlerFactory.createAttributeConverter());
        parser.addHandler(handlerFactory.createChoiceConverter());
        parser.addHandler(handlerFactory.createNoteConverter());
        parser.addHandler(handlerFactory.createRelationshipConverter());
        parser.addHandler(handlerFactory.createClassConverter());
        parser.parse(input);

        MetaSpecGenerator generator = new MetaSpecGenerator(handlerFactory);
        generator.generateSpec();
        return generator.spec;
    }
}
