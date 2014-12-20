package ar.com.overflowdt.minekkit.ruleta;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;
import ar.com.overflowdt.minekkit.util.Parametro;
import ar.com.overflowdt.minekkit.util.Session;

/**
 * Created by Fede on 30/04/14.
 */
public class RuletaActionEnviable implements Enviable {
    public String action = "";

    public RuletaActionEnviable(String act) {
        action = act;
    }

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> lista = Session.getInstance().armarArrayDeParametros();
        lista.add(new Parametro().setValores("action", action));
        return lista;
    }
}
