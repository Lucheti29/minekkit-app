package ar.com.overflowdt.minekkit.recompensas;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.LoadImageThread;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

//import android.widget.ListView;

 
public class AllRecompensasActivity extends ListActivity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 

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
 
    // products JSONArray
    JSONArray products = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas_tabs);

        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        //Setea la primer tab
        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1_recompensas);
        //Le pone un icono a la primer tab
        spec.setIndicator("Packs",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        //Setea la segunda tab
        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Armaduras",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        //Setea la segunda tab
        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Community",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        //Setea la segunda tab
        spec=tabs.newTabSpec("mitab4");
        spec.setContent(R.id.tab4_recompensas);
        //Le pone un icono a la segunda tab
        spec.setIndicator("Otros",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        //Setea la primer tab como default
        tabs.setCurrentTab(0);
        // Loading products in Background Thread
        new LoadAllProducts().execute();
 
        // Get listview
        ListView lv = getListView();
 
//         on seleting single product
//         launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {
 
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
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {
        private ArrayList<Thread> threads=new ArrayList<Thread>();

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
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllRecompensasActivity.this);
            pDialog.setMessage("Cargando recompensas. Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
            packRecompensasList= new ArrayList<PackRecompensas>();
            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch(success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.", AllRecompensasActivity.this);
                        break;
                    case 1:
                        // products found
                        // Getting Array of Products
                        products = json.getJSONArray(TAG_PRODUCTS);
                        threads.clear();
                        // looping through All Products
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            PackRecompensas item = new PackRecompensas();
                            item.Name = c.getString(TAG_NAME);
                            item.Cost = c.getInt(TAG_COSTO);
                            item.id = c.getInt(TAG_ID);
                            item.descripcion = c.getString(TAG_DESC);

                            item.logo = BitmapFactory.decodeResource(getResources(),
                                    R.drawable.img_chest);
                            String urlImage=c.getString(TAG_LOGO);
                            Thread thread = new Thread(new LoadImageThread(urlImage,item), item.Name);
                            threads.add(thread);
                            thread.start();
                            packRecompensasList.add(item);

                        }
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
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    RecompensasAdapter adapter = new RecompensasAdapter(); 
                    adapter.aRA=AllRecompensasActivity.this;
                    adapter.packRecompensasList=packRecompensasList;
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}



