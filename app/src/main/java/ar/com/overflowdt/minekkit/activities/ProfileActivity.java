package ar.com.overflowdt.minekkit.activities;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.Job;
import ar.com.overflowdt.minekkit.adapters.JobListAdapter;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 23/03/14.
 */
public class ProfileActivity extends ActionBarActivity {


    public static final String TAG = "Perfil";
    // Progress Dialog
    private ProgressDialog pDialog;

    ListView lv;

    List<Job> jobList;
    // url to get all pms list
    private static String url = "http://minekkit.com/api/profile.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_AVATAR = "avatar";
    private static final String TAG_JOBS = "jobs";
    private static final String TAG_JOB = "job";
    private static final String TAG_TITLE = "title";
    private static final String TAG_LEVEL = "level";
    private static final String TAG_XP = "experience";
    private static final String TAG_RECOPLAS = "recoplas";
    // pms JSONArray
    JSONArray jobs = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Loading pms in Background Thread
        new LoadProfile().execute();

        // Get listview
        lv = (ListView) findViewById(R.id.listJob_listview);

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
    class LoadProfile extends AsyncTask<String, String, String> {

        int recoplas;
        Bitmap logo;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileActivity.this);
            pDialog.setMessage("Cargando Perfil. Por favor, espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        /**
         * getting All pms from url
         */
        protected String doInBackground(String... args) {
            HttpHandler handler = new HttpHandler();
            JSONObject json = null;

            try {
                json = handler.post(url, Session.getInstance());
                Log.d("Jobs", json.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            jobList = new ArrayList<Job>();

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -100:
                        ShowAlertMessage.showMessage(getString(R.string.CantConnect), ProfileActivity.this);
                        break;
                    case 1:
                        // pms found
                        // Getting Array of Products
                        jobs = json.getJSONArray(TAG_JOBS);

                        recoplas = json.getInt(TAG_RECOPLAS);

                        try {
                            URL newurl = new URL(json.getString(TAG_AVATAR));
                            logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        } catch (IOException e) {

                            e.printStackTrace();
                        }


                        // looping through All Products
                        for (int i = 0; i < jobs.length(); i++) {
                            JSONObject c = jobs.getJSONObject(i);

                            // Storing each json item in variable
                            Job item = new Job();
                            item.name = c.getString(TAG_JOB);
                            item.level = c.getInt(TAG_LEVEL);
                            item.xp = c.getLong(TAG_XP);
                            Job.contJobs++;
                            jobList.add(item);
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
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all pms
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ImageView avatar = (ImageView) findViewById(R.id.profile_avatar);
                    avatar.setImageBitmap(logo);
                    TextView name = (TextView) findViewById(R.id.profile_name);
                    name.setText(Session.getInstance().user);
                    TextView reco = (TextView) findViewById(R.id.profile_reco);
                    reco.setText("Recoplas: " + String.valueOf(recoplas));
                    JobListAdapter adapter = new JobListAdapter();
                    adapter.activity = ProfileActivity.this;
                    adapter.listJobs = jobList;
                    // updating listview
                    lv.setAdapter(adapter);
                }
            });

        }

    }
}
