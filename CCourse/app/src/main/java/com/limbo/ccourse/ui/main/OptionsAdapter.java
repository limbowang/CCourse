package com.limbo.ccourse.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.limbo.ccourse.R;
import com.limbo.ccourse.ui.course.CourseFragment;
import com.limbo.ccourse.ui.note.NoteFragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by Limbo on 2015/4/11.
 */
public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionsHolder> {

    private List<Option> data = Collections.emptyList();
    private final LayoutInflater inflater;
    private Context context;

    public OptionsAdapter(Context context, List<Option> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public OptionsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.option_row, viewGroup, false);
        OptionsHolder optionsHolder = new OptionsHolder(view);
        return optionsHolder;
    }

    @Override
    public void onBindViewHolder(OptionsHolder optionsHolder, int pos) {
        Option option = data.get(pos);
        optionsHolder.title.setText(option.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OptionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        public OptionsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.option_title);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getPosition();
            Fragment fragment = null;
            FragmentActivity activity = (FragmentActivity) context;
            switch (pos) {
                case 0:
                    fragment = new NoteFragment();
                    break;
                case 1:
                    fragment = new CourseFragment();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
            activity.setTitle(data.get(pos).title);
            ((DrawerLayout) activity.findViewById(R.id.drawer_layout)).closeDrawers();
        }
    }
}
