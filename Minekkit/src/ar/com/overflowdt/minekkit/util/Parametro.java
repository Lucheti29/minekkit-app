package ar.com.overflowdt.minekkit.util;

//@author: luksdlt92

public class Parametro {
	
	private String _id;
	private String _valor;
	
	public String getId()
	{
		return _id;
	}

	public String getValor()
	{
		return _valor;
	}
	
	public void setId(String value)
	{
		_id = value;
	}
	
	public void setValor(String value)
	{
		_valor = value;
	}
	
	public void setValores(String id, String value)
	{
		_id = id;
		_valor = value;
	}
}
