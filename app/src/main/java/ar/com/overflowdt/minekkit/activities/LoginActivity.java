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

import org.json.JSONException;
import org.json.JSONObject;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.asynkTasks.UpdateAppAsyncTask;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

public class LoginActivity extends ActionBarActivity {

    EditText user;
    EditText pass;
    Button aceptar;
    Boolean iniciado = false;
    String url = "http://minekkit.com/api/login.php";
    JSONObject resp;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
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
                resp = handler.post(url, Session.getInstance());

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
                    case -1:
                        if (!iniciado) iniciate();
                        user.setText(Session.getInstance().user);
                        pass.setText(Session.getInstance().pass);
                        ShowAlertMessage.showMessageWithOkAndCancel("Hay una nueva actualización para Minekkit App. ¿Desea instalarla?", LoginActivity.this,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogo1, int id) {
                                        new UpdateAppAsyncTask().setContext(LoginActivity.this).execute();
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogo1, int id) {
                                        LoginActivity.this.finish();
                                    }
                                });

                        break;
                    case 0:
                        if (!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_auth), LoginActivity.this);
                        break;
                    case 1:
                        SharedPreferences preferencias = getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("user", Session.getInstance().user);
                        editor.putString("pass", Session.getInstance().pass);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
                        startActivity(i);
                        finish();
                        break;
                }
                pDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
