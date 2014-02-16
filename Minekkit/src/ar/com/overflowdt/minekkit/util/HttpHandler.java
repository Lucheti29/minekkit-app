package ar.com.overflowdt.minekkit.util;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;

public class HttpHandler {
	
	private List<NameValuePair> params = new ArrayList<NameValuePair>();

	//public String post(String posturl){
	public String post(String posturl, Enviable instancia){

	  try {

		  HttpClient httpclient = new DefaultHttpClient();
		  
		  /*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		  HttpPost httppost = new HttpPost(posturl);
		  
		  /*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		  //AÑADIR PARAMETROS
		  
		  setearParametros(instancia.armarArrayDeParametros());
		  
		  /*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		  httppost.setEntity(new UrlEncodedFormEntity(params));

          /*Finalmente ejecutamos enviando la info al server*/
		  HttpResponse resp = httpclient.execute(httppost);
		  
		  /*y obtenemos una respuesta*/
		  HttpEntity ent = resp.getEntity();

		  String text = EntityUtils.toString(ent);

		  return text;

	  }
	  catch(Exception e) { return "error";}

	}
  
  private void setearParametros(ArrayList<Parametro> arrayInstancia)
  {
	  Iterator<Parametro> parametrosIterator = arrayInstancia.iterator();
	  while(parametrosIterator.hasNext()){
	  	Parametro parametroInstancia = parametrosIterator.next();
	  	params.add(new BasicNameValuePair(parametroInstancia.getId(), parametroInstancia.getValor()));
	  }
  }
  

}