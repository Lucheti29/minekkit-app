package ar.com.overflowdt.minekkit;

import java.io.IOException;

import java.net.URL;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecompensasAdapter extends BaseAdapter {

	List<PackRecompensas> packRecompensasList;
	AllRecompensasActivity aRA;
//	public RecompensasAdapter(List<PackRecompensas> pL,AllRecompensasActivity a) {
//		RecompensasAdapter r=new RecompensasAdapter();
//		 r.packRecompensasList=pL;
//		 r.aRA=a;
//		 
//    }
//	public RecompensasAdapter() {
//		
//	}
	@Override
	public int getCount() {
		
		return  packRecompensasList.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return  packRecompensasList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) aRA.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item, arg2,false);
        }
		TextView name = (TextView)arg1.findViewById(R.id.name);
        TextView cost = (TextView)arg1.findViewById(R.id.costo);
        ImageView logo = (ImageView)arg1.findViewById(R.id.logo);
        PackRecompensas p = packRecompensasList.get(arg0);
        
        URL newurl;
        Bitmap mIcon_val;
		try {
			newurl = new URL(p.logo);
			mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			 name.setText(p.Name);
			 cost.setText(String.valueOf(p.Cost));
			 logo.setImageBitmap(mIcon_val);
		} catch (IOException e) {

			e.printStackTrace();
		} 
       
       

        return arg1;
	}

}
