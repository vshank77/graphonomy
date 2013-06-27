package org.polyglotted.graphonomy.exports;

public class JsonException extends RuntimeException {

    private static final long serialVersionUID = -6284718432043315190L;

    public JsonException(String arg0) {
        super(arg0);
    }

    public JsonException(Throwable arg0) {
        super(arg0);
    }

    public JsonException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
