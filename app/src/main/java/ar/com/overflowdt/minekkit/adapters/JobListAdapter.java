package ar.com.overflowdt.minekkit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.activities.ProfileActivity;
import ar.com.overflowdt.minekkit.models.Job;

/**
 * Created by Fede on 01/03/14.
 */
public class JobListAdapter extends BaseAdapter {
    public List<Job> listJobs;
    ProfileActivity activity;

    @Override
    public int getCount() {

        return listJobs.size();
    }

    @Override
    public Object getItem(int arg0) {

        return listJobs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item_job, arg2, false);
        }
        TextView name = (TextView) arg1.findViewById(R.id.itemJob_name);
        ProgressBar bar = (ProgressBar) arg1.findViewById(R.id.itemJob_pgrBar);
        TextView level = (TextView) arg1.findViewById(R.id.itemJob_upper_right);
        TextView xp = (TextView) arg1.findViewById(R.id.itemJob_bottom_right);

        Job job = listJobs.get(arg0);

        name.setText(job.name);
        bar.setProgress(job.getProgessPercent());

        level.setText("Lv. " + String.valueOf(job.level));
        xp.setText(String.valueOf(job.xp) + "/" + String.valueOf(job.getXPMax()));

        return arg1;
    }
}
