package ar.com.overflowdt.minekkit.adapters;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.PackRecompensas;

public class RecompensasAdapter extends BaseAdapter {

    public List<PackRecompensas> packRecompensasList;
    public Activity context;

    @Override
    public int getCount() {

        return packRecompensasList.size();
    }

    @Override
    public Object getItem(int arg0) {

        return packRecompensasList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item, arg2, false);
        }
        TextView name = (TextView) arg1.findViewById(R.id.name);
        TextView cost = (TextView) arg1.findViewById(R.id.costo);
        TextView id = (TextView) arg1.findViewById(R.id.pid);
        ImageView logo = (ImageView) arg1.findViewById(R.id.logo);
        PackRecompensas p = packRecompensasList.get(arg0);

        name.setText(p.Name);
        String t = String.valueOf(p.Cost);
        t = t.concat(" Recoplas");
        cost.setText(t);
        Picasso.with(context).load(p.urlImage).placeholder(R.drawable.img_chest).into(logo);
        id.setText(String.valueOf(p.id));
        return arg1;
    }

}
