package com.limbo.ccourse.ui.note;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.limbo.ccourse.R;
import com.limbo.ccourse.persistence.db.dao.DaoMaster;
import com.limbo.ccourse.persistence.db.dao.DaoSession;
import com.limbo.ccourse.persistence.db.dao.NoteDao;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.utils.db.DbUtil;

import org.w3c.dom.Text;

import java.util.Date;

public class NoteCreateActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_note_save) {
            EditText etContent = (EditText) findViewById(R.id.edit_text_note_content);
            String noteContent = etContent.getText().toString();
            Note note = new Note(null, noteContent, new Date(), null);

            // insert db
            NoteDao noteDao = DbUtil.getWritableSession(this).getNoteDao();
            noteDao.insert(note);
            NoteContent.addItem(note);

            // return to last activity
            setResult(2);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
