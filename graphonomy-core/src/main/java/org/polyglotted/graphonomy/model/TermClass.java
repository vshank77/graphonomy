package org.polyglotted.graphonomy.model;

public class TermClass {

    private long id = -1;
    private String className;

    public TermClass() {}

    public TermClass(String className) {
        setClassName(className);
    }

    @Override
    public int hashCode() {
        return 17 * ((className == null) ? 0 : className.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TermClass other = (TermClass) obj;
        return (className == null) ? (other.className == null) : (className.equals(other.className));
    }

    public long getId() {
        return id;
    }

    public TermClass setId(long id) {
        this.id = id;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public TermClass setClassName(String className) {
        this.className = className;
        return this;
    }
}
