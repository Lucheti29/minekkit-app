package ar.com.overflowdt.minekkit.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ar.com.overflowdt.minekkit.MinekkitApplication;
import ar.com.overflowdt.minekkit.models.User;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import io.fabric.sdk.android.Fabric;

import org.json.JSONException;
import org.json.JSONObject;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.asynkTasks.UpdateAppAsyncTask;
import ar.com.overflowdt.minekkit.util.ApiUrls;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "Login";
    EditText user;
    EditText pass;
    Button aceptar;
    Boolean iniciado = false;
    JSONObject resp;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = 0;
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Session.getInstance().ver = this.getString(R.string.version);
        SharedPreferences prefe = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        if (!prefe.getString("user", "").equals("") && !prefe.getString("pass", "").equals("")) {
            Session.getInstance().user = prefe.getString("user", "");
            Session.getInstance().pass = prefe.getString("pass", "");
            new Login().execute();

        } else {
            iniciado = true;
            iniciate();
        }


    }

    private void iniciate() {
        setContentView(R.layout.activity_login);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        user = (EditText) findViewById(R.id.login_user);
        pass = (EditText) findViewById(R.id.login_pass);
        aceptar = (Button) findViewById(R.id.btn_aceptar_login);


        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Session.getInstance().user = String.valueOf(user.getText());
                Session.getInstance().pass = String.valueOf(pass.getText());

                new Login().execute();

            }
        });
    }


    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getString(R.string.login_conectando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            HttpHandler handler = new HttpHandler();


            try {
                resp = handler.post(ApiUrls.getInstance().getLoginURL(), Session.getInstance());

                Log.d("Login", resp.toString());


            } catch (Exception ex) {
                ex.printStackTrace();
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
                switch (resp.getInt("success")) {
                    case -100:
                        if (!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_timeout), LoginActivity.this);
                        user.setText(Session.getInstance().user);
                        pass.setText(Session.getInstance().pass);
                        break;
//                    case -1:
//                        if (!iniciado) iniciate();
//                        user.setText(Session.getInstance().user);
//                        pass.setText(Session.getInstance().pass);
//                        ShowAlertMessage.showMessageWithOkAndCancel(getString(R.string.actualizacion), LoginActivity.this,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialogo1, int id) {
//                                        new UpdateAppAsyncTask().withContext(LoginActivity.this).execute();
//                                    }
//                                },
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialogo1, int id) {
//                                        LoginActivity.this.finish();
//                                    }
//                                });
//
//                        break;
                    case 0:
                        if (!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_auth), LoginActivity.this);
                        break;
                    case 1:
                        Session.getInstance().avatar = resp.getString("avatar");
                        Session.getInstance().recoplas = resp.getString("recoplas");
                        Session.getInstance().saveUserData(LoginActivity.this);
                        JsonRequest postRequest = new JsonRequest(Request.Method.POST, ApiUrls.getInstance().getProfileURL(), new Response.Listener<JsonObject>() {
                            @Override
                            public void onResponse(JsonObject response) {
                                //muestro el aviso
                                User user = new Gson().fromJson(response, User.class);
                                Session.getInstance().addUserToParse(user);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Error: " + error.getMessage());
                            }

                        });
                        postRequest.setParams(Session.getInstance().getAuthParams());
                        Volley.newRequestQueue(LoginActivity.this).add(postRequest);
                        
                        Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
                        startActivity(i);
                        finish();
                        break;
                }
                pDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
