package ar.com.overflowdt.minekkit.util;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

public class Session implements Enviable{

	String user;
	String pass;
	static Session instance;
	
	static Session getInstance(){
		if(instance == null )
			instance=new Session();			
		return instance;
	}

	@Override
	public ArrayList<Parametro> armarArrayDeParametros() {
		ArrayList<Parametro> list = new ArrayList<Parametro>();
		list.add(new Parametro().setValores("user", user));
		list.add(new Parametro().setValores("pass", pass));
		return list;		
	}
}
