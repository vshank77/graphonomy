package org.polyglotted.graphonomy.domain;

import org.neo4j.graphdb.Transaction;
import org.polyglotted.graphonomy.model.Defaults;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;

abstract class DatabaseDefaults {

    public static void saveAll(GraphonomyDatabase database) {
        Transaction tx = database.graphDb.beginTx();
        try {
            for (NoteClass noteClass : Defaults.getAllNotes()) {
                database.saveNode(noteClass);
            }
            for (RelationClass relationClass : Defaults.getAllRelations()) {
                database.saveNode(relationClass);
            }
            database.saveNode(Defaults.ROOTS);
            database.saveNode(Defaults.ENTITIES);
            tx.success();
        }
        catch (Exception ex) {
            tx.failure();
            throw new DbException(ex);
        }
        finally {
            tx.finish();
        }
    }

    public static class DbException extends RuntimeException {
        private static final long serialVersionUID = -137850585478729566L;

        public DbException(Throwable cause) {
            super(cause);
        }
    }
}
