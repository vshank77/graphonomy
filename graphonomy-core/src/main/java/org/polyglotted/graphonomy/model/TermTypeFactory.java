package org.polyglotted.graphonomy.model;

public class TermTypeFactory {

    public static TermType with(String name) {
        try {
            return StandardTermType.valueOf(name);
        }
        catch (Exception ex) {
            return new DynamicTermType(name);
        }
    }

    public static class DynamicTermType implements TermType {

        private final String name;

        private DynamicTermType(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }

    public enum StandardTermType implements TermType {
        PT("Preferred Term"), ND("Non Descriptor"), NL("Node Label");

        public final String description;

        StandardTermType(String desc) {
            this.description = desc;
        }
    }
}