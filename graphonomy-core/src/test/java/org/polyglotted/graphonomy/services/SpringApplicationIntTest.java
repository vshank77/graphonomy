package org.polyglotted.graphonomy.services;

import static org.polyglotted.graphonomy.dao.AbstractDaoTest.asStream;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.kernel.impl.util.FileUtils;
import org.polyglotted.graphonomy.imports.MetaSpecImporter;
import org.polyglotted.graphonomy.imports.TermsImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:/spring-test/unit-test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringApplicationIntTest {

    protected static final Logger logger = LoggerFactory.getLogger(SpringApplicationIntTest.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.debug("Setting up test db location");
        String dbLocation = "target/graphonomydb";
        FileUtils.deleteRecursively(new File(dbLocation));
        System.setProperty("graphonomy.neo4j.db.location", dbLocation);
    }

    @Autowired
    private MetaSpecImporter metaSpecImporter;

    @Autowired
    private TermsImporter termsImporter;
    
    @Test
    public void test() throws IOException {
        metaSpecImporter.importSpec(asStream("files/metaspec-simple.txt"));
        termsImporter.importTerms(asStream("files/ontology-simple.txt"));
    }
}
