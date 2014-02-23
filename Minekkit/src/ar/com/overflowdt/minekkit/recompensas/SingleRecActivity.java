package ar.com.overflowdt.minekkit.recompensas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.MenuHandler;

public class SingleRecActivity extends Activity {
	
	// Progress Dialog
    private ProgressDialog pDialog;
 // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ImageView logo;
	TextView titulo;
	TextView costo;
	TextView desc;
	String id;
	PackRecompensas pack;
	private static String url_all_products = "http://minekkit.com/api/listRecompensas.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "items";
    private static final String TAG_NAME = "Nombre";
    private static final String TAG_LOGO = "Logo";
    private static final String TAG_COSTO = "Costo";
    private static final String TAG_ID = "Id";
    private static final String TAG_DESC = "Descripcion";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recompensa);
		
		Bundle bundle = getIntent().getExtras();
		id= bundle.getString(TAG_ID);
		desc=(TextView) findViewById(R.id.descripcion);
		costo=(TextView) findViewById(R.id.cost);
		titulo=(TextView) findViewById(R.id.titulo);
		logo=(ImageView) findViewById(R.id.single_logo);
		
		new LoadAProducts().execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.bindearLogica(item, this);
    }

	
	class LoadAProducts extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleRecActivity.this);
            pDialog.setMessage("Cargando Pack...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting a products from url
         * */
        protected String doInBackground(String... args) {
            
            // getting JSON string from URL
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            
            nameValuePairs.add(new BasicNameValuePair("id",id));
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", nameValuePairs);

            // Check your log cat for JSON response
            Log.d("Product: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                   JSONArray products = json.getJSONArray(TAG_PRODUCTS);
 
                    // looping through All Products
                    
                        JSONObject c = products.getJSONObject(0);
                        pack= new PackRecompensas();
                        // Storing each json item in variable
                       pack.id=c.getInt(TAG_ID); 
                       pack.Name= c.getString(TAG_NAME);
                       pack.Cost= c.getInt(TAG_COSTO);
                       pack.descripcion=c.getString(TAG_DESC);
                        try {
		                    URL newurl = new URL(c.getString(TAG_LOGO));
		        			pack.logo=(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
                		} catch (IOException e) {

                			e.printStackTrace();
                		} 
                        
                        

                    
                } 
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            titulo.setText(pack.Name);
            costo.setText(String.valueOf(pack.Cost) + " Recoplas");
            desc.setText(pack.descripcion);
            logo.setImageBitmap(pack.logo);
        }
	}
}
