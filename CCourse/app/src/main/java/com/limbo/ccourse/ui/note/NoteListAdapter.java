package com.limbo.ccourse.ui.note;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.limbo.ccourse.R;
import com.limbo.ccourse.persistence.db.model.Note;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limbo on 2014/8/3.
 */
public class NoteListAdapter extends BaseAdapter {

    private List<Note> mNoteList;
    private Context mContext;

    public NoteListAdapter(List<Note> noteList, Context context) {
        mNoteList = noteList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mNoteList.size();
    }

    @Override
    public Note getItem(int position) {
        return mNoteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mNoteList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteListItem noteListItem;

        if (convertView == null) {
            noteListItem = new NoteListItem();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_note_list_item, null);
            noteListItem.mTextViewTitle = (TextView) convertView.findViewById(R.id.text_view_note_item_title);
            noteListItem.mTextViewContent = (TextView) convertView.findViewById(R.id.text_view_note_item_content);
            noteListItem.mImageView = (ImageView) convertView.findViewById(R.id.image_thumbnail);
            noteListItem.mTextViewTitle.setText(mNoteList.get(position).getTitle());
            noteListItem.mTextViewContent.setText(mNoteList.get(position).getDescription());
            String thumbnail = mNoteList.get(position).getThumbnail();
            if (thumbnail != null && new File(thumbnail).isFile()) {
                noteListItem.mImageView.setImageDrawable(Drawable.createFromPath(thumbnail));
            }
            convertView.setTag(noteListItem);
        } else {
            noteListItem = (NoteListItem) convertView.getTag();
            noteListItem.mTextViewTitle.setText(mNoteList.get(position).getTitle());
            noteListItem.mTextViewContent.setText(mNoteList.get(position).getDescription());
            String thumbnail = mNoteList.get(position).getThumbnail();
            if (thumbnail != null && new File(thumbnail).isFile()) {
                noteListItem.mImageView.setImageDrawable(Drawable.createFromPath(thumbnail));
            }
        }

        return convertView;
    }

    class NoteListItem {
        public TextView mTextViewTitle;
        public TextView mTextViewContent;
        public ImageView mImageView;
    }
}
