package ar.com.overflowdt.minekkit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mapa:
        	verMapa();
            break;
        case R.id.salir:
            finish();
        }
        return true;
    }
	
	public void acercaDe(View view)
	{
		Intent i = new Intent(this,AcercaDe.class);
		startActivity(i);
	}
	
	public void mapa(View view)
	{
		verMapa();
	}

	private void verMapa() {
		Intent i = new Intent(this,Mapa.class);
		i.putExtra("direccion", "minekkit.com:8123");
		startActivity(i);
	}

}
