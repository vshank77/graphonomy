package org.polyglotted.graphonomy.api;

import java.io.IOException;
import java.io.InputStream;

public interface Importer<T> {

    public T doImport(InputStream is) throws IOException;
}
