package com.limbo.ccourse.ui.note;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.limbo.ccourse.R;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.utils.StringUtil;

import org.w3c.dom.Text;

import static android.text.Html.ImageGetter;

public class NoteViewActivity extends ActionBarActivity {

    private TextView mTextViewNoteTitle;
    private TextView mTextViewNoteContent;
    private long mNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        mTextViewNoteTitle = (TextView) findViewById(R.id.text_view_note_title);
        mTextViewNoteContent = (TextView) findViewById(R.id.text_view_note_content);
        mNoteId = getIntent().getLongExtra("id", -1);
        if (mNoteId != -1) {
            Note note = NoteContent.getItem(mNoteId);
            mTextViewNoteTitle.setText(note.getTitle());
            mTextViewNoteContent.append(StringUtil.getSpannedFromString(note.getContent()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(NoteViewActivity.this, NoteCreateActivity.class);
            intent.putExtra("id", mNoteId);
            startActivityForResult(intent, 3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 2) {
            Note note = NoteContent.getItem(mNoteId);
            mTextViewNoteTitle.setText(note.getTitle());
            mTextViewNoteContent.setText("");
            mTextViewNoteContent.append(StringUtil.getSpannedFromString(note.getContent()));
        }

    }
}
