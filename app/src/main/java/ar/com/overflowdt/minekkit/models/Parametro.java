package ar.com.overflowdt.minekkit.models;

//@author: luksdlt92

public class Parametro {

    private String _id;
    private String _valor;

    public String getId() {
        return _id;
    }

    public String getValor() {
        return _valor;

    }

    public Parametro setId(String value) {
        _id = value;
        return this;
    }

    public Parametro setValor(String value) {
        _valor = value;
        return this;
    }

    public Parametro setValores(String id, String value) {
        _id = id;
        _valor = value;
        return this;
    }
}
