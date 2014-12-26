package ar.com.overflowdt.minekkit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.PackRecompensas;
import ar.com.overflowdt.minekkit.adapters.RecompensasAdapter;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

//import android.widget.ListView;


public class AllRecompensasActivity extends ActionBarActivity {

    public static final String TAG = "Shop";
    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ArrayList<ListView> arrayListViews;
    List<PackRecompensas> packRecompensasList;
    // url to get all products list
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas_tabs);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        //Setea la primer tab
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1_recompensas);
        //Le pone un icono a la primer tab
        spec.setIndicator("Packs");
        tabs.addTab(spec);

        //Setea la segunda tab
        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Armor");
        tabs.addTab(spec);

        //Setea la segunda tab
        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Event");
        tabs.addTab(spec);

        //Setea la segunda tab
        spec = tabs.newTabSpec("mitab4");
        spec.setContent(R.id.tab4_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Otros");
        tabs.addTab(spec);

        //Setea la primer tab como default
        tabs.setCurrentTab(0);
        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        arrayListViews = new ArrayList<ListView>();
        arrayListViews.add((ListView) findViewById(R.id.list1_recompensas));
        arrayListViews.add((ListView) findViewById(R.id.list2_recompensas));
        arrayListViews.add((ListView) findViewById(R.id.list3_recompensas));
        arrayListViews.add((ListView) findViewById(R.id.list4_recompensas));
//         on seleting single product
//         launching Edit Product Screen
        for (int j = 0; j < 4; j++) {
            arrayListViews.get(j).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // getting values from selected ListItem
                    String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                            .toString();

                    // Starting new intent
                    Intent in = new Intent(AllRecompensasActivity.this,
                            SingleRecActivity.class);
                    // sending pid to next activity
                    in.putExtra(TAG_ID, pid);

                    startActivity(in);
                    // starting new activity and expecting some response back
                    //                startActivityForResult(in, 100);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadAllProducts extends AsyncTask<String, String, String> {
        private ArrayList<Thread> threads = new ArrayList<Thread>();
        private ArrayList<List<PackRecompensas>> arrayPacks;
        // products JSONArray
        JSONArray products = null;
        JSONArray prod = null;

        protected void listenResults() {
            while (threads.size() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < threads.size(); ++i) {
                    Thread thread = threads.get(i);
                    if (!thread.isAlive()) {
                        threads.remove(i);
                    }
                }
            }
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllRecompensasActivity.this);
            pDialog.setMessage("Cargando recompensas. Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            try {
                pDialog.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            arrayPacks = new ArrayList<List<PackRecompensas>>();
            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.", AllRecompensasActivity.this);
                        break;
                    case 1:
                        threads.clear();
                        JSONObject prod = json.getJSONObject(TAG_PRODUCTS);
                        Bitmap logoChest = BitmapFactory.decodeResource(getResources(),
                                R.drawable.img_chest);
                        for (int j = 1; j <= 4; j++) {
                            // Getting Array of Products

                            products = prod.getJSONArray(String.valueOf(j));
                            // looping through All Products
                            packRecompensasList = new ArrayList<PackRecompensas>();
                            for (int i = 0; i < products.length(); i++) {
                                JSONObject c = products.getJSONObject(i);

                                // Storing each json item in variable
                                PackRecompensas item = new PackRecompensas();
                                item.Name = c.getString(TAG_NAME);
                                item.Cost = c.getInt(TAG_COSTO);
                                item.id = c.getInt(TAG_ID);
                                item.descripcion = c.getString(TAG_DESC);
                                item.logo = logoChest;
                                item.urlImage = c.getString(TAG_LOGO);
/*                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AllRecompensasActivity.this);
                                boolean individualImagesEnabled = prefs.getBoolean("individualImagesEnabled", true);
                                if (individualImagesEnabled) {
                                    Thread thread = new Thread(new LoadImageThread(urlImage, item), item.Name);
                                    threads.add(thread);
                                    thread.start();
                                }*/
                                packRecompensasList.add(item);

                            }
                            arrayPacks.add(packRecompensasList);
                        }
                        // products found
                        listenResults();
                        break;
                    default:
                        ShowAlertMessage.showMessage("Hubo un error en la solicitud.", AllRecompensasActivity.this);
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
            try {
                pDialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */

                    for (int j = 0; j < 4; j++) {
                        RecompensasAdapter adapter = new RecompensasAdapter();

                        adapter.context = AllRecompensasActivity.this;
                        adapter.packRecompensasList = arrayPacks.get(j);
                        // updating listview
                        arrayListViews.get(j).setAdapter(adapter);
                        //setListAdapter(adapter);
                    }
                }
            });

        }

    }
}



