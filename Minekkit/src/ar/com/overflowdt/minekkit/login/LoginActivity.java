package ar.com.overflowdt.minekkit.login;

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

import org.json.JSONException;
import org.json.JSONObject;

import ar.com.overflowdt.minekkit.MainActivity;
import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

public class LoginActivity extends Activity {
	
	EditText user;
	EditText pass;
	Button aceptar;
    Boolean iniciado=false;
    String url = "http://minekkit.com/api/login.php";
    JSONObject resp;
    ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.getInstance().ver=this.getString(R.string.version);
        SharedPreferences prefe=getSharedPreferences("logindata",Context.MODE_PRIVATE);
        if(!prefe.getString("user", "").equals("") && !prefe.getString("pass", "").equals(""))
        {
            Session.getInstance().user=prefe.getString("user", "");
            Session.getInstance().pass=prefe.getString("pass", "");
            new Login().execute();

        }else{
            iniciado=true;
            iniciate();
        }
    }

    private void iniciate() {
        setContentView(R.layout.login_activity);

        user= (EditText) findViewById(R.id.login_user);
        pass = (EditText) findViewById(R.id.login_pass);
        aceptar = (Button)  findViewById(R.id.btn_aceptar_login);


        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Session.getInstance().user= String.valueOf(user.getText());
                Session.getInstance().pass= String.valueOf(pass.getText());

                new Login().execute();

            }
        });
    }

    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
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


            try{
                resp = handler.post(url, Session.getInstance());

                Log.d("Login", resp.toString());


            }catch(Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products



            try {
                switch(resp.getInt("success")) {
                    case -100:
                        if(!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_timeout),LoginActivity.this);
                        break;
                    case -1:
                        if(!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_version),LoginActivity.this);
                        break;
                    case 0:
                        if(!iniciado) iniciate();
                        ShowAlertMessage.showMessage(getString(R.string.login_fallo_auth),LoginActivity.this);
                        break;
                    case 1:
                        SharedPreferences preferencias=getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferencias.edit();
                        editor.putString("user", Session.getInstance().user);
                        editor.putString("pass", Session.getInstance().pass);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
