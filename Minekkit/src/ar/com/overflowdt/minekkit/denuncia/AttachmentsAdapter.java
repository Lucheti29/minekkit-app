package ar.com.overflowdt.minekkit.denuncia;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import ar.com.overflowdt.minekkit.R;

/**
 * Created by Fede on 28/10/2014.
 */
public class AttachmentsAdapter extends BaseAdapter {

    public Map<String,Bitmap> attachments;
    ListActivity activity;
    public AttachmentsAdapter(Map<String, Bitmap> data,ListActivity act){
        activity=act;
        attachments  = data;
    }

    @Override
    public int getCount() {
        return attachments.size();
    }

    @Override
    public Object getItem(int position) {
        String[] mKeys = attachments.keySet().toArray(new String[attachments.size()]);
        return attachments.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        String[] mKeys = attachments.keySet().toArray(new String[attachments.size()]);
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item_attachment, arg2, false);
        }

        TextView fname = (TextView) arg1.findViewById(R.id.txt_list_attachment);
        ImageView image = (ImageView) arg1.findViewById(R.id.img_list_attachment);
        Button btn = (Button) arg1.findViewById(R.id.btn_list_attachment);

        Bitmap bitmap = attachments.get(mKeys[arg0]);
        final String filename = mKeys[arg0];
        String name=new File(filename).getName();

        fname.setText(name);

        // Get the dimensions of the View

        int targetW = Math.round(60 * (arg1.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
        int targetH = Math.round(80 * (arg1.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(filename, bmOptions);

        image.setImageBitmap(bitmap);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                attachments.remove(filename);
                notifyDataSetChanged();
            }

        });
        return arg1;
    }

}


