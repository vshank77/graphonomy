package org.polyglotted.graphonomy.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Posting implements Fragment<Posting> {

    private URI sourceDb;
    private String fieldName;
    private String hitCount;

    public static Posting from(String sourceDb, String fieldName, String hitCount) {
        return new Posting(URI.create(sourceDb), fieldName, hitCount);
    }

    @Override
    public Posting validate() {
        checkNotNull(sourceDb);
        checkNotNull(hitCount);
        return this;
    }
}
