package ar.com.overflowdt.minekkit.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class LoginActivity extends Activity {
	
	EditText user;
	EditText pass;
	Button aceptar;
    String url = "http://minekkit.com/api/login.php";
    JSONObject resp;
    ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
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
            pDialog.setMessage("Logeando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
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
            pDialog.dismiss();


            try {
                if (resp.getInt("success") == 1) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else{
                    AlertDialog.Builder notlogged = new AlertDialog.Builder(LoginActivity.this);
                    notlogged.setTitle("Aviso");
                    notlogged.setMessage("El usuario o la contraseña no son correctos. Por favor, vuelva a intentar.");
                    notlogged.setCancelable(false);
                    notlogged.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {}
                    });
                    notlogged.show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
