package ar.com.overflowdt.minekkit.pms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.BBCodeParser;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 01/03/14.
 */
public class SinglePMActivity extends Activity {

    TextView titulo;
    TextView from;
    TextView contenido;
    String id;
    PM message;
    private static String url = "http://minekkit.com/api/listPms.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "pms";
    private static final String TAG_TITLE = "title";
    private static final String TAG_LOGO = "Logo";
    private static final String TAG_FROM = "from";
    private static final String TAG_ID = "pmid";
    private static final String TAG_DATE = "date";
    private static final String TAG_READ = "read";
    private static final String TAG_CONTENIDO = "content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pm);

        Bundle bundle = getIntent().getExtras();
        id= bundle.getString(TAG_ID);
        contenido =(TextView) findViewById(R.id.txt_pm_contenido);
        from =(TextView) findViewById(R.id.txt_pm_from);
        titulo=(TextView) findViewById(R.id.txt_pm_title);
        Log.d("PMID", id.toString());
        new LoadAPM().execute();


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


    class LoadAPM extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SinglePMActivity.this);
            pDialog.setMessage("Cargando Mensaje...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        /**
         * getting a products from url
         * */
        protected String doInBackground(String... args) {

            PM pm=new PM();
            pm.idpm= Integer.parseInt(id);

            JSONObject json=null;
            try{
                json = new HttpHandler().post(url, pm);
                Log.d("PMs", json.toString());
            }catch(Exception ex){
                ex.printStackTrace();
            }
            // Check your log cat for JSON response
            Log.d("PM: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                switch(success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.", SinglePMActivity.this);
                        break;
                    case 0:
                        ShowAlertMessage.showMessage("Hubo un error en la solicitud del mensaje.", SinglePMActivity.this);
                        break;
                    case 1:
                        // products found
                        // Getting Array of Products
                        JSONArray products = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products

                        JSONObject c = products.getJSONObject(0);
                        message = pm;
                        // Storing each json item in variable
                        message.titulo= c.getString(TAG_TITLE);
                        message.from= c.getString(TAG_FROM);
                        message.content=c.getString(TAG_CONTENIDO);
                        message.read=c.getInt(TAG_READ);

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                titulo.setText(message.titulo);
                                from.setText("De: " + message.from );
                                contenido.setText(Html.fromHtml(BBCodeParser.bbcode(message.content)));
                            }
                        });
                        break;
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


        }
    }
}
