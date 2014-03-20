package ar.com.overflowdt.minekkit.denuncia;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.Browser;
import ar.com.overflowdt.minekkit.util.MenuHandler;

public class Denuncia extends Activity {

    private EditText et_titulo, et_fecha, et_hora, et_mundo, et_ciudad, et_normas, et_solucion, et_explicacion, et_usuarios;
    private RadioButton rb_denuncia1, rb_denuncia2, rb_denuncia3, rb_denuncia4, rb_denuncia5, rb_denuncia6, rb_denuncia7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_denuncia_tabs);
		
		Resources res = getResources();
		
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		 
		//Setea la primer tab
		TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
		spec.setContent(R.id.tab1);
		//Le pone un icono a la primer tab
		spec.setIndicator("Paso 1",
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
		tabs.addTab(spec);
		
		//Setea la segunda tab
		spec=tabs.newTabSpec("mitab2");
		spec.setContent(R.id.tab2);
		//Le pone un icono a la segunda tab
		spec.setIndicator("Paso 2",
                res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		
		//Setea la segunda tab
		spec=tabs.newTabSpec("mitab3");
		spec.setContent(R.id.tab3);
		//Le pone un icono a la segunda tab
		spec.setIndicator("Paso 3",
                res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		
		//Setea la segunda tab
		spec=tabs.newTabSpec("mitab4");
		spec.setContent(R.id.tab4);
		//Le pone un icono a la segunda tab
		spec.setIndicator("Paso 4",
                res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		
		//Setea la primer tab como default
		tabs.setCurrentTab(0);

        //Eventos de los Editboxes
        et_titulo=(EditText)findViewById(R.id.et_titulo);
        et_fecha=(EditText)findViewById(R.id.et_fecha);
        et_hora=(EditText)findViewById(R.id.et_hora);
        et_mundo=(EditText)findViewById(R.id.et_mundo);
        et_ciudad=(EditText)findViewById(R.id.et_ciudad);
        rb_denuncia1=(RadioButton)findViewById(R.id.rb_denuncia1);
        rb_denuncia2=(RadioButton)findViewById(R.id.rb_denuncia2);
        rb_denuncia3=(RadioButton)findViewById(R.id.rb_denuncia3);
        rb_denuncia4=(RadioButton)findViewById(R.id.rb_denuncia4);
        rb_denuncia5=(RadioButton)findViewById(R.id.rb_denuncia5);
        rb_denuncia6=(RadioButton)findViewById(R.id.rb_denuncia6);
        rb_denuncia7=(RadioButton)findViewById(R.id.rb_denuncia7);
        et_normas=(EditText)findViewById(R.id.et_normas);
        et_normas=(EditText)findViewById(R.id.et_solucion);
        et_normas=(EditText)findViewById(R.id.et_explicacion);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.bindearLogica(item, this);
    }

    public void verReglas(View view) {
        Intent i = new Intent(this,Browser.class);
        i.putExtra("title","Reglas");
        i.putExtra("direccion", "minekkit.com/foro/showthread.php?tid=2371");
        startActivity(i);
    }

    public void crearDenuncia() {
        DenunciaInstance denuncia = new DenunciaInstance();

        denuncia.setTitulo(et_titulo.getText().toString());
        denuncia.setCiudad(et_ciudad.getText().toString());
        denuncia.setExplicacion(et_explicacion.getText().toString());
        denuncia.setSolucion(et_solucion.getText().toString());
        denuncia.setFecha(et_fecha.getText().toString());
        denuncia.setHora(et_hora.getText().toString());
        denuncia.setMundo(et_mundo.getText().toString());
        denuncia.setNormasNoCumplidas(et_normas.getText().toString());
        denuncia.setUsuariosInvolucrados(et_usuarios.getText().toString());

        String tipo;

        if(rb_denuncia1.isChecked())
        {
            tipo = rb_denuncia1.getText().toString();
        }
        else if (rb_denuncia2.isChecked())
        {
            tipo = rb_denuncia2.getText().toString();
        }
        else if (rb_denuncia3.isChecked())
        {
            tipo = rb_denuncia3.getText().toString();
        }
        else if (rb_denuncia4.isChecked())
        {
            tipo = rb_denuncia4.getText().toString();
        }
        else if (rb_denuncia5.isChecked())
        {
            tipo = rb_denuncia5.getText().toString();
        }
        else if (rb_denuncia6.isChecked())
        {
            tipo = rb_denuncia6.getText().toString();
        }
        else if (rb_denuncia7.isChecked())
        {
            tipo = rb_denuncia7.getText().toString();
        }
        else
        {
            tipo = "Sin tipo";
        }

        denuncia.setTipoDenuncia(tipo);

    }
}
