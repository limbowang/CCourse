package com.limbo.ccourse.ui.note;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.limbo.ccourse.persistence.db.dao.DaoMaster;
import com.limbo.ccourse.persistence.db.dao.DaoSession;
import com.limbo.ccourse.persistence.db.dao.NoteDao;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.utils.db.DbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NoteContent {

    /**
     * An array of sample (dummy) items.
     */
    private static List<Note> items = new ArrayList<Note>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    private static Map<Long, Note> itemMap = new HashMap<Long, Note>();

    public static List<Note> getItems() {
        return items;
    }

    public static void addItem(Note item) {
        items.add(item);
        itemMap.put(item.getId(), item);
    }

    public static Note getItem(Long id) {
        return itemMap.get(id);
    }

    public static void load(Activity activity, int n) {
        // insert db
        NoteDao noteDao = DbUtil.getReadableSession(activity).getNoteDao();
        items = noteDao.loadAll();
    }

}
