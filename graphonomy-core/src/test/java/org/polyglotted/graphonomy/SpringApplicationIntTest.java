package org.polyglotted.graphonomy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertTrue;
import static org.polyglotted.graphonomy.domain.AbstractDaoTest.asStream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.impl.util.FileUtils;
import org.polyglotted.graphonomy.api.Importer;
import org.polyglotted.graphonomy.domain.PageResult;
import org.polyglotted.graphonomy.model.MetaSpec;
import org.polyglotted.graphonomy.model.Term;
import org.polyglotted.graphonomy.terms.TermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Slf4j
@ContextConfiguration(locations = { "classpath:/spring-test/unit-test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpringApplicationIntTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        log.debug("Setting up test db location");
        String dbLocation = "target/graphonomydb";
        FileUtils.deleteRecursively(new File(dbLocation));
        System.out.println(new File(dbLocation).isDirectory());
        System.setProperty("graphonomy.neo4j.db.location", dbLocation);
    }

    @Autowired
    private TermDao termDao;

    @Autowired
    @Qualifier("graphonomy.MetaSpecImporter")
    private Importer<MetaSpec> metaSpecImporter;

    @Autowired
    @Qualifier("graphonomy.TermsImporter")
    private Importer<List<Term>> termsImporter;

    @Before
    public void initImports() throws IOException {
        metaSpecImporter.doImport(asStream("files/metaspec-simple.txt"));
        termsImporter.doImport(asStream("files/ontology-simple.txt"));
    }

    @Test
    public void testLoadAllTerms() {
        List<String> all = Lists.newArrayList();
        IndexHits<Node> result = termDao.loadAll(Arrays.asList("1562990748", "2069613425", "1551323960"));
        try {
            for (Node node : result) {
                all.add((String) node.getProperty("termName"));
            }
        }
        finally {
            result.close();
        }
        Collections.sort(all);
        assertThat(all, contains("Belgium", "France", "Norway"));
    }

    @Test
    public void testFindAllTerms() {
        List<String> all = Lists.newArrayList();
        IndexHits<Node> result = termDao.findAll();
        try {
            for (Node node : result) {
                all.add((String) node.getProperty("termName"));
            }
        }
        finally {
            result.close();
        }
        Collections.sort(all);
        assertThat(all, matchesAllTerms());
    }

    @Test
    public void testFindAllTermsByPage() {
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
                result = termDao.findAll(pageSize, ++start);
        }
        Collections.sort(all);
        assertThat(all, matchesAllTerms());
    }

    @Test
    public void testSearchTerms() {
        List<String> items = Lists.newArrayList();
        PageResult result = termDao.search("chi", 10);
        try {
            for (Node node : result) {
                assertTrue(result.currentScore() > 0);
                items.add((String) node.getProperty("termName"));
            }
        }
        finally {
            result.close();
        }
        Collections.sort(items);
        assertThat(items,
                contains("China", "Chinese (Simplified)", "Chinese (Traditional)", "Chinese, Wu", "Chinese, Xiang"));
    }

    @Test
    public void testSearchLanguageTerms() {
        List<String> items = Lists.newArrayList();
        PageResult result = termDao.search("chi", "language class", 10);
        try {
            for (Node node : result) {
                assertTrue(result.currentScore() > 0);
                items.add((String) node.getProperty("termName"));
            }
        }
        finally {
            result.close();
        }
        Collections.sort(items);
        assertThat(items, contains("Chinese (Simplified)", "Chinese (Traditional)", "Chinese, Wu", "Chinese, Xiang"));
    }

    private Matcher<Iterable<? extends String>> matchesAllTerms() {
        return Matchers.contains("Belgium", "China", "Chinese (Simplified)", "Chinese (Traditional)", "Chinese, Wu",
                "Chinese, Xiang", "Countries", "English", "France", "French", "German", "Germany", "Hong Kong",
                "India", "Norway", "Spain", "United Kingdom", "United States");
    }
}
