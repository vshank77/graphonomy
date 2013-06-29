package org.polyglotted.graphonomy.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.polyglotted.graphonomy.dao.AbstractDaoTest.asStream;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.impl.util.FileUtils;
import org.polyglotted.graphonomy.dao.TermDao;
import org.polyglotted.graphonomy.domain.PageResult;
import org.polyglotted.graphonomy.imports.MetaSpecImporter;
import org.polyglotted.graphonomy.imports.TermsImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@ContextConfiguration(locations = { "classpath:/spring-test/unit-test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
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

    @Autowired
    private TermDao termDao;

    @Before
    public void initImports() throws IOException {
        metaSpecImporter.importSpec(asStream("files/metaspec-simple.txt"));
        termsImporter.importTerms(asStream("files/ontology-simple.txt"));
    }

    @Test
    public void testFindAllTerms() {
        List<String> all = Lists.newArrayList();
        final int pageSize = 6;
        PageResult result = termDao.findAll(pageSize, 0);
        int totalSize = result.size(), index = 0, start = 0;
        while (index < totalSize) {
            try {
                for (Node node : result) {
                    all.add((String) node.getProperty("termName"));
                    index++;
                }
            }
            finally {
                result.close();
            }
            if (index < totalSize)
                result = termDao.findAll(pageSize, start++);
        }
        Collections.sort(all);
        assertThat(all, Matchers.contains("Belgium", "China", "Chinese (Simplified)", "Chinese (Traditional)",
                "Chinese, Wu", "Chinese, Xiang", "Countries", "English", "France", "French", "German", "Germany",
                "Hong Kong", "India", "Norway", "Spain", "United Kingdom", "United States"));
    }
}
