package ar.com.overflowdt.minekkit.denuncia;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import ar.com.overflowdt.minekkit.R;

public class Denuncia extends Activity {

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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
