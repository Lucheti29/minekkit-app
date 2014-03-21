package ar.com.overflowdt.minekkit.denuncia;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.util.Parametro;
import ar.com.overflowdt.minekkit.interfaces.Enviable;

public class DenunciaInstance implements Enviable {
	
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
	
	//Id de atributos que se envian por http
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
		
		Parametro parametroTitulo = new Parametro();
		parametroTitulo.setValores(_titulo, TITULO_VAR);
		arrayParametros.add(parametroTitulo);
		
		Parametro parametroFecha = new Parametro();
		parametroFecha.setValores(_fecha, FECHA_VAR);
		arrayParametros.add(parametroFecha);
		
		Parametro parametroHorario = new Parametro();
		parametroHorario.setValores(_hora, HORARIO_VAR);
		arrayParametros.add(parametroHorario);
		
		Parametro parametroMundo = new Parametro();
		parametroMundo.setValores(_mundo, MUNDO_VAR);
		arrayParametros.add(parametroMundo);
		
		Parametro parametroCiudad = new Parametro();
		parametroCiudad.setValores(_ciudad, CIUDAD_VAR);
		arrayParametros.add(parametroCiudad);
		
		Parametro parametroUsuarios = new Parametro();
		parametroUsuarios.setValores(_usuarios_involucrados, USUARIOS_VAR);
		arrayParametros.add(parametroUsuarios);
		
		Parametro parametroTipo = new Parametro();
		parametroTipo.setValores(_tipo_denuncia, TIPO_VAR);
		arrayParametros.add(parametroTipo);
		
		Parametro parametroReglas = new Parametro();
		parametroReglas.setValores(_normas_no_cumplidas, REGLAS_VAR);
		arrayParametros.add(parametroReglas);
		
		Parametro parametroExplicacion = new Parametro();
		parametroExplicacion.setValores(_explicacion, EXPLICACION_VAR);
		arrayParametros.add(parametroExplicacion);
		
		Parametro parametroSolucion = new Parametro();
		parametroSolucion.setValores(_solucion, SOLUCION_VAR);
		arrayParametros.add(parametroCiudad);
		
		return arrayParametros;
	}
	
	public void setTitulo(String titulo)
	{
		_titulo = titulo;
	}
	
	public void setFecha(String dia, String mes, String anio)
	{
		_fecha = dia + "/" +  mes + "/" + anio;
	}

    public void setFecha(String fecha)
    {
        _fecha = fecha;
    }
	
	public void setHora(String hora, String minutos)
	{
		_hora = hora + ":" + minutos;
	}

    public void setHora(String hora)
    {
        _hora = hora;
    }
	
	public void setMundo(String mundo)
	{
		_mundo = mundo;
	}
	
	public void setCiudad(String ciudad)
	{
		_ciudad = ciudad;
	}
	
	public void setTipoDenuncia(String tipodenuncia)
	{
		_tipo_denuncia = tipodenuncia;
	}
	
	public void setUsuariosInvolucrados(String usuarios)
	{
		_usuarios_involucrados = usuarios;
	}
	
	public void setNormasNoCumplidas(String normas)
	{
		_normas_no_cumplidas = normas;
	}
	
	public void setExplicacion(String explicacion)
	{
		_explicacion = explicacion;
	}
	
	public void setSolucion(String solucion)
	{
		_solucion = solucion;
	}

}
