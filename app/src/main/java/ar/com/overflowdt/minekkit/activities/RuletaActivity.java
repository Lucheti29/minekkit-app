package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import ar.com.overflowdt.minekkit.R;

import ar.com.overflowdt.minekkit.models.RuletaAction;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.MenuHandler;

import ar.com.overflowdt.minekkit.util.ShowAlertMessage;
import ar.com.overflowdt.minekkit.views.RotationView;

/**
 * Created by Fede on 29/04/14.
 */
public class RuletaActivity extends Activity {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_INTENTS = "intentos";
    private static final String TAG_PRIZE = "prize";
    RotationView logo;
    TextView titulo;
    TextView mensaje_inferior;
    TextView desc;
    String id;
    ProgressDialog pDialog;
    private RotationView mRotateImage;
    private int intents = 0;
    private int prize;
    int[] prizes = {1000, 2500, 100000, 25000, 10000, 5000, 500, 50000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);


        mensaje_inferior = (TextView) findViewById(R.id.ruleta_mensaje_inferior);
        titulo = (TextView) findViewById(R.id.ruleta_titulo);
        mRotateImage = (RotationView) findViewById(R.id.ruleta_logo);


        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");


        titulo.setTypeface(mecha_Condensed_Bold);
        mensaje_inferior.setTypeface(mecha_Condensed_Bold);
        TextView btn_compra = (TextView) findViewById(R.id.btn_girar);
        btn_compra.setTypeface(mecha_Condensed_Bold);

        new GetIntents().execute();
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

    public void toggle(View view) {
        if (!mRotateImage.isRotating())
            new GetPrize().execute();
        //mRotateImage.toggle();
    }


    class GetIntents extends AsyncTask<String, String, String> {

        private String url = "http://minekkit.com/api/girarRuleta.php";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RuletaActivity.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting a products from url
         */
        protected String doInBackground(String... args) {

            JSONObject json = null;
            try {
                json = new HttpHandler().post(url, new RuletaAction("getIntents"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
/*                    case -1:
                        ShowAlertMessage.showMessage("No tienes Suficientes Recoplas.", RuletaActivity.this);
                        break;*/
                    case 0:
                        ShowAlertMessage.showMessage("Ha ocurrido un error.", RuletaActivity.this);
                        break;
                    case 1:
                        intents = json.getInt(TAG_INTENTS);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mensaje_inferior.setText(String.valueOf(intents) + " Intentos restantes");
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

    class GetPrize extends AsyncTask<String, String, String> {

        private String url = "http://minekkit.com/api/girarRuleta.php";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RuletaActivity.this);
            pDialog.setMessage("Conectando con el Servidor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting a products from url
         */
        protected String doInBackground(String... args) {

            JSONObject json = null;
            try {
                json = new HttpHandler().post(url, new RuletaAction("getPrize"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -1:
                        ShowAlertMessage.showMessage("No tienes más intentos.", RuletaActivity.this);
                        break;
                    case 0:
                        ShowAlertMessage.showMessage("Ha ocurrido un error.", RuletaActivity.this);
                        break;
                    case 1:
                        prize = json.getInt(TAG_PRIZE);
                        mRotateImage.setFinishingAngle(prize * 45);
                        mRotateImage.setFinishMessage("Has ganado " + getRecoplas(prize) + " Recoplas!");
                        intents--;
                        mRotateImage.toggle();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mensaje_inferior.setText(String.valueOf(intents) + " Intentos restantes");
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

    private int getRecoplas(int prize) {
        return prizes[prize];
    }

}
