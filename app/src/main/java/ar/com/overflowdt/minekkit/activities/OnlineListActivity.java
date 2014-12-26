package ar.com.overflowdt.minekkit.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.adapters.OnlineListAdapter;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.LoadImageThread;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 23/03/14.
 */
public class OnlineListActivity extends ActionBarActivity {
    public static final String TAG = "JugadoresOnline";
    // Progress Dialog
    private ProgressDialog pDialog;


    List<OnlineListAdapter.Player> playerList;
    // url to get all playersOn list
    private static String url = "http://minekkit.com/api/listOnline.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PLAYERS = "players";
    private static final String TAG_NAME = "";
    private static final String TAG_DATE = "date";
    private static final String TAG_LOGO = "Logo";
    private static final String TAG_FROM = "from";
    private static final String TAG_ID = "pmid";

    // playersOn JSONArray
    JSONArray playersOn = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_list);


        // Loading playersOn in Background Thread
        new LoadPlayersOnline().execute();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

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
     */
    class LoadPlayersOnline extends AsyncTask<String, String, String> {


        private ArrayList<Thread> threads = new ArrayList<Thread>();

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OnlineListActivity.this);
            pDialog.setMessage("Cargando jugadores en linea. Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


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
         * getting All playersOn from url
         */
        protected String doInBackground(String... args) {
            HttpHandler handler = new HttpHandler();
            JSONObject json = null;

            try {
                json = handler.post(url, Session.getInstance());
                Log.d("Players", json.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            playerList = new ArrayList<OnlineListAdapter.Player>();

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -100:
                        ShowAlertMessage.showMessage("No se puede conectar con el servidor. Intente más tarde.", OnlineListActivity.this);
                        break;
                    case 1:
                        // playersOn found
                        // Getting Array of Products
                        playersOn = json.getJSONArray(TAG_PLAYERS);
                        threads.clear();
                        Bitmap steveLogo = BitmapFactory.decodeResource(getResources(),
                                R.drawable.steve);
                        // looping through All Products
                        for (int i = 0; i < playersOn.length(); i++) {
                            String c = playersOn.getString(i);

                            // Storing each json item in variable
                            OnlineListAdapter.Player item = new OnlineListAdapter.Player();
                            item.name = c;
                            item.face = steveLogo;
                            //String urlImage="https://minotar.net/avatar/"+c+"/50";
                            item.urlImage = "http://belowaverage.ga/API/SKINHEAD/?player=" + c + "&size=40";


                            playerList.add(item);
                        }
                        listenResults();
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
            // dismiss the dialog after getting all playersOn
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    OnlineListAdapter adapter = new OnlineListAdapter();
                    setTitle(String.valueOf(playersOn.length()) + " Players Online");
                    adapter.activity = OnlineListActivity.this;
                    adapter.playersOn = playerList;
                    // updating listview
                    ListView list = (ListView) findViewById(R.id.online_list);
                    list.setAdapter(adapter);
                }
            });

        }

    }
}


