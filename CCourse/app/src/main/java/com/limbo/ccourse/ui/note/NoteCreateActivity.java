package com.limbo.ccourse.ui.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.limbo.ccourse.R;
import com.limbo.ccourse.persistence.db.model.Note;
import com.limbo.ccourse.utils.ActivityCode;
import com.limbo.ccourse.utils.StringUtil;

import java.io.File;
import java.util.Date;

import static android.app.AlertDialog.Builder;
import static android.app.AlertDialog.OnClickListener;

public class NoteCreateActivity extends ActionBarActivity {

    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    private String mLastPhotoPath;

    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextTitle = (EditText) findViewById(R.id.edit_text_note_title);
        mEditTextContent = (EditText) findViewById(R.id.edit_text_note_content);

        long noteId = getIntent().getLongExtra("id", -1);
        mNote = null;
        if (noteId != -1) {
            mNote = NoteContent.getItem(noteId);
            mEditTextTitle.setText(mNote.getTitle());
            mEditTextContent.append(StringUtil.getSpannedFromString(mNote.getContent()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_create, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            alertWhenExit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_note_save) {
            String noteTitle = mEditTextTitle.getText().toString();
            Editable edit = mEditTextContent.getText();
            String noteDescription = edit.length() > 20 ? edit.toString().substring(0, 20) : edit.toString();
            String noteContent = Html.toHtml(mEditTextContent.getText());
            ImageSpan[] imageSpans = edit.getSpans(0, edit.length(), ImageSpan.class);
            String noteThumbnail = null;
            if (imageSpans.length != 0) {
                noteThumbnail = imageSpans[0].getSource().replace("file://", "");
            }

            if (mNote != null) {
                mNote.setTitle(noteTitle);
                mNote.setDescription(noteDescription);
                mNote.setThumbnail(noteThumbnail);
                mNote.setContent(noteContent);
                // update db
                NoteContent.updateItem(this, mNote);
            } else {
                mNote = new Note(null, noteTitle, noteDescription, noteThumbnail, noteContent, new Date(), null);
                // insert db
                NoteContent.addItem(this, mNote);
            }

            // return to last activity
            setResult(2);
            finish();
        } else if (id == R.id.action_note_add_photo) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            mLastPhotoPath = StringUtil.getPhotoPath();
            File file = new File(mLastPhotoPath);
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, ActivityCode.CAMERA_CAPTURE_START);
        } else if (id == android.R.id.home) {
            alertWhenExit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ActivityCode.CAMERA_CAPTURE_START:
                if (data == null) {
                    ImageSpan imageSpan = new ImageSpan(NoteCreateActivity.this,
                            Uri.fromFile(new File(mLastPhotoPath)));
                    SpannableString spannableString = new SpannableString(" ");
                    spannableString.setSpan(imageSpan, 0, spannableString.length(), spannableString.SPAN_MARK_MARK);
                    int index = mEditTextContent.getSelectionStart();
                    Editable edit = mEditTextContent.getEditableText();
                    if (index < 0 || index >= edit.length()) {
                        edit.append(spannableString);
                    } else {
                        edit.insert(index, spannableString);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void alertWhenExit() {
        new Builder(NoteCreateActivity.this)
                .setTitle("Warning")
                .setIcon(android.R.attr.alertDialogIcon)
                .setMessage(R.string.exit_waring)
                .setPositiveButton(R.string.answer_yes, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteCreateActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.answer_no, null)
                .create().show();
    }
}
