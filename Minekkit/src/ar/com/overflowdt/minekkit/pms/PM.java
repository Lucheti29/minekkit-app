package ar.com.overflowdt.minekkit.pms;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;
import ar.com.overflowdt.minekkit.util.Parametro;
import ar.com.overflowdt.minekkit.util.Session;

/**
 * Created by Fede on 01/03/14.
 */
public class PM implements Enviable {
    int idpm;
    String titulo;
    String from;
    String content;
    int read;

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<Parametro>();
        list.add(new Parametro().setValores("user", Session.getInstance().user));
        list.add(new Parametro().setValores("pass", Session.getInstance().pass));
        list.add(new Parametro().setValores("idpm", String.valueOf(idpm)));
        return list;
    }
}
