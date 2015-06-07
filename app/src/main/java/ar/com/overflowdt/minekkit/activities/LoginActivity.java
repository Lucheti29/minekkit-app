package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ar.com.overflowdt.minekkit.MinekkitApplication;
import ar.com.overflowdt.minekkit.models.User;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import io.fabric.sdk.android.Fabric;

import org.json.JSONObject;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.ApiUrls;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

public class LoginActivity extends Activity {

    private static final String TAG = "Login";
    private EditText mUserEt;
    private EditText mPassEt;
    private Button mAceptarBn;
    private Boolean mInit = false;
    private JSONObject mRespJSON;
    private ProgressDialog mPDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Session.getInstance().ver = this.getString(R.string.version);
        SharedPreferences prefe = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        if (!prefe.getString("user", "").equals("") && !prefe.getString("pass", "").equals("")) {
            Session.getInstance().user = prefe.getString("user", "");
            Session.getInstance().pass = prefe.getString("pass", "");
            new Login().execute();
        } else {
            mInit = true;
            initializeUi();
        }

        //Google Analytics
        ((MinekkitApplication) getApplication()).getTracker(MinekkitApplication.TrackerName.APP_TRACKER);
    }

    private void initializeUi() {
        setContentView(R.layout.activity_login);
        setUi();
        setListeners();
    }

    private void setUi() {
        mUserEt = (EditText) findViewById(R.id.login_user);
        mPassEt = (EditText) findViewById(R.id.login_pass);
        mAceptarBn = (Button) findViewById(R.id.btn_aceptar_login);
    }

    private void setListeners() {
        mAceptarBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getInstance().user = String.valueOf(mUserEt.getText());
                Session.getInstance().pass = String.valueOf(mPassEt.getText());

                new Login().execute();
            }
        });
    }

    class Login extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPDialog = new ProgressDialog(LoginActivity.this);
            mPDialog.setMessage(getString(R.string.login_conectando));
            mPDialog.setIndeterminate(false);
            mPDialog.setCancelable(false);
            mPDialog.show();
        }

        protected String doInBackground(String... args) {
            HttpHandler handler = new HttpHandler();

            try {
                mRespJSON = handler.post(ApiUrls.getInstance().getLoginURL(), Session.getInstance());
                Log.d("Login", mRespJSON.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            try {
                switch (mRespJSON.getInt("success")) {
                    case -100:
                        if (!mInit) initializeUi();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_timeout), LoginActivity.this);
                        mUserEt.setText(Session.getInstance().user);
                        mPassEt.setText(Session.getInstance().pass);
                        break;
//                    case -1:
//                        if (!mInit) initializeUi();
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
                        if (!mInit) initializeUi();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_auth), LoginActivity.this);
                        break;
                    case 1:
                        Session.getInstance().avatar = mRespJSON.getString("avatar");
                        Session.getInstance().recoplas = mRespJSON.getString("recoplas");
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
                mPDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
