package ar.com.overflowdt.minekkit.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import ar.com.overflowdt.minekkit.MainActivity;
import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.Session;

public class LoginActivity extends Activity {
	
	EditText user;
	EditText pass;
	Button aceptar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		final String url = "http://minekkit.com/api/login.php";
		user= (EditText) findViewById(R.id.login_user);
		pass = (EditText) findViewById(R.id.login_pass);
		aceptar = (Button)  findViewById(R.id.btn_aceptar_login);
		
		aceptar.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View view) {

                Session.getInstance().user= String.valueOf(user.getText());
                Session.getInstance().pass= String.valueOf(pass.getText());

                HttpHandler handler = new HttpHandler();

                JSONObject resp = handler.post(url, Session.getInstance());
                try{
                    if (resp.getInt("success") == 1) {
                    } else {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }catch(Exception ex){

                }

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
	            startActivity(i);

	        }
	    });
	}
}
