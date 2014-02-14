package ar.com.overflowdt.minekkit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	AlertDialog.Builder dialogo1;
	Button btnRecompensas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Buttons
	    btnRecompensas = (Button) findViewById(R.id.btnRecom);


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
        case R.id.mapa:
        	dialogo1.show();
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
		dialogo1.show();
	}

	private void verMapa() {
		Intent i = new Intent(this,Mapa.class);
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
        dialogo1.setMessage("Necesitarás una buena conexión a internet para visualizar el mapa correctamente, ¿deseas continuar?");            
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {  
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
