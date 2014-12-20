package ar.com.overflowdt.minekkit.models;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

/**
 * Created by Fede on 30/04/14.
 */
public class RuletaAction implements Enviable {
    public String action = "";

    public RuletaAction(String act) {
        action = act;
    }

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> lista = Session.getInstance().armarArrayDeParametros();
        lista.add(new Parametro().setValores("action", action));
        return lista;
    }
}
