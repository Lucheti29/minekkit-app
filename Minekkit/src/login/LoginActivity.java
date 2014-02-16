package login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ar.com.overflowdt.minekkit.MainActivity;
import ar.com.overflowdt.minekkit.R;

public class LoginActivity extends Activity {
	
	TextView user;
	TextView pass;
	Button aceptar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		user= (TextView) findViewById(R.id.login_user);
		pass = (TextView) findViewById(R.id.login_pass);
		aceptar = (Button)  findViewById(R.id.btn_aceptar_login);
		
		aceptar.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View view) {
	            
	        	
	        	
	            Intent i = new Intent(getApplicationContext(), MainActivity.class);
	            startActivity(i);

	        }
	    });
	}
}
