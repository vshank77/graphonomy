package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

public class Posting implements Fragment {

    private URI sourceDb;
    private String fieldName;
    private String hitCount;

    public Posting() {}

    public Posting(String sourceDb, String fieldName, String hitCount) {
        setSourceDb(sourceDb);
        setFieldName(fieldName);
        setHitCount(hitCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sourceDb == null) ? 0 : sourceDb.hashCode());
        result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
        result = prime * result + ((hitCount == null) ? 0 : hitCount.hashCode());
        return result;
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
        Posting other = (Posting) obj;
        if (sourceDb == null ? other.sourceDb != null : !sourceDb.equals(other.sourceDb))
            return false;
        if (fieldName == null ? other.fieldName != null : !fieldName.equals(other.fieldName))
            return false;
        if (hitCount == null ? other.hitCount != null : !hitCount.equals(other.hitCount))
            return false;
        return true;
    }

    public URI getSourceDb() {
        return sourceDb;
    }

    public Posting setSourceDb(String sourceDbStr) {
        return setSourceDb(URI.create(sourceDbStr));
    }

    public Posting setSourceDb(URI sourceDb) {
        this.sourceDb = checkNotNull(sourceDb);
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Posting setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getHitCount() {
        return hitCount;
    }

    public Posting setHitCount(String hitCount) {
        this.hitCount = checkNotNull(hitCount);
        return this;
    }
}
