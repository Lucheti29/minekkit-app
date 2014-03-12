package ar.com.overflowdt.minekkit.util;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.util.EntityUtils;

public class HttpHandler {
	
	private List<NameValuePair> params = new ArrayList<NameValuePair>();

	//public String post(String posturl){
	public JSONObject post(String posturl, Enviable instancia) throws IOException, JSONException {
        JSONObject result;
	  try {

		  HttpClient httpclient = new DefaultHttpClient();

		  /*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		  HttpPost httppost = new HttpPost(posturl);

		  /*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		  //ANIADIR PARAMETROS

		  setearParametros(instancia.armarArrayDeParametros());

		  /*Una vez aniadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos a�adido*/
		  httppost.setEntity(new UrlEncodedFormEntity(params));

          /*Finalmente ejecutamos enviando la info al server*/
		  HttpResponse resp = httpclient.execute(httppost);

		  /*y obtenemos una respuesta*/
		  HttpEntity ent = resp.getEntity();

          // Parsing JSON
		  String retSrc = EntityUtils.toString(ent);
          Log.d("response",retSrc);
          //Convertir String a JSON Object
          result = new JSONObject(retSrc);

          return  result;

	  }
      catch (IOException e) {
          result = new JSONObject();
          result.put("success", -100);
          return  result;
      }
	  catch(Exception e)
      {
        e.printStackTrace();
      }
      return null;
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