package ar.com.overflowdt.minekkit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.activities.AllPmsActivity;
import ar.com.overflowdt.minekkit.models.PM;
import ar.com.overflowdt.minekkit.views.RelativeTimeTextView;

/**
 * Created by Fede on 01/03/14.
 */
public class PmListAdapter extends BaseAdapter {
    public List<PM> packPms;
    public AllPmsActivity activity;

    @Override
    public int getCount() {

        return packPms.size();
    }

    @Override
    public Object getItem(int arg0) {

        return packPms.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item, arg2, false);
        }
        TextView title = (TextView) arg1.findViewById(R.id.name);
        TextView from = (TextView) arg1.findViewById(R.id.costo);
        TextView idpm = (TextView) arg1.findViewById(R.id.pid);
        RelativeTimeTextView date = (RelativeTimeTextView) arg1.findViewById(R.id.list_item_bottom_right);
        ImageView logo = (ImageView) arg1.findViewById(R.id.logo);
        PM p = packPms.get(arg0);

        title.setText(p.titulo);

        String t = "De: ";
        t = t.concat(p.from);
        from.setText(t);

        idpm.setText(String.valueOf(p.idpm));
        //DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM");
        date.setReferenceTime((p.date) * 1000);
        if (p.read == 1) {
            logo.setImageDrawable(arg1.getResources().getDrawable(R.drawable.mail_open));
            title.setTypeface(null, Typeface.NORMAL);
            logo.setColorFilter(Color.argb(180, 255, 255, 255));
        } else {
            logo.setImageDrawable(arg1.getResources().getDrawable(R.drawable.mail_close));
            title.setTypeface(null, Typeface.BOLD);
            logo.setColorFilter(Color.argb(240, 255, 255, 255));
        }


        return arg1;
    }
}
