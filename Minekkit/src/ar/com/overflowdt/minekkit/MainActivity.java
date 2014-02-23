package ar.com.overflowdt.minekkit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import ar.com.overflowdt.minekkit.acercade.AcercaDe;
import ar.com.overflowdt.minekkit.denuncia.Denuncia;
import ar.com.overflowdt.minekkit.recompensas.AllRecompensasActivity;
import ar.com.overflowdt.minekkit.util.Browser;
import ar.com.overflowdt.minekkit.wiki.WikiMain;

public class MainActivity extends Activity {
	
	AlertDialog.Builder dialogo1;
	ImageButton btnRecompensas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Buttons
	    btnRecompensas = (ImageButton) findViewById(R.id.btn_recom);


	    // view products click event
	    btnRecompensas.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View view) {
	            // Launching All products Activity
	            Intent i = new Intent(getApplicationContext(), AllRecompensasActivity.class);
	            startActivity(i);

	        }
	    });
		
		
		setearAlertDialog();
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
        case R.id.menu_acercaDe:
        	acercaDe(findViewById(R.id.menu_salir));
            break;
        case R.id.menu_salir:
            finish();
        }
        return true;
    }
	
	public void acercaDe(View view)
	{
		Intent i = new Intent(this,AcercaDe.class);
		startActivity(i);
	}

    public void verWiki(View view)
    {
        Intent i = new Intent(this,WikiMain.class);
        startActivity(i);
    }

	
	public void hacerDenuncia(View view)
	{
		Intent i = new Intent(this,Denuncia.class);
		startActivity(i);
	}

	
	public void mapa(View view)
	{
		dialogo1.show();
	}

	private void verMapa() {
        Intent i = new Intent(this,Browser.class);
		i.putExtra("direccion", "minekkit.com:8123");
		startActivity(i);
	}
	
	private void aceptar()
	{
		verMapa();
	}
	
	private void cancelar()
	{
		//Nothing
	}
	
	private void setearAlertDialog()
	{  
		dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Aviso");  
        dialogo1.setMessage("El mapa puede consumir muchos datos, se recomienda usar WiFi");
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {  
                aceptar();  
            }  
        });  
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {  
                cancelar();
            }  
        });
	}
}
