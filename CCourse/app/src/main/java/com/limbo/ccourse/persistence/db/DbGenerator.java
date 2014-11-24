package com.limbo.ccourse.persistence.db;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * This is db generator file to generate the dao and operation classes automatically.
 *
 * Created by Limbo on 2014/8/3.
 */
public class DbGenerator {

    private Schema schema = null;

    public static void main(String[] args) throws Exception {
        DbGenerator dbGenerator = new DbGenerator();
        String pathDao = "app/src/main/java";

        dbGenerator.generateNote();

        new DaoGenerator().generateAll(dbGenerator.getSchema(), pathDao);
    }

    public DbGenerator() {
        schema = new Schema(1, "com.limbo.ccourse.persistence.db.model");
        schema.setDefaultJavaPackageTest("com.limbo.ccourse.daotest");
        schema.setDefaultJavaPackageDao("com.limbo.ccourse.persistence.db.dao");
        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();
    }

    public Schema getSchema() {
        return schema;
    }

    public void generateNote() {
        Entity entityNote = schema.addEntity("Note");

        entityNote.addIdProperty();
        entityNote.addStringProperty("title").notNull();
        entityNote.addStringProperty("description");
        entityNote.addStringProperty("thumbnail");
        entityNote.addStringProperty("content");
        entityNote.addDateProperty("create_at").notNull();
        entityNote.addDateProperty("update_at");
    }
}
