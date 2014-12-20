package ar.com.overflowdt.minekkit.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

public class Denuncia implements Enviable {

    //Atributos que setea la Activity
    private String _titulo = "";
    private String _fecha = "";
    private String _hora = "";
    private String _mundo = "";
    private String _ciudad = "";
    private String _tipo_denuncia = "";
    private String _usuarios_involucrados = "";
    private String _normas_no_cumplidas = "";
    private String _explicacion = "";
    private String _solucion = "";

    public Map<String, Bitmap> attachments = new HashMap<String, Bitmap>();

    //Id de atributos que se envian por http
    private static String USER_VAR = "user";
    private static String PASS_VAR = "pass";
    private static String TITULO_VAR = "titulo";
    private static String FECHA_VAR = "fecha";
    private static String HORARIO_VAR = "horario";
    private static String MUNDO_VAR = "mundo";
    private static String CIUDAD_VAR = "ciudad";
    private static String USUARIOS_VAR = "involucrados";
    private static String TIPO_VAR = "tipo";
    private static String REGLAS_VAR = "reglas";
    private static String EXPLICACION_VAR = "explicacion";
    private static String SOLUCION_VAR = "solucion";

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {

        ArrayList<Parametro> arrayParametros = new ArrayList<Parametro>();

        Parametro parametroUser = new Parametro();
        parametroUser.setValores(USER_VAR, Session.getInstance().user);
        arrayParametros.add(parametroUser);

        Parametro parametroPass = new Parametro();
        parametroPass.setValores(PASS_VAR, Session.getInstance().pass64());
        arrayParametros.add(parametroPass);

        Parametro parametroTitulo = new Parametro();
        parametroTitulo.setValores(TITULO_VAR, _titulo);
        arrayParametros.add(parametroTitulo);

        Parametro parametroFecha = new Parametro();
        parametroFecha.setValores(FECHA_VAR, _fecha);
        arrayParametros.add(parametroFecha);

        Parametro parametroHorario = new Parametro();
        parametroHorario.setValores(HORARIO_VAR, _hora);
        arrayParametros.add(parametroHorario);

        Parametro parametroMundo = new Parametro();
        parametroMundo.setValores(MUNDO_VAR, _mundo);
        arrayParametros.add(parametroMundo);

        Parametro parametroCiudad = new Parametro();
        parametroCiudad.setValores(CIUDAD_VAR, _ciudad);
        arrayParametros.add(parametroCiudad);

        Parametro parametroUsuarios = new Parametro();
        parametroUsuarios.setValores(USUARIOS_VAR, _usuarios_involucrados);
        arrayParametros.add(parametroUsuarios);

        Parametro parametroTipo = new Parametro();
        parametroTipo.setValores(TIPO_VAR, _tipo_denuncia);
        arrayParametros.add(parametroTipo);

        Parametro parametroReglas = new Parametro();
        parametroReglas.setValores(REGLAS_VAR, _normas_no_cumplidas);
        arrayParametros.add(parametroReglas);

        Parametro parametroExplicacion = new Parametro();
        parametroExplicacion.setValores(EXPLICACION_VAR, _explicacion);
        arrayParametros.add(parametroExplicacion);

        Parametro parametroSolucion = new Parametro();
        parametroSolucion.setValores(SOLUCION_VAR, _solucion);
        arrayParametros.add(parametroSolucion);

        return arrayParametros;
    }

    public void setTitulo(String titulo) {
        _titulo = titulo;
    }

    public void setFecha(String dia, String mes, String anio) {
        _fecha = dia + "/" + mes + "/" + anio;
    }

    public void setFecha(String fecha) {
        _fecha = fecha;
    }

    public void setHora(String hora, String minutos) {
        _hora = hora + ":" + minutos;
    }

    public void setHora(String hora) {
        _hora = hora;
    }

    public void setMundo(String mundo) {
        _mundo = mundo;
    }

    public void setCiudad(String ciudad) {
        _ciudad = ciudad;
    }

    public void setTipoDenuncia(String tipodenuncia) {
        _tipo_denuncia = tipodenuncia;
    }

    public void setUsuariosInvolucrados(String usuarios) {
        _usuarios_involucrados = usuarios;
    }

    public void setNormasNoCumplidas(String normas) {
        _normas_no_cumplidas = normas;
    }

    public void setExplicacion(String explicacion) {
        _explicacion = explicacion;
    }

    public void setSolucion(String solucion) {
        _solucion = solucion;
    }

}
