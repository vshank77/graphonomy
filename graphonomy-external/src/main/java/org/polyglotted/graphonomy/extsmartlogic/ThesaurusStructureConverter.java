package org.polyglotted.graphonomy.extsmartlogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.polyglotted.graphonomy.util.JsonUtils;
import org.polyglotted.xpathstax.XPathStaxParser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ThesaurusStructureConverter {

    public static void main(String ar[]) {
        try {
            FileInputStream input = new FileInputStream("src/do_not_git_version/simple-dd.xml");
            ThesaurusStructureConverter converter = new ThesaurusStructureConverter();
            converter.convert(input);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void convert(InputStream input) throws IOException {
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

        Files.write(JsonUtils.asPrettyJson(generator.spec), new File("src/do_not_git_version/metaspec-simple.txt"), Charsets.UTF_8);
    }
}
