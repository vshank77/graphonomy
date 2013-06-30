package org.polyglotted.graphonomy.domain;

import static org.apache.lucene.util.Version.LUCENE_36;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Fieldable;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

public final class SimpleSchemaAnalyzer extends Analyzer {

    private static final Splitter EQSPLITTER = Splitter.on('=').trimResults();

    public enum TokenizerType {
        Standard {
            @Override
            public Analyzer create() {
                return new StandardAnalyzer(LUCENE_36);
            }
        },
        Simple {
            @Override
            public Analyzer create() {
                return new SimpleAnalyzer(LUCENE_36);
            }
        },
        Keyword {
            @Override
            public Analyzer create() {
                return new KeywordAnalyzer();
            }
        };
        public abstract Analyzer create();
    }

    private Analyzer defaultAnalyzer = TokenizerType.Keyword.create();
    private Map<String, Analyzer> analyzerMap = Maps.newHashMap();

    public SimpleSchemaAnalyzer() throws IOException {
        URL config = getClass().getClassLoader().getResource("properties/search-config.properties");
        List<String> allLines = Resources.readLines(config, Charsets.UTF_8);
        for (String line : allLines) {
            Iterator<String> values = EQSPLITTER.split(line).iterator();
            analyzerMap.put(values.next(), TokenizerType.valueOf(values.next()).create());
        }
    }

    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        Analyzer analyzer = analyzerMap.get(fieldName);
        if (analyzer == null) {
            analyzer = defaultAnalyzer;
        }

        return analyzer.tokenStream(fieldName, reader);
    }

    @Override
    public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
        Analyzer analyzer = analyzerMap.get(fieldName);
        if (analyzer == null)
            analyzer = defaultAnalyzer;

        return analyzer.reusableTokenStream(fieldName, reader);
    }

    @Override
    public int getPositionIncrementGap(String fieldName) {
        Analyzer analyzer = analyzerMap.get(fieldName);
        if (analyzer == null)
            analyzer = defaultAnalyzer;
        return analyzer.getPositionIncrementGap(fieldName);
    }

    @Override
    public int getOffsetGap(Fieldable field) {
        Analyzer analyzer = analyzerMap.get(field.name());
        if (analyzer == null)
            analyzer = defaultAnalyzer;
        return analyzer.getOffsetGap(field);
    }

    @Override
    public String toString() {
        return "SimpleSchemaAnalyzer(" + analyzerMap + ", default=" + defaultAnalyzer + ")";
    }
}
