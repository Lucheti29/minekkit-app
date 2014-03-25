package ar.com.overflowdt.minekkit.recompensas;

import android.graphics.Bitmap;

import ar.com.overflowdt.minekkit.interfaces.ImageLoadable;

public class PackRecompensas implements ImageLoadable{
	int id;
	String Name;
	int Cost;
	Bitmap logo;
	String descripcion;

    @Override
    public Bitmap getImage() {
        return logo;
    }

    @Override
    public void setImage(Bitmap image) {
        logo=image;
    }
}
