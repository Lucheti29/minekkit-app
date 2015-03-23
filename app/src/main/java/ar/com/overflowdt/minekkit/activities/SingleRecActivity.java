package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.MinekkitApplication;
import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.PackRecompensas;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

public class SingleRecActivity extends ActionBarActivity {

    private static final String TAG_DESCUENTO = "descuento";
    private static final String TAG_RECOPLAS = "recoplas";
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
        id = bundle.getString(TAG_ID);
        desc = (TextView) findViewById(R.id.descripcion);
        costo = (TextView) findViewById(R.id.cost);
        titulo = (TextView) findViewById(R.id.titulo);
        logo = (ImageView) findViewById(R.id.single_logo);

        new LoadAProducts().execute();

        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");


        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(mecha_Condensed_Bold);
        TextView cost = (TextView) findViewById(R.id.cost);
        cost.setTypeface(mecha_Condensed_Bold);
        TextView btn_compra = (TextView) findViewById(R.id.btn_compra);
        btn_compra.setTypeface(mecha_Condensed_Bold);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //Google Analytics
        ((MinekkitApplication) getApplication()).getTracker(MinekkitApplication.TrackerName.APP_TRACKER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Get an Analytics tracker to report app starts and uncaught exceptions etc.
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Stop the analytics tracking
        GoogleAnalytics.getInstance(this).reportActivityStop(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    public void buy(View view) {
        new BuyAProduct().execute();
    }


    class LoadAProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
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
         */
        protected String doInBackground(String... args) {

            // getting JSON string from URL
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("id", id));
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
                    pack = new PackRecompensas();
                    // Storing each json item in variable
                    pack.id = c.getInt(TAG_ID);
                    pack.Name = c.getString(TAG_NAME);
                    pack.Cost = c.getInt(TAG_COSTO);
                    pack.descripcion = c.getString(TAG_DESC);
                    pack.urlImage = c.getString(TAG_LOGO);


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
            try {
                pDialog.dismiss();
            } catch (IllegalArgumentException e) {
                return;
            }
            titulo.setText(pack.Name);
            costo.setText(String.valueOf(pack.Cost) + " Recoplas");
            desc.setText(pack.descripcion);
            Picasso.with(SingleRecActivity.this).load(pack.urlImage).into(logo);
        }
    }

    class BuyAProduct extends AsyncTask<String, String, String> {

        private String url_buy_product = "http://minekkit.com/api/buyPackReloaded.php";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleRecActivity.this);
            pDialog.setMessage("Comprando...");
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
                json = new HttpHandler().post(url_buy_product, pack);
                Log.d("Buy", json.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -1:
                        ShowAlertMessage.showMessage("No tienes Suficientes Recoplas.", SingleRecActivity.this);
                        break;
                    case 0:
                        ShowAlertMessage.showMessage("Ha ocurrido un error.", SingleRecActivity.this);
                        break;
                    case 1:
                        int desc = json.getInt(TAG_DESCUENTO);
                        int recos = json.getInt(TAG_RECOPLAS);
                        if (desc > 0)
                            ShowAlertMessage.showMessage("Has tenido un descuento del " + String.valueOf(desc) + "%", SingleRecActivity.this);
                        ShowAlertMessage.showMessageAndFinishActivity("Tu compra ha sido realizada con exito. Por favor reloggea para obtener tus items. Tu saldo es " + String.valueOf(recos) + " Recoplas.", SingleRecActivity.this);
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
            titulo.setText(pack.Name);
            costo.setText(String.valueOf(pack.Cost) + " Recoplas");
            desc.setText(pack.descripcion);
            logo.setImageBitmap(pack.logo);
        }
    }

}
