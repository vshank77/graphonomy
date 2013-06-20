package org.polyglotted.graphonomy.domain;

public class DomainFailureException extends RuntimeException {

    private static final long serialVersionUID = 2761508912220152327L;

    public DomainFailureException() {}

    public DomainFailureException(String message) {
        super(message);
    }
}
