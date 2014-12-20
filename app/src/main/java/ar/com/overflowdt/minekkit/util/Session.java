package ar.com.overflowdt.minekkit.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

public class Session implements Enviable {

    public String user;
    public String pass;
    static Session instance;
    public String ver;

    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    public String pass64() {
        try {
            return new String(Base64.encode(pass.getBytes("CP1252"), Base64.DEFAULT), "CP1252");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ;
        return null;
    }

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<Parametro>();
        list.add(new Parametro().setValores("user", user));
        list.add(new Parametro().setValores("pass", pass64()));
        list.add(new Parametro().setValores("version", ver));
        return list;
    }
}
