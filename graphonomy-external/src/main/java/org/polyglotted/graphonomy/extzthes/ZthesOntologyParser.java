package org.polyglotted.graphonomy.extzthes;
 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.polyglotted.xpathstax.XPathStaxParser;
import org.polyglotted.xpathstax.bind.NodeConverter;
 
public class ZthesOntologyParser {
 
    public static void main(String ar[]) {
        try {
            FileInputStream input = new FileInputStream("src/do_not_git_version/simple-ontology.xml");
 
            ZthesOntologyParser reader = new ZthesOntologyParser();
            reader.parse(input);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void parse(InputStream input) throws IOException {
        XPathStaxParser parser = new XPathStaxParser();
        final AtomicInteger counter = new AtomicInteger(); 
        parser.addHandler(new NodeConverter<TermWrapper>("/Zthes/term/*") {
            @Override
            public void process(TermWrapper wrapper) {
                counter.incrementAndGet();
            }
        });
        parser.parse(input);
        System.out.println(counter.get());
    }
}