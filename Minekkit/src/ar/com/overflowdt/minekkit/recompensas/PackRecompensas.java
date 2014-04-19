package ar.com.overflowdt.minekkit.recompensas;

import android.graphics.Bitmap;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;
import ar.com.overflowdt.minekkit.interfaces.ImageLoadable;
import ar.com.overflowdt.minekkit.util.Parametro;
import ar.com.overflowdt.minekkit.util.Session;

public class PackRecompensas implements ImageLoadable,Enviable{
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

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<Parametro>();
        list.add(new Parametro().setValores("user", Session.getInstance().user));
        list.add(new Parametro().setValores("pass", Session.getInstance().pass64()));
        list.add(new Parametro().setValores("id", String.valueOf(id)));
        return list;
    }
}
