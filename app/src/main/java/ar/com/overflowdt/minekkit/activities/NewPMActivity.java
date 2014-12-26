package ar.com.overflowdt.minekkit.activities;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.PM;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 21/03/14.
 */
public class NewPMActivity extends ActionBarActivity {

    TextView titulo;
    TextView to;
    TextView contenido;
    String id;
    String toPlayer;
    PM message;
    private static String url = "http://minekkit.com/api/listPms.php";
    private static String urlSend = "http://minekkit.com/api/enviarPM.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "pms";
    private static final String TAG_TITLE = "title";
    private static final String TAG_FROM = "from";
    public static final String TAG_ID = "pmid";
    public static final String TAG_TO = "toPlayer";
    private static final String TAG_DATE = "date";
    private static final String TAG_READ = "read";
    private static final String TAG_CONTENIDO = "content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_pm);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && getIntent().hasExtra(TAG_ID))
            id = bundle.getString(TAG_ID);
        contenido = (EditText) findViewById(R.id.edit_newpm_contenido);
        to = (EditText) findViewById(R.id.editnewpm_to);
        titulo = (EditText) findViewById(R.id.editnewpm_title);
        setTitle("Enviar PM");
        if (id != null) {
            setTitle("Responder PM");
            Log.d("PMID", id);
            new LoadAPM().execute();
        } else {
            toPlayer = getIntent().getStringExtra(TAG_TO);
            if (toPlayer != null)
                to.setText(toPlayer);
        }
        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");

        Button btn_responder;
        btn_responder = (Button) findViewById(R.id.btn_enviarPM);
        btn_responder.setTypeface(mecha_Condensed_Bold);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    public void enviarMensaje(View view) {
        new SendPM().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    class LoadAPM extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewPMActivity.this);
            pDialog.setMessage("Cargando Mensaje...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        /**
         * getting a products from url
         */
        protected String doInBackground(String... args) {

            PM pm = new PM();
            pm.idpm = Integer.parseInt(id);

            JSONObject json = null;
            try {
                json = new HttpHandler().post(url, pm);
                Log.d("PMs", json.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                switch (success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.", NewPMActivity.this);
                        break;
                    case 0:
                        ShowAlertMessage.showMessage("Hubo un error en la solicitud del mensaje.", NewPMActivity.this);
                        break;
                    case 1:
                        // products found
                        // Getting Array of Products
                        JSONArray products = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products

                        JSONObject c = products.getJSONObject(0);
                        message = pm;
                        // Storing each json item in variable
                        message.titulo = c.getString(TAG_TITLE);
                        message.from = c.getString(TAG_FROM);
                        message.content = c.getString(TAG_CONTENIDO);
                        message.read = c.getInt(TAG_READ);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                titulo.setText("RE: " + message.titulo);
                                to.setText(message.from);
                                contenido.setText("[quote='" + message.from + "']\n" + message.content + "\n[/quote]\n");

                                //Movemos los cursores al final de cada texto insertado (Es necesario para la GUI)
                                EditText mueveCursorTitulo = (EditText) findViewById(R.id.editnewpm_title);
                                mueveCursorTitulo.setSelection(mueveCursorTitulo.getText().length());
                                EditText mueveCursorFrom = (EditText) findViewById(R.id.editnewpm_to);
                                mueveCursorFrom.setSelection(mueveCursorFrom.getText().length());
                                EditText mueveCursorContenido = (EditText) findViewById(R.id.edit_newpm_contenido);
                                mueveCursorContenido.setSelection(mueveCursorContenido.getText().length());
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
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();


        }
    }

    class SendPM extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewPMActivity.this);
            pDialog.setMessage("Enviando Mensaje...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        /**
         * getting a products from url
         */
        protected String doInBackground(String... args) {

            PM pm = new PM();
            //pm.idpm= Integer.parseInt(id);
            pm.titulo = (String) String.valueOf(titulo.getText());
            pm.to = (String) String.valueOf(to.getText());
            pm.content = (String) String.valueOf(contenido.getText());

            JSONObject json = null;
            try {
                json = new HttpHandler().post(urlSend, pm);
                Log.d("Send PM answer", json.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                switch (success) {
                    case -2:
                        ShowAlertMessage.showMessage("El usuario ingresado no es valido.", NewPMActivity.this);
                        break;
                    case 0:
                        ShowAlertMessage.showMessage("Hubo un error en el envío del mensaje. Intente más tarde.", NewPMActivity.this);
                        break;
                    case 1:
                        ShowAlertMessage.showMessageAndFinishActivity("El mensaje se ha enviado correctamente.", NewPMActivity.this);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();


        }
    }
}
