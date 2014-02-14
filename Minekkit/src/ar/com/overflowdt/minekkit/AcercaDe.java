package ar.com.overflowdt.minekkit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class AcercaDe extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acercade);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void cerrar(View view)
	{
		finish();
	}

}
