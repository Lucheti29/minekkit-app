package ar.com.overflowdt.minekkit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.activities.OnlineListActivity;
import ar.com.overflowdt.minekkit.fragments.PlayerOptionsDialog;
import ar.com.overflowdt.minekkit.interfaces.ImageLoadable;

/**
 * Created by Fede on 01/03/14.
 */
public class OnlineListAdapter extends BaseAdapter {

    public static class Player implements ImageLoadable {
        public String name;
        public Bitmap face;
        public String urlImage;

        @Override
        public Bitmap getImage() {
            return face;
        }

        @Override
        public void setImage(Bitmap image) {
            face = image;
        }
    }

    public List<Player> playersOn;
    public OnlineListActivity activity;

    @Override
    public int getCount() {

        return playersOn.size();
    }

    @Override
    public Object getItem(int arg0) {

        return playersOn.get(arg0);
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
        TextView date = (TextView) arg1.findViewById(R.id.list_item_bottom_right);
        ImageView logo = (ImageView) arg1.findViewById(R.id.logo);
        final Player p = playersOn.get(arg0);

        title.setText(p.name);
        title.setTypeface(null, Typeface.BOLD);

        from.setText("Online");
        Picasso.with(activity).load(p.urlImage).into(logo);
        arg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerOptionsDialog dialog = new PlayerOptionsDialog();
                dialog.setPlayerName(p.name);
                dialog.show(activity.getSupportFragmentManager(), "playerOptions");
            }
        });
        //idpm.setText(String.valueOf(p.idpm));
        //DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM");
        //date.setText(dateFormat.format(new Date((p.date) * 1000)));


        return arg1;
    }
}
