package org.polyglotted.graphonomy.domain;

import static org.polyglotted.graphonomy.model.RelationClass.BaseType.hierarchy;
import static org.polyglotted.graphonomy.model.RelationClass.BaseType.related;
import static org.polyglotted.graphonomy.model.RelationClass.BaseType.usefor;

import org.neo4j.graphdb.Transaction;
import org.polyglotted.graphonomy.model.NoteClass;
import org.polyglotted.graphonomy.model.RelationClass;
import org.polyglotted.graphonomy.model.TermClass;
import org.polyglotted.graphonomy.model.TypeSafe;

public final class DatabaseDefaults {

    public static final NoteClass SCOPE = (NoteClass) new NoteClass("Scope Note", "Scope Note", TypeSafe.str).setRange(
            0, 4000).setPattern(".*");

    public static final RelationClass BT = new RelationClass("BT", hierarchy, "Broader Term").setExtended(false);
    public static final RelationClass NT = new RelationClass("NT", hierarchy, "Narrower Term").setExtended(false);
    public static final RelationClass RT = new RelationClass("RT", related, "Related To").setExtended(false);
    public static final RelationClass USE = new RelationClass("USE", usefor, "Use").setExtended(false);
    public static final RelationClass UF = new RelationClass("UF", usefor, "Use For").setExtended(false);

    public static final TermClass ROOTS = new TermClass("Roots Class");

    static void saveAll(GraphonomyDatabase database) {
        Transaction tx = database.graphDb.beginTx();
        try {
            database.saveNode(SCOPE);
            database.saveNode(BT);
            database.saveNode(NT);
            database.saveNode(RT);
            database.saveNode(USE);
            database.saveNode(UF);
            database.saveNode(ROOTS);
            tx.success();
        }
        catch (Exception ex) {
            tx.failure();
            throw new RuntimeException(ex);
        }
        finally {
            tx.finish();
        }
    }

    private DatabaseDefaults() {}
}
