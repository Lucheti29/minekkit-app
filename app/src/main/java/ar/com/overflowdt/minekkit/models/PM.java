package ar.com.overflowdt.minekkit.models;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

/**
 * Created by Fede on 01/03/14.
 */
public class PM implements Enviable {
    public int idpm;
    public String titulo;
    public String from;
    public String to;
    public String content;
    public int read;
    public long date;

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<Parametro>();
        list.add(new Parametro().setValores("user", Session.getInstance().user));//get Message //Send Message
        list.add(new Parametro().setValores("pass", Session.getInstance().pass64()));//get Message //Send Message
        list.add(new Parametro().setValores("idpm", String.valueOf(idpm)));//get Message

        list.add(new Parametro().setValores("title", String.valueOf(titulo)));//Send Message
        list.add(new Parametro().setValores("to", String.valueOf(to)));//Send Message
        list.add(new Parametro().setValores("content", String.valueOf(content)));//Send Message
        return list;
    }

    public boolean isMissingFields() {
        if (titulo.isEmpty() || to.isEmpty() || content.isEmpty())
            return true;
        return false;
    }
}
