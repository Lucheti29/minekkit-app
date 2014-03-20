package ar.com.overflowdt.minekkit.pms;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.JSONParser;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.util.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 01/03/14.
 */
public class AllPmsActivity extends ListActivity{
    // Progress Dialog
    private ProgressDialog pDialog;


    List<PM> packPMs;
    // url to get all pms list
    private static String url = "http://minekkit.com/api/listPms.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "pms";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DATE = "date";
    private static final String TAG_LOGO = "Logo";
    private static final String TAG_FROM = "from";
    private static final String TAG_ID = "pmid";
    private static final String TAG_READ = "read";
    private static final String TAG_CONTENIDO = "contenido";

    // pms JSONArray
    JSONArray pms = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pms);


        // Loading pms in Background Thread
        new LoadAllPms().execute();

        // Get listview
        ListView lv = getListView();

//         on seleting single product
//         launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();

                // Starting new intent
                Intent in = new Intent(AllPmsActivity.this, SinglePMActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_ID, pid);


                startActivityForResult(in, 100);
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
        if (requestCode == 100) {
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
    class LoadAllPms extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllPmsActivity.this);
            pDialog.setMessage("Cargando mensajes privados. Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }



        /**
         * getting All pms from url
         * */
        protected String doInBackground(String... args) {
            HttpHandler handler = new HttpHandler();
            JSONObject json = null;

            try{
                json = handler.post(url, Session.getInstance());
                Log.d("PMs", json.toString());
            }catch(Exception ex){
                ex.printStackTrace();
            }

            packPMs = new ArrayList<PM>();
            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch(success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.",AllPmsActivity.this);
                        break;
                    case 1:
                        // pms found
                        // Getting Array of Products
                        pms = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products
                        for (int i = 0; i < pms.length(); i++) {
                            JSONObject c = pms.getJSONObject(i);

                            // Storing each json item in variable
                            PM item = new PM();
                            item.titulo = c.getString(TAG_TITLE);
                            item.from = c.getString(TAG_FROM);
                            item.idpm = c.getInt(TAG_ID);
                            item.read = c.getInt(TAG_READ);
                            item.date= c.getLong(TAG_DATE);
                            packPMs.add(item);
                        }
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
            // dismiss the dialog after getting all pms
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    PmListAdapter adapter = new PmListAdapter();
                    adapter.activity=AllPmsActivity.this;
                    adapter.packPms= packPMs;
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
