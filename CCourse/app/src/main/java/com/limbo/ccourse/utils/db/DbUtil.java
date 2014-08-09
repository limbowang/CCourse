package com.limbo.ccourse.utils.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.limbo.ccourse.persistence.db.dao.DaoMaster;
import com.limbo.ccourse.persistence.db.dao.DaoSession;

/**
 * Created by Limbo on 2014/8/3.
 */
public class DbUtil {

    static public DaoSession getReadableSession(Activity activity) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "notes-db", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        return daoSession;
    }

    static public DaoSession getWritableSession(Activity activity) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        return daoSession;
    }
}
