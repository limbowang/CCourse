package com.limbo.ccourse.ui.note;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.limbo.ccourse.R;
import com.limbo.ccourse.persistence.db.dao.NoteDao;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.ui.ActivityCode;
import com.limbo.ccourse.ui.camera.CameraActivity;
import com.limbo.ccourse.utils.db.DbUtil;

import java.util.Date;

import static com.limbo.ccourse.ui.ActivityCode.*;

public class NoteCreateActivity extends ActionBarActivity {

    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        mEditTextTitle = (EditText) findViewById(R.id.edit_text_note_title);
        mEditTextContent = (EditText) findViewById(R.id.edit_text_note_content);
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
            String noteTitle = mEditTextTitle.getText().toString();
            String noteContent = mEditTextContent.getText().toString();
            Note note = new Note(null, noteTitle, noteContent, new Date(), null);

            // insert db
            NoteDao noteDao = DbUtil.getWritableSession(this).getNoteDao();
            noteDao.insert(note);
            NoteContent.addItem(note);

            // return to last activity
            setResult(2);
            finish();
        } else if (id == R.id.action_note_add_photo) {
            Intent intent = new Intent(NoteCreateActivity.this, CameraActivity.class);
            startActivityForResult(intent, CAMERA_CAPTURE_START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CAMERA_CAPTURE_END:
                byte[] picture = data.getByteArrayExtra("picture");
                ImageSpan imageSpan = new ImageSpan(NoteCreateActivity.this,
                        BitmapFactory.decodeByteArray(picture, 0, picture.length));
                SpannableString spannableString = new SpannableString("picture");
                spannableString.setSpan(imageSpan, 0, spannableString.length(), spannableString.SPAN_MARK_MARK);
                mEditTextContent.append(spannableString);
                break;
            default:
                break;
        }
    }
}
