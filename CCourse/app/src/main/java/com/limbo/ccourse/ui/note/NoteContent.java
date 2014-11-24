package com.limbo.ccourse.ui.note;

import android.app.Activity;

import com.limbo.ccourse.persistence.db.dao.NoteDao;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.utils.DbUtil;

import java.util.ArrayList;
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

    public static void addItem(Activity activity, Note note) {
        NoteDao noteDao = DbUtil.getWritableSession(activity).getNoteDao();
        noteDao.insert(note);
        items.add(note);
        itemMap.put(note.getId(), note);
    }

    public static List<Note> getItems() {
        return items;
    }

    public static Note getItem(Long id) {
        return itemMap.get(id);
    }

    public static void updateItem(Activity activity, Note note) {
        NoteDao noteDao = DbUtil.getWritableSession(activity).getNoteDao();
        noteDao.update(note);
        itemMap.put(note.getId(), note);
    }

    public static void deleteItem(Activity activity, long id) {
        NoteDao noteDao = DbUtil.getWritableSession(activity).getNoteDao();
        noteDao.delete(itemMap.get(id));
        items.remove(itemMap.get(id));
        itemMap.remove(id);
    }

    public static void load(Activity activity, int n) {
        NoteDao noteDao = DbUtil.getReadableSession(activity).getNoteDao();
        items = noteDao.loadAll();
        for(Note item : items) {
            itemMap.put(item.getId(), item);
        }
    }

}
