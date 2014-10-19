package ar.com.overflowdt.minekkit.util;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.entity.StringEntity;
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
		  //AÑADIR PARAMETROS

		  setearParametros(instancia.armarArrayDeParametros());
          Log.d("SentPArams:", params.toString());
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

    public JSONObject postJSON(String url, Enviable instancia) throws JSONException {
        InputStream inputStream = null;
        JSONObject result = new JSONObject();
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject =setearJSONParametros(instancia.armarArrayDeParametros());

            Log.d("snetJSON",jsonObject.toString());
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            //StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
           //httpPost.setEntity(se);

            StringEntity se = new StringEntity("json="+json);
            //httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            //httpPost.setHeader("Accept", "application/json");
            //httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            /*y obtenemos una respuesta*/
            HttpEntity ent = httpResponse.getEntity();

            // Parsing JSON
            String retSrc = EntityUtils.toString(ent);
            Log.d("response",retSrc);
            //Convertir String a JSON Object
            result = new JSONObject(retSrc);


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            result.put("success", -100);
            return  result;
        }

        // 11. return result
        return result;
    }
  
  private void setearParametros(ArrayList<Parametro> arrayInstancia)
  {
	  Iterator<Parametro> parametrosIterator = arrayInstancia.iterator();
	  while(parametrosIterator.hasNext()){
	  	Parametro parametroInstancia = parametrosIterator.next();
	  	params.add(new BasicNameValuePair(parametroInstancia.getId(), parametroInstancia.getValor()));
	  }
  }

    private JSONObject setearJSONParametros(ArrayList<Parametro> arrayInstancia) throws JSONException {
        JSONObject result = new JSONObject();
        Iterator<Parametro> parametrosIterator = arrayInstancia.iterator();
        while(parametrosIterator.hasNext()){
            Parametro parametroInstancia = parametrosIterator.next();
            result.accumulate(parametroInstancia.getId(),parametroInstancia.getValor().replace("\\", ""));
        }
        return result;
    }

}